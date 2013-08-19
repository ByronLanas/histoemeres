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
            clienteFacade.create(cliente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean insertarProducto(Producto producto){
        try{
            productoFacade.create(producto);
            return true;
        } catch(Exception e){
            return false;
        }
    }
    public List<Producto> mostrarProductos(){
        try{
            List<Producto> productos = productoFacade.findAll();
            return productos;
        }catch(Exception e){
            return null;
        }
    }

    public boolean modificarCliente(Cliente cliente) {
        try{
            clienteFacade.edit(cliente);
            return true;
        }catch (Exception e){
        return false;    
        }
    }


    
}
