/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensibs.algoconsensusbyzantinee.main;

import java.security.PublicKey;
import java.util.HashMap;

/**
 *
 * @author Mehdi
 */

public class Main {
    private static int nbrProces;
    private static int nbrMaxTraitre;
    private static int nbrTraitre;
    
    private static HashMap<Long, PublicKey> annuaire = new HashMap<>();
    
    private static int timeOut;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
      
    }

    public static HashMap<Long, PublicKey> getAnnuaire() {
        return annuaire;
    }

    public static void setAnnuaire(HashMap<Long, PublicKey> annuaire) {
        Main.annuaire = annuaire;
    }

    public static int getTimeOut() {
        return timeOut;
    }

    public static void setTimeOut(int timeOut) {
        Main.timeOut = timeOut;
    }
    
    private static void Init() {
        
    }
    
    private static int CalculTimeOut() {
        // TO-DO
        return 0;
    }
    
}
