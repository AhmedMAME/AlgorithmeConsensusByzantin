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
public class Commandant extends Process{
    public Byte[] intialMsg; 

    public Commandant(Boolean isFaulty) {
        super(isFaulty);
    }
    
    public void sendMessage(Byte[] msg) {
        
    }
}
