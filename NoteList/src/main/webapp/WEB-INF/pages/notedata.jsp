<%--
  Created by IntelliJ IDEA.
  User: Knout
  Date: 10.09.2018
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Заметка ${note.id}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
    ID заметки: ${note.id} <br/>
    <h1>${note.noteHeader} <br/></h1>
    <h3>Текст заметки:</h3>
    <br/>
    <p>${note.noteText}</p>
</body>
</html>
