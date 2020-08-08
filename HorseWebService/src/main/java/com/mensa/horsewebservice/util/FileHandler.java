/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author matt_
 */
public class FileHandler {
    
    public static boolean AppendStringToFile(String filename, String content) {
        File file = new File(filename);
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(content);
            fileWriter.write("\n"); 
            fileWriter.flush();
            return true; 
        } catch (IOException ex) {
            ex.printStackTrace();
            return false; 
        }
    }
    
    public static boolean SaveToFile(String filename, String content) throws IOException {
        try (Writer fstream = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8)) {
            fstream.write(content);
            fstream.flush();
            return true; 
        }
        
//        File file = new File(filename);
//        try (FileWriter fileWriter = new FileWriter(file, false)) {
//            fileWriter.write(content);
//            fileWriter.flush();
//            return true; 
//        } 
    }
    
    public static String ReadFromFile(String filename) throws IOException {
        StringBuilder answer = new StringBuilder();
        File file = new File(filename);
        try (FileReader fileReader = new FileReader(file)) {
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while (bufferedReader.ready()) {
                    answer.append(bufferedReader.readLine()).append("\n"); 
                }
            }
        } 
        return answer.toString(); 
    }
    
}
