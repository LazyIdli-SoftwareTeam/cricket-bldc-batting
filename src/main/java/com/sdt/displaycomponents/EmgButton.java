/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import java.io.File;
import java.io.FileInputStream;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class EmgButton extends Group{
    int type=0;
    public EmgButton(int type){
        try {
            this.type=type;
            final Circle circle = new Circle(45);
            String workingDir = System.getProperty("user.dir");
            String option1 = "";
            switch(type){
                case Variables.button_type_play:
                    option1 = "/Media/images/game_btn.png";
                    break;
                case Variables.button_type_pause:
                    option1 = "/Media/images/game_btn.png";
                    break;
                case Variables.button_type_start:
                    option1 = "/Media/images/game_btn.png";
                    break;
                case Variables.button_type_emg:
                    option1 = "/Media/images/emg.png";
                    break;
            }
            FileInputStream img1 = new FileInputStream(new File(workingDir, option1));
            final Image image = new Image(img1);
            circle.setFill(new ImagePattern(image));
            getChildren().add(circle);
            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    HandleEvents.handleEvent(type, 0);
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    circle.setStroke(Color.BLACK);
                    circle.setStrokeWidth(6);
                }
            });
            
            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    circle.setStroke(null);
                    circle.setStrokeWidth(1);
                }
            });
        } catch (Exception e) {
        }
    }
}
