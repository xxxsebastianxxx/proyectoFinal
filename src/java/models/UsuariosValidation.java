/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ryzen3
 */
public class UsuariosValidation implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return UsuarioBean.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
       UsuarioBean usuarios  = (UsuarioBean) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "nombre",
                "required.nombre",
                "el mombre del  es obligatorio"
        );
         ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "apellido",
                "required.apellido",
                "el apellido del usuario es obligatorio"
        );
          ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "tipo_de_identificacion",
                "required.tipo_de_identificacion",
                "el tipo_de_identificacion es obligatorio"
        );
           ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "identificacion",
                "required.identificacion",
                "la identificacion es obligatoria"
        );
            ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "direccion",
                "required.direccion",
                "la direccion es obligatoria"
        );
        
    }
  
    
}
