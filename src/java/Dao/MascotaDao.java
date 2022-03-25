/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.MascotasBean;
import models.UsuarioBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author ryzen3
 */
public class MascotaDao {
     JdbcTemplate jdbcTemplate;
     ConectarDB con = new ConectarDB();
     
     
      public void borrarImagenMascotas (String fotomascotas, String deletePath, int id_mascotas){
      this.jdbcTemplate = new JdbcTemplate(con.conDB());
      final String DELETE_DIRECTORY = "..\\..\\web\\";
      String deleteFile = deletePath + DELETE_DIRECTORY + fotomascotas;
      System.out.print(deleteFile);
      File borrar = new File(deleteFile); 
      if(borrar.delete()){
        String sql ="delete from mascotas where id_mascotas = ? ";
        jdbcTemplate.update(sql,id_mascotas);
    }else {
         System.out.print("no se pudo borrar....");
      }
  
     }
      
      public void actMascotasSinfoto(MascotasBean masBean, List lista){
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       ArrayList<String> listados = new ArrayList<>();
         for(int i = 0; i < lista.size(); i++){
            FileItem fileItem = (FileItem) lista.get(i);
            listados.add(fileItem.getString());
         }
         masBean.setNombre_mascota(listados.get(0));
         masBean.setRaza(listados.get(1));
         masBean.setGenero(listados.get(2));
         masBean.setTipo_de_mascotas(listados.get(3));
         masBean.setVacunas(listados.get(4));
         //------------------------sql------------------------------//
         String sql = "update mascotas set nombre_mascota = ?, raza = ?, genero = ?, tipo_de_mascotas = ?, vacunas = ? where id_mascotas = ? ";
         jdbcTemplate.update(sql, masBean.getNombre_mascota(),masBean.getRaza(),
                 masBean.getGenero(),masBean.getTipo_de_mascotas(),
                 masBean.getVacunas(),masBean.getId_mascotas());
     } 
      
      
      
      private static final String UPLOAD_DIRECTORY = "..\\..\\web\\images\\photos";
   private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
   private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
   
   
   
   private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;
   
   public void actMascotasConImagen (MascotasBean masBean,
           boolean isMultipart,
            HttpServletRequest request,
            List items){
      this.jdbcTemplate = new JdbcTemplate(con.conDB());
      ArrayList<String> listados = new ArrayList<>();
      if(isMultipart){
          DiskFileItemFactory file = new DiskFileItemFactory();
          file.setSizeThreshold(MEMORY_THRESHOLD);
          file.setRepository(new File(System.getProperty("java.io.tmpdir")));
          ServletFileUpload fileUpload = new ServletFileUpload(file);
          fileUpload.setFileSizeMax(MAX_FILE_SIZE);
          fileUpload.setSizeMax(MAX_REQUEST_SIZE);
          String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
          String deletePath = request.getServletContext().getRealPath("") + File.separator;
          File uploadDir = new File(uploadPath);
          if(!uploadDir.exists()){
             uploadDir.mkdir();
          }
          for(int i = 0; i < items.size(); i++){
           FileItem fileItem = ( FileItem ) items.get(i);
           if(!fileItem.isFormField()){
             String fileName = new File(fileItem.getName()).getName();
             String filePath = uploadPath + File.separator + "ID-" + listados.get(0) + "-" + fileName;
             File uploadFile = new File(filePath);
             String nameFile = ("images/photos/" + "ID-" + listados.get(0) + "-" + fileName );
             try{
               borraImagenActualizada(masBean.getFotoOld(), deletePath);
               uploadFile.delete();
               fileItem.write(uploadFile);
               masBean.setFotoMascota(nameFile);
             }catch(Exception e){
              System.out.print("escritura...." + e.getMessage());
             }
           }else{
             listados.add(fileItem.getString());
           }
          }
         masBean.setNombre_mascota(listados.get(0));
         masBean.setRaza(listados.get(1));
         masBean.setGenero(listados.get(2));
         masBean.setTipo_de_mascotas(listados.get(3));
         masBean.setVacunas(listados.get(4));
      }
      String sql = "update mascotas set nombre_mascota = ?, raza = ?, genero = ?, tipo_de_mascotas = ?, vacunas = ?, fotoMascota = ?, fotoOld = ?  where id_mascotas = ?";
         jdbcTemplate.update(sql, masBean.getNombre_mascota(),masBean.getRaza(),
                 masBean.getGenero(),masBean.getTipo_de_mascotas(),
                 masBean.getVacunas(),masBean.getFotoMascota(),masBean.getFotoMascota(), masBean.getId_mascotas());
   }
   
   
   
   
   
   
   public void borraImagenActualizada(String fotoMascota, String deletePath){
     final String DELETE_DIRECTORY = "..\\..\\web\\";
     
     String deleteFile = deletePath + DELETE_DIRECTORY + fotoMascota;
     File borrar = new File(deleteFile);
     if(borrar.delete()){
       System.out.print("borrado");
     }else{
       System.out.print("nose pudo borrado");
     }
   }
    
      
      
      
      
      
      
      
      
     
     //-------------------mostrar los clientes-----------------------------------
     
     public List consultarMascotasadopcion(){
        List datos = new ArrayList();
             this.jdbcTemplate = new JdbcTemplate(con.conDB());
             String sql = "select id_mascotas, nombre_mascota from mascotas where is_adopted = 0";
             datos = this.jdbcTemplate.queryForList(sql);
            return datos; 
       }
     
   public MascotasBean consultarmascotasporid (int id  ){
       MascotasBean ub = new MascotasBean();
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       String sql = "select * from mascotas where id_mascotas  = " + id;
       
       return (MascotasBean) this.jdbcTemplate.query(
                sql, (ResultSet rs) -> {
                    if (rs.next()) {
                        ub.setId_mascotas(rs.getInt("id_mascotas"));
                        ub.setNombre_mascota(rs.getString("nombre_mascota"));
                        ub.setRaza(rs.getString("raza"));
                        ub.setGenero(rs.getString("genero"));
                        ub.setTipo_de_mascotas(rs.getString("tipo_de_mascotas"));
                        ub.setVacunas(rs.getString("vacunas"));
                        ub.setFotoMascota(rs.getString("fotoMascota"));
                    }
                    return ub;
                });
     }  
   
   
   public List consultarmascotaspornombre ( String nombreMascota  ){
       List mas = new ArrayList();
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       String sql = "select * from mascotas where nombre_mascota  = ? ";
       mas = this.jdbcTemplate.queryForList(sql,nombreMascota  );
       
       return mas;
     } 
   
   public List consultarmascotasporraza ( String raza  ){
       List mas = new ArrayList();
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       String sql = "select * from mascotas where raza  = ? ";
       mas = this.jdbcTemplate.queryForList(sql,raza  );
       
       return mas;
     }
   public List consultarmascotasporgenero ( String genero  ){
       List mas = new ArrayList();
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       String sql = "select * from mascotas where genero  = ? ";
       mas = this.jdbcTemplate.queryForList(sql,genero  );
       
       return mas;
     }
   public List consultarmascotasportipodemascota ( String tipo_de_mascotas  ){
       List mas = new ArrayList();
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       String sql = "select * from mascotas where tipo_de_mascotas  = ? ";
       mas = this.jdbcTemplate.queryForList(sql,tipo_de_mascotas  );
       
       return mas;
   } 
       public List consultarmascotasporvacunas ( String vacunas  ){
       List mas = new ArrayList();
       this.jdbcTemplate = new JdbcTemplate(con.conDB());
       String sql = "select * from mascotas where vacunas  = ? ";
       mas = this.jdbcTemplate.queryForList(sql,vacunas  );
       
       return mas;
     } 
}
