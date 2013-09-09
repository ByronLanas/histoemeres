/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Cliente;
import entities.Producto;
import entities.Venta;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import sessionbeans.ClienteFacadeLocal;
import sessionbeans.ProductoFacadeLocal;
import sessionbeans.SessionBeanComercial;
import sessionbeans.VentaFacade;
import sessionbeans.VentaFacadeLocal;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanVenta")
@RequestScoped
public class ManagedBeanVenta {

    @EJB
    private ProductoFacadeLocal productoFacade;
    @EJB
    private ClienteFacadeLocal clienteFacade;
    @EJB
    private SessionBeanComercial sessionBeanComercial;
    @EJB
    private VentaFacadeLocal ventaFacade;
    /**
     * Creates a new instance of ManagedBeanVenta
     */
    private Cliente cliente;
    private Producto producto;
    private String rutCliente;
    private String codigoProducto;
    private Integer cantidadVenta;
    private Date fechaVenta;
    private List<Venta> ventasFiltradas;
    private Venta selectedVenta;
    private Venta venta;
    private List<Venta> ventas;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Integer getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(Integer cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public List<Venta> getVentasFiltradas() {
        return ventasFiltradas;
    }

    public void setVentasFiltradas(List<Venta> ventasFiltradas) {
        this.ventasFiltradas = ventasFiltradas;
    }

    public Venta getSelectedVenta() {
        return selectedVenta;
    }

    public void setSelectedVenta(Venta selectedVenta) {
        this.selectedVenta = selectedVenta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public ManagedBeanVenta() {
        cantidadVenta = null;
    }

    @PostConstruct
    public void init() {
        ventas = ventaFacade.findAll();
    }

    public void ingresarVenta() {
        FacesContext context = FacesContext.getCurrentInstance();
        cliente = clienteFacade.buscarPorRut(rutCliente).get(0);
        producto = productoFacade.buscarPorCodigoProducto(codigoProducto).get(0);
                venta = new Venta(null, cantidadVenta, fechaVenta, cliente, producto);
        if (sessionBeanComercial.ingresarVenta(venta)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta ingresada con éxito", "La venta : " + venta.getCodigoProducto().getNombreProducto() + ": " + venta.getCantidadVenta() + " fue ingresada con éxito"));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Venta no ingresada", "El cliente: " + venta.getRutCliente().getRutCliente() + " no existe."));
        }

    }
}
