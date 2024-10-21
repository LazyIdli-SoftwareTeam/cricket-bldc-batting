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
import zapcricketsimulator.MediaStageNew;
//import zapcricketsimulator.MediaStage;

/**
 *
 * @author possi
 */
public class RunSelector extends Group{
    int type = 0;
    public static final int straight_side=1;
    public static final int leg_side=2;
    public static final int off_side=3;
    Polygon straight =null,legside=null,offside=null;
    public RunSelector(double x_pos , double y_pos , double inner_radius,double outer_radius , int type){
        this.type = type;
        straight = new Polygon(getPoints(32, 148, x_pos, y_pos, inner_radius, outer_radius));
        straight.setFill(Color.RED);
        getChildren().add(straight);
        straight.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //straight.setFill(Color.WHITE);
            }
        });
        straight.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //straight.setFill(Color.TRANSPARENT);
            }
        });
        straight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
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
                if(MediaStageNew.screen_status==4){
                  //MediaStage.matchdataBean.setBall_result(type);
                  //MediaStage.matchdataBean.setBall_result_dir(straight_side);
                  MediaStageNew.this_obj.triggerEvent(MediaStageNew.score_player);  
                }
                
                straight.setStroke(Color.BLACK);
                straight.setStrokeWidth(5);
                /*Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            //straight.setFill(Color.RED);
                        } catch (Exception e) {
                        }
                    }
                });*/
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
        legside = new Polygon(getPoints(-88, 28, x_pos, y_pos, inner_radius, outer_radius));
        legside.setFill(Color.GREEN);
        getChildren().add(legside);
        legside.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                switch(type){
                    case 1:                        
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
                if(MediaStageNew.screen_status==4){
                    //MediaStage.matchdataBean.setBall_result(type);
                    //MediaStage.matchdataBean.setBall_result_dir(leg_side);
                    MediaStageNew.this_obj.triggerEvent(MediaStageNew.score_player);
                }                
                legside.setStroke(Color.BLACK);
                legside.setStrokeWidth(5);
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
        offside = new Polygon(getPoints(152, 268, x_pos, y_pos, inner_radius, outer_radius));
        offside.setFill(Color.BLUE);
        getChildren().add(offside);
        offside.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                switch(type){
                    case 1:
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
                if(MediaStageNew.screen_status==4){
                    //MediaStage.matchdataBean.setBall_result(type);
                    //MediaStage.matchdataBean.setBall_result_dir(off_side);
                    MediaStageNew.this_obj.triggerEvent(MediaStageNew.score_player);
                }                
                offside.setStroke(Color.BLACK);
                offside.setStrokeWidth(5);
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
