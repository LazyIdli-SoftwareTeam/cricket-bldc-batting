/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import zapcricketsimulator.MediaStageNew;
//import zapcricketsimulator.MediaStage;

/**
 *
 * @author possi
 */
public class OptionButton extends Group{
    static String type_text[]={"Apply","Set Target","Reset"};
    int type=0;
    Circle circle =null;
    public OptionButton(double pos_x,double pos_y,double radius,int type){
        this.type=type;
        circle = new Circle(pos_x, pos_y, radius, Color.LIGHTSEAGREEN);
        getChildren().add(circle);
        Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,radius*0.3);
        TextType2 text = new TextType2(pos_x, pos_y, type_text[type], f_type1, this);
        
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                
                //System.out.println("mouse event");
                switch(type){
                    case 0:
                        if(MediaStageNew.screen_status==1){
                            circle.setStroke(Color.BLACK);
                            circle.setStrokeWidth(5);
                            MediaStageNew.this_obj.triggerEvent(MediaStageNew.welcome_screen);
                        }
                        break;
                    case 1:
                        if(MediaStageNew.screen_status==2){
                            circle.setStroke(Color.BLACK);
                            circle.setStrokeWidth(5);
                            MediaStageNew.this_obj.triggerEvent(MediaStageNew.target_screen);
                        }
                        break;
                    case 2:
                        circle.setStroke(Color.BLACK);
                        circle.setStrokeWidth(5);
                        MediaStageNew.this_obj.triggerEvent(MediaStageNew.reset_screen);
                        break;
                }
            }
        });
        /*setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                circle.setStroke(Color.BLACK);
                circle.setStrokeWidth(5);
                System.out.println("Touce event");
                switch(type){
                    case 0:
                        MediaStage.this_obj.triggerEvent(MediaStage.welcome_screen);
                        break;
                }
            }
        });*/
    }
    
    public void refreshOption(){
        circle.setStroke(null);
        circle.setStrokeWidth(1);
    }
}
