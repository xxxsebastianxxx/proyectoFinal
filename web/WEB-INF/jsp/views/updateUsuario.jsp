<%-- 
    Document   : formUsuario
    Created on : 14/02/2022, 08:38:33 AM
    Author     : ryzen3
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
         <script src="js/newjavascript.js" type="text/javascript"></script>
          <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script> 
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>actualizar usuario</title>
    </head>
    <body>
        <%@include file="../views/caebezera.jsp" %>
         <h4 style="font-family: cursive;
                   padding: 18px;
                   text-align: center;
                   color: black;">
            actualizar usuario!
        </h4>
        <hr>
        <div style=" 
                     padding-left: 22rem; 
                     padding-right: 22rem;
                     margin-bottom: 10.7rem;">
            
             <c:if test="${not empty param.error}">
                    <div class="alert alert-danger">
                        <strong>Error   </strong><c:out value="${param.error}"></c:out><br>                     
                    </div>
                </c:if>
             <form:form commandName="usuario" method="POST"cssClass="navbar-form" enctype="multipart/form-data">
              <div>
                <form:label  style="margin-bottom: 7px;" path="nombre" cssClass="input-group-adddon">nombre</form:label> 
                   
                <br>
                <form:input path="nombre" cssClass="form-control"></form:input>
                <form:errors path="nombre" element="div" cssClass="btn btn-warning"></form:errors>
            </div><br>
             <div >
                <form:label style="margin-bottom: 7px;" path="apellido" cssClass="input-group-adddon">apellido usuario</form:label> 
               <br>
                <form:input path="apellido" cssClass="form-control"></form:input>
                <form:errors path="apellido" element="div" cssClass="btn btn-warning"></form:errors>
            </div><br>
             <div >
                <form:label style="margin-bottom: 7px;" path="tipo_de_identificacion" cssClass="input-group-adddon">tipo identificacion </form:label> 
              <br>
                <form:input path="tipo_de_identificacion" cssClass="form-control"></form:input>
                <form:errors path="tipo_de_identificacion" element="div" cssClass="btn btn-warning"></form:errors>
            </div><br>
            <div >
                <form:label style="margin-bottom: 7px;" path="identificacion" cssClass="input-group-adddon">identificacion</form:label> 
               <br>
                <form:input path="identificacion" cssClass="form-control"></form:input>
                 <form:errors path="identificacion" element="div" cssClass="btn btn-warning"></form:errors>
            </div><br>
             <div >
                <form:label style="margin-bottom: 7px;" path="direccion" cssClass="input-group-adddon">direccion</form:label> 
              <br>
                <form:input path="direccion" cssClass="form-control"></form:input>
                 <form:errors path="direccion" element="div" cssClass="btn btn-warning"></form:errors>
            </div><br>
            <div >
                <form:label style="margin-bottom: 7px;" path="fotoUsuario" cssClass="input-group-adddon">foto:</form:label> 
              <br>
                <img style="height: 50px; width: 55px;" alt="tu foto :(" src="<c:out value="${usuario.fotoOld}"></c:out>"/>
                <form:input path="fotoUsuario" type="file" cssClass="form-control"></form:input>
                
            </div><br>
            
                <form:button name="enviar" class="btn btn-warning">EDITAR</form:button>
                <a class="btn btn-primary " href="index.htm" role="button">atras</a>
        </form:form>
                
               
        </div>
        
        <%@include file="../views/footer.jsp" %>    
    </body>
</html>
