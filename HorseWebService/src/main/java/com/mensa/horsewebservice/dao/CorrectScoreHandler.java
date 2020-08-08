/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.CorrectScore;
import java.util.List;

/**
 *
 * @author matt_
 */
public class CorrectScoreHandler implements IHandler<CorrectScore> {
    private RecordHandler<CorrectScore> handler;
    
    public CorrectScoreHandler() {
        handler = new RecordHandler<CorrectScore>(); 
    }
    
    @Override
    public boolean Exist(CorrectScore record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<CorrectScore> GetAll() {
        String sql = "select record from CorrectScore record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public CorrectScore Get(CorrectScore record) {
        String sql = "select record from CorrectScore record where record.ID = :ID";
        List<CorrectScore> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public CorrectScore GetById(long id) {
        String sql = "select record from CorrectScore record where record.idKey = :idKey";
        List<CorrectScore> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public CorrectScore CreateAndReturn(CorrectScore record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(CorrectScore record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(CorrectScore record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(CorrectScore record) {
        return handler.Delete(record); 
    }

    
}
