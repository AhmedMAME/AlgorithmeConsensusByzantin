package fr.ensibs.algoconsensusbyzantinee;

/**
 *
 * @author Mehdi
 */
public class Commandant extends Process{
	
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
        	// Créer le message à destination du lieutenant
        	Message msg = new Message(initial, getId(), lieutenant);
        	// Le commandant est le premier signataire du message
        	msg.update(publicKey, signedMsg);
        	// Mettre le message dans la boîte aux lettres correspondante
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
        // Fin du commandant
        System.out.println("Le commandant a diffusé l'ordre. Fin du commandant.");
    }
}