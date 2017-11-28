/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensibs.algoconsensusbyzantinee;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

/**
 *
 * @author Mehdi
 */
public class Process extends Thread {
    protected PrivateKey privateKey;
    protected PublicKey publicKey;
    
    private Boolean isFaulty;

    public Process(Boolean isFaulty) {
        this.isFaulty = isFaulty;
    }
    
    public void generateKey() {
        
    }
    
    public void publishKey(Long id, PublicKey publicKey) {
        
    }

    public Boolean getIsFaulty() {
        return isFaulty;
    }
    
    
}
