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
    
	// Coefficient utilisé pour calculé le timeout de l'attente
	// d'un processus sans recevoir de message 
	public final double COEFF = 0.6;
	
	// Annuaire regroupant toutes les clés publiques publiées
	private HashMap<Long, PublicKey> annuaire;
	// Liste des boîtes aux lettres des processus
	private HashMap<Long, List<Message>> messages;
	// Nombre de message reçus par Lieutenants
	private HashMap<Long, Integer> compteurs;
	// Clé publique du commandant
	private PublicKey commandantSignature;
	// L'id du commandant
	private Long commandantId;
	// Nombre maximum de traitres autorisés
    private final int nbrMaxTraitre;
    // Nombre de lieutenants
    private int nbrLieutenants;
	
	public SharedMemory(int nbrMax, int nbrPrc) {
		annuaire = new HashMap<>();
		nbrMaxTraitre = nbrMax;
		nbrLieutenants = nbrPrc-1;
		messages = new HashMap<>();
		compteurs = new HashMap<>();
	}
	
	/**
	 * Récupération d'un message de la boîte
	 * aux lettres d'un processus
	 * 
	 * @param id L'identifiant du processus
	 * @param time temps d'exécution
	 * @return le premier message dans la boîte aux lettres
	 */
	public synchronized Message take(Long id, long time){
		// Tant que sa boîte aux lettres est vide, il attend
		while(messages.get(id).size() <= 0 ){
			// S'il a reçu le nombre max de massages
			// c.a.d n-1 lieutenant + 1 commandant
			// il s'arrête
			if(compteurs.get(id) >= nbrLieutenants){
				return null;
			}
			// sinon 
			try {
				// Attendre au maximum le timeout
				wait(calculTimeOut(time));
			} catch (InterruptedException e) {
			}
		}
		
		// Récupère le premier message
		Message msg = messages.get(id).remove(0);
		compteurs.put(id, compteurs.get(id)+1);
		
		return msg;
	}
	
	/**
	 * Livraison d'un message à un processus
	 * 
	 * @param msg message à livrer
	 */
	public synchronized void put(Message msg){
		// dépose le message dans la boîte
		// aux lettres du destinataire
		messages.get(msg.getDestination()).add(msg);
		// alerte les autres processus qu'un 
		// message est disponible
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
	
	public synchronized void addCompteurs(long id) {
		compteurs.put(id, 0);
	}
	
	/**
	 * Methode pour calculer le temps d'attente
	 * sans recevoir de message
	 * 
	 * @param debut temps de démarrage du thread
	 * @return le temps d'exécution
	 */
	public synchronized long calculTimeOut(long debut){
		return (System.currentTimeMillis()-debut);
	}
	
	public int getNbrLieutenants() {
		return nbrLieutenants;
	}
}