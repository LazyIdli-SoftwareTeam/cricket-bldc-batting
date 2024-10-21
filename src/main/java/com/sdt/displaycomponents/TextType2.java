/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;


import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author srikanth.possim
 */
public class TextType2 extends Text{
    double x_in_mm=0;
    double y_in_mm=0;
    public TextType2(double x_in_mm , double y_in_mm , String value , Font font , Group pane){
        super(value);
        setFont(font);
        this.x_in_mm=x_in_mm;
        this.y_in_mm=y_in_mm;
        double start_x = (x_in_mm)-(getLayoutBounds().getWidth()/2);
        double start_y = (y_in_mm)+(getLayoutBounds().getHeight()/4);
        setX(start_x);
        setY(start_y);
        pane.getChildren().add(this);
    }
    
    public void setValue(String value){
        //double prev_width = getLayoutBounds().getWidth();
        setText(value);
        //double curr_width = getLayoutBounds().getWidth();
        //System.out.println(prev_width+","+curr_width);
        double start_x = (x_in_mm)-(getLayoutBounds().getWidth()/2);
        setX(start_x);
        setY(y_in_mm);
    }
}
