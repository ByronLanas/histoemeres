/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Aporte;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Battousai
 */
@Stateless
public class AporteFacade extends AbstractFacade<Aporte> implements AporteFacadeLocal {
    @PersistenceContext(unitName = "HistoEmeres-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AporteFacade() {
        super(Aporte.class);
    }

    @Override
    public boolean buscarAporte(Aporte aporte) {
        Query query;
        query= em.createNamedQuery("Aporte.findByMunicipioFechaMunicipalidad").setParameter("municipioAporte", aporte.getAportePK().getMunicipioAporte()).setParameter("fechaMunicipalidad", aporte.getAportePK().getFechaMunicipalidad());
        if(query.getResultList().isEmpty())
            return true;
        else            
            return false;
    }

    
}
