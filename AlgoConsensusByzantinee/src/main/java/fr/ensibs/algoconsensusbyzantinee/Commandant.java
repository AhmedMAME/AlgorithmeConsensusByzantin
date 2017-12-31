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
    
    /**
     * Envoyer le message aux lieutenants
     * 
     * @param msg
     */
    public void sendMessage(byte[] msg) {
    	// Recupérer une instance du chiffreur pour signer
    	
    	// Signer le message et récupérer les données signées
    	
    	// récupèrer la liste des lieutenants
    	
    	// Diffuser le message signé aux lieutenants
    }
    
    @Override
    public void run() {
    	// Creer le message et le convertir en byte[]
    	
    	// Envoyer le message aux lieutenants
    }
}
