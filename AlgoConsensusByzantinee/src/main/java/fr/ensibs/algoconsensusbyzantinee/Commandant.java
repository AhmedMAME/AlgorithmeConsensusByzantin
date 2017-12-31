package fr.ensibs.algoconsensusbyzantinee;

import fr.ensibs.algoconsensusbyzantinee.main.Main;

/**
 *
 * @author Mehdi
 */
public class Commandant extends Process{
    public byte[] initialMsg; 

    /*
    
    */
    public Commandant(Boolean isFaulty) {
        super(isFaulty);
    }
    

   

    /**
     * Envoyer le message aux lieutenants
     * 
     * @param msg
     */
    public void sendMessage(byte[] msg) {
    	// Recupérer une instance du chiffreur pour signer
    	Chiffrement chiffrement = new Chiffrement();
        // Signer le message et récupérer les données signées
        this.initialMsg = chiffrement.signer(privateKey, msg);
        // récupèrer la liste des lieutenants
        Lieutenant[] lieutenants = Main.getLieutenants();
        // Diffuser le message signé aux lieutenants
        for (Lieutenant lieutenant : lieutenants) {
            lieutenant.sendMessage(_socket, initialMsg); //sendMessage de la classe Lieutenant doit avoir un seul paramètre qui est le byte[] message
            //lieutenant.sendMessage(initialMsg);
        }
    }
    
    @Override
    public void run() {
    	// Creer le message et le convertir en byte[]
        String test = "1";
    	byte[] msg = test.getBytes();
    	// Envoyer le message aux lieutenants
        sendMessage(msg);
    }
}
