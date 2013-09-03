/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;
 
import entities.Usuario;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.annotation.PostConstruct;
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
import javax.naming.InitialContext;
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
    private Usuario usuario;
    private List<Usuario> usuarios;
    private String correo;

    public ManagedBeanUsuario() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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

    @PostConstruct
    public void init() {
        usuarios = usuarioFacade.findAll();
    }

    public void nuevoUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuario;
        Herramientas encripta;
        encripta = new Herramientas();
        contraseña = encripta.encriptaEnMD5(nombre).substring(0, 8);
        if (usuarioFacade.buscarPorNombreUsuario(nombre).isEmpty()) {
            usuario = new Usuario(null, nombre, tipo, encripta.encriptaEnMD5(contraseña));
            usuarioFacade.create(usuario);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario ingresado con éxito", "El usuario: " + nombre + " fue ingresado con éxito."));
            try {
                enviaMail();
            } catch (Exception e) {
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no fue ingresado", "El usuario: " + nombre + " ya había sido ingresado al sistema."));
        }
    }

    public void enviaMail() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp", "diegog.asd@gmail.com");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
        message.setSubject("Tu cuenta en Histoemeres ha sido creada");
        message.setText("Sr(a) " + nombre + ": Le informamos que su cuenta en Histoemeres a sido creada."
                + "\n Para acceder a ella utilice el usuario: " + nombre + " y la contraseña: " + contraseña
                + "\n Recomendamos cambiarla una vez que ingrese por primera vez."
                + "\n Saludos.");
        Transport t = session.getTransport("smtp");
        t.connect("diegog.asd@gmail.com", "all_16940203");
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }
}
