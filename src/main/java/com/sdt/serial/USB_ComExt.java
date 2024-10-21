/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.serial;

import jssc.SerialPort;
import jssc.SerialPortList;
import zapcricketsimulator.HandleEvents;


//same copy of the above file

/**
 *
 * @author srikanth.possim
 */
public class USB_ComExt {
    public static boolean status = false;
    static SerialPort serialPort = null;
    
    public static String[] getPortList(){
        return  SerialPortList.getPortNames();
    }
    
    public static void Connect(){
        Connect(HandleEvents.generalSettings.getAutoScotringBean().getCom_port(), HandleEvents.generalSettings.getAutoScotringBean().getBaudrate(), SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        if(status){
            byte cmd[]={0x23,0x12,0x09,1,1,0,21};
            USB_ComExt.WriteData(cmd);
            
        }
    }
    
    public static void Connect(String port,int baudrate,int data_bits , int stop_bits , int parity){
        try {
            serialPort = new SerialPort(port);
            serialPort.openPort();
            serialPort.setParams(baudrate, data_bits, stop_bits, parity);
            status = true;
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
        //for(int  i = 0 ; i < data.length ; i++)
        //    System.out.println(Byte.toUnsignedInt(data[i]));
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
            //e.printStackTrace();
        }
        return tempbyte;
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
