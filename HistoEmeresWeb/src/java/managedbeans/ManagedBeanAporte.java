    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;


import entities.Aporte;
import entities.AportePK;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Min;
import sessionbeans.AporteFacadeLocal;
import sessionbeans.SessionBeanFinanaciero;


/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanAporte")
@RequestScoped
public class ManagedBeanAporte implements Serializable{
    @EJB
    private SessionBeanFinanaciero sessionBeanFinanaciero;
    @EJB
    private AporteFacadeLocal aporteFacade;


    private String municipio;
    private Date fecha;
    @Min(value = 0,message = "El valor del aporte debe ser mayor a 0")
    private Integer valor;
    private Aporte aporte;
    private static Aporte aporteAnterior;
    private List<Aporte> aportes;
    private AportePK pk;
    private static Aporte selectedAporte;
    private List<Aporte> aportesFiltrados;

    public Aporte getSelectedAporte() {
        return selectedAporte;
    }

    public void setSelectedAporte(Aporte selectedAporte) {
        this.selectedAporte = selectedAporte;
    }

    public Aporte getAporteAnterior() {
        return aporteAnterior;
    }

    public void setAporteAnterior(Aporte aporteAnterior) {
        this.aporteAnterior = aporteAnterior;
    }

    public List<Aporte> getAportesFiltrados() {
        return aportesFiltrados;
    }

    public void setAportesFiltrados(List<Aporte> aportesFiltrados) {
        this.aportesFiltrados = aportesFiltrados;
    }

    public Date getFecha() {
        return fecha;
    }

    public Aporte getAporte() {
        return aporte;
    }

    public void setAporte(Aporte aporte) {
        this.aporte = aporte;
    }

    public List<Aporte> getAportes() {
        return aportes;
    }

    public void setAportes(List<Aporte> aportes) {
        this.aportes = aportes;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }



    public boolean verificarValor(Integer valor) {
        if (valor >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    @PostConstruct
    public void init(){
        aportes=aporteFacade.findAll();
    }

    public AportePK getPk() {
        return pk;
    }

    public void setPk(AportePK pk) {
        this.pk = pk;
    }

    public void insertarAporte(){
        FacesContext context = FacesContext.getCurrentInstance();
        fecha.setDate(1);
        pk=new AportePK(municipio, fecha);
        aporte=new Aporte(pk, valor);      
        if(sessionBeanFinanaciero.insertarAporte(aporte)){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aporte ingresado con éxito", "El aporte de la municipalidad de: "+municipio+ " fue ingresado con éxito"));
        }
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"El aporte no fue ingresado", "El aporte de la municipalidad de: "+municipio+ " ya había sido ingresado"));
    }
    
    public void modificarAporte() {
        Herramientas herramienta=new Herramientas();
        FacesContext context = FacesContext.getCurrentInstance();
        fecha=selectedAporte.getAportePK().getFechaMunicipalidad();
        fecha.setDate(1);
        pk = new AportePK(selectedAporte.getAportePK().getMunicipioAporte(), fecha);
        aporte = new Aporte(pk, selectedAporte.getValorAporte());
        
        
        if (sessionBeanFinanaciero.modificarAporte(aporte,aporteAnterior)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aporte modificado con éxito", "El aporte : " + aporte.getAportePK().getMunicipioAporte() + "; " + herramienta.fechaConPalabras( aporte.getAportePK().getFechaMunicipalidad()) + ": " + aporte.getValorAporte() + " fue modificado con éxito"));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El aporte no fue modificado", "El aporte: " + aporte.getAportePK().getMunicipioAporte() + ": " + aporte.getValorAporte() + "no pudo ser modificado."));
        }
    }
    public void guardarAnterior(){
        aporteAnterior=selectedAporte;
    }

    public ManagedBeanAporte() {
        valor = null;
    }
}
