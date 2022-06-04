<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="post" action="meals">
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <input type="hidden" name="id" value="${meal.id}">
    <label>
        Description: <input type="text" name="description" value="${meal.description}">
    </label>
    <label>
        Calories: <input type="text" name="calories" value="${meal.calories}">
    </label>
    <label>
        Date: <input type="datetime-local" name="date" value="${meal.dateTime}">
    </label>
    <br>
    <button type="submit">Отправить</button>
</form>
</body>
</html>