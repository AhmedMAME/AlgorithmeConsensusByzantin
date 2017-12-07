/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensibs.algoconsensusbyzantinee;

/**
 *
 * @author Mehdi
 */
public class Lieutenant extends Process{
    
    public Lieutenant(Boolean isFaulty) {
        super(isFaulty);
    }
    
    public void sendMessage(Byte[] msg) {
        //notify();
    }
    
    public void choiceOrder() {
        
    }
    
    public void receiveMsg(Byte[] msg) {

    }
    
    @Override
    public void run(){
        Byte[] msg = null;
        receiveMsg(msg);
        choiceOrder();
        sendMessage(msg);
        //this.wait();
    }
}
