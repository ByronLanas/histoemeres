/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Battousai
 */
@Stateless
@LocalBean
public class SessionBeanFinanaciero {

    SessionBeanIngreso ingreso;
    EgresoFacade egreso;

}
