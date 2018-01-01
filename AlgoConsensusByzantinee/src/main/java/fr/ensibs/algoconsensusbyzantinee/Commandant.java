package fr.ensibs.algoconsensusbyzantinee;

/**
 *
 * @author Mehdi
 */
public class Commandant extends Process{

    /*
    
    */
    public Commandant(Boolean isFaulty, SharedMemory memory) {
        super(isFaulty, memory);
    }

    /**
     * Envoyer le message aux lieutenants
     * 
     * @param msg
     */
    public void sendMessage(byte[] initial) {
    	
    	// Signer le message et récupérer les données signées
        byte[] signedMsg = chiffreur.signer(privateKey, initial);
        // Diffuser le message signé aux lieutenants
        for (long lieutenant : memory.getAnnuaire().keySet()) {
        	Message msg = new Message(initial, getId(), lieutenant);
        	msg.update(publicKey, signedMsg);
            memory.put(msg);
        }
    }
    
    @Override
    public void run() {
    	// Creer le message et le convertir en byte[]
        String message = Decisions.UN.toString();
    	byte[] msg = message.getBytes();
    	// Envoyer le message aux lieutenants
        sendMessage(msg);
    }
}
