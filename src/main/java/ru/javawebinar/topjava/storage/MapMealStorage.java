package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapMealStorage implements Storage<Meal> {
    private static final ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Meal meal) {
        int key = getKeyIfNotExist(meal.getId());
        storage.put(key, meal);
    }

    @Override
    public void update(Meal meal) {
        int key = getKeyIfExist(meal.getId());
        storage.replace(key, meal);
    }

    @Override
    public Meal get(int id) {
        int key = getKeyIfExist(id);
        return storage.get(key);
    }

    @Override
    public void delete(int id) {
        int key = getKeyIfExist(id);
        storage.remove(key);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    private boolean isExist(int id) {
        return storage.containsKey(id);
    }

    private int getKeyIfExist(int id) {
        if (!isExist(id)) {
            throw new RuntimeException("Meal " + id + " doesn't exist");
        }
        return id;
    }

    private int getKeyIfNotExist(int id) {
        if (isExist(id)) {
            throw new RuntimeException("Meal " + id + " already exist");
        }
        return id;
    }
}
