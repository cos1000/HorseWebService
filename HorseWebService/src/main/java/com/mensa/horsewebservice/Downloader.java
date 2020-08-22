/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice;

import com.google.gson.Gson;
import com.mensa.horsewebservice.model.Football.Football;
import com.mensa.horsewebservice.util.FileHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author matt_
 */
public class Downloader {
    private final Logger log = LoggerFactory.getLogger(Downloader.class);
    private final String USER_AGENT = "Mozilla/5.0";
    
    // <editor-fold desc="Testing Main">
    public static void main(String[] args) {
        String url = "https://bet.hkjc.com/football/getJSON.aspx?jsontype=index.aspx";
        String filename = "C:\\Temp\\Downloader_horse.txt";
        Downloader downloader = new Downloader(); 
        try {
            FileHandler.SaveToFile(filename, downloader.GetResponseFromUrl(url)); 
        } catch (IOException e)  {
            e.printStackTrace();
        } 
        /*
        try {
            String response = downloader.GetResponseFromUrl(url); 
            //System.out.println(response);
            response = Converter.ConvertHtmlToString(Converter.ConvertXmlFromString(response)); 
            FileHandler.SaveToFile(filename, response); 
        } catch (IOException e)  {
            e.printStackTrace();
        }
*/
    }
    // </editor-fold>
    
    // <editor-fold desc="Public Tools Methods">
    
    public String GetResponseFromUrl(String url) {
        log.debug("Start GetResponseFromUrl");
        String answer = "";
        try {
            URL iurl = new URL(url);
            //CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            CookieHandler.setDefault(new CookieManager());
            HttpURLConnection uc = (HttpURLConnection)iurl.openConnection();
            uc.setRequestMethod("GET"); 
            uc.setRequestProperty("User-Agent", USER_AGENT);
            //uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            uc.setRequestProperty("Accept", "application/json");
            uc.setRequestProperty("Accept-Encoding", "gzip");
            uc.setDoOutput(true); 
            uc.connect();
            
            try (BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(uc.getInputStream()), StandardCharsets.UTF_8))) {
                String inputLine;
                StringBuilder response = new StringBuilder(); 
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                answer = response.toString(); 
            }
            
            //answer = uc.getContentEncoding(); 
            uc.disconnect(); 
            //log.debug(answer);
        } catch (MalformedURLException e) {
            log.error(String.format("GetResponseFromUrl Error: %s", e.getMessage())); 
        } catch (IOException e) {
            log.error(String.format("GetResponseFromUrl Error: %s", e.getMessage())); 
        }
        log.debug("Finished GetResponseFromUrl");
            
        return answer; 
    }
    
    
    // </editor-fold>
    
}
