/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.data;

import java.util.ArrayList;

/**
 *
 * @author possi
 */
public class GeneralSettingsBean {
    
    String machine_id = "TestMach001";
    boolean keypad_enable = false;
    boolean test_mode = false;
    boolean tablet_mode_enable = false;
    boolean replay_enable = false;
    String format = "libx264";
    String camera_ip = "192.168.1.64";
    int camera_port=554;
    String camera_user_id="admin";
    String camera_password="Sd123456";    
    int replayduration_ms = 5000;
    //String default_bowler_path="";
    //int default_bowler_relese_pos=500;
    //int default_bowler_relese_pos2=500;
    int default_bowler=1;
    int projector_screen_index = 1;
    int touchscreen_index =0;
    String com_port="COM3";
    int match_negative_scoring = 2;
    int min_file_size = 50000;
    int max_replay_delay = 2000;
    boolean skill_test=false;
    int skill_test_value=10;
    int baudrate =57600;
    int tcp_port = 9999;
    boolean play_sixer_video = true;
    boolean play_wicket_video = true;
    boolean cloud_reporting = false;
    int auto_scoring_enable=0;
    AutoScoringBean autoScotringBean = new AutoScoringBean();
    ModeDatBean modeData = new ModeDatBean();
    ArrayList<Integer> bowler_sequence1 = new ArrayList<>();
    ArrayList<Integer> bowler_sequence2 = new ArrayList<>();
    
    int default_speed = 45;
    int player_init_time = 15;

    public String getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(String machine_id) {
        this.machine_id = machine_id;
    }
    
    public boolean isKeypad_enable() {
        return keypad_enable;
    }

    public void setKeypad_enable(boolean keypad_enable) {
        this.keypad_enable = keypad_enable;
    }

    public boolean isTest_mode() {
        return test_mode;
    }

    public void setTest_mode(boolean test_mode) {
        this.test_mode = test_mode;
    }

    public boolean isTablet_mode_enable() {
        return tablet_mode_enable;
    }

    public void setTablet_mode_enable(boolean tablet_mode_enable) {
        this.tablet_mode_enable = tablet_mode_enable;
    }
    
    public boolean isReplay_enable() {
        return replay_enable;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    
    public String getCamera_ip() {
        return camera_ip;
    }

    public void setCamera_ip(String camera_ip) {
        this.camera_ip = camera_ip;
    }

    public int getCamera_port() {
        return camera_port;
    }

    public void setCamera_port(int camera_port) {
        this.camera_port = camera_port;
    }

    public String getCamera_user_id() {
        return camera_user_id;
    }

    public void setCamera_user_id(String camera_user_id) {
        this.camera_user_id = camera_user_id;
    }

    public String getCamera_password() {
        return camera_password;
    }

    public void setCamera_password(String camera_password) {
        this.camera_password = camera_password;
    }

    
    
    public void setReplay_enable(boolean replay_enable) {
        this.replay_enable = replay_enable;
    }

    public int getReplayduration_ms() {
        return replayduration_ms;
    }

    public void setReplayduration_ms(int replayduration_ms) {
        this.replayduration_ms = replayduration_ms;
    }

    /*public String getDefault_bowler_path() {
        return default_bowler_path;
    }

    public void setDefault_bowler_path(String default_bowler_path) {
        this.default_bowler_path = default_bowler_path;
    }

    public int getDefault_bowler_relese_pos() {
        return default_bowler_relese_pos;
    }

    public void setDefault_bowler_relese_pos(int default_bowler_relese_pos) {
        this.default_bowler_relese_pos = default_bowler_relese_pos;
    }

    public int getDefault_bowler_relese_pos2() {
        return default_bowler_relese_pos2;
    }

    public void setDefault_bowler_relese_pos2(int default_bowler_relese_pos2) {
        this.default_bowler_relese_pos2 = default_bowler_relese_pos2;
    }*/

    public int getDefault_bowler() {
        return default_bowler;
    }

    public void setDefault_bowler(int default_bowler) {
        this.default_bowler = default_bowler;
    }   

    public int getProjector_screen_index() {
        return projector_screen_index;
    }

    public void setProjector_screen_index(int projector_screen_index) {
        this.projector_screen_index = projector_screen_index;
    }

    public int getTouchscreen_index() {
        return touchscreen_index;
    }

    public void setTouchscreen_index(int touchscreen_index) {
        this.touchscreen_index = touchscreen_index;
    }

    public String getCom_port() {
        return com_port;
    }

    public void setCom_port(String com_port) {
        this.com_port = com_port;
    }

    public int getMatch_negative_scoring() {
        return match_negative_scoring;
    }

    public void setMatch_negative_scoring(int match_negative_scoring) {
        this.match_negative_scoring = match_negative_scoring;
    }

    public int getMin_file_size() {
        return min_file_size;
    }

    public void setMin_file_size(int min_file_size) {
        this.min_file_size = min_file_size;
    }

    /*public int getSkill_test() {
        return skill_test;
    }

    public void setSkill_test(int skill_test) {
        this.skill_test = skill_test;
    }*/

    public boolean isSkill_test() {
        return skill_test;
    }

    public void setSkill_test(boolean skill_test) {
        this.skill_test = skill_test;
    }

    public int getSkill_test_value() {
        return skill_test_value;
    }

    public void setSkill_test_value(int skill_test_value) {
        this.skill_test_value = skill_test_value;
    }    

    public boolean isPlay_sixer_video() {
        return play_sixer_video;
    }

    public void setPlay_sixer_video(boolean play_sixer_video) {
        this.play_sixer_video = play_sixer_video;
    }

    public boolean isPlay_wicket_video() {
        return play_wicket_video;
    }

    public void setPlay_wicket_video(boolean play_wicket_video) {
        this.play_wicket_video = play_wicket_video;
    }

    public boolean isCloud_reporting() {
        return cloud_reporting;
    }

    public void setCloud_reporting(boolean cloud_reporting) {
        this.cloud_reporting = cloud_reporting;
    }

    public ArrayList<Integer> getBowler_sequence1() {
        return bowler_sequence1;
    }

    public void setBowler_sequence1(ArrayList<Integer> bowler_sequence1) {
        this.bowler_sequence1 = bowler_sequence1;
    }

    public ArrayList<Integer> getBowler_sequence2() {
        return bowler_sequence2;
    }

    public void setBowler_sequence2(ArrayList<Integer> bowler_sequence2) {
        this.bowler_sequence2 = bowler_sequence2;
    }
    
    

    /*public int getBall_type() {
        return ball_type;
    }

    public void setBall_type(int ball_type) {
        this.ball_type = ball_type;
    }*/

    public int getMax_replay_delay() {
        return max_replay_delay;
    }

    public void setMax_replay_delay(int max_replay_delay) {
        this.max_replay_delay = max_replay_delay;
    }

    public int getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(int baudrate) {
        this.baudrate = baudrate;
    }

    public int getTcp_port() {
        return tcp_port;
    }

    public void setTcp_port(int tcp_port) {
        this.tcp_port = tcp_port;
    }

    public int getAuto_scoring_enable() {
        return auto_scoring_enable;
    }

    public void setAuto_scoring_enable(int auto_scoring_enable) {
        this.auto_scoring_enable = auto_scoring_enable;
    }

    public AutoScoringBean getAutoScotringBean() {
        return autoScotringBean;
    }

    public void setAutoScotringBean(AutoScoringBean autoScotringBean) {
        this.autoScotringBean = autoScotringBean;
    }

    public ModeDatBean getModeData() {
        return modeData;
    }

    public void setModeData(ModeDatBean modeData) {
        this.modeData = modeData;
    }

    public int getDefault_speed() {
        return default_speed;
    }

    public void setDefault_speed(int default_speed) {
        this.default_speed = default_speed;
    }

    public int getPlayer_init_time() {
        return player_init_time;
    }

    public void setPlayer_init_time(int player_init_time) {
        this.player_init_time = player_init_time;
    }
    
    
    
}
