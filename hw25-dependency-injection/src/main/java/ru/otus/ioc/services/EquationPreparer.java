package ru.otus.ioc.services;


import ru.otus.ioc.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
