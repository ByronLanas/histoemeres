/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Egreso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Battousai
 */
@Local
public interface EgresoFacadeLocal {

    void create(Egreso egreso);

    void edit(Egreso egreso);

    void remove(Egreso egreso);

    Egreso find(Object id);

    List<Egreso> findAll();

    List<Egreso> findRange(int[] range);

    int count();
    
}
