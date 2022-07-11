package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final LocalDate DATE_1 = LocalDate.of(2022, 7, 5);
    public static final LocalDate DATE_2 = LocalDate.of(2022, 7, 6);
    public static final LocalDate DATE_3 = LocalDate.of(2022, 7, 7);
    public static final LocalTime BREAKFAST_TIME = LocalTime.of(8, 0);
    public static final LocalTime LUNCH_TIME = LocalTime.of(12, 0);
    public static final LocalTime DINNER_TIME = LocalTime.of(21, 0);

    public static final int USER_MEAL_ID_1 = START_SEQ + 3;
    public static final int USER_MEAL_ID_2 = START_SEQ + 4;
    public static final int USER_MEAL_ID_3 = START_SEQ + 5;

    public static final int NOT_EXIST_MEAL_ID = 10;

    public static final Meal userMeal1 = new Meal(USER_MEAL_ID_1, LocalDateTime.of(DATE_2, BREAKFAST_TIME), "Завтрак",
            500);
    public static final Meal userMeal2 = new Meal(USER_MEAL_ID_2, LocalDateTime.of(DATE_2, LUNCH_TIME), "Обед", 700);
    public static final Meal userMeal3 = new Meal(USER_MEAL_ID_3, LocalDateTime.of(DATE_2, DINNER_TIME), "Ужин", 800);

    public static final int ADMIN_MEAL_ID_1 = START_SEQ + 6;
    public static final int ADMIN_MEAL_ID_2 = START_SEQ + 7;
    public static final int ADMIN_MEAL_ID_3 = START_SEQ + 8;
    public static final int ADMIN_MEAL_ID_4 = START_SEQ + 9;
    public static final int ADMIN_MEAL_ID_5 = START_SEQ + 10;

    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID_1, LocalDateTime.of(DATE_1, LUNCH_TIME), "Обед " +
            "предыдущего дня", 700);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_ID_2, LocalDateTime.of(DATE_2, BREAKFAST_TIME),
            "Завтрак", 500);
    public static final Meal adminMeal3 = new Meal(ADMIN_MEAL_ID_3, LocalDateTime.of(DATE_2, LUNCH_TIME), "Обед", 700);
    public static final Meal adminMeal4 = new Meal(ADMIN_MEAL_ID_4, LocalDateTime.of(DATE_2, DINNER_TIME), "Ужин", 800);

    public static final Meal adminMeal5 = new Meal(ADMIN_MEAL_ID_5, LocalDateTime.of(DATE_3, BREAKFAST_TIME),
            "Завтрак на след. день", 500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2022, 7, 9, 12, 0), "Новая еда", 155);
    }

    public static Meal getUpdated(Meal meal) {
        Meal updated = new Meal(meal);
        updated.setDateTime(LocalDateTime.of(2022, 7, 8, 15, 15));
        updated.setCalories(200);
        updated.setDescription("Обновленная еда");
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
