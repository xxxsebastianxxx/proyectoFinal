/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ryzen3
 */
public class MascotaValidation implements Validator {

    @Override
    public boolean supports(Class<?> type) {
       return MascotasBean.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MascotasBean mascotas  = (MascotasBean) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "nombre_mascota",
                "required.nombre",
                "el mombre es obligatorio"
        );
        
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "raza",
                "required.raza",
                "la raza es obligatoria"
        );
        
          ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "genero",
                "required.genero",
                "el genero es obligatorio"
        );
          
          
         ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "tipo_de_mascotas",
                "required.tipo_de_mascotas",
                "el tipo_de_mascotas es obligatorio"
        );
         
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "vacunas",
                "required.vacunas",
                "el vacunas es obligatorio"
        );
    }

   
     
}
