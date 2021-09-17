<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<body>
<c:if test="${not empty sessionScope.recentlyViewed}">
    <h2>Recently Viewed</h2>
    <table>
        <tr>
            <c:forEach var="product" items="${sessionScope.recentlyViewed}">
                <td>
                    <img class="product-tile" src="${product.imageUrl}"/><br/>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a><br/>
                    <a class="price"
                       href="${pageContext.servletContext.contextPath}/products/history/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </c:forEach>
        </tr>
    </table>
</c:if>
</body>
</html>
