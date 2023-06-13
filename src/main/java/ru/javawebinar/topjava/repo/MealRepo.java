package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealRepo {

    Meal get(int id);

    List<Meal> getAll();

    Meal save(Meal meal);

    boolean delete(int id);

}
