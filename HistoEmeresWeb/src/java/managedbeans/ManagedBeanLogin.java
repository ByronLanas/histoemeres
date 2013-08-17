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

    public void logear(ActionEvent actionEvent) throws IOException {
        usuario = new Usuario();
        usuario.setContrasenaUsuario(contraseña);
        usuario.setNombreUsuario(nombre);
        List<Usuario> usuarios;
        usuarios = usuarioFacade.buscarPorNombreUsuario(nombre);
        Iterator<Usuario> it = usuarios.iterator();

        if (usuarios.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("")); //usuario no existe
        } else if (it.next().getContrasenaUsuario() == contraseña) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("")); //login exitoso
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("")); //contraseña incorrecta
            
            //lo q sigue debe ir en la condicion anterior de login exitoso
            
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", nombre);
            ManagedBeanMenu mngbn = new ManagedBeanMenu();

            mngbn.irMenu("historiales");

        }
    }
}
