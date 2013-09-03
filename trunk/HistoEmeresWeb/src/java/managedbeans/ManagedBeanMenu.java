/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanMenu")
@RequestScoped
public class ManagedBeanMenu {

    private static String pagina="";

    public ManagedBeanMenu() {
    }

    public void irMenu(String pagina) {
        if (this.pagina.compareTo("")==0){
            pagina="historiales";
        }
        try {
            setPagina(pagina);
                FacesContext.getCurrentInstance().getExternalContext().redirect("histoemeres.xhtml");
            
            
        } catch (IOException ex) {
            Logger.getLogger(ManagedBeanMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }
}
