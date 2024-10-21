/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 *
 * @author Srikanth
 */
public class Broadcast {
    
    public static void main(String [] args){
        try {
            String host = Inet4Address.getLocalHost().getHostAddress();
            //System.out.println(host.substring(0, host.lastIndexOf(".")));
            host = host.substring(0, host.lastIndexOf("."))+".255";
            int port = 4211;
            System.out.println(host);
            byte[] message = "BallReleased".getBytes();

            // Get the internet address of the specified host

            InetAddress address = InetAddress.getByName(host);

            // Initialize a datagram packet with data and address
            DatagramPacket packet = new DatagramPacket(message, message.length,
                address, port);

            // Create a datagram socket, send the packet through it, close it.
            DatagramSocket dsocket = new DatagramSocket();
            dsocket.send(packet);
            Thread.sleep(2);
            dsocket.send(packet);
            dsocket.close();
          } catch (Exception e) {
            System.err.println(e);
          }
    }
    
}
