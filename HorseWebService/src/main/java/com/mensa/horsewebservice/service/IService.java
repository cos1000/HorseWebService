/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.service;

import java.util.List;

/**
 *
 * @author matt_
 */
public interface IService<T> {
    public boolean add(T record);
    public boolean update(T record);
    public List<T> list();
    public T get(T record);
    public T getById(int id);
    public boolean delete(int id);
}
