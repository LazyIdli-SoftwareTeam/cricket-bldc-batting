/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import com.sdt.serial.HandleSerial;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class PanButton extends Group{
    public PanButton(double width , double height){

        double points1[]={0,height*0.32,width*0.2,0,width*0.2,height*0.16,width*0.7,height*0.16,width*0.7,height*0.48,width*0.2,height*0.48,width*0.2,height*0.64,0,height*0.32};
        Polygon off_arrow = new Polygon(points1);
        off_arrow.setFill(Color.rgb(178, 193, 219));
        off_arrow.setStroke(Color.WHITE);
        Group group1 = new Group();
        group1.getChildren().add(off_arrow);
        
        double points2[]={width,height*0.68,
            width*0.8,height*0.36,
            width*0.8,height*0.52,
            width*0.3,height*0.52,
            width*0.3,height*0.84,
            width*0.8,height*0.84,
            width*0.8,height,
            width,height*0.68};
        Polygon leg_arrow = new Polygon(points2);
        leg_arrow.setFill(Color.rgb(178, 193, 219));
        leg_arrow.setStroke(Color.WHITE);
        Group group2 = new Group();
        group2.getChildren().add(leg_arrow);
        
        Font f_type = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.1);
        TextType2 t_off = new TextType2(width*0.45, height*0.34, "OFF", f_type, group1);
        TextType2 t_leg = new TextType2(width*0.55, height*0.70, "LEG", f_type, group2);
        getChildren().add(group1);
        getChildren().add(group2);
        
        group1.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //HandleEvents.handleEvent(Variables.button_type_pan, Variables.option_off);
                HandleSerial.handleCom(HandleSerial.pan_left);
            }
        });
        group1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                off_arrow.setStroke(Color.BLACK);
            }
        });

        group1.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                off_arrow.setStroke(Color.WHITE);
            }
        });
        
        
        group2.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //HandleEvents.handleEvent(Variables.button_type_pan, Variables.option_leg);
                HandleSerial.handleCom(HandleSerial.pan_right);
            }
        });
        group2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                leg_arrow.setStroke(Color.BLACK);
            }
        });

        group2.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                leg_arrow.setStroke(Color.WHITE);
            }
        });
    }
}
