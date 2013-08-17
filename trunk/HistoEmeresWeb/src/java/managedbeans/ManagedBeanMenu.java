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
@Named(value = "managedBeanMenu")
@RequestScoped
public class ManagedBeanMenu {

    private static String pagina;
    
    public ManagedBeanMenu() {
    }
    public void irMenu(String pagina) throws IOException{
        if(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath().indexOf("faces")>0){
            FacesContext.getCurrentInstance().getExternalContext().redirect("histoemeres.xhtml");
        }else
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/histoemeres.xhtml");
        
        setPagina(pagina);
    }
    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }        
}
