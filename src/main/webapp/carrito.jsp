<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.mycompany.app_impreciones.modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Mi Carrito - Neo PrintStudio</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        :root {
            --primary: #007bff;
            --danger: #dc3545;
            --success: #28a745;
            --light: #f8f9fa;
            --dark: #212529;
        }

        body {
            font-family: 'Poppins', sans-serif;
            margin: 0;
            background-color: #f0f2f5;
        }

        .header {
            background: white;
            padding: 1rem 5%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .header h1 { font-size: 1.5rem; color: var(--primary); margin: 0; }

        .header a { text-decoration: none; color: var(--dark); margin-left: 20px; transition: 0.3s; }

        .header a:hover { color: var(--primary); }

        .main-content { max-width: 1100px; margin: 40px auto; padding: 0 20px; }

        .cart-container { background: white; border-radius: 20px; padding: 30px; box-shadow: 0 10px 30px rgba(0,0,0,0.05); }

        .table-carrito { width: 100%; border-collapse: collapse; margin-bottom: 30px; }

        .table-carrito th { text-align: left; padding: 15px; border-bottom: 2px solid #eee; color: #888; font-size: 0.8rem; }

        .table-carrito td { padding: 20px 15px; border-bottom: 1px solid #eee; vertical-align: middle; }

        .img-thumb { width: 70px; height: 70px; object-fit: cover; border-radius: 12px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }

        .product-name { font-weight: 600; }

        .btn-eliminar {
            background: #fff0f1;
            color: var(--danger);
            border: none;
            padding: 8px 12px;
            border-radius: 8px;
            cursor: pointer;
            transition: 0.3s;
        }

        .btn-eliminar:hover { background: var(--danger); color: white; }

        .checkout-section { display: flex; justify-content: flex-end; margin-top: 30px; }

        .total-box { background: #f8f9fa; padding: 30px; border-radius: 20px; width: 350px; text-align: right; border: 1px solid #eee; }

        .total-amount { display: block; font-size: 2.2rem; font-weight: 600; color: var(--primary); margin: 10px 0 20px 0; }

        .btn-checkout {
            background: linear-gradient(135deg, var(--success), #218838);
            color: white;
            padding: 15px;
            border-radius: 12px;
            font-weight: 600;
            text-decoration: none;
            display: block;
            text-align: center;
            transition: 0.3s;
        }

        .btn-checkout:hover { transform: translateY(-3px); }
    </style>
</head>
<body>

<%
    Usuario usuarioLogeado = (Usuario) session.getAttribute("usuarioLogeado");
    if (usuarioLogeado == null) {
        response.sendRedirect("login.jsp");
        return; 
    }
    request.setAttribute("usuario", usuarioLogeado);
%>

<div class="header">
    <h1><i class="fas fa-shopping-bag"></i> Mi Carrito</h1>
    <div>
        <span><i class="fas fa-user-circle"></i> ${usuario.nombre}</span>
        <a href="CatalogoServlet"><i class="fas fa-th-large"></i> Catálogo</a>
        <a href="LoginServlet?accion=cerrarSesion"><i class="fas fa-sign-out-alt"></i></a>
    </div>
</div>

<div class="main-content">
    <c:choose>
        <c:when test="${not empty sessionScope.carritoCompras}">
            <div class="cart-container">
                <table class="table-carrito">
                    <thead>
                        <tr>
                            <th>Producto</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th>Subtotal</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="totalAcumulado" value="${0.0}" />
                        <c:forEach var="item" items="${sessionScope.carritoCompras}" varStatus="loop">
                            <tr>
                                <td style="display: flex; align-items: center; gap: 15px;">
                                    <img src="${item.producto.imagenUrl}" class="img-thumb" 
                                         onerror="this.src='https://via.placeholder.com/100?text=Neo'">
                                    <span class="product-name">${item.producto.nombre}</span>
                                </td>
                                <td><fmt:formatNumber value="${item.producto.precio}" type="currency" currencySymbol="$" /></td>
                                <td><span style="background: #e9ecef; padding: 5px 12px; border-radius: 8px;">${item.cantidad}</span></td>
                                <td style="font-weight: 600; color: var(--primary);">
                                    <fmt:formatNumber value="${item.subTotal}" type="currency" currencySymbol="$" />
                                </td>
                                <td style="text-align: right;">
                                    <form action="CarritoServlet" method="post">
                                        <input type="hidden" name="idItem" value="${loop.index}">
                                        <input type="hidden" name="accion" value="eliminar">
                                        <button type="submit" class="btn-eliminar"><i class="fas fa-trash-alt"></i></button>
                                    </form>
                                </td>
                            </tr>
                            <c:set var="totalAcumulado" value="${totalAcumulado + item.subTotal}" />
                        </c:forEach>
                    </tbody>
                </table>
                
                <div class="checkout-section">
                    <div class="total-box">
                        <h3>Total del Pedido</h3>
                        <span class="total-amount"><fmt:formatNumber value="${totalAcumulado}" type="currency" currencySymbol="$" /></span>
                        <a href="CheckoutServlet" class="btn-checkout"><i class="fas fa-lock"></i> Finalizar Pedido</a>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div style="text-align: center; padding: 100px 20px;">
                <i class="fas fa-shopping-cart fa-5x" style="color: #ddd;"></i>
                <h2>Tu carrito está vacío</h2>
                <a href="CatalogoServlet" class="btn-checkout" style="display: inline-block; padding: 12px 40px; background: var(--primary);">Ir al Catálogo</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>