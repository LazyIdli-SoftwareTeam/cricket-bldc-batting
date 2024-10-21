/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import com.sdt.data.KeyValueBean;
import com.sdt.screens.MatchScreen;
import com.sdt.xml.HandleFile;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Pair;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class UpdateButton extends Group{
    int type=0;
    boolean active=false;
    public UpdateButton(int type , double width , double height){
        this.type=type;
        try {
            //final Ellipse  elipse = new Ellipse(width/2, height/2, width/2, height/2);
            //elipse.setFill(Color.rgb(79, 129, 189));
            //elipse.setStroke(Color.rgb(56, 93, 138));
            //elipse.setStrokeWidth(2);
            //getChildren().add(elipse);
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
                case Variables.button_type_match_update_a:
                    text.setText("UPDATE");
                    break;
                case Variables.button_type_match_update_b:
                    text.setText("UPDATE");
                    break;
            }
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
                    //HandleEvents.handleEvent(type, 0);
                    openSettings();
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
    Dialog<Pair<String, String>> dialog = null;
    GridPane grid[] = new GridPane[2];
    String team_name = "";
    String team_no = "";
    ArrayList<String> player_names = null;
    void openSettings(){
        dialog = new Dialog<>();
        dialog.setTitle("Team Settings");
        
        switch(type){
            case Variables.button_type_match_update_a:
                dialog.setHeaderText("Update Team A Settings");
                team_name=MatchScreen.matchBean.getTeama_name();
                team_no = MatchScreen.matchBean.getTeama_mob();
                if(MatchScreen.matchBean.getTeamA_player_names()==null){
                    MatchScreen.matchBean.setTeamA_player_names(new ArrayList<>());
                }
                player_names = MatchScreen.matchBean.getTeamA_player_names();
                break;
            case Variables.button_type_match_update_b:
                dialog.setHeaderText("Update Team B Settings");
                team_name=MatchScreen.matchBean.getTeamb_name();
                team_no = MatchScreen.matchBean.getTeamb_mob();
                if(MatchScreen.matchBean.getTeamB_player_names()==null){
                    MatchScreen.matchBean.setTeamB_player_names(new ArrayList<>());
                }
                player_names = MatchScreen.matchBean.getTeamB_player_names();
                break;
        }        
        final ButtonType ButtonType1 = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType1, ButtonType.CANCEL);
        
        TabPane tabPane = new TabPane();
        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);        
        
        Tab tab[] = new Tab[2];
        
        loadSettings();
        
        tab[0] = new Tab("Team Settings");
        tab[0].setContent(grid[0]);
        tabPane.getTabs().add(tab[0]);
        tab[0].setOnSelectionChanged(new EventHandler<Event>(){
            public void handle(Event event) {
                if(tab[0].isSelected()){
                    loadSettings();
                    tab[0].setContent(grid[0]);
                }
            }
        });
        
        grid[1] = new GridPane();
        grid[1].setHgap(10);
        grid[1].setVgap(10);
        grid[1].setPadding(new Insets(20, 150, 10, 10));
        
        tab[1] = new Tab("Common Settings");
        tab[1].setContent(grid[1]);
        tabPane.getTabs().add(tab[1]);
        
        ArrayList<KeyValueBean> game_types = new ArrayList(Arrays.asList(new KeyValueBean(Variables.match_type_one_over_each, "One Over Each"),new KeyValueBean(Variables.match_type_two_over_each, "Two Over Each")/*,new KeyValueBean(Variables.match_type_normal_game, "Normal Game")*/));
        grid[1].add(new CLabel("Game Type"), 1, 1);
        final CChoiceBox_KeyValue gametype = new CChoiceBox_KeyValue(game_types,MatchScreen.matchBean.getGame_type());
        grid[1].add(gametype, 2, 1);
        
        grid[1].add(new CLabel("No Of Players"), 1, 2);
        final CSpinner<Integer> no_of_players= new CSpinner(2, 8, MatchScreen.matchBean.getNo_of_players(), 1);
        no_of_players.setEditable(true);
        grid[1].add(no_of_players, 2, 2);
        
        ArrayList<KeyValueBean> starting_team = new ArrayList(Arrays.asList(new KeyValueBean(Variables.match_playing_team_A, "Team A"),new KeyValueBean(Variables.match_playing_team_B, "Team B")));
        grid[1].add(new CLabel("Team to Start"), 1, 3);
        final CChoiceBox_KeyValue startingteam = new CChoiceBox_KeyValue(starting_team,MatchScreen.matchBean.getPlaying_team());
        grid[1].add(startingteam, 2, 3);
                        
        Node Button1 = dialog.getDialogPane().lookupButton(ButtonType1);
        dialog.getDialogPane().setContent(tabPane);
        dialog.setResultConverter(new Callback<ButtonType, Pair<String,String>>() {
            @Override
            public Pair<String,String> call(ButtonType b) {

                if (b == ButtonType1) {
                    return new Pair<String,String>("", "");
                }
                return null;
            }
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        if(result.isPresent()){
            switch(type){
                case Variables.button_type_match_update_a:
                    MatchScreen.matchBean.setTeama_name(teamname.getText());
                    MatchScreen.matchBean.setTeama_mob(teammob.getText());
                    for(int i=0;i<MatchScreen.matchBean.getNo_of_players();i++){
                        if(names.length>i){
                            if(MatchScreen.matchBean.getTeamA_player_names().size()>i){
                                MatchScreen.matchBean.getTeamA_player_names().remove(i);
                                MatchScreen.matchBean.getTeamA_player_names().add(i, names[i].getText());
                            }else{
                                MatchScreen.matchBean.getTeamA_player_names().add(names[i].getText());
                            }
                        }
                    }
                    break;
                case Variables.button_type_match_update_b:
                    MatchScreen.matchBean.setTeamb_name(teamname.getText());
                    MatchScreen.matchBean.setTeamb_mob(teammob.getText());
                    for(int i=0;i<MatchScreen.matchBean.getNo_of_players();i++){
                        if(names.length>i){
                            if(MatchScreen.matchBean.getTeamB_player_names().size()>i){
                                MatchScreen.matchBean.getTeamB_player_names().remove(i);
                                MatchScreen.matchBean.getTeamB_player_names().add(i, names[i].getText());
                            }else{
                                MatchScreen.matchBean.getTeamB_player_names().add(names[i].getText());
                            }
                        }
                    }
                    break;
            }
            if(gametype.getValue()!=null)
                MatchScreen.matchBean.setGame_type(gametype.getValue().getKey());
             MatchScreen.matchBean.setNo_of_players(no_of_players.getValue().intValue());
             if(MatchScreen.matchBean.getGame_type()==Variables.match_type_one_over_each){
                 MatchScreen.matchBean.setNo_of_overs(MatchScreen.matchBean.getNo_of_players());
             }else{
                 MatchScreen.matchBean.setNo_of_overs(MatchScreen.matchBean.getNo_of_players()*2);
             }
             if(startingteam.getValue()!=null)
                MatchScreen.matchBean.setPlaying_team(startingteam.getValue().getKey());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    MatchScreen.refreshData();
                }
            });
        }
    }
    TextField teamname =null;
    TextField teammob = null;
    TextField names[];
    public void loadSettings(){
        grid[0] = new GridPane();
        grid[0].setHgap(10);
        grid[0].setVgap(10);
        grid[0].setPadding(new Insets(20, 150, 10, 10));       
        
        grid[0].add(new CLabel("Team Name"), 1, 1);
        teamname = new TextField(team_name);
        grid[0].add(teamname, 2, 1);
        grid[0].add(new CLabel("Team Mob"), 1, 2);
        teammob = new TextField(team_no);
        grid[0].add(teammob, 2, 2);
        names =new TextField[MatchScreen.matchBean.getNo_of_players()];
        for(int i=0;i<MatchScreen.matchBean.getNo_of_players();i++){
            if(player_names==null)
                break;
            String name= "Player "+(i+1);
            if(player_names.size()>i)
                name = player_names.get(i);
            else
                player_names.add(name);
            grid[0].add(new CLabel("Player "+(i+1)), 1, 3+i);
            names[i] = new TextField(name);
            grid[0].add(names[i], 2, 3+i);
        }       
    }
}
