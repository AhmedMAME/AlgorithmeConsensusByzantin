package fr.ensibs.algoconsensusbyzantinee;

import java.security.PrivateKey;
import java.security.PublicKey;


/**
 *
 * @author Mehdi
 */
public class Process extends Thread {
    protected PrivateKey privateKey;
    protected PublicKey publicKey;
    
    private Boolean isFaulty;

    /**
     * Création d'un processus (Commandant ou Lieutenant)
     * 
     * 3 étapes:
     * On spécifie si le processus est un traitre ou non 
     * On génère la paire des clés (privée,publique) ==> generateKey()
     * On publie la clé publique ==> publishkey()
     * 
     * @param isFaulty
     */
    public Process(Boolean isFaulty) {
        
    }
    
    /**
     * Méthode pour générer une paire de clé (privée, publique)
     */
    public void generateKey() {
    	
    	// Création d'un générateur de paire de clé avec l'algo DSA ==> KeyPairGenerator
    	
    	// Initialisation du générateur de clé avec la longueur 2048 (plus sure)
    	
    	// Génération de la paire des clés ==> generateKeyPair
    	
        
        // Initialisation des deux attributs du processus avec les deux clés
    	
    }
    
    /**
     * Publier la clé publique dans l'annuaire Main.annuaire
     * Spécifier l'id du thread comme clé
     */
    public void publishKey() {
    	// Récupération de l'annuaire
    	
    	// Ajout de l'entrée (clé publique)
    	
    	// Mise à jour de l'annuaire
    	
    }

    public Boolean getIsFaulty() {
        return isFaulty;
    }
    
    
}
