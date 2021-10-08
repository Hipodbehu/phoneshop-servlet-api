<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>

<body class="product-list">
  <header>
    <a href="${pageContext.servletContext.contextPath}/products">
      <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
      PhoneShop
    </a><br/>
    <a class="cart" href="${pageContext.servletContext.contextPath}/cart">
      Cart:
    </a>
    <span class="cart">
      ${cart.totalQuantity} items
    </span>
  </header>
  <main>
    <jsp:doBody/>
  </main>
  <footer>
    Copyright Expert-soft
  </footer>
</body>
</html>