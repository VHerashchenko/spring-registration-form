<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${param.lang}"/>

<!DOCTYPE html>
<html lang="${param.lang}">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="notes.page"/></title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/bootstrap-grid.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/my.css" rel="stylesheet">
</head>

<body>

<nav id="myNAV" class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
    <a class="navbar-brand" href="${contextPath}/"><fmt:message key="main" /></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".navbar-collapse" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${contextPath}/all-notes"><fmt:message key="notes.page" /> <span class="sr-only"></span></a>
            </li>
            <li class="nav-item dropdown">
                <div class="btn-group">
                    <button type="button" class="btn"><fmt:message key="locale" /></button>
                    <button type="button" class="btn dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="sr-only">Toggle Dropdown</span>
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="${contextPath}/all-notes/?lang=en"><fmt:message key="english" /></a>
                        <a class="dropdown-item" href="${contextPath}/all-notes/?lang=ru"><fmt:message key="russian" /></a>
                    </div>
                </div>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${contextPath}/welcome"><fmt:message key="welcome" /> <span class="sr-only"></span></a>
            </li>
        </ul>
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <a onclick="document.forms['logoutForm'].submit()"><fmt:message key="logout"/></a>
        </form>
    </div>
</nav>

<div class="container" style="margin-top: 55px;">
    <div class="row">
        <div class="col-10">
            <table class="table table-bordered table-hover">
                <thead class="thead-dark">
                <tr>
                    <th><fmt:message key="username"/></th>
                    <th><fmt:message key="password"/></th>
                    <th><fmt:message key="role"/></th>
                    <th><fmt:message key="actions"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${notes}" var="notes">
                    <tr>
                        <td>${notes.username}</td>
                        <td>${notes.password}</td>
                        <td>${notes.role}</td>
                        <td>
                            <form:form method="DELETE" action="${contextPath}/all-notes/${notes.id}"
                                   class="form-signin">
                            <button class="btn btn btn-danger btn-block" type="submit"><fmt:message key="delete"/></button>
                            </form:form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="container">
    <% if(null != request.getAttribute("errorMessage")){ %>
        <div class="form-group has-error">
            <span><fmt:message key="${errorMessage}"/></span>
        </div>
    <%} %>
</div>

<script src="${contextPath}/resources/js/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.js"></script>
</body>
</html>
