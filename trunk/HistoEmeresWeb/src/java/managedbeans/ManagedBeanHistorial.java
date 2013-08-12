/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Battousai
 */
@Named(value = "managedBeanHistorial")
@RequestScoped
public class ManagedBeanHistorial implements Serializable{

    private PieChartModel pieModel; 
    
    public ManagedBeanHistorial()  {
        crearPie();
    }
    public PieChartModel getPieModel() {  
        return pieModel;  
    } 
    public void crearPie(){
        pieModel = new PieChartModel();  
        pieModel.set("Brand 1", 540);  
        pieModel.set("Brand 2", 325);  
        pieModel.set("Brand 3", 702);  
        pieModel.set("Brand 4", 421);  
     
    }
    
}
