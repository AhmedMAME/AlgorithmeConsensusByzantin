package fr.ensibs.algoconsensusbyzantinee;

public class Message {
	
	private byte[] initial;
	private byte[] content;
	private byte[] signed;
	
	private long source;
	private long destination;
	
	public Message(byte[] initial, byte[] content, byte[] signed, long source, long destnation) {
		
		this.initial = initial;
		this.content = content;
		this.signed = signed;
		
		this.source = source;
		this.destination = destnation;
	}
	
	public Message update(Message msg, byte[] msgSigned){
		
		this.content = this.signed;
		this.signed = msgSigned;
		
		return this;
	}

	public byte[] getInitial() {
		return initial;
	}

	public byte[] getContent() {
		return content;
	}

	public byte[] getSigned() {
		return signed;
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
}
