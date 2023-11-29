package ru.otus;

import com.google.common.base.Splitter;

import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {

        String input = "Red ,,Green ,Blue, Yellow , Black,,";
        List<String> colors = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings()
                .splitToList(input);

        colors.forEach(System.out::println);
    }
}
