drop database if exists Refugio_de_tu_mejor_Amigo;

create database Refugio_de_tu_mejor_Amigo;

use Refugio_de_tu_mejor_Amigo;

drop table if exists usuarios;
create table usuarios (
id_usuario int primary key auto_increment,
nombre varchar (40) not null,
apellido varchar (40) not null,
tipo_de_identificacion varchar (40) not null,
identificacion int not null,
direccion varchar (50) not null,
fotoUsuario varchar (200),
fotoOld varchar (200),
limiteadopcion int(11)
);  
 
drop table if exists mascotas;
create table mascotas (
id_mascotas int primary key auto_increment,
nombre_mascota varchar (50) not null,
raza varchar (30) not null,
genero varchar (40) not null,
tipo_de_mascotas varchar (40) not null,
vacunas varchar (10) not null,
fotoMascota varchar (200),
fotoOld varchar (200),
is_adopted  bit(1) DEFAULT 0
);

drop table if exists adopcion;
create table adopcion(
id_adopcion int primary key auto_increment,
usuario_id int not null,
mascotas_id int not null,
fecha_de_adopcion varchar (40) not null
);



--OPCINAL 

--insert into usuarios (nombre,apellido,tipo_de_identificacion,identificacion,direccion)
-- values("alva majo","rivera rincon ","cedula",32453345,"santa rita"),
	--   ("martha ","garcia  ","cedula",23847239,"bermeo");
	   
	   
--insert into mascotas (nombre_mascota,raza,genero,tipo_de_mascotas,vacunas)
  --            values ("firulais","criollo","macho","perro","si"),
	--				  ("lucy","ladrador","hembra","perro","no"),
		--			  ("luna","gato persa","hembra","gato","si");
					  
 insert into adopcion (usuario_id,mascotas_id,fecha_de_adopcion)
 values(2,2,"20/03/2022"),
	  (4,2,"11/03/2022");--
	   	   	   
	