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
	 * Méthode verifierSignature() recevra un message et une clé publique pour
	 * vérifier si ce message a été signé par la clé privée correspondante à la
	 * clé publique reçue
	 * 
	 * @param publicKey
	 *            clé publique du signataire
	 * @param msg
	 *            message signé
	 */
	public boolean verifierSignature(Message msg) {

		boolean isSigned = true;

		try {

			// Générer une signature en utilisant SHA256withDSA
			Signature signature = Signature.getInstance("SHA256withDSA");
			
			for(int i=msg.getNumberOfSignataires()-1; i>0; i--){
				signature.initVerify(msg.getSignataire().get(i));
				signature.update(msg.getSignature().get(i-1));
				isSigned = isSigned && signature.verify(msg.getSignature().get(i));
			}
			
			signature.initVerify(msg.getSignataire().get(0));
			signature.update(msg.getInitial());
			isSigned = isSigned && signature.verify(msg.getSignature().get(0));
			
			return isSigned;

		} catch (NoSuchAlgorithmException e) {
			System.err.println("Erreur d'algorithme (inexistant)");
		} catch (InvalidKeyException e) {
			System.err.println("Erreur de clé invalide");
		} catch (SignatureException e) {
			System.err.println("Erreur de verification "+e.getMessage());
		}

		// retourner une réponse
		return false;
	}
}
