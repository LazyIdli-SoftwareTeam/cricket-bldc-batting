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
public class OverDataBean {
    ArrayList<BallInfo> ballsInfo = new ArrayList<>();

    public ArrayList<BallInfo> getBallsInfo() {
        return ballsInfo;
    }

    public void setBallsInfo(ArrayList<BallInfo> ballsInfo) {
        this.ballsInfo = ballsInfo;
    }
    
}
