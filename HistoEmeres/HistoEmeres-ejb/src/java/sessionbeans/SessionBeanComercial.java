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

    public boolean modificarProducto(Producto producto){
        try{
            productoFacade.edit(producto);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean modificarCliente(Cliente cliente,Cliente clienteAnterior) {
        if(clienteFacade.buscarPorRut(cliente.getRutCliente()).isEmpty()){
            clienteFacade.remove(clienteAnterior);
            clienteFacade.create(cliente);
            return true;
        }else if(!clienteFacade.buscarPorRut(cliente.getRutCliente()).isEmpty() && cliente.getRutCliente().toUpperCase().compareTo(clienteAnterior.getRutCliente())==0){
            clienteFacade.edit(cliente);
            return true;
        }else{
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
    
    public boolean modificarVenta(Venta venta){
        try{
            venta.setCodigoProducto(productoFacade.buscarPorCodigoProducto(venta.getCodigoProducto().getCodigoProducto()).get(0));
            venta.setRutCliente(clienteFacade.buscarPorRut(venta.getRutCliente().getRutCliente()).get(0));
            ventaFacade.edit(venta);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
