/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.serial;

import jssc.SerialPort;
import jssc.SerialPortList;

/**
 *
 * @author srikanth.possim
 */
public class USB_Com {
    public static boolean status = false;
    static SerialPort serialPort = null;
    
    public static String[] getPortList(){
        return  SerialPortList.getPortNames();
    }
    public static byte [] getCmd1(byte cmd){
        byte data[] = new byte[6];
        data[0]='#';
        data[1]=0x01;
        data[2]=cmd;
        data[3]=0x00;
        data[4]=USB_Com.getCRC(data, 3);
        data[5]='!';
        return data;
    }
    public static void Connect(String port,int baudrate,int data_bits , int stop_bits , int parity){
        try {
            serialPort = new SerialPort(port);
            serialPort.openPort();
            serialPort.setParams(baudrate, data_bits, stop_bits, parity);
            status = true;
            USB_Com.WriteData(getCmd1((byte)0x81));
            System.out.println("command written changing pan tilt");
        } catch (Exception e) {
            //e.printStackTrace();
            status = false;
        }
    }
    public static void disconnect(){
        try {
            serialPort.closePort();
        } catch (Exception e) {
            
        }
        status = false;
    }
    public static boolean WriteData(byte [] data){
        boolean success = true;
        try {
            if(status){
                serialPort.writeBytes(data);
            }else{
               success = false; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }    
    public static int readByte(int timeout){
        int tempbyte=-1;
        try {
            if(status){
                tempbyte = serialPort.readIntArray(1, timeout)[0];
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return tempbyte;
    }
    public static String print(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (byte b : bytes) {
            sb.append(String.format("0x%02X ", b));
        }
        sb.append("]");
        return sb.toString();
    }
    public static byte getCRC(byte [] temp , int len){
        byte crc = 0;
        for( int i = 0 ; i < len ; i++)
        {
            crc = (byte)(crc + temp[i]);
        }
        //System.out.println("CRC" + crc);
        return crc;
    }
}
