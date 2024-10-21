/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.xml;

import com.sdt.data.AutoScoringBean;
import com.sdt.data.ScoreCombinationBean;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.ZaPCricketSimulator;

/**
 *
 * @author possi
 */
public class HandleFile {
    public static String workingDir = System.getProperty("user.dir");
    public static String config_file = "/Media/conf.xml";
    public static String script_dir = null;
    public static void readData(){
        try {
            if(workingDir == null){
                workingDir = System.getProperty("user.dir");                
            }
            File file =new File(workingDir, config_file);
            if(!file.exists()){
                //ErrorAlert.info("File not found");
                return;
            }
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(file); 
            Element settingroot = document.getRootElement();
            if(settingroot.getName().equals("ZAPCricketSimulator")){
                Element general_info = settingroot.getChild("GeneralSettings");
                if(general_info!=null){
                    if(general_info.getChildText("KeypadEnable")!=null)
                        HandleEvents.generalSettings.setKeypad_enable(Boolean.parseBoolean(general_info.getChildText("KeypadEnable")));
                    if(general_info.getChildText("TestMode")!=null)
                        HandleEvents.generalSettings.setTest_mode(Boolean.parseBoolean(general_info.getChildText("TestMode")));
                    if(general_info.getChildText("TabletEnable")!=null)
                        HandleEvents.generalSettings.setTablet_mode_enable(Boolean.parseBoolean(general_info.getChildText("TabletEnable")));
                    HandleEvents.generalSettings.setReplay_enable(Boolean.parseBoolean(general_info.getChildText("ReplayEnable")));
                    HandleEvents.generalSettings.setReplayduration_ms(new BigDecimal(general_info.getChildText("ReplayDuration")).intValue());
                    try {
                        HandleEvents.generalSettings.setCamera_port(new BigDecimal(general_info.getChildText("CameraPort")).intValue());
                        HandleEvents.generalSettings.setFormat(general_info.getChildText("ReplayFormat"));
                        HandleEvents.generalSettings.setCamera_ip(general_info.getChildText("CameraIP"));
                        HandleEvents.generalSettings.setCamera_user_id(general_info.getChildText("CameraUserID"));
                        HandleEvents.generalSettings.setCamera_password(general_info.getChildText("CameraPassword"));
                    } catch (Exception e) {
                    }
                    if(general_info.getChildText("DefaultBowler")!=null)
                        HandleEvents.generalSettings.setDefault_bowler(new BigDecimal(general_info.getChildText("DefaultBowler")).intValue());
                    /*HandleEvents.generalSettings.setDefault_bowler_relese_pos(new BigDecimal(general_info.getChildText("DefaultBowlerRelesePos")).intValue());
                    if(general_info.getChildText("DefaultBowlerRelesePos2")==null)
                        HandleEvents.generalSettings.setDefault_bowler_relese_pos2(new BigDecimal(general_info.getChildText("DefaultBowlerRelesePos")).intValue());
                    else
                        HandleEvents.generalSettings.setDefault_bowler_relese_pos2(new BigDecimal(general_info.getChildText("DefaultBowlerRelesePos2")).intValue());*/
                    HandleEvents.generalSettings.setTouchscreen_index(new BigDecimal(general_info.getChildText("TouchScreenIndex")).intValue());
                    HandleEvents.generalSettings.setProjector_screen_index(new BigDecimal(general_info.getChildText("ProjectorScreenIndex")).intValue());
                    if(general_info.getChildText("ComPort")!=null)
                        HandleEvents.generalSettings.setCom_port(general_info.getChildText("ComPort"));
                    if(general_info.getChildText("MatchNegativeScoring")!=null)
                        HandleEvents.generalSettings.setMatch_negative_scoring(new BigDecimal(general_info.getChildText("MatchNegativeScoring")).intValue());
                     if(general_info.getChildText("MinFileSize")!=null)
                        HandleEvents.generalSettings.setMin_file_size(new BigDecimal(general_info.getChildText("MinFileSize")).intValue());
                     if(general_info.getChildText("MaxReplayDelay")!=null)
                        HandleEvents.generalSettings.setMax_replay_delay(new BigDecimal(general_info.getChildText("MaxReplayDelay")).intValue());
                     HandleEvents.generalSettings.setSkill_test(getBoolvalue(general_info.getChildText("SkillTest")));
                     if(general_info.getChildText("SkillTestValue")!=null)
                        HandleEvents.generalSettings.setSkill_test_value(new BigDecimal(general_info.getChildText("SkillTestValue")).intValue());   
                     if(general_info.getChildText("SixerVideo")!=null)
                        HandleEvents.generalSettings.setPlay_sixer_video(Boolean.parseBoolean(general_info.getChildText("SixerVideo")));
                     if(general_info.getChildText("WicketVideo")!=null)
                        HandleEvents.generalSettings.setPlay_wicket_video(Boolean.parseBoolean(general_info.getChildText("WicketVideo")));
                     if(general_info.getChildText("CloudReporting")!=null)
                        HandleEvents.generalSettings.setCloud_reporting(Boolean.parseBoolean(general_info.getChildText("CloudReporting")));
                     if(general_info.getChildText("BaudRate")!=null)
                        HandleEvents.generalSettings.setBaudrate(new BigDecimal(general_info.getChildText("BaudRate")).intValue());
                     if(general_info.getChildText("TCPPort")!=null)
                        HandleEvents.generalSettings.setTcp_port(new BigDecimal(general_info.getChildText("TCPPort")).intValue());
                     if(general_info.getChildText("AutoScoringType")!=null)
                        HandleEvents.generalSettings.setAuto_scoring_enable(Integer.parseInt(general_info.getChildText("AutoScoringType")));
                     if(general_info.getChildText("DefaultSpeed")!=null)
                        HandleEvents.generalSettings.setDefault_speed(Integer.parseInt(general_info.getChildText("DefaultSpeed")));
                     if(general_info.getChildText("PlayerInitTime")!=null)
                        HandleEvents.generalSettings.setPlayer_init_time(Integer.parseInt(general_info.getChildText("PlayerInitTime")));
                     
                }
                           
                Element bowler_sequence = settingroot.getChild("BowlerSequence");
                if(bowler_sequence!=null){
                    Element sequence1 = bowler_sequence.getChild("Sequence1");
                    Element sequence2 = bowler_sequence.getChild("Sequence2");
                    List<Element> eList1 = sequence1.getChildren("bowler_sel");
                    if(eList1!=null && eList1.size()>0){
                        for (int i = 0; i < eList1.size(); i++){
                            HandleEvents.generalSettings.getBowler_sequence1().add(new BigDecimal(eList1.get(i).getText()).intValue()); 
                        }
                    }
                    List<Element> eList2 = sequence2.getChildren("bowler_sel");
                    if(eList2!=null && eList2.size()>0){
                        for (int i = 0; i < eList2.size(); i++){
                            HandleEvents.generalSettings.getBowler_sequence2().add(new BigDecimal(eList2.get(i).getText()).intValue()); 
                        }
                    }
                }else{
                    for(int i=0;i<8;i++){
                        HandleEvents.generalSettings.getBowler_sequence1().add(i+1);
                        HandleEvents.generalSettings.getBowler_sequence2().add(i+1);
                    }
                }
                Element mode_info = settingroot.getChild("ModeInfo");
                if(mode_info!=null){
                    List<Element> eList1 = mode_info.getChildren("ModeData");
                    if(eList1!=null && eList1.size()>0){
                        for (int i = 0; i < eList1.size(); i++){
                            Element modedata = eList1.get(i);
                            List<Element> eList2 = modedata.getChildren("BowlerPath");
                            for(int j=0;j<eList2.size();j++){
                                HandleEvents.generalSettings.getModeData().getBowler_path()[j]=eList2.get(j).getText();
                            }
                            List<Element> eList3 = modedata.getChildren("BallReleasePos");
                            for(int j=0;j<eList3.size();j++){
                                HandleEvents.generalSettings.getModeData().getTrigger_interval()[j]=new BigDecimal(eList3.get(j).getText()).intValue();                                
                            }
                            List<Element> eList4 = modedata.getChildren("Ballspeed1");
                            for(int j=0;eList4!=null&&j<eList4.size();j++){
                                HandleEvents.generalSettings.getModeData().getBowling_speed()[j][0]=new BigDecimal(eList4.get(j).getText()).intValue();                                
                            }
                            List<Element> eList5 = modedata.getChildren("Ballspeed2");
                            for(int j=0;eList5!=null&&j<eList5.size();j++){
                                HandleEvents.generalSettings.getModeData().getBowling_speed()[j][1]=new BigDecimal(eList5.get(j).getText()).intValue();                                
                            }
                            List<Element> eList6 = modedata.getChildren("Ballspeed3");
                            for(int j=0;eList6!=null&&j<eList6.size();j++){
                                HandleEvents.generalSettings.getModeData().getBowling_speed()[j][2]=new BigDecimal(eList6.get(j).getText()).intValue();                                
                            }
                            List<Element> eList7 = modedata.getChildren("Ballspeed4");
                            for(int j=0;eList7!=null&&j<eList7.size();j++){
                                HandleEvents.generalSettings.getModeData().getBowling_speed()[j][3]=new BigDecimal(eList7.get(j).getText()).intValue();                                
                            }
                            List<Element> eList8 = modedata.getChildren("BowlingType");
                            for(int j=0;eList8!=null&&j<eList8.size();j++){
                                HandleEvents.generalSettings.getModeData().getBowling_type()[j]=new BigDecimal(eList8.get(j).getText()).intValue();                                
                            }
                        }
                    }
                }
                Element autoscoring_info = settingroot.getChild("AutoScoringInfo");
                if(autoscoring_info!=null){
                    HandleEvents.generalSettings.getAutoScotringBean().setSerial_no(autoscoring_info.getChildText("SerialNo"));
                    HandleEvents.generalSettings.getAutoScotringBean().setPort_no(new BigDecimal(autoscoring_info.getChildText("PortNo")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setWicket_input(Boolean.parseBoolean(autoscoring_info.getChildText("StumpsEnable")));
                    if(autoscoring_info.getChildText("StumpsValue")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setWicket_sensor(new BigDecimal(autoscoring_info.getChildText("StumpsValue")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setSolenoid_input(Boolean.parseBoolean(autoscoring_info.getChildText("SolenoidEnable")));
                    if(autoscoring_info.getChildText("SolenoidValue")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSolenoid_sensor(new BigDecimal(autoscoring_info.getChildText("SolenoidValue")).intValue());
                    if(autoscoring_info.getChildText("BallSensor1")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setBall_sensor_1(new BigDecimal(autoscoring_info.getChildText("BallSensor1")).intValue());
                    if(autoscoring_info.getChildText("BallSensor2")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setBall_sensor_2(new BigDecimal(autoscoring_info.getChildText("BallSensor2")).intValue());
                    if(autoscoring_info.getChildText("SpeedFactor")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setMax_prev_pole_time_factor(new BigDecimal(autoscoring_info.getChildText("SpeedFactor")).doubleValue());
                    if(autoscoring_info.getChildText("ComPort")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setCom_port(autoscoring_info.getChildText("ComPort"));
                    if(autoscoring_info.getChildText("BaudRate")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setBaudrate(new BigDecimal(autoscoring_info.getChildText("BaudRate")).intValue());
                    if(autoscoring_info.getChildText("SixOutput")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSix_output(new BigDecimal(autoscoring_info.getChildText("SixOutput")).intValue());
                    if(autoscoring_info.getChildText("FourOutput")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setFour_output(new BigDecimal(autoscoring_info.getChildText("FourOutput")).intValue());
                    if(autoscoring_info.getChildText("TwoOutput")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setTwo_output(new BigDecimal(autoscoring_info.getChildText("TwoOutput")).intValue());
                    
                    HandleEvents.generalSettings.getAutoScotringBean().setDi_count(new BigDecimal(autoscoring_info.getChildText("DICount")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi1_pole(new BigDecimal(autoscoring_info.getChildText("DI1Pole")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi2_pole(new BigDecimal(autoscoring_info.getChildText("DI2Pole")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi3_pole(new BigDecimal(autoscoring_info.getChildText("DI3Pole")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi4_pole(new BigDecimal(autoscoring_info.getChildText("DI4Pole")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi5_pole(new BigDecimal(autoscoring_info.getChildText("DI5Pole")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi6_pole(new BigDecimal(autoscoring_info.getChildText("DI6Pole")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi7_pole(new BigDecimal(autoscoring_info.getChildText("DI7Pole")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi8_pole(new BigDecimal(autoscoring_info.getChildText("DI8Pole")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi9_pole(new BigDecimal(autoscoring_info.getChildText("DI9Pole")).intValue());
                    HandleEvents.generalSettings.getAutoScotringBean().setDi10_pole(new BigDecimal(autoscoring_info.getChildText("DI10Pole")).intValue());
                    if(autoscoring_info.getChildText("skill_1_score_time_1")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_1_score_time_1(new BigDecimal(autoscoring_info.getChildText("skill_1_score_time_1")).intValue());
                    if(autoscoring_info.getChildText("skill_1_score_time_2")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_1_score_time_2(new BigDecimal(autoscoring_info.getChildText("skill_1_score_time_2")).intValue());
                    if(autoscoring_info.getChildText("skill_1_score_time_3")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_1_score_time_3(new BigDecimal(autoscoring_info.getChildText("skill_1_score_time_3")).intValue());
                    if(autoscoring_info.getChildText("skill_2_score_time_1")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_2_score_time_1(new BigDecimal(autoscoring_info.getChildText("skill_2_score_time_1")).intValue());
                    if(autoscoring_info.getChildText("skill_2_score_time_2")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_2_score_time_2(new BigDecimal(autoscoring_info.getChildText("skill_2_score_time_2")).intValue());
                    if(autoscoring_info.getChildText("skill_2_score_time_3")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_2_score_time_3(new BigDecimal(autoscoring_info.getChildText("skill_2_score_time_3")).intValue());
                    if(autoscoring_info.getChildText("skill_3_score_time_1")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_3_score_time_1(new BigDecimal(autoscoring_info.getChildText("skill_3_score_time_1")).intValue());
                    if(autoscoring_info.getChildText("skill_3_score_time_2")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_3_score_time_2(new BigDecimal(autoscoring_info.getChildText("skill_3_score_time_2")).intValue());
                    if(autoscoring_info.getChildText("skill_3_score_time_3")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_3_score_time_3(new BigDecimal(autoscoring_info.getChildText("skill_3_score_time_3")).intValue());
                    if(autoscoring_info.getChildText("skill_4_score_time_1")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_4_score_time_1(new BigDecimal(autoscoring_info.getChildText("skill_4_score_time_1")).intValue());
                    if(autoscoring_info.getChildText("skill_4_score_time_2")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_4_score_time_2(new BigDecimal(autoscoring_info.getChildText("skill_4_score_time_2")).intValue());
                    if(autoscoring_info.getChildText("skill_4_score_time_3")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_4_score_time_3(new BigDecimal(autoscoring_info.getChildText("skill_4_score_time_3")).intValue());
                    
                    if(autoscoring_info.getChildText("skill_1_wicket_time")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_1_wicket_time(new BigDecimal(autoscoring_info.getChildText("skill_1_wicket_time")).intValue());
                    if(autoscoring_info.getChildText("skill_2_wicket_time")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_2_wicket_time(new BigDecimal(autoscoring_info.getChildText("skill_2_wicket_time")).intValue());
                    if(autoscoring_info.getChildText("skill_3_wicket_time")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_3_wicket_time(new BigDecimal(autoscoring_info.getChildText("skill_3_wicket_time")).intValue());
                    if(autoscoring_info.getChildText("skill_4_wicket_time")!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setSkill_4_wicket_time(new BigDecimal(autoscoring_info.getChildText("skill_4_wicket_time")).intValue());
                    
                    HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().clear();
                    Element combination_info = autoscoring_info.getChild("CombinationInfo");
                    if(combination_info!=null){
                        HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().clear();
                        List<Element> eList1 = combination_info.getChildren("Combination");
                        if(eList1!=null && eList1.size()>0){
                            for (int i = 0; i < eList1.size(); i++){
                                Element combination = eList1.get(i);
                                ScoreCombinationBean combinationBean = new ScoreCombinationBean();
                                HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().add(combinationBean);
                                combinationBean.setResult(new BigDecimal(combination.getChildText("Result")).intValue());
                                combinationBean.setPos_1(new BigDecimal(combination.getChildText("Pos1")).intValue());
                                combinationBean.setPos_2(new BigDecimal(combination.getChildText("Pos2")).intValue());
                                combinationBean.setPos_3(new BigDecimal(combination.getChildText("Pos3")).intValue());
                                combinationBean.setPos_4(new BigDecimal(combination.getChildText("Pos4")).intValue());
                                combinationBean.setPos_5(new BigDecimal(combination.getChildText("Pos5")).intValue());
                            }
                        }                        
                    }
                }else{
                    HandleEvents.generalSettings.setAutoScotringBean(new AutoScoringBean());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static boolean getBoolvalue(String value){
        boolean val = false;
        try {
            val = Boolean.parseBoolean(value);
        } catch (Exception e) {
            try {
                int int_val = new BigDecimal(value).intValue();                       
                if(int_val==1)
                    val=true;
            } catch (Exception ex) {

            }
        }        
        return val;
    }
    public static void saveData(){
       try {
            if(workingDir == null)
                workingDir = System.getProperty("user.dir");
            Document doc=new Document();
            Element root=new Element("ZAPCricketSimulator");
            doc.setRootElement(root);
            Calendar calendar_test = Calendar.getInstance();
            root.setAttribute("UpdatedOn", calendar_test.getTime().toString());
            root.setAttribute("version", ZaPCricketSimulator.version);
            
            Element general_info = new Element("GeneralSettings");
            root.addContent(general_info);
            general_info.addContent(createElement("KeypadEnable", HandleEvents.generalSettings.isKeypad_enable()+""));
            general_info.addContent(createElement("TestMode", HandleEvents.generalSettings.isTest_mode()+""));
            general_info.addContent(createElement("TabletEnable", HandleEvents.generalSettings.isTablet_mode_enable()+""));
            general_info.addContent(createElement("TCPPort", HandleEvents.generalSettings.getTcp_port()+""));
            general_info.addContent(createElement("ReplayEnable", HandleEvents.generalSettings.isReplay_enable()+""));
            general_info.addContent(createElement("ReplayDuration", HandleEvents.generalSettings.getReplayduration_ms()+""));
            general_info.addContent(createElement("ReplayFormat", HandleEvents.generalSettings.getFormat()+""));
            general_info.addContent(createElement("CameraIP", HandleEvents.generalSettings.getCamera_ip()+""));
            general_info.addContent(createElement("CameraPort", HandleEvents.generalSettings.getCamera_port()+""));
            general_info.addContent(createElement("CameraUserID", HandleEvents.generalSettings.getCamera_user_id()+""));
            general_info.addContent(createElement("CameraPassword", HandleEvents.generalSettings.getCamera_password()+""));
            general_info.addContent(createElement("DefaultBowler", HandleEvents.generalSettings.getDefault_bowler()+""));
            //general_info.addContent(createElement("DefaultBowlerRelesePos", HandleEvents.generalSettings.getDefault_bowler_relese_pos()+""));
            //general_info.addContent(createElement("DefaultBowlerRelesePos2", HandleEvents.generalSettings.getDefault_bowler_relese_pos2()+""));
            general_info.addContent(createElement("TouchScreenIndex", HandleEvents.generalSettings.getTouchscreen_index()+""));
            general_info.addContent(createElement("ProjectorScreenIndex", HandleEvents.generalSettings.getProjector_screen_index()+""));
            general_info.addContent(createElement("ComPort", HandleEvents.generalSettings.getCom_port()+""));
            general_info.addContent(createElement("MatchNegativeScoring", HandleEvents.generalSettings.getMatch_negative_scoring()+""));
            general_info.addContent(createElement("MinFileSize", HandleEvents.generalSettings.getMin_file_size()+""));
            general_info.addContent(createElement("MaxReplayDelay", HandleEvents.generalSettings.getMax_replay_delay()+""));
            general_info.addContent(createElement("SkillTest", HandleEvents.generalSettings.isSkill_test()+""));
            general_info.addContent(createElement("SkillTestValue", HandleEvents.generalSettings.getSkill_test_value()+""));
            general_info.addContent(createElement("BaudRate", HandleEvents.generalSettings.getBaudrate()+""));            
            general_info.addContent(createElement("AutoScoringType", HandleEvents.generalSettings.getAuto_scoring_enable()+""));
            general_info.addContent(createElement("SixerVideo", HandleEvents.generalSettings.isPlay_sixer_video()+""));
            general_info.addContent(createElement("WicketVideo", HandleEvents.generalSettings.isPlay_wicket_video()+""));
            general_info.addContent(createElement("CloudReporting", HandleEvents.generalSettings.isCloud_reporting()+""));
            general_info.addContent(createElement("DefaultSpeed", HandleEvents.generalSettings.getDefault_speed()+""));
            general_info.addContent(createElement("PlayerInitTime", HandleEvents.generalSettings.getPlayer_init_time()+""));
            
            Element bowler_sequence = new Element("BowlerSequence");
            root.addContent(bowler_sequence);
            Element sequence1 = new Element("Sequence1");
            bowler_sequence.addContent(sequence1);
            for(int j=0;j<HandleEvents.generalSettings.getBowler_sequence1().size();j++){
                sequence1.addContent(createElement("bowler_sel", HandleEvents.generalSettings.getBowler_sequence1().get(j).intValue()+""));
            }
            Element sequence2 = new Element("Sequence2");
            bowler_sequence.addContent(sequence2);
            for(int j=0;j<HandleEvents.generalSettings.getBowler_sequence2().size();j++){
                sequence2.addContent(createElement("bowler_sel", HandleEvents.generalSettings.getBowler_sequence2().get(j).intValue()+""));
            }
            
            Element mode_info = new Element("ModeInfo");
            root.addContent(mode_info);
            Element modedata = new Element("ModeData");
            mode_info.addContent(modedata);
            for(int j=0;j<8;j++){
                modedata.addContent(createElement("BowlerPath", HandleEvents.generalSettings.getModeData().getBowler_path()[j]+""));
                modedata.addContent(createElement("BallReleasePos", HandleEvents.generalSettings.getModeData().getTrigger_interval()[j]+""));
                modedata.addContent(createElement("Ballspeed1", HandleEvents.generalSettings.getModeData().getBowling_speed()[j][0]+""));
                modedata.addContent(createElement("Ballspeed2", HandleEvents.generalSettings.getModeData().getBowling_speed()[j][1]+""));
                modedata.addContent(createElement("Ballspeed3", HandleEvents.generalSettings.getModeData().getBowling_speed()[j][2]+""));
                modedata.addContent(createElement("Ballspeed4", HandleEvents.generalSettings.getModeData().getBowling_speed()[j][3]+""));
                modedata.addContent(createElement("BowlingType", HandleEvents.generalSettings.getModeData().getBowling_type()[j]+""));
            }
            Element autoscoring_info = new Element("AutoScoringInfo");
            root.addContent(autoscoring_info);
            autoscoring_info.addContent(createElement("SerialNo", HandleEvents.generalSettings.getAutoScotringBean().getSerial_no()+""));
            autoscoring_info.addContent(createElement("PortNo", HandleEvents.generalSettings.getAutoScotringBean().getPort_no()+""));
            autoscoring_info.addContent(createElement("StumpsEnable", HandleEvents.generalSettings.getAutoScotringBean().isWicket_input()+""));
            autoscoring_info.addContent(createElement("StumpsValue", HandleEvents.generalSettings.getAutoScotringBean().getWicket_sensor()+""));
            autoscoring_info.addContent(createElement("SolenoidEnable", HandleEvents.generalSettings.getAutoScotringBean().isSolenoid_input()+""));
            autoscoring_info.addContent(createElement("SolenoidValue", HandleEvents.generalSettings.getAutoScotringBean().getSolenoid_sensor()+""));
            autoscoring_info.addContent(createElement("BallSensor1", HandleEvents.generalSettings.getAutoScotringBean().getBall_sensor_1()+""));
            autoscoring_info.addContent(createElement("BallSensor2", HandleEvents.generalSettings.getAutoScotringBean().getBall_sensor_2()+""));
            autoscoring_info.addContent(createElement("SpeedFactor", HandleEvents.generalSettings.getAutoScotringBean().getMax_prev_pole_time_factor()+""));
            
            autoscoring_info.addContent(createElement("ComPort", HandleEvents.generalSettings.getAutoScotringBean().getCom_port()+""));
            autoscoring_info.addContent(createElement("BaudRate", HandleEvents.generalSettings.getAutoScotringBean().getBaudrate()+""));
            autoscoring_info.addContent(createElement("SixOutput", HandleEvents.generalSettings.getAutoScotringBean().getSix_output()+""));
            autoscoring_info.addContent(createElement("FourOutput", HandleEvents.generalSettings.getAutoScotringBean().getFour_output()+""));
            autoscoring_info.addContent(createElement("TwoOutput", HandleEvents.generalSettings.getAutoScotringBean().getTwo_output()+""));
            
            autoscoring_info.addContent(createElement("DICount", HandleEvents.generalSettings.getAutoScotringBean().getDi_count()+""));
            autoscoring_info.addContent(createElement("DI1Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi1_pole()+""));
            autoscoring_info.addContent(createElement("DI2Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi2_pole()+""));
            autoscoring_info.addContent(createElement("DI3Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi3_pole()+""));
            autoscoring_info.addContent(createElement("DI4Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi4_pole()+""));
            autoscoring_info.addContent(createElement("DI5Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi5_pole()+""));
            autoscoring_info.addContent(createElement("DI6Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi6_pole()+""));
            autoscoring_info.addContent(createElement("DI7Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi7_pole()+""));
            autoscoring_info.addContent(createElement("DI8Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi8_pole()+""));
            autoscoring_info.addContent(createElement("DI9Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi9_pole()+""));
            autoscoring_info.addContent(createElement("DI10Pole", HandleEvents.generalSettings.getAutoScotringBean().getDi10_pole()+""));
            
            autoscoring_info.addContent(createElement("skill_1_score_time_1", HandleEvents.generalSettings.getAutoScotringBean().getSkill_1_score_time_1()+""));
            autoscoring_info.addContent(createElement("skill_1_score_time_2", HandleEvents.generalSettings.getAutoScotringBean().getSkill_1_score_time_2()+""));
            autoscoring_info.addContent(createElement("skill_1_score_time_3", HandleEvents.generalSettings.getAutoScotringBean().getSkill_1_score_time_3()+""));
            autoscoring_info.addContent(createElement("skill_2_score_time_1", HandleEvents.generalSettings.getAutoScotringBean().getSkill_2_score_time_1()+""));
            autoscoring_info.addContent(createElement("skill_2_score_time_2", HandleEvents.generalSettings.getAutoScotringBean().getSkill_2_score_time_2()+""));
            autoscoring_info.addContent(createElement("skill_2_score_time_3", HandleEvents.generalSettings.getAutoScotringBean().getSkill_2_score_time_3()+""));
            autoscoring_info.addContent(createElement("skill_3_score_time_1", HandleEvents.generalSettings.getAutoScotringBean().getSkill_3_score_time_1()+""));
            autoscoring_info.addContent(createElement("skill_3_score_time_2", HandleEvents.generalSettings.getAutoScotringBean().getSkill_3_score_time_2()+""));
            autoscoring_info.addContent(createElement("skill_3_score_time_3", HandleEvents.generalSettings.getAutoScotringBean().getSkill_3_score_time_3()+""));
            autoscoring_info.addContent(createElement("skill_4_score_time_1", HandleEvents.generalSettings.getAutoScotringBean().getSkill_4_score_time_1()+""));
            autoscoring_info.addContent(createElement("skill_4_score_time_2", HandleEvents.generalSettings.getAutoScotringBean().getSkill_4_score_time_2()+""));
            autoscoring_info.addContent(createElement("skill_4_score_time_3", HandleEvents.generalSettings.getAutoScotringBean().getSkill_4_score_time_3()+""));
            
            autoscoring_info.addContent(createElement("skill_1_wicket_time", HandleEvents.generalSettings.getAutoScotringBean().getSkill_1_wicket_time()+""));
            autoscoring_info.addContent(createElement("skill_2_wicket_time", HandleEvents.generalSettings.getAutoScotringBean().getSkill_2_wicket_time()+""));
            autoscoring_info.addContent(createElement("skill_3_wicket_time", HandleEvents.generalSettings.getAutoScotringBean().getSkill_3_wicket_time()+""));
            autoscoring_info.addContent(createElement("skill_4_wicket_time", HandleEvents.generalSettings.getAutoScotringBean().getSkill_4_wicket_time()+""));
            
            if(HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations()!=null && HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().size()>0){
                Element combination_info = new Element("CombinationInfo");
                autoscoring_info.addContent(combination_info);
                for(int i=0;i<HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().size();i++){
                    ScoreCombinationBean combinationBean = HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().get(i);
                    Element combination = new Element("Combination");
                    combination_info.addContent(combination);
                    combination.addContent(createElement("Result", combinationBean.getResult()+""));
                    combination.addContent(createElement("Pos1", combinationBean.getPos_1()+""));
                    combination.addContent(createElement("Pos2", combinationBean.getPos_2()+""));
                    combination.addContent(createElement("Pos3", combinationBean.getPos_3()+""));
                    combination.addContent(createElement("Pos4", combinationBean.getPos_4()+""));
                    combination.addContent(createElement("Pos5", combinationBean.getPos_5()+""));
                }                
            }            
            XMLOutputter outter=new XMLOutputter();
            outter.setFormat(Format.getPrettyFormat());
            FileWriter mdata = new FileWriter(new File(workingDir, config_file));
            outter.output(doc, mdata);   
            mdata.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static Element createElement(String name , String data){
        Element tempele = new Element(name);
        tempele.addContent(data);
        return tempele;
    }
}
