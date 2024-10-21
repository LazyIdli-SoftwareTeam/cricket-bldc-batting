/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.data;

/**
 *
 * @author Srikanth
 */
public class PlayerBean {
    String player_id="";
    String player_name="";
    String player_email="";
    String player_mob="";
    int default_skill=2;

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

    public String getPlayer_email() {
        return player_email;
    }

    public void setPlayer_email(String player_email) {
        this.player_email = player_email;
    }

    public String getPlayer_mob() {
        return player_mob;
    }

    public void setPlayer_mob(String player_mob) {
        this.player_mob = player_mob;
    }

    public int getDefault_skill() {
        return default_skill;
    }

    public void setDefault_skill(int default_skill) {
        this.default_skill = default_skill;
    }
    
    
}
