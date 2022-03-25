/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;


import Dao.ConectarDB;
import Dao.UsuarioDao;
import java.io.File;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.UsuarioBean;
import models.UsuariosValidation;
import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
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

public class principalController {
    private JdbcTemplate jdbcTemplate;
    private UsuariosValidation mostararDatos; 
 
    public principalController() {
        this.mostararDatos = new UsuariosValidation();
        ConectarDB con = new ConectarDB();
        jdbcTemplate = new JdbcTemplate(con.conDB());
    }
    
   
     @RequestMapping("PrincipalADMIN.htm")
    
    public ModelAndView vitaadmin(){
     ModelAndView mav = new ModelAndView();
     mav.setViewName("views/PrincipalADMIN");
     return mav;
    }
     
    @RequestMapping("ListarUsuario.htm")
    
    public ModelAndView formUsuario(){
     ModelAndView mav = new ModelAndView();
     String sql = "select * from usuarios";
     List datos = jdbcTemplate.queryForList(sql);
     mav.addObject("usuarios",datos);
     mav.setViewName("views/ListarUsuario");
     return mav;
    }
     
    
   @RequestMapping( value = "formUsuario.htm",method = RequestMethod.GET)
   public ModelAndView addCliente (){
     ModelAndView mav  = new ModelAndView();
     mav.addObject("usuario", new UsuarioBean());
     mav.setViewName("views/formUsuario");
     return mav;    
   }
   
   private static final String UPLOAD_DIRECTORY = "..\\..\\web\\images\\photos";
   
   private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
   private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
   private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;
    
   @RequestMapping(value = "formUsuario.htm",method = RequestMethod.POST)
   public ModelAndView addCliente(UsuarioBean usu , HttpServletRequest request){
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
             usu.setFotoUsuario(nameFile);
             usu.setFotoOld(nameFile);
           }catch(Exception e){
             System.out.print("escriba..." + e.getMessage());
           }
         }else{
          listados.add(fileItem.getString());
         }
       }
       usu.setNombre(listados.get(0));
       usu.setApellido(listados.get(1));
       usu.setTipo_de_identificacion(listados.get(2));
       usu.setIdentificacion(Integer.parseInt(listados.get(3)));
       usu.setDireccion(listados.get(4));
     }
     
    String sql = "insert into usuarios ( nombre, apellido,  tipo_de_identificacion, identificacion, direccion, fotoUsuario, fotoOld, limiteadopcion )"
            + " values (?,?,?,?,?,?,?,0)";
           jdbcTemplate.update(sql, usu.getNombre(), usu.getApellido(), usu.getTipo_de_identificacion(),
                   usu.getIdentificacion(), usu.getDireccion(), usu.getFotoUsuario(), usu.getFotoOld());
           mav.setViewName("redirect:/ListarUsuario.htm");
            return mav;    
    }
   
   
   
   
   
//    public ModelAndView addCliente (UsuarioBean usu,
//            @ModelAttribute("usuario") UsuarioBean users,   
//            BindingResult result,
//             SessionStatus status
//    ){  
//     this.mostararDatos.validate(users, result);
//     if(result.hasErrors()){
//                    ModelAndView mav = new ModelAndView();
//                    mav.setViewName("views/formUsuario");
//                    return mav;
//    }else{
//            ModelAndView mav  = new ModelAndView();
//             String sql = "insert into usuarios ( nombre, apellido,  tipo_de_identificacion, identificacion, direccion ) values (?,?,?,?,?)";
//             jdbcTemplate.update(sql, usu.getNombre(), usu.getApellido(), usu.getTipo_de_identificacion(),usu.getIdentificacion(), usu.getDireccion());
//             mav.setViewName("redirect:/ListarUsuario.htm");
//             return mav;    
//           }
//    }  
    
    
   @RequestMapping("deleteUsuario.htm")    
     public ModelAndView deleteUsuario(  HttpServletRequest req){
     ModelAndView mav = new ModelAndView();
     UsuarioDao usuDao = new UsuarioDao();
     int id_usuario = Integer.parseInt(req.getParameter("id_usuario"));
     UsuarioBean masb = usuDao.consultarusuarioporid(id_usuario);
     String deletePath = req.getServletContext().getRealPath("") + File.separator;
     String fotousuario = req.getParameter("fotousuario");
     usuDao.borrarImagenUsuario(masb.getFotoUsuario() , deletePath, id_usuario);
       System.out.println(masb.getFotoUsuario());
    mav.setViewName("redirect:/ListarUsuario.htm");    
    return mav;
    }
     
     
     
     
     
    @RequestMapping(value="updateUsuario.htm" ,method = RequestMethod.GET)
    public ModelAndView actUsuario(HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        int id_usuario = Integer.parseInt(req.getParameter("id_usuario"));
        String fotoOld = req.getParameter("fotoOld");
        UsuarioBean usu = cunsultarUsuarioxId(id_usuario);
        usu.setFotoOld(fotoOld);
        mav.addObject("usuario",usu);
        mav.setViewName("views/updateUsuario");
     return mav;
    }
    
    
    
    public UsuarioBean cunsultarUsuarioxId( int id_usuario){
        UsuarioBean usu = new UsuarioBean();
        String sql = "select * from usuarios where id_usuario = " + id_usuario ;
        return(UsuarioBean)jdbcTemplate.query(
                sql, new ResultSetExtractor<UsuarioBean>(){
                 @Override
                 public UsuarioBean extractData(ResultSet rs) throws SQLException, DataAccessException {
                   if(rs.next()){
                      usu.setId_usuario(rs.getInt("id_usuario"));
                      usu.setNombre(rs.getString("nombre"));
                      usu.setApellido(rs.getString("apellido"));
                      usu.setTipo_de_identificacion(rs.getString("tipo_de_identificacion"));
                      usu.setIdentificacion(rs.getInt("identificacion"));
                      usu.setDireccion(rs.getString("direccion"));
                      usu.setFotoUsuario(rs.getString("fotoUsuario"));
                   }
                     
                     return usu;
              }
          }
                
         );
    }
    
    
    //__________ACTUALIZAR USUARIO----------------------------------------//
    
     @RequestMapping(value = "updateUsuario.htm",method = RequestMethod.POST)
     public ModelAndView actUsuario (UsuarioBean usuBean, HttpServletRequest req){
       ModelAndView mav = new ModelAndView();
       UsuarioDao usuDao = new UsuarioDao();
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
         usuDao.actUsuarioSinfoto(usuBean, items);
       }else{
          
        usuDao.actUsuarioConImagen(usuBean,isMultipart, req , items);
       }
       mav.setViewName("redirect:/ListarUsuario.htm");
       return mav;
     }
     
     
     
//    public ModelAndView actUsuario (UsuarioBean usu,
//            @ModelAttribute("usuario") UsuarioBean users,   
//            BindingResult result,
//             SessionStatus status
//    ){  
//     this.mostararDatos.validate(users, result);
//     if(result.hasErrors()){
//                    ModelAndView mav = new ModelAndView();
//                    mav.setViewName("views/formUsuario");
//                    return mav;
//    }else{
//            ModelAndView mav  = new ModelAndView();
//             String sql = "update usuarios set nombre = ?, apellido = ?,  tipo_de_identificacion = ?, identificacion = ?, direccion = ? where id_usuario = ?";
//             jdbcTemplate.update(sql, usu.getNombre(), usu.getApellido(), usu.getTipo_de_identificacion(),usu.getIdentificacion(), usu.getDireccion(), usu.getId_usuario());
//             mav.setViewName("redirect:/ListarUsuario.htm");
//             return mav;    
//           }
//    } 
    
    
    
    
    
    @RequestMapping(value = "consultarUsuarioxId.htm",method = RequestMethod.GET)
     public ModelAndView listarusuarioxid (){
       ModelAndView mav = new ModelAndView();
       UsuarioBean usuario = new UsuarioBean();
       mav.addObject("usuario",usuario);
       mav.setViewName("views/consultarUsuarioxId");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarUsuarioxId.htm",method = RequestMethod.POST)
    public ModelAndView listarusuarioxid (
       @ModelAttribute ("usuario") UsuarioBean usu,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      UsuarioDao usuDao = new UsuarioDao();
      
      int id = usu.getId_usuario();
      mav.addObject("usuario", usuDao.consultarusuarioporid(id) );
      mav.setViewName("views/resulUsuarioXId");
      return mav;
    }
    
    //----------------------------------------------------------------//
    
    @RequestMapping(value = "consultarUsuarioxNombre.htm",method = RequestMethod.GET)
     public ModelAndView listarusuarioxnombre (){
       ModelAndView mav = new ModelAndView();
       UsuarioBean usuario = new UsuarioBean();
       mav.addObject("usuario",usuario);
       mav.setViewName("views/consultarUsuarioxNombre");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarUsuarioxNombre.htm",method = RequestMethod.POST)
    public ModelAndView listarusuarioxnombre (
       @ModelAttribute ("usuario") UsuarioBean usu,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      UsuarioDao usuDao = new UsuarioDao();
      
      String nombre = usu.getNombre();
      mav.addObject("usuario", usuDao.consultarusuariopornombre(nombre) );
      mav.setViewName("views/resulUsuarioXId");
      return mav;
    }
    
    //-----------------------------------------------------------//
    
     @RequestMapping(value = "consultarUsuarioxapellido.htm",method = RequestMethod.GET)
     public ModelAndView listarusuarioxapellido (){
       ModelAndView mav = new ModelAndView();
       UsuarioBean usuario = new UsuarioBean();
       mav.addObject("usuario",usuario);
       mav.setViewName("views/consultarUsuarioxapellido");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarUsuarioxapellido.htm",method = RequestMethod.POST)
    public ModelAndView listarusuarioxapellido (
       @ModelAttribute ("usuario") UsuarioBean usu,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      UsuarioDao usuDao = new UsuarioDao();
      
      String apellido = usu.getApellido();
      mav.addObject("usuario", usuDao.consultarusuarioporapellido(apellido) );
      mav.setViewName("views/resulUsuarioXId");
      return mav;
    }
    
   //--------------------------------------------------------// 
    
     @RequestMapping(value = "consultarUsuarioxtipoid.htm",method = RequestMethod.GET)
     public ModelAndView listarusuarioxtipoid (){
       ModelAndView mav = new ModelAndView();
       UsuarioBean usuario = new UsuarioBean();
       mav.addObject("usuario",usuario);
       mav.setViewName("views/consultarUsuarioxtipoid");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarUsuarioxtipoid.htm",method = RequestMethod.POST)
    public ModelAndView listarusuarioxtipoid (
       @ModelAttribute ("usuario") UsuarioBean usu,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      UsuarioDao usuDao = new UsuarioDao();
      
      String tipoid = usu.getTipo_de_identificacion();
      mav.addObject("usuario", usuDao.consultarusuarioportipoid(tipoid) );
      mav.setViewName("views/resulUsuarioXId");
      return mav;
    }
    
    //______________________________________________//
    
    @RequestMapping(value = "consultarUsuarioxidentificacion.htm",method = RequestMethod.GET)
     public ModelAndView listarusuarioxidentificacion (){
       ModelAndView mav = new ModelAndView();
       UsuarioBean usuario = new UsuarioBean();
       mav.addObject("usuario",usuario);
       mav.setViewName("views/consultarUsuarioxidentificacion");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarUsuarioxidentificacion.htm",method = RequestMethod.POST)
    public ModelAndView listarusuarioxidentificacion (
       @ModelAttribute ("usuario") UsuarioBean usu,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      UsuarioDao usuDao = new UsuarioDao();
      
      int identificacion = usu.getIdentificacion();
      mav.addObject("usuario", usuDao.consultarusuarioporidentificacion(identificacion) );
      mav.setViewName("views/resulUsuarioXId");
      return mav;
    }
    //---------------------------------------------------------------------------//
     @RequestMapping(value = "consultarUsuarioxdireccion.htm",method = RequestMethod.GET)
     public ModelAndView listarusuarioxdireccion (){
       ModelAndView mav = new ModelAndView();
       UsuarioBean usuario = new UsuarioBean();
       mav.addObject("usuario",usuario);
       mav.setViewName("views/consultarUsuarioxdireccion");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarUsuarioxdireccion.htm",method = RequestMethod.POST)
    public ModelAndView listarusuarioxdireccion (
       @ModelAttribute ("usuario") UsuarioBean usu,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      UsuarioDao usuDao = new UsuarioDao();
      
      String direccion = usu.getDireccion();
      mav.addObject("usuario", usuDao.consultarusuariopordireccion(direccion) );
      mav.setViewName("views/resulUsuarioXId");
      return mav;
    }
    
    
}
