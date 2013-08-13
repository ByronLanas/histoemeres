/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Battousai
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();

    List<Usuario> buscarPorNombreUsuario(String nombre);


    
}
