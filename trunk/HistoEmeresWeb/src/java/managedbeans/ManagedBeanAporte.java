/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Objetos.Aporte;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import sessionbeans.SessionBeanFinanaciero;
import sessionbeans.SessionBeanIngreso;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanAporte")
@RequestScoped
public class ManagedBeanAporte {

    private Aporte aporte;
    private String municipio;
    private Date fecha;
    private float valor;

    public Aporte getAporte() {
        return aporte;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setAporte(Aporte aporte) {
        this.aporte = aporte;
    }

    /**
     * Creates a new instance of ManagedBeanAporte
     */
    public ManagedBeanAporte() {
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int insertarAporte() {
        boolean flag = verificarValor(valor);
        if(!flag) return 0;
        Aporte nuevoAporte = new Aporte(municipio, valor, fecha);
        SessionBeanIngreso ingreso = new SessionBeanIngreso();
        
        return 1;
    }

    public boolean verificarValor(float valor) {
        if (valor >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
