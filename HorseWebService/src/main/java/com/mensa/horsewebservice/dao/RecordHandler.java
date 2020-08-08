/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matt_
 */
public class RecordHandler<T> {
    private static Logger log = LoggerFactory.getLogger(RecordHandler.class);
    private static EntityManagerFactory entityManagerFactory; 
    private static EntityManager entityManager; 

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory( "org.hibernate.share.jpa" );
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    @Override
    public void finalize() {
        //entityManager.close();
        //entityManagerFactory.close();
    }
    
    public static void setEntityManager() {
        if (!entityManager.isOpen()) {
            entityManager.close(); 
            log.info("Create Entity Manager");
            entityManager = entityManagerFactory.createEntityManager();
        }        
    }
    
    public static EntityManager getEntityManager() {
        return entityManager; 
    }
    
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory; 
    }
    
    public boolean CreateOrUpdate(T record) {
        log.debug("CreateOrUpdate");
        boolean answer = true; 
        setTransaction();
        try {
            //entityManager.persist(record);
            entityManager.merge(record);
            setCommit();
        } catch (Exception e) {
            setRollback(); 
            answer = MergeCreateOrUpdate(record); 
        }
        return answer; 
    }

    public boolean Delete(T record) {
        boolean answer = true; 
        setTransaction();
        try {
            entityManager.remove(record);
            setCommit();
        } catch (Exception e) {
            setRollback(); 
            answer = MergeAndDelete(record); 
        }
        return answer; 
    }

    public boolean MergeCreateOrUpdate(T record) {
        log.debug("CreateOrUpdate");
        boolean answer = true; 
        setTransaction();
        try {
            record = entityManager.merge(record); 
            entityManager.persist(record);
            setCommit();
        } catch (Exception e) {
            if (e != null) log.error("CreateOrUpdate", e);
            setRollback(); 
            answer = false; 
        }
        return answer; 
    }

    private boolean MergeAndDelete(T record) {
        boolean answer = true; 
        setTransaction();
        try {
            record = entityManager.merge(record); 
            entityManager.remove(record);
            setCommit();
        } catch (Exception e) {
            if (e != null) log.error("Delete", e);
            setRollback(); 
            answer = false; 
        }
        return answer; 
    }

    public List<T> GetList(String sql) {
        return GetQuery(sql).getResultList(); 
    }
    
    public static Query GetQuery(String sql) {
        setEntityManager(); 
        return entityManager.createQuery(sql); 
    }
        
    public static void setTransaction() {
        if (entityManager.getTransaction().isActive())  {
            setRollback(); 
        }
        setEntityManager(); 
        entityManager.getTransaction().begin();
    }

    public static void setRollback() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }
    
    public static void setCommit() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }
    
}
