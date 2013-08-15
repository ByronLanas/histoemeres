    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;


import entities.Aporte;
import entities.AportePK;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import sessionbeans.AporteFacadeLocal;
import sessionbeans.SessionBeanFinanaciero;


/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanAporte")
@RequestScoped
public class ManagedBeanAporte {
    @EJB
    private SessionBeanFinanaciero sessionBeanFinanaciero;
    @EJB
    private AporteFacadeLocal aporteFacade;


    private String municipio;
    private Date fecha;
    private float valor;
    private Aporte aporte;
    private List<Aporte> aportes;
    private AportePK pk;


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
        pk=new AportePK(municipio, fecha);
        aporte=new Aporte(pk, valor);      
        if(sessionBeanFinanaciero.insertarAporte(aporte)){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aporte ingresado con éxito", "El aporte de la municipalidad de: "+municipio+ " fue ingresado con éxito"));
        }
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"El aporte no fue ingresado", "El aporte de la municipalidad de: "+municipio+ " ya había sido ingresado"));
        
    }
}
