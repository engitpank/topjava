package ru.javawebinar.topjava.storage;


import java.util.List;

public interface Storage<T> {
    T save(T t);

    T update(T t);

    T get(int id);

    void delete(int id);

    List<T> getAll();
}
