/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * Comment by lazy idli 
 * util file to communicate with the serial port the straight drive hardware machine 
 * 
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
    
    //connect to the serial port and set the connection status to true
    public static void Connect(String port,int baudrate,int data_bits , int stop_bits , int parity){
        try {
            serialPort = new SerialPort(port);
            serialPort.setParams(baudrate, data_bits, stop_bits, parity);

            serialPort.openPort();
            serialPort.setParams(baudrate, data_bits, stop_bits, parity);
            status = true;


        } catch (Exception e) {
            System.out.println("something is happening here");
            //e.printStackTrace();
            status = false;
        }
    }
    //disconnect from the serial port and set the connection status to false
    public static void disconnect(){
        try {
            serialPort.closePort();
        } catch (Exception e) {
            
        }
        status = false;
    }
    //write data to the serial port streamline to the machine
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
    //read data from the machine
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
    //not sure but this will check any errors or changes in the communication channel
    
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
