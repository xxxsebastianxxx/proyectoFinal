/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import Dao.ConectarDB;
import Dao.MascotaDao;
import Dao.UsuarioDao;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.MascotaValidation;
import models.MascotasBean;
import models.UsuarioBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ryzen3
 */
@Controller


public class MascotasController {
    private JdbcTemplate jdbcTemplate;
    private MascotaValidation mostararDatos;

    public MascotasController() {
        this.mostararDatos = new MascotaValidation();
        ConectarDB con = new ConectarDB();
        jdbcTemplate = new JdbcTemplate(con.conDB());
    }
    
    
     @RequestMapping("ListarMascotas.htm")
        public ModelAndView formMascota(){
         ModelAndView mav = new ModelAndView();
         String sql = "select * from mascotas where is_adopted = 0";
         List datosm = jdbcTemplate.queryForList(sql);
         mav.addObject("mascotas",datosm);
         mav.setViewName("views/ListarMascotas");
         return mav;
    }
        
        
    
     @RequestMapping(value = "formMascota.htm", method = RequestMethod.GET)
     public ModelAndView addmascotas(){
      ModelAndView mav = new ModelAndView();
      mav.addObject("mascotas", new MascotasBean());
      mav.setViewName("views/formMascota");
      return mav;
     }
     
      private static final String UPLOAD_DIRECTORY = "..\\..\\web\\images\\photos";
        private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
        private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
        private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

  @RequestMapping(value = "formMascota.htm", method = RequestMethod.POST)
     public ModelAndView addMascotas(MascotasBean mas , HttpServletRequest request){
     ModelAndView mav = new ModelAndView();  
     boolean isMultipart = ServletFileUpload.isMultipartContent(request);
     ArrayList<String> listados = new ArrayList<>();
     if(isMultipart){
       DiskFileItemFactory file = new DiskFileItemFactory();
       file.setSizeThreshold(MEMORY_THRESHOLD);
       file.setRepository(new File(System.getProperty("java.io.tmpdir")));
       ServletFileUpload fileUpload = new ServletFileUpload(file);
       fileUpload.setFileSizeMax(MAX_FILE_SIZE);
       String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
       File uploadDir = new File(uploadPath);
       if(!uploadDir.exists()){
           uploadDir.mkdir();
       }
       List<FileItem> items = null;
       try{
         items = fileUpload.parseRequest(request);
       }catch(FileUploadException ex){
         System.out.print("carga..." + ex.getMessage());
       }
       for(int i = 0; i < items.size(); i++){
         FileItem fileItem = (FileItem) items.get(i);
         
         if(!fileItem.isFormField()){
           String fileName = new File(fileItem.getName()).getName();
           String filePath = uploadPath + File.separator + "ID-" + listados.get(0) + "-" + fileName;
           File uploadFile = new File(filePath);
           String nameFile = ("images/photos/" + "ID-" + listados.get(0) + "-" + fileName );
           
           try{
             fileItem.write(uploadFile);
             mas.setFotoMascota(nameFile);
             mas.setFotoOld(nameFile);
           }catch(Exception e){
             System.out.print("escriba..." + e.getMessage());
           }
         }else{
          listados.add(fileItem.getString());
         }
       }
       mas.setNombre_mascota(listados.get(0));
       mas.setRaza(listados.get(1));
       mas.setGenero(listados.get(2));
       mas.setTipo_de_mascotas(listados.get(3));
       mas.setVacunas(listados.get(4));
     }
     
     String sql ="insert into mascotas(nombre_mascota,raza,genero,tipo_de_mascotas,"
             + "vacunas,fotoMascota, fotoOld, is_adopted )values(?,?,?,?,?,?,?,0)";
     jdbcTemplate.update(sql, mas.getNombre_mascota(), mas.getRaza(),mas.getGenero(),
             mas.getTipo_de_mascotas(),mas.getVacunas(), mas.getFotoMascota(), mas.getFotoOld());
     mav.setViewName("redirect:/ListarMascotas.htm");
     return mav;  
    }
        
        
//___________________BORRAR MASCOTA-------------------------//
            @RequestMapping("deleteMascota.htm")  
            public ModelAndView deleteMascota(  HttpServletRequest req){
            ModelAndView mav = new ModelAndView();
            MascotaDao masDao = new MascotaDao();
            int id_mascotas = Integer.parseInt(req.getParameter("id_mascotas"));
            MascotasBean masb = masDao.consultarmascotasporid(id_mascotas);
            String deletePath = req.getServletContext().getRealPath("") + File.separator;
            String fotomascotas = req.getParameter("fotomascotas");
            masDao.borrarImagenMascotas(masb.getFotoMascota(), deletePath,id_mascotas );
            mav.setViewName("redirect:/ListarMascotas.htm");
            return mav;
            }
     
     
     
       @RequestMapping(value="updateMascota.htm" ,method = RequestMethod.GET)
        public ModelAndView actMascota(HttpServletRequest req){
         ModelAndView mav = new ModelAndView();
         int id_mascotas = Integer.parseInt(req.getParameter("id_mascotas"));
         String fotoOld = req.getParameter("fotoOld");
         MascotasBean mas = consultarMascotasxid(id_mascotas); 
         mas.setFotoOld(fotoOld);
         mav.addObject("mascotas",mas);
         mav.setViewName("views/updateMascota");
         return mav;
    }
        
        
        
        
       public MascotasBean consultarMascotasxid ( int id_mascotas){
         MascotasBean mas = new MascotasBean();
         String sql = "select * from mascotas where id_mascotas =" + id_mascotas ;
         return(MascotasBean)jdbcTemplate.query(
         sql, new ResultSetExtractor<MascotasBean>(){

             @Override
             public MascotasBean extractData(ResultSet rs) throws SQLException, DataAccessException {
              if(rs.next()){
                   mas.setId_mascotas(rs.getInt("id_mascotas"));
                   mas.setNombre_mascota(rs.getString("nombre_mascota"));
                   mas.setRaza(rs.getString("raza"));
                   mas.setGenero(rs.getString("genero"));
                   mas.setTipo_de_mascotas(rs.getString("tipo_de_mascotas"));
                   mas.setVacunas(rs.getString("vacunas"));
                   mas.setFotoMascota(rs.getString("fotoMascota"));
              }
               
            return mas;
             }
         
           }
         );
       
       }
       
       @RequestMapping(value = "updateMascota.htm",method = RequestMethod.POST)
     public ModelAndView actmascota (MascotasBean masBean, HttpServletRequest req){
       ModelAndView mav = new ModelAndView();
       MascotaDao masDao = new MascotaDao();
       ArrayList<String> listados = new ArrayList<>();
       
       boolean isMultipart = ServletFileUpload.isMultipartContent(req);
       
       DiskFileItemFactory file = new DiskFileItemFactory();
       ServletFileUpload fileUpload = new ServletFileUpload(file);
       List<FileItem> items = null;
       try{
          items = fileUpload.parseRequest(req);
          for (int i = 0; i < items.size(); i++){
           FileItem fileItem = (FileItem)items.get(i);
           listados.add(fileItem.getString());
          }
       }catch (FileUploadException ex){
         System.out.print("error en la carga de la imagen principal controller/actUsuario" + ex.getMessage());
       }
       if(listados.get(5).isEmpty() || listados.get(5).equals("") || listados.get(5).equals(null)){
         masDao.actMascotasSinfoto(masBean, items);
       }else{
          
        masDao.actMascotasConImagen(masBean,isMultipart, req , items);
       }
       mav.setViewName("redirect:/ListarMascotas.htm");
       return mav;
     }
        
     //-------------------------------------------------------------------------///
        
      @RequestMapping(value = "consultarMascotaXId.htm",method = RequestMethod.GET)
     public ModelAndView listarmascotaxid (){
       ModelAndView mav = new ModelAndView();
       MascotasBean mascotas = new MascotasBean();
       mav.addObject("mascotas",mascotas);
       mav.setViewName("views/ConsultarMascotaXId");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarMascotaXId.htm",method = RequestMethod.POST)
    public ModelAndView listarmascotaxid (
       @ModelAttribute ("mascotas") MascotasBean mas,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      MascotaDao masDao = new MascotaDao();
      
      int id = mas.getId_mascotas();
      mav.addObject("mascotas", masDao.consultarmascotasporid(id) );
      mav.setViewName("views/ResultMascotasxID");
      return mav;
    }  
    
    
    //--------------------------------------------------//
      @RequestMapping(value = "consultarMascotaXNombre.htm",method = RequestMethod.GET)
     public ModelAndView listarmascotaxnombre (){
       ModelAndView mav = new ModelAndView();
       MascotasBean mascotas = new MascotasBean();
       mav.addObject("mascotas",mascotas);
       mav.setViewName("views/consultarMascotaXNombre");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarMascotaXNombre.htm",method = RequestMethod.POST)
    public ModelAndView listarmascotaxnombre (
       @ModelAttribute ("mascotas") MascotasBean mas,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      MascotaDao masDao = new MascotaDao();
      
      String nombreMascota = mas.getNombre_mascota();
      mav.addObject("mascotas", masDao.consultarmascotaspornombre(nombreMascota) );
      mav.setViewName("views/ResultMascotasxID");
      return mav;
    }  
    //-----------------------------------------------------//
    
     @RequestMapping(value = "consultarMascotaXRaza.htm",method = RequestMethod.GET)
     public ModelAndView listarmascotaxraza (){
       ModelAndView mav = new ModelAndView();
       MascotasBean mascotas = new MascotasBean();
       mav.addObject("mascotas",mascotas);
       mav.setViewName("views/consultarMascotaXRaza");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarMascotaXRaza.htm",method = RequestMethod.POST)
    public ModelAndView listarmascotaxraza (
       @ModelAttribute ("mascotas") MascotasBean mas,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      MascotaDao masDao = new MascotaDao();
      
      String raza = mas.getRaza();
      mav.addObject("mascotas", masDao.consultarmascotasporraza(raza) );
      mav.setViewName("views/ResultMascotasxID");
      return mav;
    } 
    //---------------------------------------------------------//
     @RequestMapping(value = "consultarMascotaXGenero.htm",method = RequestMethod.GET)
     public ModelAndView listarmascotaxgenero (){
       ModelAndView mav = new ModelAndView();
       MascotasBean mascotas = new MascotasBean();
       mav.addObject("mascotas",mascotas);
       mav.setViewName("views/consultarMascotaXGenero");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarMascotaXGenero.htm",method = RequestMethod.POST)
    public ModelAndView listarmascotaxgenero (
       @ModelAttribute ("mascotas") MascotasBean mas,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      MascotaDao masDao = new MascotaDao();
      
      String genero = mas.getGenero();
      mav.addObject("mascotas", masDao.consultarmascotasporgenero(genero) );
      mav.setViewName("views/ResultMascotasxID");
      return mav;
    }
    //____________________________________________________//
     @RequestMapping(value = "consultarMascotaXTipodemascotas.htm",method = RequestMethod.GET)
     public ModelAndView listarmascotaxTipodemascotas (){
       ModelAndView mav = new ModelAndView();
       MascotasBean mascotas = new MascotasBean();
       mav.addObject("mascotas",mascotas);
       mav.setViewName("views/consultarMascotaXTipodemascotas");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarMascotaXTipodemascotas.htm",method = RequestMethod.POST)
    public ModelAndView listarmascotaxTipodemascotas (
       @ModelAttribute ("mascotas") MascotasBean mas,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      MascotaDao masDao = new MascotaDao();
      
      String tipo_de_mascotas = mas.getTipo_de_mascotas();
      mav.addObject("mascotas", masDao.consultarmascotasportipodemascota(tipo_de_mascotas) );
      mav.setViewName("views/ResultMascotasxID");
      return mav;
    }
    //------------------------------------------------//
    
    @RequestMapping(value = "consultarMascotaXVacunas.htm",method = RequestMethod.GET)
     public ModelAndView listarmascotaxvacunas (){
       ModelAndView mav = new ModelAndView();
       MascotasBean mascotas = new MascotasBean();
       mav.addObject("mascotas",mascotas);
       mav.setViewName("views/consultarMascotaXVacunas");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarMascotaXVacunas.htm",method = RequestMethod.POST)
    public ModelAndView listarmascotaxvacunas (
       @ModelAttribute ("mascotas") MascotasBean mas,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      MascotaDao masDao = new MascotaDao();
      
      String vacunas = mas.getVacunas();
      mav.addObject("mascotas", masDao.consultarmascotasporvacunas(vacunas) );
      mav.setViewName("views/ResultMascotasxID");
      return mav;
    }
}
