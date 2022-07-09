package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID = UserTestData.USER_ID;
    public static final int ADMIN_ID = UserTestData.ADMIN_ID;

    public static final LocalDate YESTERDAY = LocalDate.of(2022, 7, 5);
    public static final LocalDate TODAY = LocalDate.of(2022, 7, 6);
    public static final LocalDate TOMORROW = LocalDate.of(2022, 7, 7);
    public static final LocalDateTime BREAKFAST_DT = LocalDateTime.of(TODAY, LocalTime.of(8, 0));
    public static final LocalDateTime LUNCH_DT = LocalDateTime.of(TODAY, LocalTime.of(12, 0));
    public static final LocalDateTime DINNER_DT = LocalDateTime.of(TODAY, LocalTime.of(21, 0));

    public static final int USER_BREAKFAST_ID = START_SEQ + 4;
    public static final int USER_LUNCH_ID = START_SEQ + 5;
    public static final int USER_DINNER_ID = START_SEQ + 6;

    public static final Meal USER_BREAKFAST = new Meal(USER_BREAKFAST_ID, BREAKFAST_DT, "Завтрак", 500);
    public static final Meal USER_LUNCH = new Meal(USER_LUNCH_ID, LUNCH_DT, "Обед", 700);
    public static final Meal USER_DINNER = new Meal(USER_DINNER_ID, DINNER_DT, "Ужин", 800);

    public static final int ADMIN_BREAKFAST_ID = START_SEQ + 7;
    public static final int ADMIN_LUNCH_ID = START_SEQ + 8;
    public static final int ADMIN_DINNER_ID = START_SEQ + 9;
    public static final int ADMIN_TOMORROW_BREAKFAST_ID = START_SEQ + 10;
    public static final int ADMIN_YESTERDAY_LUNCH_ID = START_SEQ + 11;


    public static final Meal ADMIN_BREAKFAST = new Meal(ADMIN_BREAKFAST_ID, BREAKFAST_DT, "Завтрак", 500);
    public static final Meal ADMIN_LUNCH = new Meal(ADMIN_LUNCH_ID, LUNCH_DT, "Обед", 700);
    public static final Meal ADMIN_DINNER = new Meal(ADMIN_DINNER_ID, DINNER_DT, "Ужин", 800);
    public static final Meal ADMIN_YESTERDAY_LUNCH = new Meal(ADMIN_YESTERDAY_LUNCH_ID, LocalDateTime.of(YESTERDAY, LocalTime.of(13, 0)), "Обед предыдущего дня", 700);
    public static final Meal ADMIN_TOMORROW_BREAKFAST = new Meal(ADMIN_TOMORROW_BREAKFAST_ID, LocalDateTime.of(TOMORROW, LocalTime.of(8, 0)), "Завтрак на след. день", 500);

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
