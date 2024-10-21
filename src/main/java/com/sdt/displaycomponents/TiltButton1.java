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
public class TiltButton1 extends Group{
    public TiltButton1(double width , double height){
        double arrowidth = width*0.5;
        double mid_point = width/2;
        double points1[]={mid_point-(arrowidth/2),height*0.45,
                            mid_point-(arrowidth/2),height*0.2,
                            mid_point-(arrowidth),height*0.2,
                            mid_point,height*0.025,
                            mid_point+(arrowidth),height*0.2,
                            mid_point+(arrowidth/2),height*0.2,
                            mid_point+(arrowidth/2),height*0.45};
        Polygon up_arrow = new Polygon(points1);
        up_arrow.setFill(Color.rgb(178, 193, 219));
        up_arrow.setStroke(Color.WHITE);
        Group group1 = new Group();
        group1.getChildren().add(up_arrow);
        
        double points2[]={mid_point-(arrowidth/2),height*0.55,
                            mid_point-(arrowidth/2),height*0.8,
                            mid_point-(arrowidth),height*0.8,
                            mid_point,height*0.975,
                            mid_point+(arrowidth),height*0.8,
                            mid_point+(arrowidth/2),height*0.8,
                            mid_point+(arrowidth/2),height*0.55};
        Polygon down_arrow = new Polygon(points2);
        down_arrow.setFill(Color.rgb(178, 193, 219));
        down_arrow.setStroke(Color.WHITE);
        Group group2 = new Group();
        group2.getChildren().add(down_arrow);
        
        Font f_type = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.1);
        TextType2 t_up = new TextType2(width/2, height*0.15, "UP", f_type, group1);
        TextType2 t_down = new TextType2(width/2, height*0.85, "DOWN", f_type, group2);
        getChildren().add(group1);
        getChildren().add(group2);
        
        group1.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //HandleEvents.handleEvent(Variables.button_type_tilt, Variables.option_up);
                HandleSerial.handleCom(HandleSerial.tilt_up);
            }
        });
        group1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                up_arrow.setStroke(Color.BLACK);
            }
        });

        group1.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                up_arrow.setStroke(Color.WHITE);
            }
        });
        
        
        group2.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //HandleEvents.handleEvent(Variables.button_type_tilt, Variables.option_down);
                HandleSerial.handleCom(HandleSerial.tilt_down);
            }
        });
        group2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                down_arrow.setStroke(Color.BLACK);
            }
        });

        group2.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                down_arrow.setStroke(Color.WHITE);
            }
        });
    }
}
