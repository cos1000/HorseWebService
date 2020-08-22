/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.FootballResult;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author matt_
 */
public class FootballResultHandler implements IHandler<FootballResult> {
    private RecordHandler<FootballResult> handler;
    
    public FootballResultHandler() {
        handler = new RecordHandler<FootballResult>(); 
    }
    
    @Override
    public boolean Exist(FootballResult record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<FootballResult> GetAll() {
        String sql = "select record from FootballResult record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public FootballResult Get(FootballResult record) {
        String sql = "select record from FootballResult record where record.idKey = :idKey";
        List<FootballResult> answer = handler.GetQuery(sql).setParameter("idKey", record.getIdKey()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FootballResult GetById(long id) {
        String sql = "select record from FootballResult record where record.idKey = :idKey";
        List<FootballResult> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FootballResult CreateAndReturn(FootballResult record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(FootballResult record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(FootballResult record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(FootballResult record) {
        return handler.Delete(record); 
    }

    public boolean Delete() {
        String sql = "delete from FootballResult record where record.created_at = null";
        Query query = RecordHandler.getEntityManager().createNativeQuery(sql); 
        query.executeUpdate(); 
        return true; 
    }
    
}
