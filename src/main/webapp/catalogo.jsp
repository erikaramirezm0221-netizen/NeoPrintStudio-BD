<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.mycompany.app_impreciones.modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Catálogo Neo PrintStudio</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        :root {
            --primary: #007bff;
            --secondary: #6c757d;
            --success: #28a745;
            --warning: #ffc107;
            --dark: #212529;
            --light: #f8f9fa;
        }

        body {
            font-family: 'Poppins', sans-serif;
            margin: 0;
            background-color: #f4f7f6;
            color: var(--dark);
        }

        .navbar {
            background: white;
            padding: 1rem 5%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            position: sticky;
            top: 0;
            z-index: 1000;
        }

        .brand {
            font-size: 1.5rem;
            font-weight: 600;
            color: var(--primary);
            text-decoration: none;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .nav-links a {
            color: var(--dark);
            text-decoration: none;
            margin-left: 20px;
            transition: 0.3s;
        }

        .nav-links a:hover { color: var(--primary); }

        .cart-icon {
            background: var(--light);
            padding: 8px 15px;
            border-radius: 50px;
            border: 1px solid #ddd;
        }

        .hero {
            text-align: center;
            padding: 60px 20px;
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: white;
            margin-bottom: 40px;
        }

        .container { max-width: 1200px; margin: 0 auto; padding: 0 20px; }

        .productos-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 30px;
            padding-bottom: 50px;
        }

        .producto-card {
            background: white;
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 10px 20px rgba(0,0,0,0.05);
            transition: 0.3s;
            display: flex;
            flex-direction: column;
            border: 1px solid rgba(0,0,0,0.05);
        }

        .producto-card:hover { transform: translateY(-10px); box-shadow: 0 15px 30px rgba(0,0,0,0.1); }

        .img-container { width: 100%; height: 220px; overflow: hidden; background: #eee; }

        .producto-card img { width: 100%; height: 100%; object-fit: cover; transition: 0.5s; }

        .producto-card:hover img { transform: scale(1.1); }

        .info-container { padding: 20px; text-align: center; flex-grow: 1; display: flex; flex-direction: column; }

        .descripcion { font-size: 0.85rem; color: var(--secondary); margin-bottom: 15px; height: 40px; overflow: hidden; }

        .precio { font-size: 1.5rem; color: var(--primary); font-weight: 600; margin-bottom: 15px; }

        .btn-add {
            background: var(--warning);
            color: var(--dark);
            border: none;
            padding: 12px;
            border-radius: 12px;
            font-weight: 600;
            cursor: pointer;
            width: 100%;
            transition: 0.3s;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .btn-add:hover { background: #e0a800; }
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

<nav class="navbar">
    <a href="CatalogoServlet" class="brand"><i class="fas fa-print"></i> Neo PrintStudio</a>
    <div class="nav-links">
        <span>Hola, <strong>${usuario.nombre}</strong></span>
        <a href="CarritoServlet" class="cart-icon">
            <i class="fas fa-shopping-cart"></i> Carrito
        </a>
        <a href="LoginServlet?accion=cerrarSesion" title="Salir">
            <i class="fas fa-sign-out-alt"></i>
        </a>
    </div>
</nav>

<div class="hero">
    <h1>Soluciones Visuales de Alta Calidad</h1>
    <p>Personaliza tus ideas con la mejor tecnología de impresión</p>
</div>

<div class="container">
    <c:choose>
        <c:when test="${not empty requestScope.productos}">
            <div class="productos-grid">
                <c:forEach var="p" items="${requestScope.productos}">
                    <div class="producto-card">
                        <div class="img-container">
                            <img src="${p.imagenUrl}" 
                                 alt="${p.nombre}" 
                                 onerror="this.src='https://via.placeholder.com/400x300?text=Imagen+No+Disponible'">
                        </div>

                        <div class="info-container">
                            <div>
                                <h3>${p.nombre}</h3>
                                <p class="descripcion">${p.descripcion}</p>
                            </div>
                            <div>
                                <p class="precio">
                                    <fmt:formatNumber value="${p.precio}" type="currency" currencySymbol="$" />
                                </p>
                                <form action="CarritoServlet" method="post">
                                    <input type="hidden" name="idProducto" value="${p.id}">
                                    <input type="hidden" name="accion" value="agregar">
                                    <button type="submit" class="btn-add">
                                        <i class="fas fa-plus-circle"></i> Agregar al Carrito
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div style="text-align: center; padding: 50px;">
                <i class="fas fa-box-open fa-3x"></i>
                <p>No hay productos disponibles.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>