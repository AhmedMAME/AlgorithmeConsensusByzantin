package fr.ensibs.algoconsensusbyzantinee.main;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import fr.ensibs.algoconsensusbyzantinee.Chiffrement;
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
    
    private static int timeOut;
    
    /**
     * @param args the command line arguments
     * @throws NoSuchAlgorithmException 
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        
    	System.out.println("Debut");
    	
    	System.out.println("Verification arguments");
    	if(args.length != 2)
    		usage();
    	
    	System.out.println("Arguments bons");
    	int max = 0;
    	
    	System.out.println("Parsing arguments");
    	try {
			nbrProces = Integer.parseInt(args[0]);
			max = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			usage();
		}
    	
    	System.out.println("Initialisation Appel");
    	Init(max);
    	
    	/*byte[] test = "1".getBytes();
    	Chiffrement chif = new Chiffrement();
    	KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(2048);
        KeyPair kp1 = kpg.generateKeyPair();
        KeyPair kp2 = kpg.generateKeyPair();
        
        byte[] signe1 = chif.signer(kp1.getPrivate(), test);
        byte[] signe2 = chif.signer(kp2.getPrivate(), signe1);
        boolean isSigned1 = chif.verifierSignature(kp1.getPublic(), test, signe1);
        boolean isSigned2 = chif.verifierSignature(kp2.getPublic(), signe1, signe2);
        
        System.out.println((new String(test))+" "+(new String(signe1))+" "+(new String(signe2))+" "+isSigned1+" "+isSigned2);
    	*/
    }

    private static void usage() {
		System.out.println("Usage : \n\t\t AlgoConsensusByzantin nbrProcess nbrMaxTrait");
		System.exit(0);
		
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
    
    private static void Init(int nbrMax) {
    	
    	System.out.println("Debut initialisation");
    	
    	System.out.println("Configurations ...");
    	memory = new SharedMemory(nbrMax);
    	setTimeOut(CalculTimeOut());
    	nbrTraitre=(new Random().nextInt(nbrMax + 1));
    	
    	System.out.println("Creation processus commandant");
    	Commandant commandant = new Commandant(false, memory);
    	lieutenants = new Lieutenant[nbrProces-1];
    	
    	boolean isFaulty = true;
    	
    	System.out.println("Creation des lieutenants");
    	for (int i = 0, j=0; i < lieutenants.length; i++, j++) {
    		
    		if(j>=nbrTraitre)
    			isFaulty = false;
    		
    		lieutenants[i] = new Lieutenant(isFaulty, memory);
		}
    	
    	System.out.println("Demarrage du commandant");
    	commandant.start();
    	
    	System.out.println("Demarrage des lieutenants");
    	for (int i = 0; i < lieutenants.length; i++) {
			lieutenants[i].start();
    		;
		}
        
    }
    
    private static int CalculTimeOut() {
    	return (new Random().nextInt(10000 - 5000 + 1) + 5000);
    }
    
}
