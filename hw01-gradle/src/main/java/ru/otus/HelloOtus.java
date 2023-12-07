package ru.otus;

import com.google.common.base.Splitter;
import java.util.List;
import java.util.logging.Logger;

public class HelloOtus {
    static Logger log = Logger.getLogger(HelloOtus.class.getName());

    public static void main(String[] args) {

        String input = "Red ,,Green ,Blue, Yellow , Black,,";
        List<String> colors = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(input);

        colors.forEach(x -> log.info(x));
    }
}
