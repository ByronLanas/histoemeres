/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Venta;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Battousai
 */
@Local
public interface VentaFacadeLocal {

    void create(Venta venta);

    void edit(Venta venta);

    void remove(Venta venta);

    Venta find(Object id);

    List<Venta> findAll();

    List<Venta> findRange(int[] range);

    int count();

    List<Venta> buscarPorPeriodo(Date inicio, Date fin);
    
}
