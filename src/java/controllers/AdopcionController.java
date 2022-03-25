/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import Dao.AdopcionBeanDao;
import Dao.ConectarDB;
import Dao.MascotaDao;
import Dao.UsuarioDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.MascotasBean;
import models.UsuarioBean;
import models.adopcionBean;
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
public class AdopcionController {
    JdbcTemplate jdbcTemplate;

    public AdopcionController() {
        ConectarDB con = new ConectarDB();
        jdbcTemplate = new JdbcTemplate(con.conDB());
    }
     
    
    
      @RequestMapping(value = "adoptarMascota.htm", method = RequestMethod.GET)
      public ModelAndView adotarmascota(){
      ModelAndView mav = new ModelAndView();
      adopcionBean adop = new adopcionBean();
      AdopcionBeanDao  AdopcionBDao = new  AdopcionBeanDao();
      List datos = AdopcionBDao.consultarAdopciones();
      mav.addObject("adopcion", datos);
      mav.setViewName("views/ListarAdopcion");
      return mav;
     }
      
      @RequestMapping(value = "formRegistarAdopcion.htm",method = RequestMethod.POST)
      public ModelAndView formRegistarAdopcion(
            @ModelAttribute("adopcion") adopcionBean adopcionadd,   
            BindingResult result,
             SessionStatus status){
            ModelAndView mav = new ModelAndView();
            String sql = "insert into adopcion (usuario_id, mascotas_id,fecha_de_adopcion)"
                + "values (?,?,?)";
            
            String sql2 = "update mascotas set is_adopted = 1 where id_mascotas= ? ";
            
            String sql3 = "update usuarios set limiteadopcion = limiteadopcion +1 where id_usuario = ? ";
            jdbcTemplate.update(sql,adopcionadd.getUsuario_id(), adopcionadd.getMascotas_id(),adopcionadd.getFecha_de_adopcion());
            jdbcTemplate.update(sql2,adopcionadd.getMascotas_id());
            jdbcTemplate.update(sql3,adopcionadd.getUsuario_id());
            mav.addObject("adopcion", new adopcionBean());
            mav.setViewName("redirect:/adoptarMascota.htm");
            return mav;
    }
      
     @RequestMapping(value = "formRegistarAdopcion.htm", method = RequestMethod.GET)
      public ModelAndView formRegistarAdopcion(){
        ModelAndView mav = new ModelAndView();
        adopcionBean adop = new adopcionBean();
        UsuarioDao usuDao = new UsuarioDao();
        MascotaDao masDao = new MascotaDao();
        AdopcionBeanDao  AdopcionBDao = new  AdopcionBeanDao();
        //---------------------------------------------------------
        List idUsuario = usuDao.consultarUsuariosAdopcion();
        mav.addObject("listarUsuarios",idUsuario );
        //---------------------------------------------------------
        List idMascota = masDao.consultarMascotasadopcion();
        mav.addObject("listarMascotas",idMascota);
        //---------------------------------------------------------
        int cod = AdopcionBDao.consultarCodigoAdopcion();
        adop.setId_adopcion(cod);
        //---------------------------------------------------------
        mav.addObject("adopcion", adop);
        mav.setViewName("views/formRegistarAdopcion");
        return mav;
      }
      
      
     @RequestMapping("deleteAdopcion.htm")    
     public ModelAndView deleteAdopcion(  HttpServletRequest req){
     ModelAndView mav = new ModelAndView();
     int id_adopcion = Integer.parseInt(req.getParameter("id_adopcion"));
     String sql ="delete from adopcion where id_adopcion = ? ";
     jdbcTemplate.update(sql , id_adopcion);
     mav.setViewName("redirect:/adoptarMascota.htm");
    
     return mav;
    }




@RequestMapping(value = "updateadopcion.htm", method = RequestMethod.GET)
    public ModelAndView ActualizarAdopcion(HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        int id_adopcion = Integer.parseInt(req.getParameter("id_adopcion"));
        adopcionBean user_masc = consultarAdopcionId (id_adopcion);
        mav.addObject("adopcion", user_masc);
        UsuarioDao usuDao = new UsuarioDao();
        MascotaDao masDao = new MascotaDao();
        //---------------------------------------------------------
        List idUsuario = usuDao.consultarUsuariosAdopcion();
        mav.addObject("listarUsuarios",idUsuario );
        //---------------------------------------------------------
        List idMascota = masDao.consultarMascotasadopcion();
        mav.addObject("listarMascotas",idMascota);
        mav.setViewName("views/updateadopcion");
        return mav;
    }
  
    public adopcionBean consultarAdopcionId(int id_adopcion){
        adopcionBean user_masc = new adopcionBean();
        String sql = "select * from adopcion where id_adopcion =" + id_adopcion;
        return (adopcionBean)jdbcTemplate.query(sql,new ResultSetExtractor<adopcionBean>(){
            @Override
            public adopcionBean extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    user_masc.setId_adopcion(rs.getInt("id_adopcion"));
                    user_masc.setUsuario_id(rs.getInt("usuario_id"));
                    user_masc.setMascotas_id(rs.getInt("mascotas_id"));
                    user_masc.setFecha_de_adopcion(rs.getString("fecha_de_adopcion"));
                }
                return user_masc;
            }
        });
    }
@RequestMapping(value="updateadopcion.htm",method = RequestMethod.POST)
public ModelAndView ActualizarAdopcion(
  @ModelAttribute("adopcion") adopcionBean adop){
     ModelAndView mav = new ModelAndView();
     String sql ="update adopcion set usuario_id = ?,mascotas_id = ?,fecha_de_adopcion = ? where id_adopcion = ? ";
     jdbcTemplate.update(sql, adop.getUsuario_id(), adop.getMascotas_id(),adop.getFecha_de_adopcion(), adop.getId_adopcion());
     mav.addObject("adopcion",new adopcionBean());
     mav.setViewName("redirect:/adoptarMascota.htm");
     return mav;
}
    
    
    
//--------------------------------------------------------------------//

 @RequestMapping(value = "consultarAdopcionXId.htm",method = RequestMethod.GET)
     public ModelAndView listaradopcionxid (){
       ModelAndView mav = new ModelAndView();
       adopcionBean adopcion = new adopcionBean();
       mav.addObject("adopcion",adopcion);
       mav.setViewName("views/consultarAdopcionXId");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarAdopcionXId.htm",method = RequestMethod.POST)
    public ModelAndView listaradopcionxid (
       @ModelAttribute ("adopcion") adopcionBean adop,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      AdopcionBeanDao adopDao = new AdopcionBeanDao();
      
      int id_adopcion = adop.getId_adopcion();
      mav.addObject("adopcion", adopDao.consultaradopcionporid(id_adopcion) );
      mav.setViewName("views/ResultAdopcionXId");
      return mav;
    }
  //--------------------------------------------------------//
    @RequestMapping(value = "consultarAdopcionXFecha.htm",method = RequestMethod.GET)
     public ModelAndView listaradopcionxfecha (){
       ModelAndView mav = new ModelAndView();
       adopcionBean adopcion = new adopcionBean();
       mav.addObject("adopcion",adopcion);
       mav.setViewName("views/consultarAdopcionXFecha");
       return mav;
     }
     
     
    @RequestMapping(value = "consultarAdopcionXFecha.htm",method = RequestMethod.POST)
    public ModelAndView listaradopcionxfecha (
       @ModelAttribute ("adopcion") adopcionBean adop,
       BindingResult result,
       SessionStatus status)
    {
      ModelAndView mav = new ModelAndView();
      AdopcionBeanDao adopDao = new AdopcionBeanDao();
      
      String fecha_de_adopcion = adop.getFecha_de_adopcion();
      mav.addObject("adopcion", adopDao.consultaradopcionporfecha(fecha_de_adopcion) );
      mav.setViewName("views/ResultAdopcionXId");
      return mav;
    }
    
}
    