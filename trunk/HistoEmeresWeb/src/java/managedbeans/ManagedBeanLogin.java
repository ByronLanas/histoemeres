/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanLogin")
@RequestScoped
public class ManagedBeanLogin {

    /**
     * Creates a new instance of ManagedBeanLogin
     */
    public ManagedBeanLogin() {
        
    }
    public void irLogin() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    }
}
