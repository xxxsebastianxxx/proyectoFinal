/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import javax.ejb.Stateless;

/**
 *
 * @author ryzen3
 */
@Stateless
public class adopcionBean {
   private Integer id_adopcion;
   private Integer usuario_id;
   private Integer mascotas_id;
   private String fecha_de_adopcion;
  

    public adopcionBean() {
    }

    public adopcionBean(Integer id_adopcion, Integer usuario_id, Integer mascotas_id, String fecha_de_adopcion) {
        this.id_adopcion = id_adopcion;
        this.usuario_id = usuario_id;
        this.mascotas_id = mascotas_id;
        this.fecha_de_adopcion = fecha_de_adopcion;
    }

    

    

    public Integer getId_adopcion() {
        return id_adopcion;
    }

    public void setId_adopcion(Integer id_adopcion) {
        this.id_adopcion = id_adopcion;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Integer getMascotas_id() {
        return mascotas_id;
    }

    public void setMascotas_id(Integer mascotas_id) {
        this.mascotas_id = mascotas_id;
    }

    public String getFecha_de_adopcion() {
        return fecha_de_adopcion;
    }

    public void setFecha_de_adopcion(String fecha_de_adopcion) {
        this.fecha_de_adopcion = fecha_de_adopcion;
    }

}
