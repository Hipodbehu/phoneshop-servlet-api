<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Advanced Search">
    <h1>
        Advanced search
    </h1>
    <form action="${pageContext.servletContext.contextPath}/products/advancedSearch" method="post">
        <label>Description</label>
        <input name="description" value="${param.description}"/>
        <select name="searchType">
            <c:forEach var="searchType" items="${searchTypes}">
                <option>${searchType}</option>
            </c:forEach>
        </select><br/><br/>
        <label>Min price</label>
        <input name="minPrice" value="${param.minPrice}"/><br/>
        <c:if test="${not empty errors['minPrice']}">
            <div class="error">
            ${errors['minPrice']}
            </div>
        </c:if><br/>
        <label>Max price</label>
        <input name="maxPrice" value="${param.maxPrice}"/><br/>
        <c:if test="${not empty errors['maxPrice']}">
            <div class="error">
                ${errors['maxPrice']}
            </div>
        </c:if><br/>
        <button>Search</button>
    </form>
    <c:if test="${not empty error}">
        <div class="error">
                ${error}
        </div>
    </c:if>
    <c:if test="${not empty products}">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
            </tr>
            </thead>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>
                        <img class="product-tile" src="${product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                ${product.description}
                        </a>
                    </td>
                    <td class="price">
                        <a href="${pageContext.servletContext.contextPath}/products/history/${product.id}">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <jsp:include page="recentlyViewed.jsp"/>
</tags:master>