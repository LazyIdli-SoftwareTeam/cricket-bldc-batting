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
public class PlayerGameBean {
    String player_id = "100";
    String player_name = "Player";
    int skill_level = 1;
    int hand_usage = 1;
    int ball_count =0;
    int total_score = 0;
    int extras = 0;
    int wickets = 0;    
    ArrayList<OverDataBean> overs = new ArrayList<>();

    public PlayerGameBean(int player_no){
        player_id = player_id+player_no;
        player_name = player_name +player_no;
    }
    
    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getSkill_level() {
        return skill_level;
    }

    public void setSkill_level(int skill_level) {
        this.skill_level = skill_level;
    }

    public int getBall_count() {
        return ball_count;
    }

    public void setBall_count(int ball_count) {
        this.ball_count = ball_count;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public int getExtras() {
        return extras;
    }

    public void setExtras(int extras) {
        this.extras = extras;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public ArrayList<OverDataBean> getOvers() {
        return overs;
    }

    public void setOvers(ArrayList<OverDataBean> overs) {
        this.overs = overs;
    }

    public int getHand_usage() {
        return hand_usage;
    }

    public void setHand_usage(int hand_usage) {
        this.hand_usage = hand_usage;
    }
   
}
