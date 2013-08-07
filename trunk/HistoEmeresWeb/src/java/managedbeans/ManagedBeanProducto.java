/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanProducto")
@RequestScoped
public class ManagedBeanProducto {

    /**
     * Creates a new instance of ManagedBeanProducto
     */
    public ManagedBeanProducto() {
    }
}
