/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 *
 * @author srikanth.possim
 */
public class CLabel extends Label {
    
    public CLabel(String text){
        super(text);
        setPadding(new Insets(0, 0, 0,7));
        setStyle("-fx-font-family: sans-serif;");
    }
    public CLabel(String text,Color color){
        super(text);
        setPadding(new Insets(0, 0, 0,7));
        setTextFill(color);
        setStyle("-fx-font-family: sans-serif;");
    }
    
    public CLabel(String text , boolean bold){
        super(text);
        setPadding(new Insets(0, 0, 0,7));
        if(bold)
            setStyle("-fx-font-family: sans-serif;-fx-font-weight : bold");
        else
            setStyle("-fx-font-family: sans-serif;");
    }
    
}
