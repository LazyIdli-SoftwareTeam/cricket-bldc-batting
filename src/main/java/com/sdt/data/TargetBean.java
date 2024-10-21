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
public class TargetBean {
    String name="Player1";
    String mob = "+91";
    int target_score=35;
    int target_bals=18;
    String comments = "";
    
    int current_score=0;
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

    public int getTarget_score() {
        return target_score;
    }

    public void setTarget_score(int target_score) {
        this.target_score = target_score;
    }

    public int getTarget_bals() {
        return target_bals;
    }

    public void setTarget_bals(int target_bals) {
        this.target_bals = target_bals;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getCurrent_score() {
        return current_score;
    }

    public void setCurrent_score(int current_score) {
        this.current_score = current_score;
    }

    public int getCurrent_balls() {
        return current_balls;
    }

    public void setCurrent_balls(int current_balls) {
        this.current_balls = current_balls;
    }
    
    
    
    
}
