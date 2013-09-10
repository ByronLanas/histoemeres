/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanMenu")
@RequestScoped
public class ManagedBeanMenu {

    private static String pagina = "";
    private static String historiales;
    private static String aportes;
    private static String clientes;
    private static String ventas;
    private static String productos;
    private static String egresos;
    private static String usuarios;

    public ManagedBeanMenu() {


    }

    public String getHistoriales() {
        return historiales;
    }

    public void setHistoriales(String historiales) {
        this.historiales = historiales;
    }

    public String getAportes() {
        return aportes;
    }

    public void setAportes(String aportes) {
        this.aportes = aportes;
    }

    public String getClientes() {
        return clientes;
    }

    public void setClientes(String clientes) {
        this.clientes = clientes;
    }

    public String getVentas() {
        return ventas;
    }

    public void setVentas(String ventas) {
        this.ventas = ventas;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getEgresos() {
        return egresos;
    }

    public void setEgresos(String egresos) {
        this.egresos = egresos;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public void irMenu(String pagina) {
        historiales = "";
        aportes = "";
        clientes = "";
        ventas = "";
        productos = "";
        egresos = "";
        usuarios = "";



        if (getPagina().isEmpty() || getPagina() == null) {
            pagina = "historiales";
        }

        if (pagina.contains("historiales")) {
            historiales = "selected-menu";
        } else if (pagina.contains("Aportes")) {
            aportes = "selected-menu";
        } else if (pagina.contains("Cliente")) {
            clientes = "selected-menu";
        } else if (pagina.contains("Ventas")) {
            ventas = "selected-menu";
        } else if (pagina.contains("Productos")) {
            productos = "selected-menu";
        } else if (pagina.contains("Egreso")) {
            egresos = "selected-menu";
        } else if (pagina.contains("Usuario")) {
            clientes = "selected-menu";
        }

        try {
            setPagina(pagina);
            FacesContext.getCurrentInstance().getExternalContext().redirect("histoemeres.xhtml");




        } catch (IOException ex) {
            Logger.getLogger(ManagedBeanMenu.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }
}
