package fr.ensibs.algoconsensusbyzantinee;

import java.util.ArrayList;
import java.util.List;

import fr.ensibs.algoconsensusbyzantinee.main.Main;

/**
 *
 * @author Mehdi
 */
public class Lieutenant extends Process{
	
	private List<Long> listes = new ArrayList<>();
	
    public Lieutenant(Boolean isFaulty, SharedMemory memory) {
        super(isFaulty, memory);
    }
    
    public void receiveMessage() {
    	
    	Message msg = memory.take(this.getId());
    	
        if(memory.isCommandant(msg.getSource())){
        	if(chiffreur.verifierSignature(memory.getCommandantSignature(), msg.getContent(), msg.getSigned())){
	        	memory.addValue(new String(msg.getInitial()));
	        	sendMessage(msg.update(msg, chiffreur.signer(privateKey, msg.getSigned())));
        	}
        }
        else if(memory.getAnnuaire().containsKey(msg.getSource())){
        	listes.add(msg.getSource());
        	
        	if(chiffreur.verifierSignature(memory.getAnnuaire().get(msg.getSource()), msg.getContent(), msg.getSigned())){
        		memory.addValue(new String(msg.getInitial()));
        		sendMessage(msg.update(msg, chiffreur.signer(privateKey, msg.getSigned())));
			}
        }
        else{
        	System.err.println("Thread "+this.getId()+": Reception d'un message non signé");
        	System.exit(0);
        }
    }
    
    public void sendMessage(Message msg) {
    	for(Lieutenant l : Main.getLieutenants()){
        	if(!listes.contains(l.getId()) && l.getId() != getId()){
        		msg.setSource(getId());
        		msg.setDestination(l.getId());
        		if(this.getIsFaulty())
        			msg.setInitial("0".getBytes());
        		memory.put(msg);
        	}
        }
    }
    
    public String choiceOrder() {
        
    	System.err.println("Thread "+getId()+" :"+memory.getV());
    	
    	return memory.getValue();
    }
    
    @Override
    public void run(){
        while(listes.size() != Main.getLieutenants().length-1){
        	receiveMessage();
        }
        System.out.println("Thread "+getId()+" :"+choiceOrder()+" sources ="+listes.size());
    }
}
