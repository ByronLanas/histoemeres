/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Cliente;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private Integer rut_cliente;
    private String nombre_cliente;
    private List<Cliente> clientes;
    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getRut_cliente() {
        return rut_cliente;
    }

    public void setRut_cliente(Integer rut_cliente) {
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
        cliente = new Cliente(rut_cliente, nombre_cliente);
        if (sessionBeanComercial.insertarCliente(cliente)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente ingresado con éxito", "El cliente : " + cliente.getNombreCliente() + ": "+ cliente.getRutCliente() + " fue ingresado con éxito"));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El cliente no fue ingresado", "El cliente: " + cliente + " ya había sido ingresado"));
        }
    }

    public ManagedBeanCliente() {
        rut_cliente = null;
    }
}
