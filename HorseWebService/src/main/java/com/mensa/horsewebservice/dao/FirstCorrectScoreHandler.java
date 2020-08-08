/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.FirstCorrectScore;
import java.util.List;

/**
 *
 * @author matt_
 */
public class FirstCorrectScoreHandler implements IHandler<FirstCorrectScore> {
    private RecordHandler<FirstCorrectScore> handler;
    
    public FirstCorrectScoreHandler() {
        handler = new RecordHandler<FirstCorrectScore>(); 
    }
    
    @Override
    public boolean Exist(FirstCorrectScore record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<FirstCorrectScore> GetAll() {
        String sql = "select record from FirstCorrectScore record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public FirstCorrectScore Get(FirstCorrectScore record) {
        String sql = "select record from FirstCorrectScore record where record.ID = :ID";
        List<FirstCorrectScore> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstCorrectScore GetById(long id) {
        String sql = "select record from FirstCorrectScore record where record.idKey = :idKey";
        List<FirstCorrectScore> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstCorrectScore CreateAndReturn(FirstCorrectScore record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(FirstCorrectScore record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(FirstCorrectScore record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(FirstCorrectScore record) {
        return handler.Delete(record); 
    }

    
}
