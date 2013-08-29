/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Venta;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
    private SessionBeanComercial sessionBeanComercial;
    @EJB
    private VentaFacadeLocal ventaFacade;

    /**
     * Creates a new instance of ManagedBeanVenta
     */
    private Integer rutCliente;
    private Integer codigoProducto;
    private Integer cantidadVenta;
    private Date fechaVenta;
    private List<Venta> ventasFiltradas;
    private Venta selectedVenta;
    private Venta venta;
    private List<Venta> ventas;





    public Integer getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(Integer rutCliente) {
        this.rutCliente = rutCliente;
    }

    public Integer getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Integer codigoProducto) {
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
        rutCliente = null;
        codigoProducto = null;
        cantidadVenta = null;
    }
    
    @PostConstruct
    public void  init(){
        ventas = ventaFacade.findAll();
    }
}
