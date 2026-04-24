<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Создание мероприятия - DonorTrack</title>
    </head>
    <body>
    <div class="container">
        <div class="header">
            <h1>Создание мероприятия</h1>
            <p>Организуйте донорскую акцию и спасайте жизни</p>
        </div>

        <div class="form-container">
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ❌ ${error}
                </div>
            </c:if>

            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    ✅ ${success}
                </div>
            </c:if>

            <form action="/events/create" method="post">
                <div class="form-group">
                    <label for="title">
                        Название мероприятия<span class="required">*</span>
                    </label>
                    <input type="text"
                           id="title"
                           name="title"
                           required
                           maxlength="200"
                           placeholder="Например: День донора в центре крови"
                           value="${eventDto.title}">
                    <div class="help-text">Краткое и понятное название (до 200 символов)</div>
                </div>

                <div class="form-group">
                    <label for="description">
                        Описание<span class="required">*</span>
                    </label>
                    <textarea id="description"
                              name="description"
                              required
                              placeholder="Опишите мероприятие: что нужно знать участникам, какие документы взять с собой, особенности...">${eventDto.description}</textarea>
                    <div class="help-text">Подробное описание поможет участникам подготовиться</div>
                </div>

                <div class="form-group">
                    <label for="eventDate">
                        Дата и время проведения<span class="required">*</span>
                    </label>
                    <input type="datetime-local"
                           id="eventDate"
                           name="eventDate"
                           required
                           value="${eventDto.eventDate}">
                    <div class="help-text">Выберите удобные дату и время для донации</div>
                </div>

                <div class="form-group">
                    <label for="address">
                        Адрес проведения<span class="required">*</span>
                    </label>
                    <input type="text"
                           id="address"
                           name="address"
                           required
                           placeholder="Город, улица, дом, отделение переливания крови"
                           value="${eventDto.address}">
                    <div class="help-text">Укажите точный адрес пункта сдачи крови</div>
                </div>

                <div class="form-group">
                    <label for="maxParticipants">
                        Максимальное количество участников
                    </label>
                    <input type="number"
                           id="maxParticipants"
                           name="maxParticipants"
                           min="1"
                           max="1000"
                           placeholder="Оставьте пустым для неограниченного количества"
                           value="${eventDto.maxParticipants}">
                    <div class="help-text">Ограничение поможет организовать поток доноров (опционально)</div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Отправить на модерацию</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">↩Отмена</button>
                </div>
            </form>

            <a href="<c:url value='/home'/>&" class="back-link">← Вернуться на главную</a>
        </div>
    </div>

    </body>
</html>