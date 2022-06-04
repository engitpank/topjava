package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Storage<Meal> storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapMealStorage();
        MealsUtil.getTemplateList().forEach(m -> storage.save(m));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String desc = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal;
        if (id == null || id.length() == 0 || Integer.parseInt(id) == Meal.EMPTY_MEAL.getId()) {
            meal = new Meal(date, desc, calories);
            storage.save(meal);
        } else {
            meal = storage.get(Integer.parseInt(id));
            meal.setDescription(desc);
            meal.setCalories(calories);
            meal.setDateTime(date);
            storage.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals.jsp");
        String action = request.getParameter("action");
        if (action == null) {
            List<MealTo> mealToList = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", mealToList);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        String receivedId = request.getParameter("id");
        int mealId = -1;
        if (receivedId != null) {
            mealId = Integer.parseInt(receivedId);
        }
        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(mealId);
                response.sendRedirect("meals");
                return;
            case "add":
                meal = Meal.EMPTY_MEAL;
                break;
            case "update":
                meal = storage.get(mealId);
                break;
            default:
                throw new IllegalArgumentException("Type " + action + " is illegal");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
    }
}
