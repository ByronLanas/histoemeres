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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Battousai
 */
@Entity
@Table(name = "egreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egreso.findAll", query = "SELECT e FROM Egreso e"),
    @NamedQuery(name = "Egreso.findByCodigoEgreso", query = "SELECT e FROM Egreso e WHERE e.codigoEgreso = :codigoEgreso"),
    @NamedQuery(name = "Egreso.findByTipoEgreso", query = "SELECT e FROM Egreso e WHERE e.tipoEgreso = :tipoEgreso"),
    @NamedQuery(name = "Egreso.findByValorEgreso", query = "SELECT e FROM Egreso e WHERE e.valorEgreso = :valorEgreso"),
    @NamedQuery(name = "Egreso.findByFechaEgreso", query = "SELECT e FROM Egreso e WHERE e.fechaEgreso = :fechaEgreso")})
public class Egreso implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR_EGRESO")
    private int valorEgreso;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO_EGRESO")
    private Integer codigoEgreso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TIPO_EGRESO")
    private String tipoEgreso;
    @Column(name = "FECHA_EGRESO")
    @Temporal(TemporalType.DATE)
    private Date fechaEgreso;

    public Egreso() {
    }

    public Egreso(Integer codigoEgreso) {
        this.codigoEgreso = codigoEgreso;
    }

    public Egreso(Integer codigoEgreso, String tipoEgreso, int valorEgreso) {
        this.codigoEgreso = codigoEgreso;
        this.tipoEgreso = tipoEgreso;
        this.valorEgreso = valorEgreso;
    }

    public Egreso(Integer codigoEgreso, String tipoEgreso, int valorEgreso, Date fechaEgreso) {
        this.codigoEgreso = codigoEgreso;
        this.tipoEgreso = tipoEgreso;
        this.valorEgreso = valorEgreso;
        this.fechaEgreso = fechaEgreso;
    }

    
    public Integer getCodigoEgreso() {
        return codigoEgreso;
    }

    public void setCodigoEgreso(Integer codigoEgreso) {
        this.codigoEgreso = codigoEgreso;
    }

    public String getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(String tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoEgreso != null ? codigoEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egreso)) {
            return false;
        }
        Egreso other = (Egreso) object;
        if ((this.codigoEgreso == null && other.codigoEgreso != null) || (this.codigoEgreso != null && !this.codigoEgreso.equals(other.codigoEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Egreso[ codigoEgreso=" + codigoEgreso + " ]";
    }

    public int getValorEgreso() {
        return valorEgreso;
    }

    public void setValorEgreso(int valorEgreso) {
        this.valorEgreso = valorEgreso;
    }
    
}
