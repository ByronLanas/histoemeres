/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Battousai
 */
@Embeddable
public class AportePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "MUNICIPIO_APORTE")
    private String municipioAporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_MUNICIPALIDAD")
    @Temporal(TemporalType.DATE)
    private Date fechaMunicipalidad;

    public AportePK() {
    }

    public AportePK(String municipioAporte, Date fechaMunicipalidad) {
        this.municipioAporte = municipioAporte;
        this.fechaMunicipalidad = fechaMunicipalidad;
    }

    public String getMunicipioAporte() {
        return municipioAporte;
    }

    public void setMunicipioAporte(String municipioAporte) {
        this.municipioAporte = municipioAporte;
    }

    public Date getFechaMunicipalidad() {
        return fechaMunicipalidad;
    }

    public void setFechaMunicipalidad(Date fechaMunicipalidad) {
        this.fechaMunicipalidad = fechaMunicipalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (municipioAporte != null ? municipioAporte.hashCode() : 0);
        hash += (fechaMunicipalidad != null ? fechaMunicipalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AportePK)) {
            return false;
        }
        AportePK other = (AportePK) object;
        if ((this.municipioAporte == null && other.municipioAporte != null) || (this.municipioAporte != null && !this.municipioAporte.equals(other.municipioAporte))) {
            return false;
        }
        if ((this.fechaMunicipalidad == null && other.fechaMunicipalidad != null) || (this.fechaMunicipalidad != null && !this.fechaMunicipalidad.equals(other.fechaMunicipalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AportePK[ municipioAporte=" + municipioAporte + ", fechaMunicipalidad=" + fechaMunicipalidad + " ]";
    }
    
}
