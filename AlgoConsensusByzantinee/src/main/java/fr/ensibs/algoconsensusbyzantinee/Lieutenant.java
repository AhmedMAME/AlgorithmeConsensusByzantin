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
	
	private Set<String> V;
	
    public Lieutenant(Boolean isFaulty, SharedMemory memory) {
        super(isFaulty, memory);
		V = new HashSet<>();
    }
    
    public void receiveMessage() {
    	
    	Message msg = memory.take(this.getId());
    	
        if(memory.isCommandant(msg.getSource())){
        	if(chiffreur.verifierSignature(msg)){
	        	V.add(new String(msg.getInitial()));
	        	byte[] msgSigned = msg.getSignature().get(msg.getNumberOfSignataires()-1);
	        	sendMessage(msg.update(publicKey, chiffreur.signer(privateKey, msgSigned)));
        	}
        }
        else if(memory.getAnnuaire().containsKey(msg.getSource())){
        	if(chiffreur.verifierSignature(msg)){
        		V.add(new String(msg.getInitial()));
        		byte[] msgSigned = msg.getSignature().get(msg.getNumberOfSignataires()-1);
        		if(msg.getNumberOfSignataires() < memory.getNbrMaxTraitre())
        			sendMessage(msg.update(publicKey, chiffreur.signer(privateKey, msgSigned)));
			}
        }
        else{
        	System.err.println("Thread "+this.getId()+": Reception d'un message non signé");
        	System.exit(0);
        }
    }
    
    public void sendMessage(Message msg) {
    	for(Lieutenant l : Main.getLieutenants()){
        	if(!msg.getSignataire().contains(memory.getAnnuaire().get(l.getId()))){
        		msg.setSource(getId());
        		msg.setDestination(l.getId());
        		if(this.isFaulty()){
        			int rand = new Random().nextInt(Decisions.values().length);
        			String fake = "NONE";
        			
        			for (int i = 0; i < Decisions.values().length; i++) {
						if(rand == i){
							fake = Decisions.values()[i].toString();
						}
					}
        			
        			msg.setInitial(fake.getBytes());
        		}
        		memory.put(msg);
        	}
        }
    }
    
    public String choiceOrder() {
    	if(V.size() != 1)
    		return (String) V.toArray()[V.size()/2];
    	
    	return (String)V.toArray()[0];
    }
    
    @Override
    public void run(){
    	
    	long debut = System.currentTimeMillis();
    	
        while(memory.isNotTime(debut)){
        	receiveMessage();
        }
        System.out.println("Thread "+getId()+" a voté : "+choiceOrder()+" "+V);
    }
}
