package fr.ensibs.algoconsensusbyzantinee;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
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

    public Process(Boolean isFaulty) {
        this.isFaulty = isFaulty;
    }
    
    /*
    
    */
    public KeyPair generateKey() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();
            //privateKey = kp.getPrivate();
            //publicKey = kp.getPublic();
            //byte[] prk = privateKey.getEncoded();
            //byte[] pbk = publicKey.getEncoded();
            return kp;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /*
    
    */
    public void publishKey(Long id, PublicKey publicKey) {
        publicKey = generateKey().getPublic();
    }
}
