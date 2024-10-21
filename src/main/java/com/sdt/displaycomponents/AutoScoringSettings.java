/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import com.sdt.data.KeyValueBean;
import com.sdt.data.ScoreCombinationBean;
import com.sdt.serial.USB_ComExt;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import zapcricketsimulator.HandleEvents;

/**
 *
 * @author Srikanth
 */
public class AutoScoringSettings {
    public static TabPane tabpane = null;
    public static TabPane changeSettings(){
        if(tabpane==null){
            autoscoresettings();
        }
        return tabpane;
    }
    static ScoreCombinationBean combination = new ScoreCombinationBean();
    static GridPane grid[] = new GridPane[3];
    public static TextField serialno = null;
    public static TextField portno = null;
    public static CChoiceBox_EN_DE stumps = null;
    public static CChoiceBox_KeyValue stumps_val = null;
    public static CChoiceBox_EN_DE solenoid = null;
    public static CChoiceBox_KeyValue solenoid_val = null;
    public static CChoiceBox_KeyValue ball_sensor_1 = null;
    public static CChoiceBox_KeyValue ball_sensor_2 = null;
    public static CSpinner<Double> speed_factor = null;
    public static CSpinner<Integer> input_count = null;
    public static CChoiceBox_KeyValue di1 = null;
    public static CChoiceBox_KeyValue di2 = null;
    public static CChoiceBox_KeyValue di3 = null;
    public static CChoiceBox_KeyValue di4 = null;
    public static CChoiceBox_KeyValue di5 = null;
    public static CChoiceBox_KeyValue di6 = null;
    public static CChoiceBox_KeyValue di7 = null;
    public static CChoiceBox_KeyValue di8 = null;
    public static CChoiceBox_KeyValue di9 = null;
    public static CChoiceBox_KeyValue di10 = null;
    public static ChoiceBox<String> com_port = null;
    public static CSpinner<Integer> t_baudrate = null;
    public static CSpinner<Integer> t_six_output =null;
    public static CSpinner<Integer> t_four_output =null;
    public static CSpinner<Integer> t_two_output =null;
    static Button com_btn = null;
    static void autoscoresettings(){
        tabpane = new TabPane();
        tabpane.setRotateGraphic(false);
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabpane.setSide(Side.TOP);   
        
        
        final Tab tab[] = new Tab[3];
        
        grid[0] = new GridPane();
        grid[0].setHgap(10);
        grid[0].setVgap(10);
        grid[0].setPadding(new Insets(20, 150, 10, 10));
        int row1 = 1;
        
        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(800, 500);
        sp.setContent(grid[0]);
        tab[0] = new Tab("Input Settings");
        tab[0].setContent(sp);
        tabpane.getTabs().add(tab[0]);
        /*tab[0].setOnSelectionChanged(new EventHandler<Event>(){
            public void handle(Event event) {                    
                Tab tab = (Tab)event.getSource();
            }
        });*/
        grid[0].add(new CLabel("Serial NO"), 1, row1);
        serialno = new TextField(HandleEvents.generalSettings.getAutoScotringBean().getSerial_no());
        grid[0].add(serialno, 2, row1++);
        
        grid[0].add(new CLabel("Port No"), 1, row1);
        portno = new TextField(HandleEvents.generalSettings.getAutoScotringBean().getPort_no()+"");
        grid[0].add(portno, 2, row1++);
        
        grid[0].add(new CLabel("Stumps Input"), 1, row1);
        stumps = new CChoiceBox_EN_DE(HandleEvents.generalSettings.getAutoScotringBean().isWicket_input());
        grid[0].add(stumps, 2, row1++);
        ArrayList<KeyValueBean> input_info = new ArrayList(Arrays.asList(new KeyValueBean(0, "Not Used"),new KeyValueBean(1, "Input 1"),new KeyValueBean(2, "Input 2"),new KeyValueBean(3, "Input 3"),new KeyValueBean(4, "Input 4"),new KeyValueBean(5, "Input 5"),new KeyValueBean(6, "Input 6"),new KeyValueBean(7, "Input 7"),new KeyValueBean(8, "Input 8"),new KeyValueBean(9, "Input 9"),new KeyValueBean(10, "Input 10"),new KeyValueBean(11, "Input 11"),new KeyValueBean(12, "Input 12")));
        grid[0].add(new CLabel("Stumps Input"), 1, row1);
        stumps_val = new CChoiceBox_KeyValue(input_info,HandleEvents.generalSettings.getAutoScotringBean().getWicket_sensor());
        grid[0].add(stumps_val, 2, row1++);
        
        grid[0].add(new CLabel("Solenoid Input"), 1, row1);
        solenoid = new CChoiceBox_EN_DE(HandleEvents.generalSettings.getAutoScotringBean().isSolenoid_input());
        grid[0].add(solenoid, 2, row1++);
        
        grid[0].add(new CLabel("Solenoid Input"), 1, row1);
        solenoid_val = new CChoiceBox_KeyValue(input_info,HandleEvents.generalSettings.getAutoScotringBean().getSolenoid_sensor());
        grid[0].add(solenoid_val, 2, row1++);
        
        grid[0].add(new CLabel("Ball Sensor 1"), 1, row1);
        ball_sensor_1 = new CChoiceBox_KeyValue(input_info,HandleEvents.generalSettings.getAutoScotringBean().getBall_sensor_1());
        grid[0].add(ball_sensor_1, 2, row1++);
        
        grid[0].add(new CLabel("Ball Sensor 2"), 1, row1);
        ball_sensor_2 = new CChoiceBox_KeyValue(input_info,HandleEvents.generalSettings.getAutoScotringBean().getBall_sensor_2());
        grid[0].add(ball_sensor_2, 2, row1++);
        
        grid[0].add(new CLabel("Speed Time Factor"), 1, row1);
        speed_factor = new CSpinner<>(2, 20, HandleEvents.generalSettings.getAutoScotringBean().getMax_prev_pole_time_factor(), 0.1);
        grid[0].add(speed_factor, 2, row1++);
        
        grid[0].add(new CLabel("Com Port"), 1, row1);
        ArrayList<String> comlist = new ArrayList<>();
        for(int i=0;i<USB_ComExt.getPortList().length;i++){
            comlist.add(USB_ComExt.getPortList()[i]);
        }
        com_port = new ChoiceBox<>();
        com_port.getItems().addAll(comlist);
        com_port.getSelectionModel().select(HandleEvents.generalSettings.getAutoScotringBean().getCom_port());
        grid[0].add(com_port, 2, row1); 
        String conn_status = "Connect";
        if(USB_ComExt.status)
            conn_status = "Disconnect";
        com_btn = new Button(conn_status);
        grid[0].add(com_btn, 3, row1++); 
        com_btn.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(!USB_ComExt.status){
                    if(com_port.getValue()!=null){
                        HandleEvents.generalSettings.getAutoScotringBean().setCom_port(com_port.getValue());
                        USB_ComExt.Connect();
                    }
                }else{
                    USB_ComExt.disconnect();
                }
                
                if(USB_ComExt.status)
                    com_btn.setText("Disconnect");
                else
                    com_btn.setText("Connect");  
            }
        });
        
        grid[0].add(new CLabel("Baud Rate"), 1, row1);
        t_baudrate = new CSpinner<>(9600, 256000, HandleEvents.generalSettings.getAutoScotringBean().getBaudrate(), 1);
        grid[0].add(t_baudrate, 2, row1++); 
        
        grid[0].add(new CLabel("Six Output Pos"), 1, row1);
        t_six_output = new CSpinner<>(0, 7, HandleEvents.generalSettings.getAutoScotringBean().getSix_output(), 1);
        grid[0].add(t_six_output, 2, row1++);
        
        grid[0].add(new CLabel("Four Output Pos"), 1, row1);
        t_four_output = new CSpinner<>(0, 7, HandleEvents.generalSettings.getAutoScotringBean().getFour_output(), 1);
        grid[0].add(t_four_output, 2, row1++);
        
        grid[0].add(new CLabel("Two Output Pos"), 1, row1);
        t_two_output = new CSpinner<>(0, 7, HandleEvents.generalSettings.getAutoScotringBean().getTwo_output(), 1);
        grid[0].add(t_two_output, 2, row1++);
        
        grid[0].add(new CLabel("Input Count"), 1, row1);
        input_count = new CSpinner<>(1, 10, HandleEvents.generalSettings.getAutoScotringBean().getDi_count(), 1);
        grid[0].add(input_count, 2, row1++);
        
        ArrayList<KeyValueBean> pole_info = new ArrayList(Arrays.asList(new KeyValueBean(-1, "Not Used"),new KeyValueBean(0, "Pole 0"),new KeyValueBean(1, "Pole 1"),new KeyValueBean(2, "Pole 2"),new KeyValueBean(3, "Pole 3"),new KeyValueBean(4, "Pole 4"),new KeyValueBean(5, "Pole 5"),new KeyValueBean(10, "No Run"),new KeyValueBean(11, "Single"),new KeyValueBean(12, "Double"),new KeyValueBean(13, "Triple"),new KeyValueBean(14, "Four"),new KeyValueBean(16, "Six"),new KeyValueBean(19, "Out")));
        //ArrayList<KeyValueBean> score_info = new ArrayList(Arrays.asList(new KeyValueBean(-1, "Not Used"),new KeyValueBean(0, "No Run"),new KeyValueBean(1, "Single"),new KeyValueBean(2, "Double"),new KeyValueBean(3, "Triple"),new KeyValueBean(4, "Four"),new KeyValueBean(6, "Six")));
        
        grid[0].add(new CLabel("Input 1"), 1, row1);
        di1 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi1_pole());
        grid[0].add(di1, 2, row1++);
        
        grid[0].add(new CLabel("Input 2"), 1, row1);
        di2 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi2_pole());
        grid[0].add(di2, 2, row1++);
        
        grid[0].add(new CLabel("Input 3"), 1, row1);
        di3 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi3_pole());
        grid[0].add(di3, 2, row1++);
        
        grid[0].add(new CLabel("Input 4"), 1, row1);
        di4 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi4_pole());
        grid[0].add(di4, 2, row1++);
        
        grid[0].add(new CLabel("Input 5"), 1, row1);
        di5 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi5_pole());
        grid[0].add(di5, 2, row1++);
        
        grid[0].add(new CLabel("Input 6"), 1, row1);
        di6 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi6_pole());
        grid[0].add(di6, 2, row1++);
        
        grid[0].add(new CLabel("Input 7"), 1, row1);
        di7 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi7_pole());
        grid[0].add(di7, 2, row1++);
        
        grid[0].add(new CLabel("Input 8"), 1, row1);
        di8 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi8_pole());
        grid[0].add(di8, 2, row1++);
        
        grid[0].add(new CLabel("Input 9"), 1, row1);
        di9 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi9_pole());
        grid[0].add(di9, 2, row1++);
        
        grid[0].add(new CLabel("Input 10"), 1, row1);
        di10 = new CChoiceBox_KeyValue(pole_info,HandleEvents.generalSettings.getAutoScotringBean().getDi10_pole());
        grid[0].add(di10, 2, row1++);
        
        grid[0].add(new CLabel("General Settings"), 1, row1);
        Button gen_settings_config  = new Button("Config");
        grid[0].add(gen_settings_config, 2, row1++);
        gen_settings_config.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                SettingsButton.dialog.getDialogPane().setContent(SettingsButton.tabPane);
            }
        });
        
        grid[1] = new GridPane();
        grid[1].setHgap(10);
        grid[1].setVgap(10);
        grid[1].setPadding(new Insets(20, 150, 10, 10));
        
        ScrollPane sp1 = new ScrollPane();
        sp1.setPrefSize(800, 500);
        sp1.setContent(grid[1]);
        tab[1] = new Tab("Scoring");
        tab[1].setContent(sp1);
        tabpane.getTabs().add(tab[1]);
        
        loadcombination();
        
        grid[2] = new GridPane();
        grid[2].setHgap(10);
        grid[2].setVgap(10);
        grid[2].setPadding(new Insets(20, 150, 10, 10));
        
        ScrollPane sp2 = new ScrollPane();
        sp2.setPrefSize(800, 500);
        sp2.setContent(grid[2]);
        tab[2] = new Tab("Score Timings");
        tab[2].setContent(sp2);
        tabpane.getTabs().add(tab[2]);
        
        loadtimings();
    }
    
    static Button addbutton  = null;
    static Button modifybutton  = null;
    static Button deletebutton  = null;
    static void loadcombination(){
        
        int row = 1;
        ToggleGroup group = new ToggleGroup();
        grid[1].getChildren().clear();
        
        grid[1].add(new CLabel("Score"), 1, row);
        grid[1].add(new CLabel("Pos 1"), 2, row);
        grid[1].add(new CLabel("Pos 2"), 3, row);
        grid[1].add(new CLabel("Pos 3"), 4, row);
        grid[1].add(new CLabel("Pos 4"), 5, row);
        grid[1].add(new CLabel("Pos 5"), 6, row++);
        
        ArrayList<KeyValueBean> score_info = new ArrayList(Arrays.asList(new KeyValueBean(-1, "Out"),new KeyValueBean(0, "No Run"),new KeyValueBean(1, "Single"),new KeyValueBean(2, "2 Runs"),new KeyValueBean(3, "3 Runs"),new KeyValueBean(4, "Four"),new KeyValueBean(6, "Six")));
        ArrayList<KeyValueBean> input_info = new ArrayList(Arrays.asList(new KeyValueBean(0, "Not Used"),new KeyValueBean(1, "Input 1"),new KeyValueBean(2, "Input 2"),new KeyValueBean(3, "Input 3"),new KeyValueBean(4, "Input 4"),new KeyValueBean(5, "Input 5"),new KeyValueBean(6, "Input 6"),new KeyValueBean(7, "Input 7"),new KeyValueBean(8, "Input 8"),new KeyValueBean(9, "Input 9"),new KeyValueBean(10, "Input 10")));
        
        final CChoiceBox_KeyValue score = new CChoiceBox_KeyValue(score_info,combination.getResult());
        final CChoiceBox_KeyValue pos1 = new CChoiceBox_KeyValue(input_info,combination.getPos_1());
        final CChoiceBox_KeyValue pos2 = new CChoiceBox_KeyValue(input_info,combination.getPos_2());
        final CChoiceBox_KeyValue pos3 = new CChoiceBox_KeyValue(input_info,combination.getPos_3());
        final CChoiceBox_KeyValue pos4 = new CChoiceBox_KeyValue(input_info,combination.getPos_4());
        final CChoiceBox_KeyValue pos5 = new CChoiceBox_KeyValue(input_info,combination.getPos_5());
        grid[1].add(score, 1, row);
        grid[1].add(pos1, 2, row);
        grid[1].add(pos2, 3, row);
        grid[1].add(pos3, 4, row);
        grid[1].add(pos4, 5, row);
        grid[1].add(pos5, 6, row++);
        
        addbutton  = new Button("Add");
        addbutton.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(combination==null)
                    combination=new ScoreCombinationBean();
                if(score.getValue()!=null)
                    combination.setResult(score.getValue().getKey());
                if(pos1.getValue()!=null)
                    combination.setPos_1(pos1.getValue().getKey());
                if(pos2.getValue()!=null)
                    combination.setPos_2(pos2.getValue().getKey());
                if(pos3.getValue()!=null)
                    combination.setPos_3(pos3.getValue().getKey());
                if(pos4.getValue()!=null)
                    combination.setPos_4(pos4.getValue().getKey());
                if(pos5.getValue()!=null)
                    combination.setPos_5(pos5.getValue().getKey());
                HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().add(combination);
                combination=new ScoreCombinationBean();
                loadcombination();
            }
        });
        modifybutton  = new Button("Modify");
        modifybutton.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(group.getSelectedToggle()!=null){
                    try {
                        RadioButton rb = (RadioButton)group.getSelectedToggle();
                        int pos = Integer.parseInt(rb.getText());
                        if(HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().size()>=pos){
                            combination = HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().remove(pos-1);
                            loadcombination();
                        }
                    } catch (Exception e) {
                    }
                    
                }
            }
        });
        deletebutton  = new Button("Delete");
        deletebutton.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(group.getSelectedToggle()!=null){
                    try {
                        RadioButton rb = (RadioButton)group.getSelectedToggle();
                        int pos = Integer.parseInt(rb.getText());
                        if(HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().size()>=pos){
                            HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().remove(pos-1);
                            loadcombination();
                        }
                    } catch (Exception e) {
                    }
                    
                }
            }
        });
        
        grid[1].add(addbutton, 1,row);
        grid[1].add(modifybutton, 2,row);
        grid[1].add(deletebutton, 3,row++);
        
        
        
        for(int i=0;i<HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().size();i++){
            ScoreCombinationBean scorecombination = HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().get(i);
            RadioButton rb = new RadioButton(i+1+"");            
            rb.setToggleGroup(group);
            grid[1].add(rb, 0,row);
            grid[1].add(new CLabel(getValue(score_info,scorecombination.getResult())), 1,row);
            grid[1].add(new CLabel(getValue(input_info,scorecombination.getPos_1())), 2,row);
            grid[1].add(new CLabel(getValue(input_info,scorecombination.getPos_2())), 3,row);
            grid[1].add(new CLabel(getValue(input_info,scorecombination.getPos_3())), 4,row);
            grid[1].add(new CLabel(getValue(input_info,scorecombination.getPos_4())), 5,row);
            grid[1].add(new CLabel(getValue(input_info,scorecombination.getPos_5())), 6,row++);
        }
        
    }
    public static CSpinner<Integer> level_1_time_1 = null;
    public static CSpinner<Integer> level_1_time_2 = null;
    public static CSpinner<Integer> level_1_time_3 = null;
    public static CSpinner<Integer> level_2_time_1 = null;
    public static CSpinner<Integer> level_2_time_2 = null;
    public static CSpinner<Integer> level_2_time_3 = null;
    public static CSpinner<Integer> level_3_time_1 = null;
    public static CSpinner<Integer> level_3_time_2 = null;
    public static CSpinner<Integer> level_3_time_3 = null;
    public static CSpinner<Integer> level_4_time_1 = null;
    public static CSpinner<Integer> level_4_time_2 = null;
    public static CSpinner<Integer> level_4_time_3 = null;
    
    public static CSpinner<Integer> level_1_stumptime = null;
    public static CSpinner<Integer> level_2_stumptime = null;
    public static CSpinner<Integer> level_3_stumptime = null;
    public static CSpinner<Integer> level_4_stumptime = null;
    static void loadtimings(){
        int row = 1;
        grid[2].getChildren().clear();
        
        grid[2].add(new CLabel("Level 1 Time 1"), 1, row);
        level_1_time_1 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_1_score_time_1(), 1);
        grid[2].add(level_1_time_1, 2, row++);
        
        grid[2].add(new CLabel("Level 1 Time 2"), 1, row);
        level_1_time_2 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_1_score_time_2(), 1);
        grid[2].add(level_1_time_2, 2, row++);
        
        grid[2].add(new CLabel("Level 1 Time 3"), 1, row);
        level_1_time_3 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_1_score_time_3(), 1);
        grid[2].add(level_1_time_3, 2, row++);
        
        grid[2].add(new CLabel("Level 2 Time 1"), 1, row);
        level_2_time_1 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_2_score_time_1(), 1);
        grid[2].add(level_2_time_1, 2, row++);
        
        grid[2].add(new CLabel("Level 2 Time 2"), 1, row);
        level_2_time_2 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_2_score_time_2(), 1);
        grid[2].add(level_2_time_2, 2, row++);
        
        grid[2].add(new CLabel("Level 2 Time 3"), 1, row);
        level_2_time_3 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_2_score_time_3(), 1);
        grid[2].add(level_2_time_3, 2, row++);
        
        grid[2].add(new CLabel("Level 3 Time 1"), 1, row);
        level_3_time_1 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_3_score_time_1(), 1);
        grid[2].add(level_3_time_1, 2, row++);
        
        grid[2].add(new CLabel("Level 3 Time 2"), 1, row);
        level_3_time_2 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_3_score_time_2(), 1);
        grid[2].add(level_3_time_2, 2, row++);
        
        grid[2].add(new CLabel("Level 3 Time 3"), 1, row);
        level_3_time_3 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_3_score_time_3(), 1);
        grid[2].add(level_3_time_3, 2, row++);
        
        grid[2].add(new CLabel("Level 4 Time 1"), 1, row);
        level_4_time_1 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_4_score_time_1(), 1);
        grid[2].add(level_4_time_1, 2, row++);
        
        grid[2].add(new CLabel("Level 4 Time 2"), 1, row);
        level_4_time_2 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_4_score_time_2(), 1);
        grid[2].add(level_4_time_2, 2, row++);
        
        grid[2].add(new CLabel("Level 4 Time 3"), 1, row);
        level_4_time_3 = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_4_score_time_3(), 1);
        grid[2].add(level_4_time_3, 2, row++);
        
        grid[2].add(new CLabel("Level 1 Stump Time"), 1, row);
        level_1_stumptime = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_1_wicket_time(), 1);
        grid[2].add(level_1_stumptime, 2, row++);
        
        grid[2].add(new CLabel("Level 2 Stump Time"), 1, row);
        level_2_stumptime = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_2_wicket_time(), 1);
        grid[2].add(level_2_stumptime, 2, row++);
        
        grid[2].add(new CLabel("Level 3 Stump Time"), 1, row);
        level_3_stumptime = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_3_wicket_time(), 1);
        grid[2].add(level_3_stumptime, 2, row++);
        
        grid[2].add(new CLabel("Level 4 Stump Time"), 1, row);
        level_4_stumptime = new CSpinner<>(1, 5000, HandleEvents.generalSettings.getAutoScotringBean().getSkill_4_wicket_time(), 1);
        grid[2].add(level_4_stumptime, 2, row++);
        
        
    }
    
    static String getValue(ArrayList<KeyValueBean> data,int key){
        String val = "Not Def";
        for(int i=0;i<data.size();i++){
            if(data.get(i).getKey()==key){
                val = data.get(i).getValue();
                break;
            }
        }
        return val;
    }
    
}
