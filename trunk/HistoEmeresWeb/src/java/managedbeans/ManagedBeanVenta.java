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
@Named(value = "managedBeanVenta")
@RequestScoped
public class ManagedBeanVenta {

    /**
     * Creates a new instance of ManagedBeanVenta
     */
    public ManagedBeanVenta() {
    }
}
