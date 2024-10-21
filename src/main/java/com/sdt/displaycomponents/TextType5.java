/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;


import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author srikanth.possim
 */
public class TextType5 extends Group{
    double width=0;
    double height=0;
    Text text =null;
    public TextType5(double width , double height , String value){
        Rectangle rect = new Rectangle(0, 0, width, height);
        rect.setFill(Color.rgb(255, 231, 134));
        rect.setOpacity(50);
        getChildren().add(rect);
        Font font = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.5);
        text = new Text( value);
        getChildren().add(text);
        text.setFont(font);
        this.width=width;
        this.width=height;
        double start_x = (width*0.1);
        double start_y = (height/4)+(getLayoutBounds().getHeight()/4);
        text.setX(start_x);
        text.setY(start_y);
    }
    
    public TextType5(double width , double height , String value , Color textcolor, Color bgcolor){
        Rectangle rect = new Rectangle(0, 0, width, height);
        rect.setFill(bgcolor);
        rect.setOpacity(50);
        getChildren().add(rect);
        Font font = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.5);
        text = new Text( value);
        getChildren().add(text);
        text.setFill(textcolor);
        text.setFont(font);
        this.width=width;
        this.width=height;
        double start_x = (width*0.1);
        double start_y = (height/4)+(getLayoutBounds().getHeight()/4);
        text.setX(start_x);
        text.setY(start_y);
    }
    
    public void setValue(String value){
        //double prev_width = getLayoutBounds().getWidth();
        text.setText(value);
        //double curr_width = getLayoutBounds().getWidth();
        //System.out.println(prev_width+","+curr_width);
        double start_x = (width)-(getLayoutBounds().getWidth()/2);
        double start_y = (height)+(getLayoutBounds().getHeight()/4);
        text.setX(start_x);
        text.setY(start_y);
    }
}
