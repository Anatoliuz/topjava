package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final Meal userMeal1 = new Meal(START_SEQ + 3,
            LocalDateTime.of(2023, Month.JANUARY, 29, 11, 0),
            "User 1 Завтрак", 300);
    public static final Meal userMeal2 = new Meal(START_SEQ + 4,
            LocalDateTime.of(2023, Month.JANUARY, 29, 19, 1),
            "User 1 Ужин", 410);
    public static final Meal userMeal3 = new Meal(START_SEQ + 5,
            LocalDateTime.of(2023, Month.JANUARY, 29, 19, 2),
            "User 1 Ужин 2", 3000);

    public static final Meal userMeal4 = new Meal(START_SEQ + 6,
            LocalDateTime.of(2023, Month.JANUARY, 30, 10, 0),
            "User 1 Завтрак", 500);
    public static final Meal userMeal5 = new Meal(START_SEQ + 7,
            LocalDateTime.of(2023, Month.JANUARY, 30, 20, 0),
            "User 1 Ужин 1", 3000);
    public static final Meal userMeal6 = new Meal(START_SEQ + 8,
            LocalDateTime.of(2023, Month.JANUARY, 30, 22, 10),
            "User 1 Ужин 2", 410);

    public static final Meal userMeal7 = new Meal(START_SEQ + 9,
            LocalDateTime.of(2023, Month.JANUARY, 31, 10, 0),
            "User 1 Завтрак", 1000);
    public static final Meal userMeal8 = new Meal(START_SEQ + 10,
            LocalDateTime.of(2023, Month.JANUARY, 31, 20, 0),
            "User 1 Ужин", 410);

    public static final Meal adminMeal1 = new Meal(START_SEQ + 11,
            LocalDateTime.of(2023, Month.JANUARY, 29, 1, 1),
            "Admin Ночной Перекус", 100);
    public static final Meal adminMeal2 = new Meal(START_SEQ + 12,
            LocalDateTime.of(2023, Month.JANUARY, 29, 13, 30),
            "Admin Обед", 500);
    public static final Meal adminMeal3 = new Meal(START_SEQ + 13,
            LocalDateTime.of(2023, Month.JANUARY, 29, 19, 30),
            "Admin Ужин", 2000);
    public static final Meal adminMeal4 = new Meal(START_SEQ + 14,
            LocalDateTime.of(2023, Month.JANUARY, 30, 9, 0),
            "Admin Завтрак", 440);
    public static final Meal adminMeal5 = new Meal(START_SEQ + 15,
            LocalDateTime.of(2023, Month.JANUARY, 30, 13, 0),
            "Admin Обед", 1000);
    public static final Meal adminMeal6 = new Meal(START_SEQ + 16,
            LocalDateTime.of(2023, Month.JANUARY, 31, 0, 0),
            "Admin Еда на граничное значение", 100);
    public static final Meal adminMeal7 = new Meal(START_SEQ + 17,
            LocalDateTime.of(2023, Month.JANUARY, 31, 13, 0),
            "Admin Обед", 3000);

    public static final int NOT_FOUND = 10;

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2023, Month.FEBRUARY, 21, 10, 0, 0), "New meal", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(adminMeal5);
        updated.setDescription("Updated meal description");
        updated.setCalories(500);
        updated.setDateTime(LocalDateTime.of(2023, Month.MARCH, 22, 0, 0));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}