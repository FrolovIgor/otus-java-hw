package ru.otus.dataprocessor;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ru.otus.model.Measurement;

import static java.util.stream.Collectors.*;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        // группирует выходящий список по name, при этом суммирует поля value
        return data.stream()
                .collect(groupingBy(Measurement::name,
                        TreeMap::new,
                        mapping(Measurement::value, summingDouble(x -> x))));
    }
}
