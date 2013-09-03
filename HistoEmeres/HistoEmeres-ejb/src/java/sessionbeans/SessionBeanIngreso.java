/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;


import entities.Aporte;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Battousai
 */
@Stateless
@LocalBean
public class SessionBeanIngreso {
    @EJB
    private SessionBeanComercial sessionBeanComercial;
    @EJB
    private AporteFacadeLocal aporteFacade;


    
    public SessionBeanIngreso(){
        
    }
    

    public boolean verificarAporte(Aporte aporte) {
        if(aporteFacade.buscarAporte(aporte)){
            aporteFacade.create(aporte);
            return true;
        }
        else
            return false;
    }

    public boolean verificarModificarAporte(Aporte aporte,Aporte aporteAnterior) {
        if(aporteFacade.buscarAporte(aporte) || aporte.getAportePK().getFechaMunicipalidad().compareTo(aporteAnterior.getAportePK().getFechaMunicipalidad())==0){
            aporteFacade.remove(aporteAnterior);
            aporteFacade.create(aporte);
            return true;
        }
        else
            return false;
    }

}
