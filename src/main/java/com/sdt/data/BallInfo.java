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
public class BallInfo {
    int result = 0;
    int type =0;
    int speed = 0;
    int level =0;
    boolean skill_enable = false;
    int other_data=0;
    public BallInfo(int result,int type,int speed,int level,boolean skill_enable){
        this.result = result;
        this.type=type;
        this.speed=speed;
        this.level=level;
        this.skill_enable=skill_enable;
    }
    public BallInfo(int result,int type,int speed,int level,int other_data){
        this.result = result;
        this.type=type;
        this.speed=speed;
        this.level=level;
        this.other_data=other_data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOther_data() {
        return other_data;
    }

    public void setOther_data(int other_data) {
        this.other_data = other_data;
    }
    
    
}
