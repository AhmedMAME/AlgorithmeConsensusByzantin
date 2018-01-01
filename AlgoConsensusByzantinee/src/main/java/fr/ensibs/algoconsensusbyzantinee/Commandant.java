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
        System.out.println("Thread "+this.getId()+": Commandant: Envoi à tous les lieutenants");
        for (long lieutenant : memory.getAnnuaire().keySet()) {
            memory.put(new Message(initial, initial, signedMsg, getId(), lieutenant));
        }
    }
    
    @Override
    public void run() {
    	// Creer le message et le convertir en byte[]
        String test = "1";
    	byte[] msg = test.getBytes();
    	// Envoyer le message aux lieutenants
        sendMessage(msg);
        System.out.println("Thread "+this.getId()+": Fin run du commandant");
    }
}
