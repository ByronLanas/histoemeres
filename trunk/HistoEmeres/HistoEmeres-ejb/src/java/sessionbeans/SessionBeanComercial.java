/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Cliente;
import entities.Producto;
import entities.Venta;
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
    private VentaFacadeLocal ventaFacade;
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
        try {
            if (clienteFacade.buscarPorRut(cliente.getRutCliente()).isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ingresarVenta(Venta venta) {
        try {
            if (!verificarCliente(venta.getRutCliente())) {
                ventaFacade.create(venta);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
