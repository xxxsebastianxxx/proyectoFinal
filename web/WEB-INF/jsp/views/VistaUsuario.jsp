<%-- 
    Document   : VistaUsuario
    Created on : 15/02/2022, 06:16:43 PM
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
          <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script> 
        <link href="public/css/index.css" rel="stylesheet" type="text/css"/>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>vista usuario</title>
    </head>
    <body>
        
        
          <c:if test="${empty param.nombre}">
            <c:redirect url="formUsuario.htm">
                <c:param name="error" value="nombre obligatorio"></c:param>
            </c:redirect>
        </c:if> 
           <c:if test="${empty param.apellido}">
            <c:redirect url="formUsuario.htm">
                <c:param name="error" value="apellido obligatorio"></c:param>
            </c:redirect>
        </c:if> 
           <c:if test="${empty param.tipo_de_identificacion}">
            <c:redirect url="formUsuario.htm">
                <c:param name="error" value="tipo de identificacion obligatorio"></c:param>
            </c:redirect>
        </c:if> 
           <c:if test="${empty param.identificacion}">
            <c:redirect url="formUsuario.htm">
                <c:param name="error" value="identificacion obligatorio"></c:param>
            </c:redirect>
        </c:if> 
          <c:if test="${empty param.direccion}">
            <c:redirect url="formUsuario.htm">
                <c:param name="error" value="direccion obligatorio"></c:param>
            </c:redirect>
        </c:if>
        <%@include file="../views/caebezera.jsp" %>
        <h4 style="font-family: cursive;
                   padding: 18px;
                   text-align: center;
                   color: black;">
           usuario guardado!
        </h4>
       <div style=" padding-left: 17rem; 
                    padding-right: 17rem;
                    margin-bottom: 10.7rem;">
        <table class="table table-dark table-striped">
            <thead>
              <tr>
                <th scope="col">nombre</th>
                <th scope="col">apellido</th>
                <th scope="col">tipo de identificacion</th>
                <th scope="col">identificacion</th>
                <th scope="col">direccion</th>
              </tr>
            </thead>
              <tbody>
               
              <tr>
                  <td><c:out value="${nombre}"></c:out></td>
                  <td><c:out value="${apellido}"></c:out></td>
                  <td><c:out value="${tipo_de_identificacion}"></c:out></td>
                  <td><c:out value="${identificacion}"></c:out></td>
                 <td><c:out value="${direccion}"></c:out></td>
              </tr>
            </tbody>
          </table>
              <a class="btn btn-primary" href="formUsuario.htm">atras</a>
        </div>
      <%@include file="../views/footer.jsp" %>   
    </body>
</html>
