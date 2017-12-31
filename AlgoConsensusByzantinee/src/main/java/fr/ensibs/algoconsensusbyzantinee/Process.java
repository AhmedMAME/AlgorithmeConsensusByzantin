package fr.ensibs.algoconsensusbyzantinee;

import fr.ensibs.algoconsensusbyzantinee.main.Main;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


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
        this.isFaulty = isFaulty;
        if(!getIsFaulty()) {
            KeyPair kp = generateKey();
            //initialisation private key
            this.privateKey = kp.getPrivate();
            //initialisation public key
            this.publicKey = kp.getPublic();
            publishKey();
        }
        
    }
    

    
    /**
     * Méthode pour générer une paire de clé (privée, publique)
     * @return 
     */
    
    public KeyPair generateKey() {
        try {
            // Création d'un générateur de paire de clé avec l'algo RSA
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            // Initialisation du générateur de clé avec la longueur 2048 
            kpg.initialize(2048);
            // Génération de la paire des clés 
            KeyPair kp = kpg.generateKeyPair();
            
            
            //byte[] prk = privateKey.getEncoded();
            //
            //byte[] pbk = publicKey.getEncoded();
            
            // Initialisation des deux attributs du processus avec les deux clés
            return kp;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
    /**
     * Publier la clé publique dans l'annuaire Main.annuaire
     * Spécifier l'id du thread comme clé
     */
    public void publishKey() {
        // Récupération de l'annuaire
        HashMap<Long, PublicKey> annuaire = Main.getAnnuaire();
        // Ajout de l'entrée (clé publique)
        annuaire.put(this.getId(), this.publicKey); //Long ?
        // Mise à jour de l'annuaire
        Main.setAnnuaire(annuaire);
    	
    }

    public Boolean getIsFaulty() {
        return isFaulty;
    }
    
    
}
