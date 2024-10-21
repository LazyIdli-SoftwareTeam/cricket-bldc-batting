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
public class BowlerButton extends Group{
    int type =0;
    boolean active =false;
    
    public BowlerButton(int type , double width , double height){
        this.type=type;
        try {
            //double points[]={0,height*0.2,0,height,width,height,width,height*0.2,width*0.8,0,width*0.2,0,0,height*0.2};
            //final Polygon button = new Polygon(points);
            //final Rectangle button =new Rectangle(width, height)           
            //ImagePattern imgptrn = new ImagePattern(btn_image);
            //button.setFill(Color.rgb(51, 106, 173));
            //button.setFill(imgptrn);
            //getChildren().add(button);
            /*Font f_type = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.3);
            Text text = new Text("BOWLER");
            text.setFont(f_type);
            getChildren().add(text);
            double t_width = text.getLayoutBounds().getWidth();
            text.setX((width/2)-(t_width/2));
            text.setY(height*0.4);

            Text text1 = new Text(width*0.47,height*0.8,type+"");
            text1.setFont(f_type);
            getChildren().add(text1);*/
            
            String workingDir = System.getProperty("user.dir");
            /*String overlay_path = "/Media/images/ButtonOverlay.png";
            FileInputStream overlay_img = new FileInputStream(new File(workingDir, overlay_path));
            final Image overlay_image = new Image(overlay_img);
            ImageView overlay = new ImageView(overlay_image);
            overlay.setFitWidth(width);
            overlay.setFitHeight(height);
            getChildren().add(overlay);*/
            
            String bowler_path = "/Media/images/Bowler"+type+".png";
            FileInputStream bowler_img = new FileInputStream(new File(workingDir, bowler_path));
            final Image bowler_image = new Image(bowler_img);
            String bowlerc_path = "/Media/images/Bowler"+type+"c.png";
            FileInputStream bowlerc_img = new FileInputStream(new File(workingDir, bowlerc_path));
            final Image bowlerc_image = new Image(bowlerc_img);
            ImageView bowler = new ImageView(bowler_image);
            bowler.setFitWidth(width);
            bowler.setFitHeight(height);
            getChildren().add(bowler);

            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) { 
                    //setScaleX(1);
                    //setScaleY(1);
                    bowler.setImage(bowler_image);
                    HandleEvents.handleEvent(Variables.button_type_bowler, type);
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    if(active){
                        //setScaleX(1.1);
                        //setScaleY(1.1);
                        bowler.setImage(bowlerc_image);
                    }
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    //setScaleX(1.1);
                    //setScaleY(1.1);
                    bowler.setImage(bowlerc_image);
                    active=true;
                }
            });

            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    //setScaleX(1);
                    //setScaleY(1);
                    bowler.setImage(bowler_image);
                    active=false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
