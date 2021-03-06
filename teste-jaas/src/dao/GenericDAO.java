package dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDAO<T> {
    private final static String UNIT_NAME = "digital";
 
    @PersistenceContext(unitName = UNIT_NAME)
    private EntityManager em;
 
    private Class<T> entityClass;
 
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
 
    public void save(T entity) {
        em.persist(entity);
    }

    public T saveAndReturn(T entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    public void delete(Object id, Class<T> classe) {
        T entityToBeRemoved = em.getReference(classe, id);
 
        em.remove(entityToBeRemoved);
    }
 
    public T update(T entity) {
        return em.merge(entity);
    }
 
    public T find(int entityID) {
        return em.find(entityClass, entityID);
    }
    public T find(long entityID) {
        return em.find(entityClass, entityID);
    }
    public T find(String entityID) {
        return em.find(entityClass, entityID);
    }
 
    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }
 
    // Using the unchecked because JPA does not have a
    // ery.getSingleResult()<T> method
    @SuppressWarnings("unchecked")
    protected T findOneResultNamedQuery(String namedQuery, Map<String, Object> parameters) {
        T result = null;
 
        try {
            Query query = em.createNamedQuery(namedQuery);
 
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
 
            result = (T) query.getSingleResult();
 
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
 
        return result;
    }
 
    @SuppressWarnings("unchecked")
    public List<T> findNamedQuery(String namedQuery, Map<String, Object> parameters) {
        List<T> result = null;
 
        try {
            Query query = em.createNamedQuery(namedQuery);
 
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
 
            result = query.getResultList();
 
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
 
        return result;
    }
    
    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
 
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<T> find(String query, Map<String, Object> parameters) {
        List<T> result = null;
 
        try {
            Query objQuery = em.createQuery(query);
 
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(objQuery, parameters);
            }
 
            result = objQuery.getResultList();
 
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
 
        return result;
    }
    
    @SuppressWarnings("unchecked")
	public T findOneResult(String query, Map<String, Object> parameters) {
        T result = null;
 
        try {
            Query objQuery = em.createQuery(query);
 
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(objQuery, parameters);
            }
 
            result = (T) objQuery.getSingleResult();
 
        } catch (NoResultException n) {
            result = null;
            
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
 
        return result;
    }
    

}