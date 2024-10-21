/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.data;

import java.util.ArrayList;

/**
 *
 * @author Srikanth
 */
public class AutoScoringBean {
    String serial_no = "DISTS";
    int port_no= 4211;
    int di_count = 0;
    int di1_pole = -1;
    int di2_pole = -1;
    int di3_pole = -1;
    int di4_pole = -1;
    int di5_pole = -1;
    int di6_pole = -1;
    int di7_pole = -1;
    int di8_pole = -1;
    int di9_pole = -1;
    int di10_pole= -1;
    boolean wicket_input = false;
    int wicket_sensor = 11;
    boolean solenoid_input = false;
    int solenoid_sensor = 12;
    
    int skill_1_score_time_1 = 1000;
    int skill_1_score_time_2 = 1200;
    int skill_1_score_time_3 = 1500;
    int skill_2_score_time_1 = 700;
    int skill_2_score_time_2 = 900;
    int skill_2_score_time_3 = 1200;
    int skill_3_score_time_1 = 600;
    int skill_3_score_time_2 = 800;
    int skill_3_score_time_3 = 1100;
    int skill_4_score_time_1 = 500;
    int skill_4_score_time_2 = 700;
    int skill_4_score_time_3 = 1000;
    
    int skill_1_wicket_time = 1300;
    int skill_2_wicket_time = 1100;
    int skill_3_wicket_time = 1000;
    int skill_4_wicket_time = 900;
    
    int ball_sensor_1 = 5;
    int ball_sensor_2 = 6;
    double max_prev_pole_time_factor = 3.0;
    int max_prev_pole_time_ref = 650;
    
    String com_port="COM9";
    int baudrate =56000;
    int six_output=2;
    int four_output=1;
    int two_output=0;
    
    ArrayList<ScoreCombinationBean> scorecombinations = new ArrayList<>();

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public int getPort_no() {
        return port_no;
    }

    public void setPort_no(int port_no) {
        this.port_no = port_no;
    }

    public int getDi_count() {
        return di_count;
    }

    public void setDi_count(int di_count) {
        this.di_count = di_count;
    }

    public int getDi1_pole() {
        return di1_pole;
    }

    public void setDi1_pole(int di1_pole) {
        this.di1_pole = di1_pole;
    }

    public int getDi2_pole() {
        return di2_pole;
    }

    public void setDi2_pole(int di2_pole) {
        this.di2_pole = di2_pole;
    }

    public int getDi3_pole() {
        return di3_pole;
    }

    public void setDi3_pole(int di3_pole) {
        this.di3_pole = di3_pole;
    }

    public int getDi4_pole() {
        return di4_pole;
    }

    public void setDi4_pole(int di4_pole) {
        this.di4_pole = di4_pole;
    }

    public int getDi5_pole() {
        return di5_pole;
    }

    public void setDi5_pole(int di5_pole) {
        this.di5_pole = di5_pole;
    }

    public int getDi6_pole() {
        return di6_pole;
    }

    public void setDi6_pole(int di6_pole) {
        this.di6_pole = di6_pole;
    }

    public int getDi7_pole() {
        return di7_pole;
    }

    public void setDi7_pole(int di7_pole) {
        this.di7_pole = di7_pole;
    }

    public int getDi8_pole() {
        return di8_pole;
    }

    public void setDi8_pole(int di8_pole) {
        this.di8_pole = di8_pole;
    }

    public int getDi9_pole() {
        return di9_pole;
    }

    public void setDi9_pole(int di9_pole) {
        this.di9_pole = di9_pole;
    }

    public int getDi10_pole() {
        return di10_pole;
    }

    public void setDi10_pole(int di10_pole) {
        this.di10_pole = di10_pole;
    }

    public boolean isWicket_input() {
        return wicket_input;
    }

    public void setWicket_input(boolean wicket_input) {
        this.wicket_input = wicket_input;
    }

    public boolean isSolenoid_input() {
        return solenoid_input;
    }

    public void setSolenoid_input(boolean solenoid_input) {
        this.solenoid_input = solenoid_input;
    }

    public ArrayList<ScoreCombinationBean> getScorecombinations() {
        return scorecombinations;
    }

    public void setScorecombinations(ArrayList<ScoreCombinationBean> scorecombinations) {
        this.scorecombinations = scorecombinations;
    }

    public int getSkill_1_score_time_1() {
        return skill_1_score_time_1;
    }

    public void setSkill_1_score_time_1(int skill_1_score_time_1) {
        this.skill_1_score_time_1 = skill_1_score_time_1;
    }

    public int getSkill_1_score_time_2() {
        return skill_1_score_time_2;
    }

    public void setSkill_1_score_time_2(int skill_1_score_time_2) {
        this.skill_1_score_time_2 = skill_1_score_time_2;
    }

    public int getSkill_1_score_time_3() {
        return skill_1_score_time_3;
    }

    public void setSkill_1_score_time_3(int skill_1_score_time_3) {
        this.skill_1_score_time_3 = skill_1_score_time_3;
    }

    public int getSkill_2_score_time_1() {
        return skill_2_score_time_1;
    }

    public void setSkill_2_score_time_1(int skill_2_score_time_1) {
        this.skill_2_score_time_1 = skill_2_score_time_1;
    }

    public int getSkill_2_score_time_2() {
        return skill_2_score_time_2;
    }

    public void setSkill_2_score_time_2(int skill_2_score_time_2) {
        this.skill_2_score_time_2 = skill_2_score_time_2;
    }

    public int getSkill_2_score_time_3() {
        return skill_2_score_time_3;
    }

    public void setSkill_2_score_time_3(int skill_2_score_time_3) {
        this.skill_2_score_time_3 = skill_2_score_time_3;
    }

    public int getSkill_3_score_time_1() {
        return skill_3_score_time_1;
    }

    public void setSkill_3_score_time_1(int skill_3_score_time_1) {
        this.skill_3_score_time_1 = skill_3_score_time_1;
    }

    public int getSkill_3_score_time_2() {
        return skill_3_score_time_2;
    }

    public void setSkill_3_score_time_2(int skill_3_score_time_2) {
        this.skill_3_score_time_2 = skill_3_score_time_2;
    }

    public int getSkill_3_score_time_3() {
        return skill_3_score_time_3;
    }

    public void setSkill_3_score_time_3(int skill_3_score_time_3) {
        this.skill_3_score_time_3 = skill_3_score_time_3;
    }

    public int getSkill_4_score_time_1() {
        return skill_4_score_time_1;
    }

    public void setSkill_4_score_time_1(int skill_4_score_time_1) {
        this.skill_4_score_time_1 = skill_4_score_time_1;
    }

    public int getSkill_4_score_time_2() {
        return skill_4_score_time_2;
    }

    public void setSkill_4_score_time_2(int skill_4_score_time_2) {
        this.skill_4_score_time_2 = skill_4_score_time_2;
    }

    public int getSkill_4_score_time_3() {
        return skill_4_score_time_3;
    }

    public void setSkill_4_score_time_3(int skill_4_score_time_3) {
        this.skill_4_score_time_3 = skill_4_score_time_3;
    }

    public int getSkill_1_wicket_time() {
        return skill_1_wicket_time;
    }

    public void setSkill_1_wicket_time(int skill_1_wicket_time) {
        this.skill_1_wicket_time = skill_1_wicket_time;
    }

    public int getSkill_2_wicket_time() {
        return skill_2_wicket_time;
    }

    public void setSkill_2_wicket_time(int skill_2_wicket_time) {
        this.skill_2_wicket_time = skill_2_wicket_time;
    }

    public int getSkill_3_wicket_time() {
        return skill_3_wicket_time;
    }

    public void setSkill_3_wicket_time(int skill_3_wicket_time) {
        this.skill_3_wicket_time = skill_3_wicket_time;
    }

    public int getSkill_4_wicket_time() {
        return skill_4_wicket_time;
    }

    public void setSkill_4_wicket_time(int skill_4_wicket_time) {
        this.skill_4_wicket_time = skill_4_wicket_time;
    }    

    public int getBall_sensor_1() {
        return ball_sensor_1;
    }

    public void setBall_sensor_1(int ball_sensor_1) {
        this.ball_sensor_1 = ball_sensor_1;
    }

    public int getBall_sensor_2() {
        return ball_sensor_2;
    }

    public void setBall_sensor_2(int ball_sensor_2) {
        this.ball_sensor_2 = ball_sensor_2;
    }

    public double getMax_prev_pole_time_factor() {
        return max_prev_pole_time_factor;
    }

    public void setMax_prev_pole_time_factor(double max_prev_pole_time_factor) {
        this.max_prev_pole_time_factor = max_prev_pole_time_factor;
    }

    public int getMax_prev_pole_time_ref() {
        return max_prev_pole_time_ref;
    }

    public void setMax_prev_pole_time_ref(int max_prev_pole_time_ref) {
        this.max_prev_pole_time_ref = max_prev_pole_time_ref;
    }

    public int getWicket_sensor() {
        return wicket_sensor;
    }

    public void setWicket_sensor(int wicket_sensor) {
        this.wicket_sensor = wicket_sensor;
    }

    public int getSolenoid_sensor() {
        return solenoid_sensor;
    }

    public void setSolenoid_sensor(int solenoid_sensor) {
        this.solenoid_sensor = solenoid_sensor;
    }

    public String getCom_port() {
        return com_port;
    }

    public void setCom_port(String com_port) {
        this.com_port = com_port;
    }

    public int getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(int baudrate) {
        this.baudrate = baudrate;
    }

    public int getSix_output() {
        return six_output;
    }

    public void setSix_output(int six_output) {
        this.six_output = six_output;
    }

    public int getFour_output() {
        return four_output;
    }

    public void setFour_output(int four_output) {
        this.four_output = four_output;
    }

    public int getTwo_output() {
        return two_output;
    }

    public void setTwo_output(int two_output) {
        this.two_output = two_output;
    }
    
    
}
