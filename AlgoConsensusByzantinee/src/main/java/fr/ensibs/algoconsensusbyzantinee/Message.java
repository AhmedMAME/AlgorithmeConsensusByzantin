package fr.ensibs.algoconsensusbyzantinee;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la structure d'un message
 * Ce message sera l'unique élément échangé entre
 * les différents threads
 * 
 * @author Ahmed
 */
public class Message {
	
	// Contenu initial du message, texte clair
	private byte[] initial;
	// Liste des PublicKey ayant été utilisées
	// pour signer les messages
	private List<PublicKey> signataire;
	// Liste des textes représentant les signatures successives
	// Le commandant est le premier à signer ainsi:
	// signataire(0) = clé publique du commandant
	// signature(0) = commandant.signer(initial)
	// signature(i) = lieut(i).signer(signature(i-1))
	private List<byte[]> signature;
	
	// Le processus source du messsage
	private long source;
	// Le processus destinataire du message
	private long destination;
	
	public Message(byte[] initial, long source, long destnation) {
		this.initial = initial;
		this.signataire = new ArrayList<>();
		this.signature = new ArrayList<>();
		
		this.source = source;
		this.destination = destnation;
	}
	
	/**
	 * L'ajout d'un nouveau signataire
	 * @param pk clé publique du signataire
	 * @param msgSigned le produit de la signature du signataire
	 * @return le message mis à jour 
	 */
	public Message update(PublicKey pk, byte[] msgSigned){
		
		this.signataire.add(pk);
		this.signature.add(msgSigned);
		
		return this;
	}

	public byte[] getInitial() {
		return initial;
	}

	public long getSource() {
		return source;
	}

	public long getDestination() {
		return destination;
	}
	
	public void setDestination(long destination) {
		this.destination = destination;
	}
	
	public void setSource(long source) {
		this.source = source;
	}
	
	public void setInitial(byte[] initial) {
		this.initial = initial;
	}
	
	public List<PublicKey> getSignataire() {
		return signataire;
	}
	
	public List<byte[]> getSignature() {
		return signature;
	}
	
	public int getNumberOfSignataires(){
		return signataire.size();
	}
}