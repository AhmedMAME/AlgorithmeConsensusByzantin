package fr.ensibs.algoconsensusbyzantinee;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Message {
	
	private byte[] initial;
	private List<PublicKey> signataire;
	private List<byte[]> signature;
	
	
	private long source;
	private long destination;
	
	public Message(byte[] initial, long source, long destnation) {
		
		this.initial = initial;
		this.signataire = new ArrayList<>();
		this.signature = new ArrayList<>();
		
		this.source = source;
		this.destination = destnation;
	}
	
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
