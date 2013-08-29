/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Cliente;
import entities.Producto;
import java.sql.SQLException;
import java.util.List;
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
    private ProductoFacadeLocal productoFacade;
    @EJB
    private ClienteFacadeLocal clienteFacade;
    ClienteFacade cliente;
    VentaFacade venta;
    ProductoFacade producto;

    public boolean insertarCliente(Cliente cliente) {
        try {
            if (!verificarCliente(cliente)) {
                return false;
            }
            clienteFacade.create(cliente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean insertarProducto(Producto producto) {
        try {
            productoFacade.create(producto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarCliente(Cliente cliente) {
        try {
            if (true) {
                return false;
            }
            clienteFacade.edit(cliente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verificarCliente(Cliente cliente) {
        Cliente client = clienteFacade.find(cliente.getRutCliente());
        try {
            if (client.getRutCliente() == null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
