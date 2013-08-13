/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;


import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;


/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanAporte")
@RequestScoped
public class ManagedBeanAporte {


    private String municipio;
    private Date fecha;
    private float valor;


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



    public boolean verificarValor(float valor) {
        if (valor >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
