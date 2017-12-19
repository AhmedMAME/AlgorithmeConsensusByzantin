/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensibs.algoconsensusbyzantinee;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mehdi
 */
public class Lieutenant extends Process{
    Socket socket;
    Chiffrement chiffrement;
    Annuaire annuaire;
    
    public Lieutenant(Boolean isFaulty, Socket _socket,Annuaire _annuaire) {
        super(isFaulty);
        this.socket = _socket;
        this.generateKey();
        this.annuaire = _annuaire;
    }
    public void byteToByte(byte[] _byte,Byte[] _Byte)
    {
        _Byte = new Byte[_byte.length];
        for (int i = 0; i < _byte.length; i++)
        {
            _Byte[i] = Byte.valueOf(_byte[i]);
        }
    }
    public boolean receiveMessage(Socket _socket, Byte[] msg) throws IOException {
        BufferedReader in = null;
        boolean lContinue = true;
        
        try {
            in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            while(lContinue) {
                this.byteToByte(in.readLine().getBytes(),msg);
                this.chiffrement.verifierSignature(this.publicKey,msg);
                if (msg != null){
                  return true;
                }
            }
        }catch(IOException e){
        }   
        return false;
    }
    public void sendMessage(Socket _socket, Byte[] msg) throws IOException {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new OutputStreamWriter(_socket.getOutputStream()));
            this.chiffrement.signer(this.privateKey,msg);
            out.println(msg);
            out.flush();
        }catch(IOException e){
        }
    }
    
    public void choiceOrder(Byte[] _msg) {
        
    }
   
    
    @Override
    public void run(){
        Socket commandant = null;
        Socket lieutenent = null;
        Byte[] msg = null;
        ServerSocket serverSocket;
        boolean lContinue = true;
        try {
            serverSocket = new ServerSocket(1234);
            while(lContinue) {
                lContinue = receiveMessage(commandant,msg);
            }
            
            for (HashMap.Entry<Long,PublicKey> e : this.annuaire.entrySet()) {
                lieutenent = new Socket("toto", 80);
                sendMessage(lieutenent, msg);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Lieutenant.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.choiceOrder(msg);
    }
}
