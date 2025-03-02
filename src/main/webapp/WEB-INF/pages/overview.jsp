<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Product List">
    <h1>
        Order overview
    </h1>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
            <td class="quantity">Quantity</td>
        </tr>
        </thead>
        <c:forEach var="item" items="${order.cartItemList}" varStatus="status">
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
                        ${item.quantity}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td/>
            <td/>
            <td>Subtotal:</td>
            <td class="price">
                <fmt:formatNumber value="${order.subtotal}" type="currency"
                                  currencySymbol="${order.cartItemList[0].product.currency.symbol}"/>
            </td>
        </tr>
        <tr>
            <td/>
            <td/>
            <td>Delivery cost:</td>
            <td class="price">
                <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                  currencySymbol="${order.cartItemList[0].product.currency.symbol}"/>
            </td>
        </tr>
        <tr>
            <td/>
            <td/>
            <td>Total cost:</td>
            <td class="price">
                <fmt:formatNumber value="${order.totalCost}" type="currency"
                                  currencySymbol="${order.cartItemList[0].product.currency.symbol}"/>
            </td>
        </tr>
    </table>
    <h2>Your info</h2>
    <table>
        <thead>
        <tags:orderOverviewRow name="firstName" label="First name" order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="lastName" label="Last name" order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="phone" label="Phone" order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="deliveryDate" label="Delivery date" order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="deliveryAddress" label="Delivery adress"
                               order="${order}"></tags:orderOverviewRow>
        <tags:orderOverviewRow name="paymentMethod" label="Payment method" order="${order}"></tags:orderOverviewRow>
        </thead>
    </table>
</tags:master>