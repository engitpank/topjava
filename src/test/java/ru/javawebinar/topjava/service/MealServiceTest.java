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

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

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
        assertMatch(service.get(USER_BREAKFAST_ID, USER_ID), USER_BREAKFAST);
    }

    @Test
    public void delete() {
        service.delete(USER_BREAKFAST_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_BREAKFAST_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(YESTERDAY, TODAY, ADMIN_ID);
        assertMatch(betweenInclusive, ADMIN_DINNER, ADMIN_LUNCH, ADMIN_BREAKFAST, ADMIN_YESTERDAY_LUNCH);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, USER_DINNER, USER_LUNCH, USER_BREAKFAST);
    }

    @Test
    public void update() {
        Meal updated = getUpdated(USER_LUNCH);
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_LUNCH_ID, USER_ID), updated);
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
        assertThrows(DataAccessException.class, () -> service.create(new Meal(BREAKFAST_DT, "еда дубликат", 200), USER_ID));
    }

    @Test
    public void otherUserDelete() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_LUNCH_ID, ADMIN_ID));
    }

    @Test
    public void otherUserUpdate() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(USER_DINNER), ADMIN_ID));
    }

    @Test
    public void otherUserGet() {
        assertThrows(NotFoundException.class, () -> service.get(USER_LUNCH_ID, ADMIN_ID));
    }
}