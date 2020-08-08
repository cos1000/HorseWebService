/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.FirstTeamToScore;
import java.util.List;

/**
 *
 * @author matt_
 */
public class FirstTeamToScoreHandler implements IHandler<FirstTeamToScore> {
    private RecordHandler<FirstTeamToScore> handler;
    
    public FirstTeamToScoreHandler() {
        handler = new RecordHandler<FirstTeamToScore>(); 
    }
    
    @Override
    public boolean Exist(FirstTeamToScore record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<FirstTeamToScore> GetAll() {
        String sql = "select record from FirstTeamToScore record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public FirstTeamToScore Get(FirstTeamToScore record) {
        String sql = "select record from FirstTeamToScore record where record.ID = :ID";
        List<FirstTeamToScore> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstTeamToScore GetById(long id) {
        String sql = "select record from FirstTeamToScore record where record.idKey = :idKey";
        List<FirstTeamToScore> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstTeamToScore CreateAndReturn(FirstTeamToScore record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(FirstTeamToScore record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(FirstTeamToScore record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(FirstTeamToScore record) {
        return handler.Delete(record); 
    }

    
}
