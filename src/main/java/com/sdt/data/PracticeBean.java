/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.data;

/**
 *
 * @author possi
 */
public class PracticeBean {
    String name="Player1";
    String mob="+";
    int overs = 3;
    int ball_dilay=10;
    int over_delay=30;
    boolean autosellect=true;
    
    int current_balls=0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public int getBall_dilay() {
        return ball_dilay;
    }

    public void setBall_dilay(int ball_dilay) {
        this.ball_dilay = ball_dilay;
    }

    public int getOver_delay() {
        return over_delay;
    }

    public void setOver_delay(int over_delay) {
        this.over_delay = over_delay;
    }

    public int getCurrent_balls() {
        return current_balls;
    }

    public void setCurrent_balls(int current_balls) {
        this.current_balls = current_balls;
    }

    public boolean isAutosellect() {
        return autosellect;
    }

    public void setAutosellect(boolean autosellect) {
        this.autosellect = autosellect;
    }
    
    
    
    
}
