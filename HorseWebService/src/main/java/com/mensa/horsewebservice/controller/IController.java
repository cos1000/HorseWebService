/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.controller;

import org.springframework.ui.Model;

/**
 *
 * @author matt_
 */
public interface IController<T> {
    public String add(T record);
    public String edit(int id, Model model);
    public String list(Model model);
    //public T get(T record);
    public String delete(int id);
}
