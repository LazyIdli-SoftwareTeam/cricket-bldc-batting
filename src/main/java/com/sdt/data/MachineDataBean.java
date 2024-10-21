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
public class MachineDataBean {
    //set speed,3 motor speed , pwm start stop , swing in level , swing out level , spin off , spin leg , seem in , seem out , bounce low , bounce high , operating mode , ball error
    int set_speed = 45;
    int motor_speed[] = {30,30,0};
    int pwm_start_stop = 0;
    int swing_in_level=0;
    int swing_out_level=0;
    int spin_off=0;
    int spin_leg=0;
    int seem_in=0;
    int seem_out=0;
    int bounce_low=0;
    int bounce_high=0;
    int operating_mode=0;
    int ball_status=0;
    int skill_test=0;
    int read_status=0;
    int led_status=0;

    public int getSet_speed() {
        return set_speed;
    }

    public void setSet_speed(int set_speed) {
        this.set_speed = set_speed;
    }

    public int[] getMotor_speed() {
        return motor_speed;
    }

    public void setMotor_speed(int[] motor_speed) {
        this.motor_speed = motor_speed;
    }

    public int getPwm_start_stop() {
        return pwm_start_stop;
    }

    public void setPwm_start_stop(int pwm_start_stop) {
        this.pwm_start_stop = pwm_start_stop;
    }

    public int getSwing_in_level() {
        return swing_in_level;
    }

    public void setSwing_in_level(int swing_in_level) {
        this.swing_in_level = swing_in_level;
    }

    public int getSwing_out_level() {
        return swing_out_level;
    }

    public void setSwing_out_level(int swing_out_level) {
        this.swing_out_level = swing_out_level;
    }

    public int getSpin_off() {
        return spin_off;
    }

    public void setSpin_off(int spin_off) {
        this.spin_off = spin_off;
    }

    public int getSpin_leg() {
        return spin_leg;
    }

    public void setSpin_leg(int spin_leg) {
        this.spin_leg = spin_leg;
    }

    public int getSeem_in() {
        return seem_in;
    }

    public void setSeem_in(int seem_in) {
        this.seem_in = seem_in;
    }

    public int getSeem_out() {
        return seem_out;
    }

    public void setSeem_out(int seem_out) {
        this.seem_out = seem_out;
    }

    public int getBounce_low() {
        return bounce_low;
    }

    public void setBounce_low(int bounce_low) {
        this.bounce_low = bounce_low;
    }

    public int getBounce_high() {
        return bounce_high;
    }

    public void setBounce_high(int bounce_high) {
        this.bounce_high = bounce_high;
    }

    public int getOperating_mode() {
        return operating_mode;
    }

    public void setOperating_mode(int operating_mode) {
        this.operating_mode = operating_mode;
    }

    public int getBall_status() {
        return ball_status;
    }

    public void setBall_status(int ball_status) {
        this.ball_status = ball_status;
    }

    public int getSkill_test() {
        return skill_test;
    }

    public void setSkill_test(int skill_test) {
        this.skill_test = skill_test;
    }

    public int getRead_status() {
        return read_status;
    }

    public void setRead_status(int read_status) {
        this.read_status = read_status;
    }    

    public int getLed_status() {
        return led_status;
    }

    public void setLed_status(int led_status) {
        this.led_status = led_status;
    }
    
}
