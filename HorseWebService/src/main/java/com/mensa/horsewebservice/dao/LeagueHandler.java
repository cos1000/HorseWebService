/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.League;
import java.util.List;

/**
 *
 * @author matt_
 */
public class LeagueHandler implements IHandler<League> {
    private RecordHandler<League> handler;
    
    public LeagueHandler() {
        handler = new RecordHandler<League>(); 
    }
    
    @Override
    public boolean Exist(League record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<League> GetAll() {
        String sql = "select record from League record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public League Get(League record) {
        String sql = "select record from League record where record.leagueID = :leagueID";
        List<League> answer = handler.GetQuery(sql).setParameter("leagueID", record.getLeagueID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public League GetById(long id) {
        String sql = "select record from League record where record.idKey = :idKey";
        List<League> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public League CreateAndReturn(League record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(League record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(League record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(League record) {
        return handler.Delete(record); 
    }

    
}
