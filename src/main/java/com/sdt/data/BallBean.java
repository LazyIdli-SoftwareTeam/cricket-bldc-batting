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
public class BallBean {
    int index = 0;
    String bowler_path = "C:\\cricket simulator\\Media\\bowler\\fast";
    int ball_release=1800;
    int ball_speed_1 = 70;
    int ball_speed_2 = 280;
    int ball_speed_3 = 280;
    int ball_speed_4 = 280;
    int tilt = 3000;
    int pan = 3000;
    int left = 1500;
    int right = 1500;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getBowler_path() {
        return bowler_path;
    }

    public void setBowler_path(String bowler_path) {
        this.bowler_path = bowler_path;
    }

    public int getBall_release() {
        return ball_release;
    }

    public void setBall_release(int ball_release) {
        this.ball_release = ball_release;
    }

    public int getBall_speed_1() {
        return ball_speed_1;
    }

    public void setBall_speed_1(int ball_speed_1) {
        this.ball_speed_1 = ball_speed_1;
    }

    public int getBall_speed_2() {
        return ball_speed_2;
    }

    public void setBall_speed_2(int ball_speed_2) {
        this.ball_speed_2 = ball_speed_2;
    }

    public int getBall_speed_3() {
        return ball_speed_3;
    }

    public void setBall_speed_3(int ball_speed_3) {
        this.ball_speed_3 = ball_speed_3;
    }

    public int getBall_speed_4() {
        return ball_speed_4;
    }

    public void setBall_speed_4(int ball_speed_4) {
        this.ball_speed_4 = ball_speed_4;
    }

    public int getTilt() {
        return tilt;
    }

    public void setTilt(int tilt) {
        this.tilt = tilt;
    }

    public int getPan() {
        return pan;
    }

    public void setPan(int pan) {
        this.pan = pan;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
    
    
}
