/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import Objetos.Aporte;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Battousai
 */
@Stateless
@LocalBean
public class SessionBeanIngreso {

    public AporteFacade aporte;
    public SessionBeanComercial comercial;
    
    public SessionBeanIngreso(){
        
    }
    public boolean verificarAporte(Aporte aporte){
        //AporteFacade.
        return false;
    }
}
