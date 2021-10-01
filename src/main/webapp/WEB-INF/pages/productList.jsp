<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <h1>
        Welcome to Expert-Soft training!
    </h1>
    <form>
        <input class="query" name="query" value="${param.query}"/>
        <button>Search</button>
    </form>
    <c:if test="${not empty error}">
        <div class="error">
            ${error}
        </div>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/products">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description
                    <tags:sortLink sort="description" order="asc"/>
                    <tags:sortLink sort="description" order="desc"/>
                </td>
                <td class="price">Price
                    <tags:sortLink sort="price" order="asc"/>
                    <tags:sortLink sort="price" order="desc"/>
                </td>
                <td class="quantity">Quantity</td>
            </tr>
            </thead>
            <c:forEach var="product" items="${products}" varStatus="status">
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
                    <td class="quantity">
                        <input class="quantity" name="quantity"
                               value="${not empty error && product.id eq productId ? paramValues['quantity'][status.index] : 1}"/>
                        <c:if test="${not empty error && product.id eq productId}">
                            <div class="error">
                                    ${error}
                            </div>
                        </c:if>
                        <input type="hidden" name="productId" value="${product.id}">
                    </td>
                    <td>
                        <button name="index" value="${status.index}">
                            Add to cart
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
    <jsp:include page="recentlyViewed.jsp"/>
</tags:master>