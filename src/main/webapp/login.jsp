<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Neo PrintStudio - Iniciar Sesión</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    
    <style>
        * {
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            /* Fondo alusivo a impresión con overlay oscuro para resaltar el formulario */
            background: linear-gradient(rgba(10, 30, 50, 0.7), rgba(10, 30, 50, 0.7)), 
                        url('https://images.unsplash.com/photo-1562654501-a0ccc0fc3fb1?q=80&w=1920');
            background-size: cover;
            background-position: center;
        }

        .login-container {
            background: rgba(255, 255, 255, 0.95);
            padding: 50px 40px;
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.4);
            width: 100%;
            max-width: 420px;
            text-align: center;
            transition: transform 0.3s ease;
        }

        .login-container:hover {
            transform: translateY(-5px);
        }

        /* Logo o Nombre de la App */
        .brand-name {
            font-size: 2.2em;
            font-weight: 600;
            color: #007bff;
            margin-bottom: 5px;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        .sub-title {
            color: #6c757d;
            margin-bottom: 35px;
            font-weight: 300;
        }

        .form-group {
            margin-bottom: 25px;
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
            font-size: 0.9em;
        }

        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e9ecef;
            border-radius: 10px;
            font-size: 1em;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
            outline: none;
        }

        .btn-login {
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: white;
            border: none;
            padding: 14px;
            border-radius: 10px;
            cursor: pointer;
            font-size: 1.1em;
            font-weight: 600;
            width: 100%;
            margin-top: 10px;
            box-shadow: 0 4px 15px rgba(0, 123, 255, 0.3);
            transition: all 0.3s ease;
        }

        .btn-login:hover {
            background: linear-gradient(135deg, #0056b3, #004085);
            box-shadow: 0 6px 20px rgba(0, 123, 255, 0.4);
            transform: scale(1.02);
        }

        /* Estilo para mensajes de error */
        .error-msg {
            background-color: #ffeef0;
            color: #d93025;
            padding: 12px;
            border-radius: 8px;
            font-size: 0.85em;
            margin-bottom: 20px;
            border-left: 5px solid #d93025;
        }

        /* Animación de entrada */
        .animate-fade {
            animation: fadeIn 0.8s ease-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

    <div class="login-container animate-fade">
        <div class="brand-name">Neo Print</div>
        <div class="sub-title">Studio & Soluciones Visuales</div>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="error-msg">
                <strong>Error:</strong> <%= error %>
            </div>
        <%
            }
        %>
        
        <form action="LoginServlet" method="post">
            <input type="hidden" name="accion" value="login">
            
            <div class="form-group">
                <label for="email">Correo Electrónico</label>
                <input type="email" id="email" name="email" class="form-control" 
                       placeholder="usuario@neoprint.com" required>
            </div>

            <div class="form-group">
                <label for="password">Contraseña</label>
                <input type="password" id="password" name="password" class="form-control" 
                       placeholder="••••••••" required>
            </div>

            <button type="submit" class="btn-login">Acceder al Sistema</button>
        </form>
    </div>

</body>
</html>