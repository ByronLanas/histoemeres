/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Egreso;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import sessionbeans.EgresoFacadeLocal;
import sessionbeans.SessionBeanFinanaciero;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanEgreso")
@RequestScoped
public class ManagedBeanEgreso {

    @EJB
    private EgresoFacadeLocal egresoFacade;
    @EJB
    private SessionBeanFinanaciero sessionBeanFinanaciero;
    private String tipoEgreso;
    private Integer valorEgreso;
    private Date fechaEgreso;
    private static Egreso selectedEgreso;
    private List<Egreso> egresosFiltrados;
    private List<Egreso> egresos;
    private Egreso egreso;

    public String getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(String tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }

    public Integer getValorEgreso() {
        return valorEgreso;
    }

    public void setValorEgreso(Integer valorEgreso) {
        this.valorEgreso = valorEgreso;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public static Egreso getSelectedEgreso() {
        return selectedEgreso;
    }

    public static void setSelectedEgreso(Egreso selectedEgreso) {
        ManagedBeanEgreso.selectedEgreso = selectedEgreso;
    }

    public List<Egreso> getEgresos() {
        return egresos;
    }

    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos;
    }

    public Egreso getEgreso() {
        return egreso;
    }

    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }

    /**
     * Creates a new instance of ManagedBeanEgreso
     */
    @PostConstruct
    public void init() {
        egresos = egresoFacade.findAll();
    }

    public void ingresarEgreso() {
        FacesContext context = FacesContext.getCurrentInstance();
        egreso = new Egreso(null, tipoEgreso, valorEgreso, fechaEgreso);
        if (sessionBeanFinanaciero.ingresarEgreso(egreso)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Egreso ingresado con éxito", "El egreso : " + egreso.getTipoEgreso()+ ": " + egreso.getValorEgreso() + " fue ingresado con éxito."));

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El egreso no fue ingresado", "El egreso : " + egreso.getTipoEgreso()+ ": " + egreso.getValorEgreso() +  " no pudo ser ingresado."));
        }
    }

    public ManagedBeanEgreso() {
        Integer valorEgreso = null;
    }
}
