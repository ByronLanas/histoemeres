/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Usuario;
import java.util.Properties;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    private String correo;
            
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuario;
        Herramientas encripta;
        encripta=new Herramientas();
        if(usuarioFacade.buscarPorNombreUsuario(nombre).isEmpty()){
            enviaMail();
            usuario = new Usuario(null, nombre, tipo, encripta.encriptaEnMD5( contraseña));
            usuarioFacade.create(usuario);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Usuario ingresado con éxito", "El usuario: "+nombre+ " fue ingresado con éxito"));
        }
        else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario no fue ingresado", "El usuario: "+nombre+ " ya había sido ingresado"));
        }                      
    }
    
    public void enviaMail(){
        FacesContext context = FacesContext.getCurrentInstance();
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "localhost");
        Session session = Session.getDefaultInstance(props);
        String msgBody;
        Herramientas encripta;
        encripta = new Herramientas();
        
        contraseña = encripta.encriptaEnMD5(nombre);
        msgBody = "Bienvenido a HistoEmeres."
                + "Su contraseña es: "+contraseña;
        
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("noreply@histoemeres.com"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(correo));
            msg.setSubject("Tu cuenta de HistoEmeres a sido activada");
            msg.setText(msgBody);
            Transport.send(msg);
        }
        catch (AddressException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario no fue ingresado", "El usuario: "+nombre+ " no se envio el mail por error de dirección"));
        } catch (MessagingException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario no fue ingresado", "El usuario: "+correo+ " no se envio el mail por error de mensaje"));
        }
    }
}
