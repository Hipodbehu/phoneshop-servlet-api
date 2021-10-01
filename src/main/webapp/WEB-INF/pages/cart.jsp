<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <h1>
        Cart
    </h1>
    <c:if test="${not empty param.message}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty errors}">
        <div class="error">
            There were errors updating cart
        </div>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
                <td class="quantity">Quantity</td>
            </tr>
            </thead>
            <c:forEach var="item" items="${cart.cartItemList}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile" src="${item.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                                ${item.product.description}
                        </a>
                    </td>
                    <td class="price">
                        <a href="${pageContext.servletContext.contextPath}/products/history/${item.product.id}">
                            <fmt:formatNumber value="${item.product.price}" type="currency"
                                              currencySymbol="${item.product.currency.symbol}"/>
                        </a>
                    </td>
                    <td class="quantity">
                        <input class="quantity" name="quantity"
                               value="${not empty errors[item.product.id] ? paramValues['quantity'][status.index] : item.quantity}">
                        <c:if test="${not empty errors[item.product.id]}">
                            <div class="error">
                                    ${errors[item.product.id]}
                            </div>
                        </c:if>
                        <input type="hidden" name="productId" value="${item.product.id}">
                    </td>
                    <td>
                        <button form="deleteCartItemForm"
                                formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${item.product.id}">
                            Remove
                        </button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td>Total:</td>
                <td/>
                <td class="price">
                    <fmt:formatNumber value="${cart.totalCost}" type="currency"
                                      currencySymbol="${cart.cartItemList[0].product.currency.symbol}"/>
                </td>
                <td class="quantity"><fmt:formatNumber value="${cart.totalQuantity}"/>
                </td>
            </tr>
        </table>
        <button>Update</button>
    </form>
    <form id="deleteCartItemForm" method="post"/>
    <jsp:include page="recentlyViewed.jsp"/>
</tags:master>