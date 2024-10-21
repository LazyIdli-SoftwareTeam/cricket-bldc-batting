/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import com.sdt.serial.HandleSerial;
import java.io.File;
import java.io.FileInputStream;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class TiltPanButton extends Group{
    boolean active1 = false;
    boolean active2 = false;
    boolean active3 = false;
    boolean active4 = false;
    
    public TiltPanButton(double width , double height){
        try {
            String workingDir = System.getProperty("user.dir");
            String tiltpan_path = "/Media/images/tilt_pan.png";
            FileInputStream tiltpan_img = new FileInputStream(new File(workingDir, tiltpan_path));
            final Image tiltpan_image = new Image(tiltpan_img);
            ImageView tiltpan = new ImageView(tiltpan_image);
            tiltpan.setLayoutX(width*0.25);
            tiltpan.setLayoutY(height*0.25);
            tiltpan.setFitWidth(width*0.5);
            tiltpan.setFitHeight(height*0.5);
            getChildren().add(tiltpan);
            
            String tiltup_path = "/Media/images/tilt_up.png";
            FileInputStream tiltup_img = new FileInputStream(new File(workingDir, tiltup_path));
            final Image tiltup_image = new Image(tiltup_img);
            ImageView tiltup = new ImageView(tiltup_image);
            tiltup.setLayoutX(width*0.25);
            tiltup.setLayoutY(height*0.025);
            tiltup.setFitWidth(width*0.5);
            tiltup.setFitHeight(height*0.25);
            getChildren().add(tiltup);
            tiltup.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {   
                    tiltup.setScaleX(1);
                    tiltup.setScaleY(1);
                    HandleSerial.handleCom(HandleSerial.tilt_up);
                }
            });
            tiltup.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    if(active1){
                        tiltup.setScaleX(1.1);
                        tiltup.setScaleY(1.1);
                    }
                }
            });
            tiltup.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    tiltup.setScaleX(1.1);
                    tiltup.setScaleY(1.1);
                    active1=true;
                }
            });

            tiltup.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    tiltup.setScaleX(1);
                    tiltup.setScaleY(1);
                    active1=false;
                }
            });
            
            String tiltdown_path = "/Media/images/tilt_down.png";
            FileInputStream tiltdown_img = new FileInputStream(new File(workingDir, tiltdown_path));
            final Image tiltdown_image = new Image(tiltdown_img);
            ImageView tiltdown = new ImageView(tiltdown_image);
            tiltdown.setLayoutX(width*0.25);
            tiltdown.setLayoutY(height*0.725);
            tiltdown.setFitWidth(width*0.5);
            tiltdown.setFitHeight(height*0.25);
            getChildren().add(tiltdown);
            
            tiltdown.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {   
                    tiltdown.setScaleX(1);
                    tiltdown.setScaleY(1);
                    HandleSerial.handleCom(HandleSerial.tilt_down);
                }
            });
            tiltdown.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    if(active2){
                        tiltdown.setScaleX(1.1);
                        tiltdown.setScaleY(1.1);
                    }                    
                }
            });
            tiltdown.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    tiltdown.setScaleX(1.1);
                    tiltdown.setScaleY(1.1);
                    active2=true;
                }
            });

            tiltdown.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    tiltdown.setScaleX(1);
                    tiltdown.setScaleY(1);
                    active2=false;
                }
            });
            
            String panleft_path = "/Media/images/pan_left.png";
            FileInputStream panleft_img = new FileInputStream(new File(workingDir, panleft_path));
            final Image panleft_image = new Image(panleft_img);
            ImageView panleft = new ImageView(panleft_image);
            panleft.setLayoutX(width*0.025);
            panleft.setLayoutY(height*0.25);
            panleft.setFitWidth(width*0.25);
            panleft.setFitHeight(height*0.5);
            getChildren().add(panleft);
            
            panleft.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {   
                    panleft.setScaleX(1);
                    panleft.setScaleY(1);
                    HandleSerial.handleCom(HandleSerial.pan_left);
                }
            });
            panleft.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    if(active3){
                        panleft.setScaleX(1.1);
                        panleft.setScaleY(1.1);
                    }
                }
            });
            
             panleft.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    panleft.setScaleX(1.1);
                    panleft.setScaleY(1.1);
                    active3=true;
                }
            });

            panleft.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    panleft.setScaleX(1);
                    panleft.setScaleY(1);
                    active3=false;
                }
            });
            
            String panright_path = "/Media/images/pan_right.png";
            FileInputStream panright_img = new FileInputStream(new File(workingDir, panright_path));
            final Image panright_image = new Image(panright_img);
            ImageView panright = new ImageView(panright_image);
            panright.setLayoutX(width*0.725);
            panright.setLayoutY(height*0.25);
            panright.setFitWidth(width*0.25);
            panright.setFitHeight(height*0.5);
            getChildren().add(panright);
            
            panright.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {   
                    panright.setScaleX(1);
                    panright.setScaleY(1);
                    HandleSerial.handleCom(HandleSerial.pan_right);
                }
            });
            panright.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    if(active4){
                        panright.setScaleX(1.1);
                        panright.setScaleY(1.1);
                    }
                }
            });
            panright.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    panright.setScaleX(1.1);
                    panright.setScaleY(1.1);
                    active4=true;
                }
            });

            panright.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    panright.setScaleX(1);
                    panright.setScaleY(1);
                    active4=false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
