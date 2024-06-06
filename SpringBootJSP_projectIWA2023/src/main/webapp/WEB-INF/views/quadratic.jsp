<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Calcul Quadratic</title>
</head>
<body>
<h1>${message}</h1>

<h2>Quadratic function</h2>
<form:form method="post" action="calculQuadratic.html" modelAttribute="quadratic">
    <form:label path="a">Coeficient a : </form:label>
    <form:input path="a" />
    <br>
    <form:label path="b">Coeficient b : </form:label>
    <form:input path="b" />
    <br>
    <form:label path="c">Coeficient c : </form:label>
    <form:input path="c" />
    <br>
    <input type="submit" value="Calculate" />

</form:form>
</body>
</html>