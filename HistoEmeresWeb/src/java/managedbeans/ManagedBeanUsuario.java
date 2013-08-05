/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Usuario;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import sessionbeans.UsuarioFacadeLocal;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanUsuario")
@RequestScoped
public class ManagedBeanUsuario {
    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    private String nombre;
    private String tipo;
    private String contraseña;
    private int codigo;
            
    public ManagedBeanUsuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public void nuevoUsuario(){
        Usuario usuario;
        EncriptarMD5 encripta;
        encripta=new EncriptarMD5();
        usuario = new Usuario(null, nombre, "admin", encripta.encriptaEnMD5( contraseña));
        usuarioFacade.create(usuario);
        
        
    }
}
