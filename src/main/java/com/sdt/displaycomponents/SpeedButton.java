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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class SpeedButton extends Group{
    
    public SpeedButton(double width , double height){
        
        Font f_type = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.1);
        Group group1 = new Group();
        final Rectangle inc_rect = new Rectangle(0, 0, width, height*0.2);
        inc_rect.setFill(Color.rgb(79, 129, 189));
        inc_rect.setStroke(Color.WHITE);
        group1.getChildren().add(inc_rect);
        TextType2 t_inc = new TextType2(width/2, height*0.1, "INCREASE", f_type, group1);
        getChildren().add(group1);
        final Rectangle speed_rect = new Rectangle(0, height*0.4, width, height*0.2);
        speed_rect.setFill(Color.rgb(79, 129, 189));
        speed_rect.setStroke(Color.WHITE);
        getChildren().add(speed_rect);
        final TextType2 t_speed = new TextType2(width/2, height*0.5, "SPEED", f_type, this);
        Group group2 = new Group();
        final Rectangle dec_rect = new Rectangle(0, height*0.8, width, height*0.2);
        dec_rect.setFill(Color.rgb(79, 129, 189));
        dec_rect.setStroke(Color.WHITE);
        group2.getChildren().add(dec_rect);
        TextType2 t_dec = new TextType2(width/2, height*0.9, "DECREASE", f_type, group2);
        getChildren().add(group2);
        
        double arrowidth = width*0.2;
        double mid_point = width/2;
        double points1[]={mid_point-(arrowidth/2),height*0.4,
                            mid_point-(arrowidth/2),height*0.3,
                            mid_point-(arrowidth),height*0.3,
                            mid_point,height*0.225,
                            mid_point+(arrowidth),height*0.3,
                            mid_point+(arrowidth/2),height*0.3,
                            mid_point+(arrowidth/2),height*0.4};
        Polygon up_arrow = new Polygon(points1);
        up_arrow.setFill(Color.rgb(178, 193, 219));
        getChildren().add(up_arrow);
        
        double points2[]={mid_point-(arrowidth/2),height*0.6,
                            mid_point-(arrowidth/2),height*0.7,
                            mid_point-(arrowidth),height*0.7,
                            mid_point,height*0.775,
                            mid_point+(arrowidth),height*0.7,
                            mid_point+(arrowidth/2),height*0.7,
                            mid_point+(arrowidth/2),height*0.6};
        Polygon down_arrow = new Polygon(points2);
        down_arrow.setFill(Color.rgb(178, 193, 219));
        getChildren().add(down_arrow);
        
        group1.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //HandleEvents.handleEvent(Variables.button_type_speed, Variables.option_up);
                if(HandleEvents.machineDataBean.getSet_speed()<150){
                    HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()+1);
                    HandleSerial.handleCom(HandleSerial.update_speed);
                    t_speed.setText(HandleEvents.machineDataBean.getSet_speed()+"");
                }
            }
        });
        group1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                inc_rect.setStroke(Color.BLACK);
                t_speed.setText(HandleEvents.machineDataBean.getSet_speed()+"");
            }
        });

        group1.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                inc_rect.setStroke(Color.WHITE);
                t_speed.setText("SPEED");
            }
        });
        
        
        group2.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //HandleEvents.handleEvent(Variables.button_type_speed, Variables.option_down);
                if(HandleEvents.machineDataBean.getSet_speed()>50){
                    HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()-1);
                    HandleSerial.handleCom(HandleSerial.update_speed);
                    t_speed.setText(HandleEvents.machineDataBean.getSet_speed()+"");
                }
            }
        });
        group2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                dec_rect.setStroke(Color.BLACK);
                t_speed.setText(HandleEvents.machineDataBean.getSet_speed()+"");
            }
        });

        group2.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                dec_rect.setStroke(Color.WHITE);
                t_speed.setText("SPEED");
            }
        });
        
        
    }
    
}
