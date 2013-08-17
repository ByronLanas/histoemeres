/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Cliente;
import java.sql.SQLException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Battousai
 */
@Stateless
@LocalBean
public class SessionBeanComercial {

    @EJB
    private ClienteFacadeLocal clienteFacade;
    ClienteFacade cliente;
    VentaFacade venta;
    ProductoFacade producto;

    public boolean insertarCliente(Cliente cliente) {
        try {
            clienteFacade.create(cliente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
