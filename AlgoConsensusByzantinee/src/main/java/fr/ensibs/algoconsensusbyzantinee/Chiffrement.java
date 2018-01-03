package fr.ensibs.algoconsensusbyzantinee;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Classe Chiffrement traite de tout ce qui est signature du message et
 * virification des signatures des autres threads
 * 
 * @author Mehdi
 */
public class Chiffrement {

	/**
	 * Méthode signer() recevra une clé privée et un message sous forme de
	 * tableau de Byte[] pour signer ce message par cette clé
	 * 
	 * @param privateKey
	 *            la clé privée utilisée pour signer
	 * @param msg
	 *            le message à signer
	 */
	public byte[] signer(PrivateKey privateKey, byte[] msg) {

		byte[] messageSigne = null;

		try {

			// Génèrer une signature en utilisant SHA256withDSA
			Signature signature = Signature.getInstance("SHA256withDSA");

			// Initialiser la signature avec la clé reçue
			signature.initSign(privateKey);

			// Mettre à jour le message
			signature.update(msg);

			// Signer le message
			messageSigne = signature.sign();

		} catch (NoSuchAlgorithmException e) {
			System.err.println("Erreur d'algorithme (inexistant)");
		} catch (InvalidKeyException e) {
			System.err.println("Erreur de clé invalide");
		} catch (SignatureException e) {
			System.err.println("Erreur de signature");
		}

		// retourner le message signé
		return messageSigne;

	}

	/**
	 * Méthode verifierSignature() recevra un message pour
	 * vérifier si ce message a été signé par l'ensemble de
	 * ses signataire ou s'il y a eu lieu d'altération de
	 * données
	 * 
	 * @param msg
	 *            message à vérifier
	 */
	public boolean verifierSignature(Message msg) {

		boolean isSigned = true;
		
		try {
			// Générer une signature en utilisant SHA256withDSA
			Signature signature = Signature.getInstance("SHA256withDSA");
			
			// Vérifier si sinature(i) est vraiment le résultat de la 
			// signature de signature(i-1) par le signataire(i)
			for(int i=msg.getNumberOfSignataires()-1; i>0; i--){
				signature.initVerify(msg.getSignataire().get(i));
				signature.update(msg.getSignature().get(i-1));
				isSigned = isSigned && signature.verify(msg.getSignature().get(i));
			}
			
			// Vérifier si la première signature est le résultat de la 
			// signature du message clair (initial) par le premier signataire
 			signature.initVerify(msg.getSignataire().get(0));
			signature.update(msg.getInitial());
			isSigned = isSigned && signature.verify(msg.getSignature().get(0));
			
			// résultat de la vérification
			return isSigned;

		} catch (NoSuchAlgorithmException e) {
			System.err.println("Erreur d'algorithme (inexistant)");
		} catch (InvalidKeyException e) {
			System.err.println("Erreur de clé invalide");
		} catch (SignatureException e) {
			System.err.println("Erreur de verification "+e.getMessage());
		}

		// si une erreur, retourner faux
		return false;
	}
}