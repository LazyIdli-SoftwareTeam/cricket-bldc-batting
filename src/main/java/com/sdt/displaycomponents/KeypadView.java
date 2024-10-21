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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class KeypadView extends Group{
    boolean active =false;
    public KeypadView(double width , double height){
        try {
            String workingDir = System.getProperty("user.dir");
            String overlay_path = "/Media/images/keypad.png";
            FileInputStream overlay_img = new FileInputStream(new File(workingDir, overlay_path));
            final Image overlay_image = new Image(overlay_img);
            ImageView keypad = new ImageView(overlay_image);
            keypad.setFitWidth(width);
            keypad.setFitHeight(height);
            getChildren().add(keypad);
            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) { 
                    try {
                        if(HandleEvents.generalSettings.isKeypad_enable()){
                            Runtime.getRuntime().exec("cmd /c osk");
                        }
                    } catch (Exception e) {
                    }                    
                    //setScaleX(1);
                    //setScaleY(1);                    
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    if(active){
                        //setScaleX(1.1);
                        //setScaleY(1.1);
                    }
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    //setScaleX(1.1);
                    //setScaleY(1.1);
                    //active=true;
                }
            });

            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    //setScaleX(1);
                    //setScaleY(1);
                    //active=false;
                }
            });
        } catch (Exception e) {
        }
    } 
}
