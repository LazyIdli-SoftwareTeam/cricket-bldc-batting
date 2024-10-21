/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import com.sdt.system.ErrorAlert;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import zapcricketsimulator.HandleEvents;
//import zapcricketsimulator.MediaStage;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class RunSelector1 extends Group{
    int type = 0;
    public static final int straight_side=1;
    public static final int leg_side=2;
    public static final int off_side=3;
    Polygon straight =null,legside=null,offside=null;
    boolean active1=false;
    boolean active2=false;
    boolean active3=false;
    public RunSelector1(double x_pos , double y_pos , double inner_radius,double outer_radius , int type){
        this.type = type;
        straight = new Polygon(getPoints(31, 149, x_pos, y_pos, inner_radius, outer_radius));
        straight.setFill(Color.RED);
        getChildren().add(straight);
        straight.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //straight.setFill(Color.WHITE);
                straight.setScaleX(1.1);
                straight.setScaleY(1.1);
                active1=true;
            }
        });
        straight.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //straight.setFill(Color.TRANSPARENT);
                straight.setScaleX(1);
                straight.setScaleY(1);
                active1=false;
            }
        });
        straight.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                straight.setScaleX(1);
                straight.setScaleY(1);
                HandleEvents.handleEvent(Variables.button_type_result_runs_straight, type);
                
            }
            
        });
        straight.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(active1){
                    straight.setScaleX(1.1);
                    straight.setScaleY(1.1);
                }                
            }
            
        });
        /*straight.setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
               switch(type){
                    case 1:
                        //ErrorAlert.info("Straight Click");
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 6:
                        break;
                }
                straight.setStroke(Color.BLACK);
                straight.setStrokeWidth(5); 
            }
        });*/
        legside = new Polygon(getPoints(-89, 29, x_pos, y_pos, inner_radius, outer_radius));
        legside.setFill(Color.GREEN);
        getChildren().add(legside);
        legside.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
               legside.setScaleX(1);
               legside.setScaleY(1);
               HandleEvents.handleEvent(Variables.button_type_result_runs_leg, type);
            }
        });
        legside.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(active2){
                    legside.setScaleX(1.1);
                    legside.setScaleY(1.1);
                }
            }
        });
        legside.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //straight.setFill(Color.WHITE);
                legside.setScaleX(1.1);
                legside.setScaleY(1.1);
                active2=true;
            }
        });
        legside.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //straight.setFill(Color.TRANSPARENT);
                legside.setScaleX(1);
                legside.setScaleY(1);
                active2=false;
            }
        });
        /*legside.setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
               switch(type){
                    case 1:
                        //ErrorAlert.info("Straight Click");
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 6:
                        break;
                }
                legside.setStroke(Color.BLACK);
                legside.setStrokeWidth(5); 
            }
        });*/
        offside = new Polygon(getPoints(151, 269, x_pos, y_pos, inner_radius, outer_radius));
        offside.setFill(Color.BLUE);
        getChildren().add(offside);
        offside.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                offside.setScaleX(1);
                offside.setScaleY(1);
                HandleEvents.handleEvent(Variables.button_type_result_runs_off, type);
            }
        });
        offside.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(active3){
                    offside.setScaleX(1.1);
                    offside.setScaleY(1.1);
                }
            }
        });
        offside.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //straight.setFill(Color.WHITE);
                offside.setScaleX(1.1);
                offside.setScaleY(1.1);
                active3=true;
            }
        });
        offside.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //straight.setFill(Color.TRANSPARENT);
                offside.setScaleX(1);
                offside.setScaleY(1);
                active3=false;
            }
        });
        /*offside.setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
               switch(type){
                    case 1:
                        //ErrorAlert.info("Straight Click");
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 6:
                        break;
                }
                offside.setStroke(Color.BLACK);
                offside.setStrokeWidth(5); 
            }
        });*/
        Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,inner_radius);
        TextType2 text = new TextType2(x_pos, y_pos, type+"", f_type1,this);
        text.setFill(Color.WHITE);
    }
    
    
    public double[] getPoints(int start , int end,double x_pos,double y_pos,double inner_radius,double outer_radius){
        int pointcount = (end-start)+1;
        double [] points = new double[pointcount*2];
        int circular_points = end-start;
        //System.out.println(pointcount+","+circular_points);
        for(int i=0;i<(end-start)/2;i++){
            int angle  = start+(i*2);
            //if(angle<0)
            //    angle=angle+360;
            points[i*2] = x_pos+(inner_radius * Math.cos((((double)angle)/360)*2*Math.PI));
            points[(i*2)+1] = y_pos+(inner_radius * Math.sin((((double)angle)/360)*2*Math.PI));
        } 
        
        for(int i=0;i<(end-start)/2;i++){
            int angle  = end-(i*2);
            //if(angle<0)
            //    angle=angle+360;
            points[(i*2)+circular_points] = x_pos+(outer_radius * Math.cos((((double)angle)/360)*2*Math.PI));
            points[(i*2)+circular_points+1] = y_pos+(outer_radius * Math.sin((((double)angle)/360)*2*Math.PI));
        } 
        points[(pointcount*2)-2]=points[0];
        points[(pointcount*2)-1]=points[1];
        /*for(int i=0;i<pointcount;i++){
            System.out.println(i+","+points[i*2]+","+points[(i*2)+1]);
        }*/
        return points;
    }
    
     public void refreshOption(){
         straight.setStroke(null);
         straight.setStrokeWidth(1);
         legside.setStroke(null);
         legside.setStrokeWidth(1);
         offside.setStroke(null);
         offside.setStrokeWidth(1);
     }
}
