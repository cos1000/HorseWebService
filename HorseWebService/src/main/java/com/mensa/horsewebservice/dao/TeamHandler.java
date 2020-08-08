/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.Team;
import java.util.List;

/**
 *
 * @author matt_
 */
public class TeamHandler implements IHandler<Team> {
    private RecordHandler<Team> handler;
    
    public TeamHandler() {
        handler = new RecordHandler<Team>(); 
    }
    
    @Override
    public boolean Exist(Team record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<Team> GetAll() {
        String sql = "select record from Team record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public Team Get(Team record) {
        String sql = "select record from Team record where record.teamID = :teamID";
        List<Team> answer = handler.GetQuery(sql).setParameter("teamID", record.getTeamID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public Team GetById(long id) {
        String sql = "select record from Team record where record.idKey = :idKey";
        List<Team> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public Team CreateAndReturn(Team record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(Team record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(Team record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(Team record) {
        return handler.Delete(record); 
    }

    
}
