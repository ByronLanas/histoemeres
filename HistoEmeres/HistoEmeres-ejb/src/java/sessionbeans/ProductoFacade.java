/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Producto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Battousai
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> implements ProductoFacadeLocal {
    @PersistenceContext(unitName = "HistoEmeres-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    @Override
    public List<Producto> buscarPorNombreProducto(String nombre_producto) {
        Query query;
        query = em.createNamedQuery("Producto.findByNombreProducto").setParameter("nombreProducto", nombre_producto); 
        return query.getResultList();
    }    
}
