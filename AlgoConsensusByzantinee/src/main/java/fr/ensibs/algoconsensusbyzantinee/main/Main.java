/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensibs.algoconsensusbyzantinee.main;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Random;

import fr.ensibs.algoconsensusbyzantinee.Commandant;
import fr.ensibs.algoconsensusbyzantinee.Lieutenant;

/**
 *
 * @author Mehdi
 */

public class Main {
    private static int nbrProces;
    private static int nbrMaxTraitre;
    private static int nbrTraitre;
    
    private static HashMap<Long, PublicKey> annuaire = new HashMap<>(); //Collections.synchronizedMap(new HashMap<>())
    private static Lieutenant[] lieutenants;
    
    private static int timeOut;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    	if(args.length != 2)
    		usage();
    	
    	try {
			nbrProces = Integer.parseInt(args[0]);
			nbrMaxTraitre = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			usage();
		}
    	
    	Init();
    }

    private static void usage() {
		System.out.println("Usage : \n\t\t AlgoConsensusByzantin nbrProcess nbrMaxTrait");
		System.exit(0);
		
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
    
    public static Lieutenant[] getLieutenants() {
		return lieutenants;
	}
    
    private static void Init() {
    	
    	setTimeOut(CalculTimeOut());
    	nbrTraitre=(new Random().nextInt(nbrMaxTraitre + 1));
    	
    	Commandant commandant = new Commandant(false);
    	lieutenants = new Lieutenant[nbrProces-1];
    	
    	boolean isFaulty = true;
    	
    	for (int i = 0, j=0; i < lieutenants.length; i++, j++) {
    		
    		if(j>=nbrTraitre)
    			isFaulty = false;
    		
    		lieutenants[i] = new Lieutenant(isFaulty);
		}
    	
    	commandant.start();
    	Thread.currentThread().sleep(3000);
    	
    	for (int i = 0; i < lieutenants.length; i++) {
			lieutenants[i].start();
		}
        
    }
    
    private static int CalculTimeOut() {
        
    	return (new Random().nextInt(10000 - 5000 + 1) + 5000);
    }
    
}
