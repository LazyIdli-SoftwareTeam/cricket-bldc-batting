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
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

/**
 *
 * @author possi
 */
public class TiltButton extends Group{
    Circle up =null,down=null,left=null,right=null;
    Polygon uparrow =null,downarrow=null,leftarrow=null,rightarrow=null;
    public TiltButton(double pos_x,double pos_y,double radius){
        Group group1 = new Group();
        up = new Circle(pos_x, pos_y-(1.5*radius), radius*0.8, Color.BLUE);
        group1.getChildren().add(up);
        double points[]={pos_x-(0.5*radius),pos_y-(1.2*radius),pos_x,pos_y-(2*radius),pos_x+(0.5*radius),pos_y-(1.2*radius),pos_x-(0.5*radius),pos_y-(1.2*radius)};
        uparrow = new Polygon(points);
        uparrow.setFill(Color.LIGHTBLUE);
        group1.getChildren().add(uparrow);
        getChildren().add(group1);
        Group group2 = new Group();
        down = new Circle(pos_x, pos_y+(1.5*radius), radius*0.8, Color.BLUE);
        group2.getChildren().add(down);
        double points1[]={pos_x-(0.5*radius),pos_y+(1.2*radius),pos_x,pos_y+(2*radius),pos_x+(0.5*radius),pos_y+(1.2*radius),pos_x-(0.5*radius),pos_y+(1.2*radius)};
        downarrow = new Polygon(points1);
        downarrow.setFill(Color.LIGHTBLUE);
        group2.getChildren().add(downarrow);
        getChildren().add(group2);
        Group group3 = new Group();
        left = new Circle(pos_x-(1.5*radius), pos_y, radius*0.8, Color.BLUE);
        group3.getChildren().add(left);
        double points2[]={pos_x-(1.2*radius),pos_y+(radius*0.5),pos_x-(2*radius),pos_y,pos_x-(1.2*radius),pos_y-(radius*0.5),pos_x-(1.2*radius),pos_y+(radius*0.5)};
        leftarrow = new Polygon(points2);
        leftarrow.setFill(Color.LIGHTBLUE);
        group3.getChildren().add(leftarrow);
        getChildren().add(group3);
        Group group4 = new Group();
        right = new Circle(pos_x+(1.5*radius), pos_y, radius*0.8, Color.BLUE);
        group4.getChildren().add(right);
        double points3[]={pos_x+(1.2*radius),pos_y+(radius*0.5),pos_x+(2*radius),pos_y,pos_x+(1.2*radius),pos_y-(radius*0.5),pos_x+(1.2*radius),pos_y+(radius*0.5)};
        rightarrow = new Polygon(points3);
        rightarrow.setFill(Color.LIGHTBLUE);
        group4.getChildren().add(rightarrow);
        getChildren().add(group4);
        group1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                up.setFill(Color.LIGHTBLUE);
                uparrow.setFill(Color.BLUE);
                HandleSerial.handleCom(HandleSerial.tilt_up);
            }
        });
        
        /*group1.setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                up.setFill(Color.LIGHTBLUE);
                uparrow.setFill(Color.BLUE);
            }
        });*/
        
        group2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                down.setFill(Color.LIGHTBLUE);
                downarrow.setFill(Color.BLUE);
                HandleSerial.handleCom(HandleSerial.tilt_down);
            }
        });
        
        /*group2.setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                down.setFill(Color.LIGHTBLUE);
                downarrow.setFill(Color.BLUE);
            }
        });*/
        
        group3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                left.setFill(Color.LIGHTBLUE);
                leftarrow.setFill(Color.BLUE);
                HandleSerial.handleCom(HandleSerial.pan_left);
            }
        });
        
        /*group3.setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                left.setFill(Color.LIGHTBLUE);
                leftarrow.setFill(Color.BLUE);
            }
        });*/
        
        group4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                right.setFill(Color.LIGHTBLUE);
                rightarrow.setFill(Color.BLUE);
                HandleSerial.handleCom(HandleSerial.pan_right);
            }
        });
        
        /*group4.setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                right.setFill(Color.LIGHTBLUE);
                rightarrow.setFill(Color.BLUE);
            }
        });*/
        
        /*Arc arc1 = new Arc(pos_x, pos_y, radius, radius, 10, radius*Math.PI);
        getChildren().add(arc1);
        arc1.setFill(Color.AZURE);
        Arc arc2 = new Arc(pos_x, pos_y, radius, radius, 190, radius*Math.PI);
        getChildren().add(arc2);
        arc2.setFill(Color.AZURE);
        Polyline upline = new Polyline(pos_x-(radius*0.5),pos_y-(radius*0.5),pos_x,pos_y-(radius*0.9),pos_x+(radius*0.5),pos_y-(radius*0.5));
        upline.setStroke(Color.BLACK);
        upline.setStrokeWidth(4);
        getChildren().add(upline);*/
        //Polyline upline = new Polyline(pos_x-(radius*0.5),pos_y-(radius*0.5),pos_x,pos_y-(radius*0.9),pos_x+(radius*0.5),pos_y-(radius*0.5));
        //getChildren().add(upline);
    }
    
    public void refreshOption(){
        up.setFill(Color.BLUE);
        uparrow.setFill(Color.LIGHTBLUE);
        down.setFill(Color.BLUE);
        downarrow.setFill(Color.LIGHTBLUE);
        left.setFill(Color.BLUE);
        leftarrow.setFill(Color.LIGHTBLUE);
        right.setFill(Color.BLUE);
        rightarrow.setFill(Color.LIGHTBLUE);
    }
}
