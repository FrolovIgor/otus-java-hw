package ru.otus.ioc.appcontainer;

import org.reflections.Reflections;
import ru.otus.ioc.appcontainer.api.AppComponent;
import ru.otus.ioc.appcontainer.api.AppComponentsContainer;
import ru.otus.ioc.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.ioc.appcontainer.exceptions.ComponentNotFoundException;
import ru.otus.ioc.appcontainer.exceptions.DuplicateComponentNameException;
import ru.otus.ioc.appcontainer.exceptions.SeveralComponentCandidatesException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<Class<?>, Object> configurationObjects = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfigDecalarations(checkAndGetDeclaredMethods(initialConfigClass));
    }

    public AppComponentsContainerImpl(String packageName) {
        var reflections = new Reflections(packageName);
        var configClasses = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class);
        var componentDeclarations = configClasses.stream()
                .flatMap(this::checkAndGetDeclaredMethods);
        processConfigDecalarations(componentDeclarations);
    }

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
        var componentDeclarations = Arrays.stream(initialConfigClasses)
                .flatMap(this::checkAndGetDeclaredMethods);
        processConfigDecalarations(componentDeclarations);
    }

    private Stream<Method> checkAndGetDeclaredMethods(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...
        return Arrays.stream(configClass.getDeclaredMethods());
    }

    private void processConfigDecalarations(Stream<Method> methods) {
        methods.filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
                .forEach(this::processComponentConfiguration);
    }


    private void processComponentConfiguration(Method method) {
        try {
            var configurationObject = getConfigurationObject(method);
            var args = Arrays.stream(method.getParameterTypes()).map(
                    this::getAppComponent
            ).toArray();
            var nameFromAnnotation = method.getAnnotation(AppComponent.class).name();
            var componentName = (!nameFromAnnotation.isBlank()) ? nameFromAnnotation : method.getName();
            var component = (method.getParameterCount() == 0) ? method.invoke(configurationObject) : method.invoke(configurationObject, args);
            if (appComponentsByName.containsKey(componentName)) {
                throw new DuplicateComponentNameException();
            }
            appComponentsByName.put(componentName, component);
            appComponents.add(component);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getConfigurationObject(Method method) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var configClass = method.getDeclaringClass();
        return Optional.ofNullable(configurationObjects.get(configClass))
                .orElseGet(() -> {
                    try {
                        var configorationObject = configClass.getConstructor().newInstance();
                        configurationObjects.put(configClass, configorationObject);
                        return configorationObject;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        var componentCandidates = appComponents.stream()
                .filter(component ->
                        component.getClass().equals(componentClass) ||
                        Arrays.asList(component.getClass().getInterfaces()).contains(componentClass)).toList();

        if (componentCandidates.isEmpty()) throw new ComponentNotFoundException();
        if (componentCandidates.size() > 1) throw new SeveralComponentCandidatesException();

        return (C) componentCandidates.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) Optional.ofNullable(appComponentsByName.get(componentName)).orElseThrow(ComponentNotFoundException::new);
    }
}
