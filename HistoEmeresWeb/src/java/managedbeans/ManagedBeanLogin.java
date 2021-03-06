/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Usuario;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import sessionbeans.UsuarioFacadeLocal;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanLogin")
@SessionScoped
public class ManagedBeanLogin implements Serializable {

    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    private Usuario usuario;
    private String contraseña;
    private String nombre;
    private boolean tipo;
    FacesContext context;

    public ManagedBeanLogin() {
    }

    public void irLogin() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/login.xhtml");
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

 

    public boolean validarNombre(){
        context = FacesContext.getCurrentInstance();
        if (nombre.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falta información:", "Nombre no ingresado"));
            return false;
        }
        if (usuarioFacade.buscarPorNombreUsuario(nombre).isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre incorrecto:", "Verificar nombre si fue ingresado correctamente"));
            return false;
        }

        return true;
    }
    
    public boolean validarContraseña(){
        context = FacesContext.getCurrentInstance();
        Herramientas encripta = new Herramientas();
        if (contraseña.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falta información:", "Contraseña no ingresada"));
            return false;
        }
        if(usuarioFacade.buscarPorNombreUsuario(nombre).get(0).getContrasenaUsuario().compareTo(encripta.encriptaEnMD5(contraseña))!=0){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña incorrecta:", "Verificar contraseña si fue ingresada correctamente"));
            return false;
        }
        return true;
    }

    public void logear() throws IOException {
        context = FacesContext.getCurrentInstance();
        if (validarNombre() && validarContraseña()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Bienvenido "+nombre+":","Inicio de sesión satisfactorio"));
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.getExternalContext().getSessionMap().put("username", nombre);
            tipo=usuarioFacade.buscarPorNombreUsuario(nombre).get(0).getTipoUsuario().compareTo("admin")==0;
            context.getExternalContext().getSessionMap().put("tipo", tipo);
            ManagedBeanMenu mngbn = new ManagedBeanMenu();
            mngbn.irMenu("historiales");
        }


    }
    
    public void deslogear() throws IOException{
        context = FacesContext.getCurrentInstance();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/HistoEmeresWeb");
    }
}
