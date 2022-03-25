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
        
        <title>vista mascotas</title>
    </head>
    <body>        
        <%@include file="../views/caebezera.jsp" %>
        <br>
        <br>
        <br>
        <h4 style="text-align: center; color:greenyellow ;">
           listado de mascotas!
        </h4>
        <div style="padding-left:8rem; padding-right: 8rem; padding-top: 3rem; padding-bottom:8rem;"> 
        <table class="table table-striped table-bordered " id="dtmascotas" style="whidth: 100%">
            <thead>
                <a href="formMascota.htm" class="btn btn-primary">add Mascota</a> 
              <tr>
                <th scope="col">id</th>
                <th scope="col">nombre</th>
                <th scope="col">raza</th>
                <th scope="col">genero</th>
                <th scope="col">tipo de animal</th>
                <th scope="col">vacunas</th>
                <th scope="col">foto de la mascota</th>
                <th scope="col">accion</th>
                
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${mascotas}" var="mascotas">
              <tr>
                  <td><c:out value="${mascotas.id_mascotas}"></c:out></td>
                  <td><c:out value="${mascotas.nombre_mascota}"></c:out></td>
                  <td><c:out value="${mascotas.raza}"></c:out></td>
                  <td><c:out value="${mascotas.genero}"></c:out></td>
                  <td><c:out value="${mascotas.tipo_de_mascotas}"></c:out></td>
                  <td><c:out value="${mascotas.vacunas}"></c:out></td>
                  <td> <img style="height: 70px; width: 67px; border:  1px solid #000; border-radius: 50%; margin-left: 60px;" alt="tu foto :(" src="<c:out value="${mascotas.fotoMascota}"></c:out>"/> </td>
                  <td> <a href="deleteMascota.htm?id_mascotas=${mascotas.id_mascotas}&fotoMascota=${mascotas.fotoMascota}" class="btn btn-danger">borrar</a> 
                  /    <a href="updateMascota.htm?id_mascotas=${mascotas.id_mascotas}&fotoOld=${mascotas.fotoMascota}" class="btn btn-warning">editar</a>
                      <a href="formRegistarAdopcion.htm?id=${mascotas.id_mascotas}" class="btn btn-primary mt-3 rounded-pill" style="border-radius: 13px;">Adoptar</a> 
                                   
                  </td>
              </tr>
              </c:forEach>
            </tbody>
          </table>
              <a class="btn btn-prymary" href="formMascota.htm">atras</a>
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