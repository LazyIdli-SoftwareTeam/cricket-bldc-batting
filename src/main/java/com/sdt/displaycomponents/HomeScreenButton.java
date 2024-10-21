/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author possi
 */
public class HomeScreenButton extends Group{
    
    public HomeScreenButton(Image image , String text,Color color){
        try {
            Stop [] stops = {new Stop(0,color),new Stop(1,Color.BLUE)};
            LinearGradient linear = new LinearGradient(0, 0, 5, 0, true, CycleMethod.NO_CYCLE, stops);
            double points[] = {50,50,100,10,500,10,500,90,100,90,50,50};
            Polygon polygon = new Polygon(points);
            polygon.setFill(linear);
            getChildren().add(polygon);
            
            Circle circle = new Circle(50,50,50);
            circle.setFill(new ImagePattern(image));
            //circle.setStroke(color);
            getChildren().add(circle);
            Font f_type1 = Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR,40);
            Text displaytext = new Text(120, 65, text);
            displaytext.setFont(f_type1);
            displaytext.setStroke(Color.WHITE);
            displaytext.setFill(Color.WHITE);
            getChildren().add(displaytext);
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    setScaleX(1.1);
                    setScaleY(1.1);
                }
            });
            
            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    setScaleX(1);
                    setScaleY(1);
                }
            });
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
}
