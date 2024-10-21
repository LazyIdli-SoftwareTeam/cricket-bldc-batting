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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
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
public class SixerButton extends Group{
    int type=0;
    boolean active=false;
    public SixerButton( double width , double height ,int type){
        this.type=type;
        /*final Ellipse  elipse = new Ellipse(width/2, height/2, width/2, height/2);
        elipse.setFill(Color.rgb(79, 129, 189));
        elipse.setStroke(Color.rgb(56, 93, 138));
        elipse.setStrokeWidth(4);
        getChildren().add(elipse);*/
        try {
            final Rectangle rect = new Rectangle(width, height/*, Color.rgb(79, 129, 189)*/);
            String workingDir = System.getProperty("user.dir");
            String option1 = "/Media/images/btn_bg.png";
            FileInputStream img1 = new FileInputStream(new File(workingDir, option1));
            final Image image = new Image(img1);
            rect.setFill(new ImagePattern(image));
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
            Font f_type = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.25);
            Text text = new Text("SIX");
            if(type==0)
                text.setText("NOT SIX");

            text.setFont(f_type);
            //text.setWrappingWidth(width*0.9);
            getChildren().add(text);
            double t_width = text.getLayoutBounds().getWidth();
            text.setX((width/2)-(t_width/2));
            text.setY(height*0.6);

            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    setScaleX(1);
                    setScaleY(1);
                    HandleEvents.handleEvent(Variables.button_type_six, type);
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //elipse.setStroke(Color.BLACK);
                    if(active){
                        setScaleX(1.1);
                        setScaleY(1.1);
                    }
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //elipse.setStroke(Color.BLACK);
                    setScaleX(1.1);
                    setScaleY(1.1);
                    active=true;
                }
            });

            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //elipse.setStroke(Color.rgb(56, 93, 138));
                    setScaleX(1);
                    setScaleY(1);
                    active=false;
                }
            });
        } catch (Exception e) {
        }
        
    }
}
