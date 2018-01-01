package fr.ensibs.algoconsensusbyzantinee.main;

import java.util.Random;

import fr.ensibs.algoconsensusbyzantinee.Commandant;
import fr.ensibs.algoconsensusbyzantinee.Lieutenant;
import fr.ensibs.algoconsensusbyzantinee.SharedMemory;

/**
 *
 * @author Mehdi
 */

public class Main {
    private static int nbrProces;
    private static int nbrTraitre;
    
    private static Lieutenant[] lieutenants;
    private static SharedMemory memory;
    
    /**
     * @param args nombre de processus et nombre maximum de traitres
     */
    public static void main(String[] args) {
        
    	if(args.length != 2)
    		usage();
    	
    	int max = 0;
    	
    	try {
			nbrProces = Integer.parseInt(args[0]);
			max = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			usage();
		}
    	
    	Init(max);
    }

    private static void usage() {
		System.out.println("Usage : \n\t\t AlgoConsensusByzantin nbrProcess nbrMaxTrait");
		System.exit(0);
		
	}
    
    public static Lieutenant[] getLieutenants() {
		return lieutenants;
	}
    
    private static void Init(int nbrMax) {
    	
    	memory = new SharedMemory(nbrMax, nbrProces);
    	nbrTraitre=(new Random().nextInt(nbrMax + 1));
    	
    	Commandant commandant = new Commandant(false, memory);
    	lieutenants = new Lieutenant[nbrProces-1];
    	
    	boolean isFaulty = true;
    	
    	for (int i = 0, j=0; i < lieutenants.length; i++, j++) {
    		
    		if(j>=nbrTraitre)
    			isFaulty = false;
    		
    		lieutenants[i] = new Lieutenant(isFaulty, memory);
		}
    	
    	commandant.start();
    	
    	for (int i = 0; i < lieutenants.length; i++) {
			lieutenants[i].start();
    		;
		}
    }
}
