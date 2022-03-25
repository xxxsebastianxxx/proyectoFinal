/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author ryzen3
 */
public class AdopcionBeanDao {
     JdbcTemplate jdbcTemplate;
     ConectarDB con = new ConectarDB();
     
     public List consultarAdopciones(){
       List datos = new ArrayList();
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       String sql = "select ad.id_adopcion, u.nombre, u.fotoUsuario , m.fotoMascota , m.nombre_mascota, ad.fecha_de_adopcion" +
"          from usuarios as u, mascotas as m, adopcion as ad " +
"		    where u.id_usuario = ad.usuario_id and m.id_mascotas = ad.mascotas_id;";
       datos = this.jdbcTemplate.queryForList(sql);
       
       return datos; 
     }
     public int consultarCodigoAdopcion(){
      int cod = 0;
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        String sql = "select max(id_adopcion)+1 as codigo from adopcion";
        cod = this.jdbcTemplate.queryForObject(sql,Integer.class);
        return cod;
        
     }
     
     
     
     
     
     
     
   public List consultaradopcionporid (int id_adopcion  ){
       List adop = new ArrayList();
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       String sql = "select ad.id_adopcion, u.nombre, m.nombre_mascota, ad.fecha_de_adopcion" +
"                      from usuarios as u, mascotas as m, adopcion as ad" +
"		          where ad.id_adopcion = ?" +
"                           and " +
"                             u.id_usuario = ad.usuario_id" +
"         	                  and m.id_mascotas = ad.mascotas_id;";
       adop = this.jdbcTemplate.queryForList(sql,id_adopcion  );
       
       return adop;
     }
   
   public List consultaradopcionporfecha (String fecha_de_adopcion  ){
       List adop = new ArrayList();
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       String sql = "select ad.id_adopcion, u.nombre, m.nombre_mascota, ad.fecha_de_adopcion" +
"                      from usuarios as u, mascotas as m, adopcion as ad" +
"		          where ad.fecha_de_adopcion = ?" +
"                           and " +
"                             u.id_usuario = ad.usuario_id" +
"         	                  and m.id_mascotas = ad.mascotas_id;";
       adop = this.jdbcTemplate.queryForList(sql,fecha_de_adopcion  );
       
       return adop;
     }
}
