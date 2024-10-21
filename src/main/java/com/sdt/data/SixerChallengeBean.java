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
public class SixerChallengeBean {
    String name= "Player1";
    String mob="+";
    int balls=18;
    int sixers = 6;
    
    int current_balls=0;
    int current_sixers=0;

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

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public int getSixers() {
        return sixers;
    }

    public void setSixers(int sixers) {
        this.sixers = sixers;
    }

    public int getCurrent_balls() {
        return current_balls;
    }

    public void setCurrent_balls(int current_balls) {
        this.current_balls = current_balls;
    }

    public int getCurrent_sixers() {
        return current_sixers;
    }

    public void setCurrent_sixers(int current_sixers) {
        this.current_sixers = current_sixers;
    }
    
    
}
