/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensibs.algoconsensusbyzantinee;

import java.net.Socket;

/**
 *
 * @author Mehdi
 */
public class Commandant extends Process{
    public Byte[] intialMsg; 
    public Socket _socket;

    public Commandant(Boolean isFaulty,Socket _socket) {
        super(isFaulty);
        this._socket = _socket;
    }
    
    public void sendMessage(Byte[] msg) {
        
    }
}
