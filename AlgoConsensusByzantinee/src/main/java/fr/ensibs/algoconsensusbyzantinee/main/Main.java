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
        
    	// Contrôle de paramètres 
    	if(args.length != 2)
    		usage();
    	
    	int max = 0;
    	
    	try {
			nbrProces = Integer.parseInt(args[0]);
			max = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			usage();
		}
    	
    	// Initialisation de l'application 
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
    	
    	// Initialisation de l'espace partagé par les threads
    	memory = new SharedMemory(nbrMax, nbrProces);
    	// Determination du nombre de traitres
    	nbrTraitre=(new Random().nextInt(nbrMax + 1));
    	
    	// Création du commandant (false pour dire qu'il n'est pas traitre)
    	Commandant commandant = new Commandant(false, memory);
    	
    	// Création des Lieutenants (parmi lesquels il y a des traitres)
    	lieutenants = new Lieutenant[nbrProces-1];
    	boolean isFaulty = true;
    	
    	for (int i = 0, j=0; i < lieutenants.length; i++, j++) {
    		if(j>=nbrTraitre)
    			isFaulty = false;
    		lieutenants[i] = new Lieutenant(isFaulty, memory);
		}
    	
    	// Démarrage du commandant
    	commandant.start();
    	
    	//Attente de 3 secondes (pour que le commandant diffuse l'ordre)
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.err.println("Erreur d'attente du commandant");
		}
    	
    	// Démarrage des Lieutenants
    	for (int i = 0; i < lieutenants.length; i++) {
			lieutenants[i].start();
    		;
		}
    }
}
