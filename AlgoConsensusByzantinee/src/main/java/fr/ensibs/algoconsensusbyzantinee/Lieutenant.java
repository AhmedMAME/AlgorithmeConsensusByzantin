package fr.ensibs.algoconsensusbyzantinee;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import fr.ensibs.algoconsensusbyzantinee.main.Main;

/**
 *
 * @author Mehdi
 */
public class Lieutenant extends Process{
	
	// Ensemble d'ordre reçus par ce lieutenant
	private Set<String> V;
	
    public Lieutenant(Boolean isFaulty, SharedMemory memory) {
        super(isFaulty, memory);
		V = new HashSet<>();
		memory.addCompteurs(this.getId());
    }
    
    /**
     * Reception d'un message
     * 
     * @param time temps de début d'exécution
     */
    public void receiveMessage(long time) {
    	
    	// Récupération du message depuis sa boîte aux lettres
    	Message msg = memory.take(this.getId(), time);
    	
    	if(msg != null){
    		// Si le message est de la part du Commandant
        	// de la forme v:0
            if(memory.isCommandant(msg.getSource())){
            	// Vérifier la signature
            	if(chiffreur.verifierSignature(msg)){
            		// Si le message est bien signé
            		// Ajouter l'ordre dans V 
            		// V n'accepte pas les doublons
            		// Il ne sera pas ajouté s'il existe déjà
    	        	V.add(new String(msg.getInitial()));
    	        	// Recupérer la dernière signature
    	        	byte[] msgSigned = msg.getSignature().get(msg.getNumberOfSignataires()-1);
    	        	// Signer le message à son tour
    	        	// Et s'enregistrer en tant que signataire du message
    	        	// Puis envoyer le message
    	        	sendMessage(msg.update(publicKey, chiffreur.signer(privateKey, msgSigned)));
            	}
            }
            // Si le message est de la forme v:0:ji:...:jk
            else if(memory.getAnnuaire().containsKey(msg.getSource())){
            	// Vérifier les signatures
            	if(chiffreur.verifierSignature(msg)){
            		// Ajouter l'ordre dans V s'il n'existe pas
            		V.add(new String(msg.getInitial()));
            		// Recupérer la dernière signature
            		byte[] msgSigned = msg.getSignature().get(msg.getNumberOfSignataires()-1);
            		// Si k < nombreMax de traitres
            		if(msg.getNumberOfSignataires() < memory.getNbrMaxTraitre())
            			// Signer et envoyer le message
            			sendMessage(msg.update(publicKey, chiffreur.signer(privateKey, msgSigned)));
    			}
            }
            else{
            	System.err.println("Thread "+this.getId()+": Reception d'un message inconnu");
            	System.exit(0);
            }
    	}
    }
    
    /**
     * Envoie de message aux Lieutenants qui ne l'ont pas
     * encore reçu
     * 
     * @param msg Message à envoyer
     */
    public void sendMessage(Message msg) {
    	// Pour tous les lieutenants
    	for(Lieutenant l : Main.getLieutenants()){
    		// Qui ne sont pas signataire de ce message (l'ont pas encore reçu)
        	if(!msg.getSignataire().contains(l.getPublicKey())){
        		// Paramètrer le message (source, dest)
        		msg.setSource(getId());
        		msg.setDestination(l.getId());
        		// Si le processus est traitre
        		// Il change l'ordre aléatoirement
        		if(this.isFaulty()){
        			int rand = new Random().nextInt(Decisions.values().length);
        			String fake = "NONE";
        			for (int i = 0; i < Decisions.values().length; i++) {
						if(rand == i){
							fake = Decisions.values()[i].toString();
						}
					}
        			// Changement d'ordre
        			msg.setInitial(fake.getBytes());
        		}
        		// Mettre le message dans la bôite aux lettres
        		memory.put(msg);
        	}
        }
    }
    
    /**
     * Fonction de choix d'ordre
     * La médiane de l'ensemble V sera choisie
     * 
     * @return choix d'ordre
     */
    public String choiceOrder() {
    	// Si V n'est pas un Singleton
    	if(V.size() != 1)
    		// Renvoyer la médiane de V
    		return (String) V.toArray()[V.size()/2];
    	
    	// Sinon, le premier et unique élément
    	return (String)V.toArray()[0];
    }
    
    @Override
    public void run(){
    	// On enregistre le temps de démarrage
    	long debut = System.currentTimeMillis();
    	
    	// Tant que le timeout n'est pas atteint,
    	// Le lieutenant continue à attendre des messages
        while(memory.calculTimeOut(debut) < ((memory.getNbrLieutenants()*memory.COEFF)*1000)){
        	receiveMessage(debut);
        }
        // A la fin, le lieutenant décide d'un ordre de son ensemble V
        System.out.println("Thread "+getId()+" a décidé : "+choiceOrder());
    }
}
