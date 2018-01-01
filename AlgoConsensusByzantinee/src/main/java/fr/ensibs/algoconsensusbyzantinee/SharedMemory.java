package fr.ensibs.algoconsensusbyzantinee;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe SharedMemory servira comme support pour
 * L'enregistrement des données partagées (à accés concurent)
 * entre les threads
 *
 * @author Mehdi
 */
public class SharedMemory {
    
	private static final double COEFF = 0.6;
	private HashMap<Long, PublicKey> annuaire;
	private HashMap<Long, List<Message>> messages;
	private PublicKey commandantSignature;
	private Long commandantId;
    private final int nbrMaxTraitre;
    private int nbrLieutenants;
	
	public SharedMemory(int nbrMax, int nbrPrc) {
		
		annuaire = new HashMap<>();
		nbrMaxTraitre = nbrMax;
		nbrLieutenants = nbrPrc-1;
		messages = new HashMap<>();
	}
	
	public synchronized Message take(Long id){
		while(messages.get(id).size() <= 0)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		
		Message msg = messages.get(id).remove(0);
		notifyAll();
		
		return msg;
	}
	
	public synchronized void put(Message msg){
		messages.get(msg.getDestination()).add(msg);
		notifyAll();
	}
	
	public synchronized void addSignature(Long id, PublicKey pubKey){	
		annuaire.put(id, pubKey);
	}
	
	public void setCommandantSignature(PublicKey commandantSignature) {
		this.commandantSignature = commandantSignature;
	}
	
	public synchronized PublicKey getCommandantSignature() {
		return commandantSignature;
	}
	
	public void setCommandantId(Long commandantId) {
		this.commandantId = commandantId;
	}
	
	public synchronized boolean isCommandant(Long id){
		return (id == this.commandantId);
	}
	
	public HashMap<Long, PublicKey> getAnnuaire() {
		return annuaire;
	}
	
	public int getNbrMaxTraitre() {
		return nbrMaxTraitre;
	}

	public synchronized void addMessages(long id, ArrayList<Message> liste) {
		messages.put(id, liste);
	}
	
	public synchronized boolean isNotTime(long debut){
		return ((System.currentTimeMillis()-debut) < ((nbrLieutenants*COEFF)*1000));
	}
}
