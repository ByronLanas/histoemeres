/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Aporte;
import entities.Egreso;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Battousai
 */
@Stateless
@LocalBean
public class SessionBeanFinanaciero {

    @EJB
    private EgresoFacadeLocal egresoFacade;
    @EJB
    private SessionBeanIngreso sessionBeanIngreso;

    public boolean insertarAporte(Aporte aporte) {
        if (sessionBeanIngreso.verificarAporte(aporte)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ingresarEgreso(Egreso egreso) {
        try {
            egresoFacade.create(egreso);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
