/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.servlet.http.HttpServletRequest;
import models.loginBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ryzen3
 */
public class loginController {
    
   String login,btnValidarLogin;
   loginBean log = new loginBean();
   
    @RequestMapping(value="btnValidarLogin.htm" ,method = RequestMethod.GET)
    public ModelAndView login (HttpServletRequest request){
     ModelAndView mav = new ModelAndView();   
     
    if (btnValidarLogin != null){
      if (btnValidarLogin.equals("ValidarLogin")){
           log.setUsuario("jose");
           log.setContraseña("1234");
           log.setPerfil("admin");
           log.setToken("1003");
           
           String user = request.getParameter("text_usuario");
           String contra = request.getParameter("text_contrasena");
           
             if(
                (user.equals(log.getUsuario())) && (contra.equals(log.getContraseña()))     
               ){
                    request.setAttribute("text_usuario", log.getUsuario());
                    request.setAttribute("text_contrasena",  log.getContraseña());
                    request.setAttribute("perfil", log.getPerfil());
                    request.setAttribute("token", log.getToken());
                    mav.setViewName("views/PrincipalADMIN");
                   System.out.print("envio esta monda");
             }else{
                   log.setUsuario(null);
                   log.setContraseña(null);
                   log.setPerfil(null);
                   log.setToken(null);
                    mav.setViewName("redirect:/index.htm");
                     System.out.print("no envio esta monda");
             }
      }
    }
    return mav;
    }
}
