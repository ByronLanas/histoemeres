/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Producto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import sessionbeans.ProductoFacadeLocal;
import sessionbeans.SessionBeanComercial;
import javax.faces.context.FacesContext;
import org.jboss.weld.bean.builtin.FacadeInjectionPoint;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanProducto")
@RequestScoped
public class ManagedBeanProducto {

    @EJB
    private SessionBeanComercial sessionBeanComercial;
    @EJB
    private ProductoFacadeLocal productoFacade;
    private Integer codigo_producto;
    private String nombre_producto;
    private Float valor_producto;
    private Producto producto;
    private static Producto selectedProducto;
    private List<Producto> productos;

    public ManagedBeanProducto() {
    }

    public Integer getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(Integer codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public Float getValor_producto() {
        return valor_producto;
    }

    public void setValor_producto(Float valor_producto) {
        this.valor_producto = valor_producto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Producto getSelectedProducto() {
        return selectedProducto;
    }

    public void setSelectedProducto(Producto selectedProducto) {
        this.selectedProducto = selectedProducto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @PostConstruct
    public void init() {
        productos = productoFacade.findAll();
    }

    public void insertarProducto() {
        FacesContext context = FacesContext.getCurrentInstance();
        producto = new Producto(null, nombre_producto, valor_producto);
        if (!productoFacade.buscarPorNombreProducto(nombre_producto).isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El producto no fue ingresado", "El producto '" + nombre_producto + "' ya existe"));
        } else {
            if (!sessionBeanComercial.insertarProducto(producto)) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El producto no fue ingresado", "El producto no pudo ser ingresado correctamente, intentelo nuevamente"));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto ingresado con éxito", "El Producto: " + producto.getNombreProducto() + " cuyo Valor es: $" + producto.getValorProducto() + " fue ingresado con éxito"));
            }
        }
    }
    
    public void modificarProducto(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            productoFacade.edit(selectedProducto);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto modificado con éxito", "El producto: " + selectedProducto.getNombreProducto() + " fue modificado con éxito."));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Producto no fue modificado", "El producto: " + selectedProducto.getNombreProducto() + " no pudo ser modificado."));
        }
    }
}
