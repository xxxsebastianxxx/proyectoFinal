<%-- 
    Document   : ListarMascotas
    Created on : 24/02/2022, 08:46:30 AM
    Author     : ryzen3
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

             <script src="js/newjavascript.js" type="text/javascript"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.11.4/css/dataTables.bootstrap4.min.css"> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>ADOPCION</title>
    </head>
    <body>        
        <%@include file="../views/caebezera.jsp" %>
        <br>
        <br>
        <br>
        <h4 style="text-align: center; color: greenyellow;" >
            ADOPCION!
        </h4>
        <div style="padding-left:8rem; padding-right: 8rem; padding-top: 3rem; padding-bottom:8rem;"> 
        <table class="table table-striped table-bordered " id="dtmascotas" style="whidth: 100%">
            <thead>
                <a href="formRegistarAdopcion.htm" class="btn btn-primary">Adoptar Mascota</a> 
              <tr>
                <th scope="col">CODIGO DE ADOPCION</th>
                <th scope="col">NOMBRE</th>
                 <th scope="col">foto usuario</th>
                <th scope="col">MASCOTA</th>
                <th scope="col">foto mascota</th>
                <th scope="col">fecha de adopcion</th>
                <th scope="col">accion</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${adopcion}" var="datos">
              <tr>
                  <td><c:out value="${datos.id_adopcion}"></c:out></td>
                  <td><c:out value="${datos.nombre}"></c:out></td>
                  <td><img style="height: 50px; width: 55px;" alt="tu foto :(" src="<c:out value="${datos.fotoUsuario}"></c:out>"/></td>
                  <td><c:out value="${datos.nombre_mascota}"></c:out></td>
                  <td><img style="height: 50px; width: 55px;" alt="tu foto :(" src="<c:out value="${datos.fotoMascota}"></c:out>"/></td>
                  <td><c:out value="${datos.fecha_de_adopcion}"></c:out></td>
                  <td> <a href="deleteAdopcion.htm?id_adopcion=${datos.id_adopcion}" class="btn btn-danger">borrar</a> 
                  /    <a href="updateadopcion.htm?id_adopcion=${datos.id_adopcion}" class="btn btn-warning">editar</a></td>
              </tr>
          </c:forEach>
            </tbody>
           
          </table>
              <a class="btn btn-primary" href="index.htm">atras</a>
        </div>
      <%@include file="../views/footer.jsp" %>
      
      
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.4/js/dataTables.bootstrap4.min.js"></script>
        
        <script>   
           $(document).ready(function() {
                $('#dtmascotas').DataTable();
            } );
        </script>
        
    </body>
</html>