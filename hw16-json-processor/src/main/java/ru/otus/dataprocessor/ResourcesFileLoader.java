package ru.otus.dataprocessor;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper objectMapper;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            var str = Files.readString(Paths.get(classloader.getResource(fileName).toURI()));
            return List.of(objectMapper.readValue(str, Measurement[].class));
        } catch (Exception e) {
            throw new FileProcessException(e);
        }
    }


}
