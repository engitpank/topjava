package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService mealService;

    public MealRestController(MealService mealService) {
        this.mealService = mealService;
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return mealService.get(authUserId(), mealId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return mealService.create(authUserId(), meal);
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        mealService.delete(authUserId(), mealId);
    }

    public void update(Meal meal, int mealId) {
        log.info("update {} with mealId={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        mealService.update(authUserId(), meal);
    }

    public List<MealTo> getAll() {
        return MealsUtil.getTos(mealService.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }
}