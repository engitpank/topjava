package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
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
        storage = new MemoryMealStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String desc = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(date, desc, calories);
        if (id == null || id.length() == 0) {
            log.debug("create meal");
            storage.create(meal);
        } else {
            log.debug("update meal");
            meal.setId(Integer.valueOf(id));
            storage.update(meal);
        }
        log.debug("redirect to /meals");
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals.jsp");
        String action = request.getParameter("action");
        Meal meal;
        switch (action == null ? "all" : action) {
            case "add":
                log.debug("add-action");
                meal = MealsUtil.EMPTY_MEAL;
                break;
            case "update":
                log.debug("update-action");
                meal = storage.get(Integer.parseInt(request.getParameter("id")));
                break;
            case "delete":
                log.debug("delete-action");
                storage.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                return;
            default:
                log.debug("get all meals");
                List<MealTo> mealToList = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("meals", mealToList);
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
    }
}
