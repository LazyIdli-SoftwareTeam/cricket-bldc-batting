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
import javafx.scene.shape.Polygon;
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
public class ResultButton extends Group{
    int type=0;
    boolean active = false;
    public ResultButton(int type , double width , double height){
        this.type=type;
        try {
            /*final Rectangle rect = new Rectangle(width, height, Color.rgb(112, 80, 151));
            rect.setArcWidth(width*0.2);
            rect.setArcHeight(height*0.4);
            getChildren().add(rect);
            Font f_type = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.3);
            Text text = new Text();*/
            String workingDir = System.getProperty("user.dir");
            String overlay_path = "/Media/images/ButtonOverlay.png";
            FileInputStream overlay_img = new FileInputStream(new File(workingDir, overlay_path));
            final Image overlay_image = new Image(overlay_img);
            ImageView overlay = new ImageView(overlay_image);
            overlay.setFitWidth(width);
            overlay.setFitHeight(height);
            getChildren().add(overlay);

            String result_path = "/Media/images/noruns.png";
            switch(type){
                case Variables.button_type_result_norun:
                    //text.setText("NO RUN");
                    result_path = "/Media/images/noruns.png";
                    break;
                case Variables.button_type_result_wide:
                    //text.setText("WIDE");
                    result_path = "/Media/images/wide.png";
                    break;
                case Variables.button_type_result_noball:
                    //text.setText("NO BALL");
                    result_path = "/Media/images/noball.png";
                    break;
                case Variables.button_type_result_freehit:
                    //text.setText("FREE HIT");
                    result_path = "/Media/images/freehit.png";
                    break;
                case Variables.button_type_result_bowled:
                    //text.setText("BOWLED");
                    result_path = "/Media/images/bowled.png";
                    break;
                case Variables.button_type_result_catch:
                    //text.setText("CATCH");
                    result_path = "/Media/images/catch.png";
                    break;
                case Variables.button_type_result_lbw:
                    //text.setText("LBW");
                    result_path = "/Media/images/lbw.png";
                    break;
                case Variables.button_type_result_stumped:
                    //text.setText("STUMPED");
                    result_path = "/Media/images/stumped.png";
                    break;
            }            
             
            FileInputStream result_img = new FileInputStream(new File(workingDir, result_path));
            final Image result_image = new Image(result_img);
            ImageView result = new ImageView(result_image);
            result.setFitWidth(width);
            result.setFitHeight(height);
            getChildren().add(result);
            //text.setStroke(Color.BLACK);
            /*text.setFont(f_type);
            getChildren().add(text);
            double t_width = text.getLayoutBounds().getWidth();
            text.setX((width/2)-(t_width/2));
            text.setY(height*0.6);*/



            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    setScaleX(1);
                    setScaleY(1);
                    HandleEvents.handleEvent(type, 0);
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
                    active = true;
                }
            });

            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //rect.setStroke(null);
                    setScaleX(1);
                    setScaleY(1);
                    active = false;
                }
            });
        } catch (Exception e) {
        }
        
    }
}
