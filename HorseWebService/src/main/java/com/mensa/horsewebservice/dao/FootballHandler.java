/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.Football;
import java.util.List;

/**
 *
 * @author matt_
 */
public class FootballHandler implements IHandler<Football> {
    private RecordHandler<Football> handler;
    
    public FootballHandler() {
        handler = new RecordHandler<Football>(); 
    }
    
    @Override
    public boolean Exist(Football record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<Football> GetAll() {
        String sql = "select record from Football record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public Football Get(Football record) {
        String sql = "select record from Football record where record.matchID = :MatchID";
        List<Football> answer = handler.GetQuery(sql).setParameter("MatchID", record.getMatchID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public Football GetById(long id) {
        String sql = "select record from Football record where record.idKey = :idKey";
        List<Football> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public Football CreateAndReturn(Football record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(Football record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(Football record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(Football record) {
        return handler.Delete(record); 
    }

    
}
