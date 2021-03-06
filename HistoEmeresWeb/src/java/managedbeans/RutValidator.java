/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.validation.ValidationException;

/**
 *
 * @author Diego
 */
@FacesValidator("RutValidator")
public class RutValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidationException {
        if (value.toString().length() < 8) {
            FacesMessage msg = new FacesMessage("Error de validación", "El largo mínimo del rut es de 8 dígitos");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        } else {
            int m = 0, s = 1;
            String rut = value.toString().toUpperCase();
            int rutSinDv = Integer.parseInt(rut.substring(0, rut.length() - 1));
            char dv = rut.charAt(rut.length() - 1);
            for (; rutSinDv != 0; rutSinDv /= 10) {
                s = (s + rutSinDv % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv != (char) (s != 0 ? s + 47 : 75)) {
                FacesMessage msg = new FacesMessage("Error de validación", "El rut: " + value + " es inválido");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}

