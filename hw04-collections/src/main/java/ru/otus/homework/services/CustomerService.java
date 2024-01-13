package ru.otus.homework.services;

import ru.otus.homework.models.Customer;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private TreeMap<Customer, String> customers = new TreeMap<>(new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return (int) (o1.getScores() - o2.getScores());
        }
    });


    public Map.Entry<Customer, String> getSmallest() {
        return Optional.ofNullable(customers.firstEntry())
                .map(a -> Map.entry(new Customer(a.getKey()), a.getValue()))
                .orElse(null);
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
//        return null; // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return Optional.ofNullable(customers.higherEntry(customer))
                .map(a -> Map.entry(new Customer(a.getKey()), a.getValue()))
                .orElse(null);
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
