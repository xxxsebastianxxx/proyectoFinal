<%-- 
    Document   : caebezera
    Created on : 14/02/2022, 10:41:35 AM
    Author     : ryzen3
--%> 

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.4/js/dataTables.bootstrap4.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css"></link>
        <link href="https://cdn.datatables.net/1.11.4/css/dataTables.bootstrap4.min.css"></link>
       <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        
      
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div style="position: fixed;  width: 100%;z-index: 2;">
            <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #0dcaf0;" >
            <div class="container-fluid">
              <a class="navbar-brand" href="PrincipalADMIN.htm">INICIO</a>
              <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                  <li class="nav-item dropdown" style="display: flex">
                                <a class="nav-link" style="padding-right: 0px" href="ListarUsuario.htm">USUARIOS</a><a style="padding-left: 0px"class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-bs-toggle="dropdown">:</a>
                                 
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                    
                                    <li><a class="dropdown-item" href="consultarUsuarioxNombre.htm">Consultar usuario por Nombre</a></li>
                                    <li><a class="dropdown-item" href="consultarUsuarioxapellido.htm">Consultar usuario por apellido</a></li>
                                    <li><a class="dropdown-item" href="consultarUsuarioxtipoid.htm">Consultar usuario por tipo  id</a></li>
                                    <li><a class="dropdown-item" href="consultarUsuarioxidentificacion.htm">Consultar usuario por identificacion</a></li>
                                    <li><a class="dropdown-item" href="consultarUsuarioxdireccion.htm">Consultar usuario por direccion</a></li>
                                </ul>
                    </li>
                  </li>
                  <li class="nav-item dropdown" style="display: flex">
                                <a class="nav-link" style="padding-right: 0px" href="ListarMascotas.htm">MASCOTAS</a><a style="padding-left: 0px"class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-bs-toggle="dropdown">:</a>
                                 
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                 
                                    <li><a class="dropdown-item" href="consultarMascotaXNombre.htm">Consultar por nombre mascota</a></li>
                                    <li><a class="dropdown-item" href="consultarMascotaXRaza.htm">Consultar mascota por raza</a></li>
                                    <li><a class="dropdown-item" href="consultarMascotaXGenero.htm">Consultar mascota por genero</a></li>
                                    <li><a class="dropdown-item" href="consultarMascotaXTipodemascotas.htm">Consultar mascota por tipo de mascotas</a></li>
                                    <li><a class="dropdown-item" href="consultarMascotaXVacunas.htm">Consultar mascota por vacunas</a></li>
                                </ul>
                    </li>
                     <li class="nav-item dropdown" style="display: flex">
                                <a class="nav-link" style="padding-right: 0px" href="adoptarMascota.htm">ADOPCION</a><a style="padding-left: 0px"class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-bs-toggle="dropdown">:</a>
                                 
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                    <li><a class="dropdown-item" href="consultarAdopcionXId.htm">Consultar adopcion ID</a></li>
                                    <li><a class="dropdown-item" href="consultarAdopcionXFecha.htm">Consultar fecha de adopcion</a></li>
                                    
                                </ul>
                    </li>
                  </li>
                
                </ul>
              </div>
            </div>
          </nav>
      </div>
    </body>
</html>
