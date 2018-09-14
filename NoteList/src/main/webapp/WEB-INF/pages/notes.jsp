<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<%--
  Created by IntelliJ IDEA.
  User: Knout
  Date: 10.09.2018
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <title>Список заметок</title>

</head>
<body>
<a href="../../index.jsp">Back to main menu</a>
<br/>
<br/>
<h1>Добавить заметку</h1>

<c:url var="addAction" value="/notes/add"/>

<form:form action="${addAction}" commandName="note" acceptCharset="utf-8">
    <table>

        <c:if test="${!empty note.noteHeader}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="noteHeader">
                    <spring:message text="Заголовок:"/>
                </form:label>
            </td>
            <td>
                <form:input path="noteHeader"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="noteText">
                    <spring:message text="Текст заметки:"/>
                </form:label>
            </td>
            <td>
                <form:input path="noteText"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${!empty note.noteHeader}">
                    <input type="submit"
                           value="<spring:message text="Edit note"/>"/>
                </c:if>
                <c:if test="${empty note.noteHeader}">
                    <input type="submit"
                           value="<spring:message text="Add note"/>"/>
                </c:if>
            </td>
        </tr>
    </table>

</form:form>

<h1>Поиск в записках по строке</h1>
<form method="post" action="/notes/filter">
    <input type="text" name="filter">
    <button type="submit">Найти</button>
</form>



<h1>Список заметок</h1>
<c:if test="${empty listNotes}">
    В заметках пока ничего нет!
</c:if>
<c:if test="${!empty listNotes}">
<ol>
    <c:forEach items="${listNotes}" var="note">
    <li> <a href="/notedata/${note.id}" target="_blank">
        <c:if test="${note.noteHeader == ''}">
            ${note.noteText}
        </c:if>
        <c:if test="${note.noteHeader != null}">
            ${note.noteHeader}
        </c:if>
    </a>
            <%--<a href="<c:url value='/edit/${note.id}'/>">Edit</a>
            <a href="<c:url value='/remove/${note.id}'/>">Delete</a>--%>
        <form method="post" action="/edit/${note.id}">
            <input type="hidden" name="edit">
            <button type="submit">Изменить</button>
        </form>
        <form method="post" action="/remove/${note.id}">
            <input type="hidden" name="remove">
            <button type="submit">Удалить</button>
        </form>
    </li>
    </c:forEach>
</ol>

</c:if>

</body>
</html>
