package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        try {
            // creao un socket / collegamento al server
            Socket socket = new Socket("localhost", 3000);
            // creo i buffer e stream per comunicare
            DataOutputStream outputVersoServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader inputServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader leggiTastiera = new BufferedReader(new InputStreamReader(System.in));
            String scelta = "";
            Ascolto ascolto = new Ascolto(inputServer);
            ascolto.start();
            // X per chiudere, C per vedere tutti gli utenti, TUTTI o il nome del utente per decidere a chi inviare il messaggio poi
            // invia il messaggio.
            // tutti gli input dalla tastiera sono messi in Uppercase automaticamente
            do {
                scelta = leggiTastiera.readLine();
                scelta = scelta.toUpperCase().trim();
                outputVersoServer.writeBytes(scelta + "\n");
            } while (!scelta.equals("X"));

            socket.close();

        } catch (Exception e) {
            System.out.println("Errore-  (main)");
        }
    }
}
