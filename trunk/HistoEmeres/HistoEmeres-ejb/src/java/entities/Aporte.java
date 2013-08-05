/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Battousai
 */
@Entity
@Table(name = "aporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aporte.findAll", query = "SELECT a FROM Aporte a"),
    @NamedQuery(name = "Aporte.findByMunicipioAporte", query = "SELECT a FROM Aporte a WHERE a.aportePK.municipioAporte = :municipioAporte"),
    @NamedQuery(name = "Aporte.findByFechaMunicipalidad", query = "SELECT a FROM Aporte a WHERE a.aportePK.fechaMunicipalidad = :fechaMunicipalidad"),
    @NamedQuery(name = "Aporte.findByValorAporte", query = "SELECT a FROM Aporte a WHERE a.valorAporte = :valorAporte")})
public class Aporte implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AportePK aportePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR_APORTE")
    private float valorAporte;

    public Aporte() {
    }

    public Aporte(AportePK aportePK) {
        this.aportePK = aportePK;
    }

    public Aporte(AportePK aportePK, float valorAporte) {
        this.aportePK = aportePK;
        this.valorAporte = valorAporte;
    }

    public Aporte(String municipioAporte, Date fechaMunicipalidad) {
        this.aportePK = new AportePK(municipioAporte, fechaMunicipalidad);
    }

    public AportePK getAportePK() {
        return aportePK;
    }

    public void setAportePK(AportePK aportePK) {
        this.aportePK = aportePK;
    }

    public float getValorAporte() {
        return valorAporte;
    }

    public void setValorAporte(float valorAporte) {
        this.valorAporte = valorAporte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aportePK != null ? aportePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aporte)) {
            return false;
        }
        Aporte other = (Aporte) object;
        if ((this.aportePK == null && other.aportePK != null) || (this.aportePK != null && !this.aportePK.equals(other.aportePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Aporte[ aportePK=" + aportePK + " ]";
    }
    
}
