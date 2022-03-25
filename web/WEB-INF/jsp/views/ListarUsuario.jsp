<%-- 
    Document   : ListarUsuario
    Created on : 24/02/2022, 08:01:59 AM
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
        <title>vista usuario</title>
    </head>
    <body >
  
        <%@include file="../views/caebezera.jsp"%>
        
        <h4 style="text-align: center; color:greenyellow ;">
            listado usuario 
        </h4>

           <div  style="padding-left:8rem; padding-right: 8rem; padding-top: 3rem; padding-bottom:8rem;">
               <table class="table table-striped table-bordered " id="dtusuarios"  >
                <thead>
                    
                   <a href="formUsuario.htm" class="btn btn-primary">add Usuario</a>
                    <tr>
                        <th>id</th>
                        <th>nombre</th>
                        <th>apellido</th>
                        <th>tipo de indentificacion</th>
                        <th>identificacion</th>
                        <th>direccion</th>
                        <th>Foto del Usuario</th>
                        <th>accion</th>
                       
                    </tr>
                </thead>
                <tbody>
                 <c:forEach items="${usuarios}" var="usuarios">
                    <tr>
                        <td><c:out value="${usuarios.id_usuario}"></c:out></td>
                        <td><c:out value="${usuarios.nombre}"></c:out></td>
                        <td><c:out value="${usuarios.apellido}"></c:out></td>
                        <td><c:out value="${usuarios.tipo_de_identificacion}"></c:out></td>
                        <td><c:out value="${usuarios.identificacion}"></c:out></td>
                        <td><c:out value="${usuarios.direccion}"></c:out></td>
                        <td> <img style="height: 50px; width: 55px;" alt="tu foto :(" src="<c:out value="${usuarios.fotoUsuario}"></c:out>"/> </td>
                        <td>
                            <a href="updateUsuario.htm?id_usuario=${usuarios.id_usuario}&fotoOld=${usuarios.fotoUsuario}"
                               class="btn btn-warning">editar</a> /
                            <a href="deleteUsuario.htm?id_usuario=${usuarios.id_usuario}&fotoUsuario=${usuarios.fotoUsuario}"
                              class="btn btn-danger">borrar</a>
                       </td>
                    </tr>
                  </c:forEach>  
                </tbody>
               
            </table>
            
        </div>    
        
               
        
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.4/js/dataTables.bootstrap4.min.js"></script>
        
        <script>   
           $(document).ready(function() {
                $('#dtusuarios').DataTable({
                    "lengtMenu":[[5, 10, 20, 50 , -1],[5,10,20,50,"All"]]
                });
            } );
        </script>
        
    
      <%@include file="../views/footer.jsp" %>   
    </body>
</html>

