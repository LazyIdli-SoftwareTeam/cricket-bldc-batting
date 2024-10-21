/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author srikanth.possim
 */
public class TextType1 extends Text{
    
    public TextType1(double x_in_px , double y_in_px , String value , Font font , Pane pane){
        super(x_in_px,y_in_px, value);
        setFont(font);
        pane.getChildren().add(this);
    }
    public TextType1(double x_in_px , double y_in_px , String value , Font font , Pane pane,Color color){
        super(x_in_px,y_in_px, value);
        setFont(font);
        setStroke(color);
        setFill(color);
        pane.getChildren().add(this);
    }
    
}
