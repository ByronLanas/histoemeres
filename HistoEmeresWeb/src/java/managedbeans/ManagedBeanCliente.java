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
@Named(value = "managedBeanCliente")
@RequestScoped
public class ManagedBeanCliente {

    /**
     * Creates a new instance of ManagedBeanCliente
     */
    public ManagedBeanCliente() {
    }
}
