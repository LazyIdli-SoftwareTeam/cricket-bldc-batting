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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class BolerSelection extends Group{
    int type=0;
    boolean active=false;
    public BolerSelection(int type , double width , double height){
        try {
            this.type=type;
        
            final Rectangle rect = new Rectangle(width, height/*, Color.rgb(79, 129, 189)*/);
            //rect.setArcWidth(width*0.2);
            //rect.setArcHeight(height*0.4);
            String workingDir = System.getProperty("user.dir");
            String option1 = "/Media/images/btn_bg.png";
            FileInputStream img1 = new FileInputStream(new File(workingDir, option1));
            final Image image = new Image(img1);
            rect.setFill(new ImagePattern(image));
            //rect.setStroke(Color.rgb(56, 93, 138));
            //rect.setStrokeWidth(2);
            rect.setStroke(null);
            rect.setStrokeWidth(0);
            getChildren().add(rect);
            String btn_capL_path = "/Media/images/btn_capL.png";
            FileInputStream btn_capL_img = new FileInputStream(new File(workingDir, btn_capL_path));
            final Image btn_capL_image = new Image(btn_capL_img);
            ImageView btn_capL = new ImageView(btn_capL_image);
            btn_capL.setLayoutX(width*0.00);
            btn_capL.setLayoutY(height*0.00);
            btn_capL.setFitWidth(width*0.2);
            btn_capL.setFitHeight(height*0.4);
            getChildren().add(btn_capL);
            
            String btn_capR_path = "/Media/images/btn_capR.png";
            FileInputStream btn_capR_img = new FileInputStream(new File(workingDir, btn_capR_path));
            final Image btn_capR_image = new Image(btn_capR_img);
            ImageView btn_capR = new ImageView(btn_capR_image);
            btn_capR.setLayoutX(width*0.8);
            btn_capR.setLayoutY(height*0.6);
            btn_capR.setFitWidth(width*0.2);
            btn_capR.setFitHeight(height*0.4);
            getChildren().add(btn_capR);
            
            Font f_type = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.25);
            Text text = new Text();
            switch(type){
                case Variables.button_type_auto_bowler_sel:
                    text.setText("AUTO BOWLER SELECTION");
                    break;
                case Variables.button_type_man_bowler_sel:
                    text.setText("MANUAL BOWLER SELECTION");
                    break;
            }
            text.setFont(f_type);
            text.setWrappingWidth(width*0.9);
            text.setStroke(Color.WHITE);
            text.setFill(Color.WHITE);
            getChildren().add(text);
            double t_width = text.getLayoutBounds().getWidth();
            text.setX((width/2)-(t_width/2));
            text.setY(height*0.35);

            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    HandleEvents.handleEvent(type, 0);
                    setScaleX(1);
                    setScaleY(1);
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if(active){
                        setScaleX(1.1);
                        setScaleY(1.1);
                    }
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //rect.setStroke(Color.BLACK);
                    setScaleX(1.1);
                    setScaleY(1.1);
                    active=true;
                }
            });

            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //rect.setStroke(Color.rgb(56, 93, 138));
                    setScaleX(1);
                    setScaleY(1);
                    active=false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
