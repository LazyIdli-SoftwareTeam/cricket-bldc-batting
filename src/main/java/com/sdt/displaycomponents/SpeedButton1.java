/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import com.sdt.screens.MultiPlayerScreen;
import com.sdt.screens.SinglePlayerScreen;
import com.sdt.serial.HandleSerial;
import com.sdt.system.ErrorAlert;
import java.io.File;
import java.io.FileInputStream;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import zapcricketsimulator.HandleEvents;
import static zapcricketsimulator.HandleEvents.gameBean;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class SpeedButton1 extends Group{
    public static TextType2 t_speed = null;
    public SpeedButton1(double width , double height){
        
        Font f_type = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.3);
        String workingDir = System.getProperty("user.dir");
        try {
            String img_filename = "/Media/images/speed_btn.png";
            FileInputStream img_file = new FileInputStream(new File(workingDir, img_filename));
            Image img = new Image(img_file);
            final Rectangle speed_rect = new Rectangle(0, 0, width, height);
            speed_rect.setFill(new ImagePattern(img));
            getChildren().add(speed_rect);
            String speed_filename = "/Media/images/speed_display.png";
            FileInputStream speed_file = new FileInputStream(new File(workingDir, speed_filename));
            Image speed_img = new Image(speed_file);
            final Rectangle speed_display_rect = new Rectangle(width*0.125, -height*0.4, width*0.75, height*0.6);
            speed_display_rect.setFill(new ImagePattern(speed_img));
            getChildren().add(speed_display_rect);
            t_speed = new TextType2(width/2, -height*0.15, HandleEvents.machineDataBean.getSet_speed()+"", f_type, this);
            t_speed.setFill(Color.AQUA);
            String plus_filename = "/Media/images/plus.png";
            FileInputStream plus_img_file = new FileInputStream(new File(workingDir, plus_filename));
            Image plus_img = new Image(plus_img_file);
            Circle plus_circle = new Circle(width*0.816, height*0.5, width*0.36);
            getChildren().add(plus_circle);
            plus_circle.setFill(new ImagePattern(plus_img));
            String minus_filename = "/Media/images/minus.png";
            FileInputStream minus_img_file = new FileInputStream(new File(workingDir, minus_filename));
            Image minus_img = new Image(minus_img_file);
            Circle minus_circle = new Circle(width*0.179, height*0.5, width*0.36);
            getChildren().add(minus_circle);
            minus_circle.setFill(new ImagePattern(minus_img));
            
            plus_circle.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //HandleEvents.handleEvent(Variables.button_type_speed, Variables.option_up);
                    if(HandleEvents.game_status == Variables.game_status_none ||  HandleEvents.game_status == Variables.game_status_init){
                        if(HandleEvents.machineDataBean.getSet_speed()<150 && HandleEvents.machineDataBean.getSet_speed()>=40){
                            HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()+1);
                            updateSpeed(HandleEvents.machineDataBean.getSet_speed());
                            HandleSerial.handleCom(HandleSerial.update_speed);                        
                        }
                    }
                    if(HandleEvents.game_mode==Variables.game_mode_sp){
                        if(HandleEvents.game_status == Variables.game_status_started && HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()>=1 && HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()<4){
                            boolean inc_skill = ErrorAlert.confirm("Are You Sure You Want To Increase Skill ?");
                            if(inc_skill){
                                HandleEvents.gameBean.getPlayer_data().get(0).setSkill_level(HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()+1);
//                                System.out.println("VALUE UPDATED"+gameBean.getSeq_pos());
//                                System.out.println(HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level());
                                SinglePlayerScreen.bplayer_skill.selectValue(HandleEvents.gameBean.getPlayer_data().get(0).getHand_usage(),HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level());
                            }       

                        }
                    }
                    if(HandleEvents.game_mode==Variables.game_mode_mp){
                        if(HandleEvents.game_status == Variables.game_status_started && HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()>=1 && HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()<4){
                            boolean inc_skill = ErrorAlert.confirm("Are You Sure You Want To Increase Skill ?");
                            if(inc_skill){
                                HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).setSkill_level(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()+1);
//                                System.out.println("VALUE UPDATED"+gameBean.getSeq_pos());
//                                System.out.println(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level());
                                MultiPlayerScreen.bplayer_skills.get(gameBean.getSeq_pos()).selectValue(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getHand_usage(),HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level());
                            }
                        }
                    }
                }
            });
            plus_circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    plus_circle.setScaleX(1.1);
                    plus_circle.setScaleY(1.1);
                }
            });

            plus_circle.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    plus_circle.setScaleX(1);
                    plus_circle.setScaleY(1);
                }
            });
            
            minus_circle.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //HandleEvents.handleEvent(Variables.button_type_speed, Variables.option_up);
                    if(HandleEvents.game_status == Variables.game_status_none ||  HandleEvents.game_status == Variables.game_status_init){
                        if(HandleEvents.machineDataBean.getSet_speed()<=150 && HandleEvents.machineDataBean.getSet_speed()>40){
                            HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()-1);
                            updateSpeed(HandleEvents.machineDataBean.getSet_speed());
                            HandleSerial.handleCom(HandleSerial.update_speed);                        
                        }
                    }
                    if(HandleEvents.game_mode==Variables.game_mode_sp){
                        if(HandleEvents.game_status == Variables.game_status_started && HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()<=4 && HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()>1){
                            boolean dec_skill = ErrorAlert.confirm("Are You Sure You Want To Decrease Skill ?");
                            if(dec_skill){
                                HandleEvents.gameBean.getPlayer_data().get(0).setSkill_level(HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()-1);
//                                System.out.println("VALUE UPDATED"+gameBean.getSeq_pos());
//                                System.out.println(HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level());
                                SinglePlayerScreen.bplayer_skill.selectValue(HandleEvents.gameBean.getPlayer_data().get(0).getHand_usage(),HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level());
                            }
                        }
                    }
                    if(HandleEvents.game_mode==Variables.game_mode_mp){
                        if(HandleEvents.game_status == Variables.game_status_started && HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()<=4 && HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()>1){
                            boolean dec_skill = ErrorAlert.confirm("Are You Sure You Want To Decrease Skill ?");
                            if(dec_skill){
                                HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).setSkill_level(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()-1);
//                                System.out.println("VALUE UPDATED"+gameBean.getSeq_pos());
//                                System.out.println(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level());
                                MultiPlayerScreen.bplayer_skills.get(gameBean.getSeq_pos()).selectValue(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getHand_usage(),HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level());
                            }
                        }
                    }
                }
            });
            minus_circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    minus_circle.setScaleX(1.1);
                    minus_circle.setScaleY(1.1);
                }
            });

            minus_circle.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    minus_circle.setScaleX(1);
                    minus_circle.setScaleY(1);
                }
            });
            
        } catch (Exception e) {
        }
        
        /*Group group1 = new Group();
        final Rectangle inc_rect = new Rectangle(0, 0, width, height*0.2);
        inc_rect.setFill(Color.rgb(79, 129, 189));
        inc_rect.setStroke(Color.WHITE);
        group1.getChildren().add(inc_rect);
        TextType2 t_inc = new TextType2(width/2, height*0.1, "INCREASE", f_type, group1);
        getChildren().add(group1);
        final Rectangle speed_rect = new Rectangle(0, height*0.4, width, height*0.2);
        speed_rect.setFill(Color.rgb(79, 129, 189));
        speed_rect.setStroke(Color.WHITE);
        getChildren().add(speed_rect);
        final TextType2 t_speed = new TextType2(width/2, height*0.5, "SPEED", f_type, this);
        Group group2 = new Group();
        final Rectangle dec_rect = new Rectangle(0, height*0.8, width, height*0.2);
        dec_rect.setFill(Color.rgb(79, 129, 189));
        dec_rect.setStroke(Color.WHITE);
        group2.getChildren().add(dec_rect);
        TextType2 t_dec = new TextType2(width/2, height*0.9, "DECREASE", f_type, group2);
        getChildren().add(group2);*/
        
        /*double arrowidth = width*0.2;
        double mid_point = width/2;
        double points1[]={mid_point-(arrowidth/2),height*0.4,
                            mid_point-(arrowidth/2),height*0.3,
                            mid_point-(arrowidth),height*0.3,
                            mid_point,height*0.225,
                            mid_point+(arrowidth),height*0.3,
                            mid_point+(arrowidth/2),height*0.3,
                            mid_point+(arrowidth/2),height*0.4};
        Polygon up_arrow = new Polygon(points1);
        up_arrow.setFill(Color.rgb(178, 193, 219));
        getChildren().add(up_arrow);
        
        double points2[]={mid_point-(arrowidth/2),height*0.6,
                            mid_point-(arrowidth/2),height*0.7,
                            mid_point-(arrowidth),height*0.7,
                            mid_point,height*0.775,
                            mid_point+(arrowidth),height*0.7,
                            mid_point+(arrowidth/2),height*0.7,
                            mid_point+(arrowidth/2),height*0.6};
        Polygon down_arrow = new Polygon(points2);
        down_arrow.setFill(Color.rgb(178, 193, 219));
        getChildren().add(down_arrow);*/
        
        /*group1.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //HandleEvents.handleEvent(Variables.button_type_speed, Variables.option_up);
                if(HandleEvents.machineDataBean.getSet_speed()<150){
                    HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()+1);
                    HandleSerial.handleCom(HandleSerial.update_speed);
                    t_speed.setText(HandleEvents.machineDataBean.getSet_speed()+"");
                }
            }
        });
        group1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                inc_rect.setStroke(Color.BLACK);
                t_speed.setText(HandleEvents.machineDataBean.getSet_speed()+"");
            }
        });

        group1.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                inc_rect.setStroke(Color.WHITE);
                t_speed.setText("SPEED");
            }
        });
        
        
        group2.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //HandleEvents.handleEvent(Variables.button_type_speed, Variables.option_down);
                if(HandleEvents.machineDataBean.getSet_speed()>50){
                    HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()-1);
                    HandleSerial.handleCom(HandleSerial.update_speed);
                    t_speed.setText(HandleEvents.machineDataBean.getSet_speed()+"");
                }
            }
        });
        group2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                dec_rect.setStroke(Color.BLACK);
                t_speed.setText(HandleEvents.machineDataBean.getSet_speed()+"");
            }
        });

        group2.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                dec_rect.setStroke(Color.WHITE);
                t_speed.setText("SPEED");
            }
        });*/
        
        
    }
    
    public static void updateSpeed(int speed){
        if(t_speed!=null){
            t_speed.setText(speed+"");
        }
    }
    
}
