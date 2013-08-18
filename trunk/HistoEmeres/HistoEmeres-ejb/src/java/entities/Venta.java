/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Battousai
 */
@Entity
@Table(name = "venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v ORDER BY v.fechaVenta"),
    @NamedQuery(name = "Venta.findByCodigoVenta", query = "SELECT v FROM Venta v WHERE v.codigoVenta = :codigoVenta"),
    @NamedQuery(name = "Venta.findByCantidadVenta", query = "SELECT v FROM Venta v WHERE v.cantidadVenta = :cantidadVenta"),
    @NamedQuery(name = "Venta.findByFechaVenta", query = "SELECT v FROM Venta v WHERE v.fechaVenta = :fechaVenta"),
    @NamedQuery(name = "Venta.findByPeriodoVenta", query = "SELECT v FROM Venta v WHERE v.fechaVenta >= :fechaVenta ")})
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO_VENTA")
    private Integer codigoVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD_VENTA")
    private int cantidadVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_VENTA")
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;
    @JoinColumn(name = "RUT_CLIENTE", referencedColumnName = "RUT_CLIENTE")
    @ManyToOne(optional = false)
    private Cliente rutCliente;
    @JoinColumn(name = "CODIGO_PRODUCTO", referencedColumnName = "CODIGO_PRODUCTO")
    @ManyToOne(optional = false)
    private Producto codigoProducto;

    public Venta() {
    }

    public Venta(Integer codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public Venta(Integer codigoVenta, int cantidadVenta, Date fechaVenta) {
        this.codigoVenta = codigoVenta;
        this.cantidadVenta = cantidadVenta;
        this.fechaVenta = fechaVenta;
    }

    public Integer getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(Integer codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public int getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(int cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Cliente getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(Cliente rutCliente) {
        this.rutCliente = rutCliente;
    }

    public Producto getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Producto codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoVenta != null ? codigoVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.codigoVenta == null && other.codigoVenta != null) || (this.codigoVenta != null && !this.codigoVenta.equals(other.codigoVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Venta[ codigoVenta=" + codigoVenta + " ]";
    }
    
}
