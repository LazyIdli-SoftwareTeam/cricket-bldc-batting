/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import zapcricketsimulator.MediaStageNew;
//import zapcricketsimulator.MediaStage;

/**
 *
 * @author possi
 */
public class RunButton extends Group{
    Circle circle = null;
    Polygon polygon =null;
    public RunButton(double pos_x,double pos_y,double radius){
        circle = new Circle(pos_x, pos_y, radius);
        circle.setFill(Color.LIGHTGREEN);
        getChildren().add(circle);
        Circle circle1 = new Circle(pos_x, pos_y, radius*0.8);
        circle1.setFill(Color.WHITE);
        getChildren().add(circle1);
        double points[]={pos_x-(radius*0.3),pos_y+(radius*0.3),pos_x-(radius*0.3),pos_y-(radius*0.3),pos_x+(radius*0.35),pos_y,pos_x-(radius*0.3),pos_y+(radius*0.3)};
        polygon = new Polygon(points);
        polygon.setFill(Color.LIGHTGREEN);
        polygon.setStroke(Color.LIGHTGREEN);
        getChildren().add(polygon);
        
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(MediaStageNew.screen_status==3){
                    circle.setFill(Color.GREEN);
                    polygon.setFill(Color.GREEN);
                    MediaStageNew.this_obj.triggerEvent(MediaStageNew.run_game);
                }
            }
        });
        /*setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                circle.setFill(Color.GREEN);
                polygon.setFill(Color.GREEN);
            }
        });*/
    }
    
    public void refreshOption(){
        circle.setFill(Color.LIGHTGREEN);
        polygon.setFill(Color.LIGHTGREEN);
    }
}
