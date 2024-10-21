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
import javafx.scene.shape.Rectangle;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class GameButton extends Group{
    int type=0;
    boolean active=false;
    public GameButton(int type){
        try {
            this.type=type;
            final Rectangle rect = new Rectangle(0,0,110,60);
            String workingDir = System.getProperty("user.dir");
            String option1 = "/Media/images/game_btn.png";
            String option2 = "/Media/images/game_btn_pressed.png";
            if(type==Variables.button_type_start){
                option1 = "/Media/images/start.png";
                option2 = "/Media/images/startc.png";
            }else if(type==Variables.button_type_pause){
                option1 = "/Media/images/pause.png";
                option2 = "/Media/images/pausec.png";
            }else if(type==Variables.button_type_play){
                option1 = "/Media/images/play.png";
                option2 = "/Media/images/playc.png";
            }
            FileInputStream img1 = new FileInputStream(new File(workingDir, option1));
            FileInputStream img2 = new FileInputStream(new File(workingDir, option2));
            final Image image1 = new Image(img1);
            final Image image2 = new Image(img2);
            rect.setFill(new ImagePattern(image1));
            getChildren().add(rect);
            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    rect.setFill(new ImagePattern(image1));
                    HandleEvents.handleEvent(type, 0);
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if(active)
                        rect.setFill(new ImagePattern(image2));
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    rect.setFill(new ImagePattern(image2));
                    //circle.setStroke(Color.BLACK);
                    //circle.setStrokeWidth(6);
                    active=true;
                }
            });
            
            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    rect.setFill(new ImagePattern(image1));
                    //circle.setStroke(null);
                    //circle.setStrokeWidth(1);
                    active=false;
                }
            });
        } catch (Exception e) {
        }
    }
}
