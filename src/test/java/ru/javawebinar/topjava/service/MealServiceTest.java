package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertMatch(service.get(USER_MEAL_ID_1, USER_ID), userMeal1);
    }

    @Test
    public void otherUserGet() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_2, ADMIN_ID));
    }

    @Test
    public void notExistGet() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXIST_MEAL_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID_1, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID_1, USER_ID));
    }

    @Test
    public void otherUserDelete() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID_2, ADMIN_ID));
    }

    @Test
    public void notExistDelete() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXIST_MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(DATE_1, DATE_2, ADMIN_ID);
        assertMatch(betweenInclusive, adminMeal4, adminMeal3, adminMeal2, adminMeal1);
    }

    @Test
    public void getBetweenInclusiveByNull() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(null, null, ADMIN_ID);
        assertMatch(betweenInclusive, adminMeal5, adminMeal4, adminMeal3, adminMeal2, adminMeal1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void update() {
        service.update(getUpdated(userMeal2), USER_ID);
        Meal updatedMeal = getUpdated(userMeal2);
        assertMatch(service.get(USER_MEAL_ID_2, USER_ID), updatedMeal);
    }

    @Test
    public void otherUserUpdate() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(userMeal3), ADMIN_ID));
    }

    @Test
    public void notExistUpdate() {
        assertThrows(NotFoundException.class, () -> service.update(new Meal(NOT_EXIST_MEAL_ID,
                LocalDateTime.of(DATE_1, BREAKFAST_TIME), "не лежащая в БД еда", 200), USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(new Meal(LocalDateTime.of(DATE_2,
                BREAKFAST_TIME), "еда" + "дубликат", 200), USER_ID));
    }
}