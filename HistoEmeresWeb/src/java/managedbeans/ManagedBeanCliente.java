/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;
import sessionbeans.ClienteFacadeLocal;
import sessionbeans.SessionBeanComercial;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanCliente")
@RequestScoped
public class ManagedBeanCliente {

    @EJB
    private SessionBeanComercial sessionBeanComercial;
    @EJB
    private ClienteFacadeLocal clienteFacade;
    /**
     * Creates a new instance of ManagedBeanCliente
     */
    private String rut_cliente;
    private String nombre_cliente;
    private List<Cliente> clientes;
    private Cliente cliente;
    private static Cliente clienteAnterior;
    private static Cliente selectedCliente;
    private List<Cliente> clientesFiltrados;
    private static String rutSeleccionado;

    public static Cliente getClienteAnterior() {
        return clienteAnterior;
    }

    public static void setClienteAnterior(Cliente clienteAnterior) {
        ManagedBeanCliente.clienteAnterior = clienteAnterior;
    }

    public String getRutSeleccionado() {
        return rutSeleccionado;
    }

    public void setRutSeleccionado(String rutSeleccionado) {
        this.rutSeleccionado = rutSeleccionado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Cliente> getClientesFiltrados() {
        return clientesFiltrados;
    }

    public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
        this.clientesFiltrados = clientesFiltrados;
    }

    public Cliente getSelectedCliente() {
        return selectedCliente;

    }

    public void setSelectedCliente(Cliente selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getRut_cliente() {
        return rut_cliente;
    }

    public void setRut_cliente(String rut_cliente) {
        this.rut_cliente = rut_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @PostConstruct
    public void init() {
        clientes = clienteFacade.findAll();
    }

    public void insertarCliente() {
        FacesContext context = FacesContext.getCurrentInstance();
        cliente = new Cliente(rut_cliente.toUpperCase(), nombre_cliente);
        if (sessionBeanComercial.insertarCliente(cliente)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente ingresado con éxito", "El cliente : " + cliente.getNombreCliente() + ": " + cliente.getRutCliente() + " fue ingresado con éxito."));
            RequestContext.getCurrentInstance().reset("form:panelCliente");
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El cliente no fue ingresado", "El cliente: " + cliente.getNombreCliente() + ": " + cliente.getRutCliente() + " ya había sido ingresado al sistema."));
        }
    }

    public void modificarCliente() {
        FacesContext context = FacesContext.getCurrentInstance();
        cliente = new Cliente(selectedCliente.getRutCliente(), selectedCliente.getNombreCliente());
        if (sessionBeanComercial.modificarCliente(cliente,clienteAnterior)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente modificado con éxito", "El cliente : " + cliente.getNombreCliente() + ": " + cliente.getRutCliente() + " fue modificado con éxito"));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El cliente no fue modificado", "El cliente: " + cliente.getRutCliente() + "no pudo ser modificado."));
        }
    }
    
    public void guardarAnterior(){
        clienteAnterior = selectedCliente;
    }

    public ManagedBeanCliente() {
        rut_cliente = null;
    }
}
