package ru.javawebinar.topjava.storage;


import java.util.List;

public interface Storage<T> {

    void clear();

    void save(T t);

    void update(T t);

    T get(int id);

    void delete(int id);

    List<T> getAll();

    int size();
}
