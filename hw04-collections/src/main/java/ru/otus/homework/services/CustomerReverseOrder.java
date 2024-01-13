package ru.otus.homework.services;

import ru.otus.homework.models.Customer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomerReverseOrder {

    private Deque<Customer> customers = new LinkedList<>();

    public void add(Customer customer) {
        customers.add(customer);
    }

    public Customer take() {
        return customers.pollLast();
    }
}
