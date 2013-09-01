/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author d5eg6
 */
@FacesValidator("UserValidator")
public class UserValidator implements Validator {
private Pattern user = Pattern.compile("[aA-zZ][aA-zZ]*");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value.toString().length() < 3) {
            FacesMessage msg = new FacesMessage("Error de validación", "El largo mínimo para un usuario es 3");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        else{
            final Matcher matcher = user.matcher(value.toString());
            if(!matcher.matches()){
                FacesMessage msg = new FacesMessage("Error de validación", "El usuario solo puede contener letras.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
            }
        }
    }
}
