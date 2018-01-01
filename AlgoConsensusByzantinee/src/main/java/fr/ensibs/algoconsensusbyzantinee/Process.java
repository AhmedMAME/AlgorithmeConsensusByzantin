package fr.ensibs.algoconsensusbyzantinee;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 *
 * @author Mehdi
 */
public class Process extends Thread {
    protected PrivateKey privateKey;
    protected PublicKey publicKey;
    protected Chiffrement chiffreur;
    protected SharedMemory memory;
    
    private Boolean isFaulty;
    private Long id;

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
    public Process(Boolean isFaulty, SharedMemory memory) {
        this.isFaulty = isFaulty;
        this.memory = memory;
        this.chiffreur = new Chiffrement();
        memory.addMessages(this.getId(), new ArrayList<Message>());
        
        System.out.println("Thread "+this.getId()+": Construction du thread "+getId());
        KeyPair kp = generateKey();
        //initialisation private key
        this.privateKey = kp.getPrivate();
        //initialisation public key
        this.publicKey = kp.getPublic();
        publishKey();
        System.out.println("Thread "+this.getId()+": Fin generation/publication des clés");
    }
    
    /**
     * Méthode pour générer une paire de clé (privée, publique)
     * @return 
     */
    
    public KeyPair generateKey() {
        try {
            // Création d'un générateur de paire de clé avec l'algo DSA
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
            // Initialisation du générateur de clé avec la longueur 2048 
            kpg.initialize(2048);
            // Génération de la paire des clés 
            KeyPair kp = kpg.generateKeyPair();
            
            // Initialisation des deux attributs du processus avec les deux clés
            return kp;
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Erreur d'algorithme de chiffrement");
        }
        return null;
    }
    
    /**
     * Publier la clé publique dans l'annuaire
     * Spécifier l'id du thread comme clé
     */
    public void publishKey() {
        // Ajout de l'entrée (clé publique)
    	if (this instanceof Commandant){
    		memory.setCommandantSignature(publicKey);
    		memory.setCommandantId(this.getId());
    	}
    	else
    		memory.addSignature(this.getId(), this.publicKey);
    }

    public Boolean getIsFaulty() {
        return isFaulty;
    }
    
}
