/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Aporte;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Battousai
 */
@Local
public interface AporteFacadeLocal {

    void create(Aporte aporte);

    void edit(Aporte aporte);

    void remove(Aporte aporte);

    Aporte find(Object id);

    List<Aporte> findAll();

    List<Aporte> findRange(int[] range);

    int count();

    boolean buscarAporte(Aporte aporte);
    
}
