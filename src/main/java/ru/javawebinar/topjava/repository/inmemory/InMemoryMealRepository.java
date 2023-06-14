package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.mealsUserFirst) {
            save(meal, 1);
        }
        for (Meal meal : MealsUtil.mealsUserSecond) {
            save(meal, 2);
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            return repository.computeIfAbsent(
                    userId,
                    mealsToCreate -> new ConcurrentHashMap<>()).put(meal.getId(), meal);
        } else {
            Map<Integer, Meal> userMeals = repository.get(userId);
            userMeals.computeIfPresent(meal.getId(), (id, exMeal) -> meal);
            return meal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals.containsKey(id) && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        return usersMeals == null ? null : usersMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filteredByPredicate(meal -> true, userId);
    }

    @Override
    public List<Meal> filteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return filteredByPredicate(meal -> DateTimeUtil.isBetweenClosed(meal.getDate(), startDate, endDate), userId);
    }

    private List<Meal> filteredByPredicate(Predicate<Meal> filter, int userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        if (usersMeals != null) {
            return usersMeals
                    .values()
                    .stream()
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
