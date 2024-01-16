package com.example;

import java.io.BufferedReader;

public class Ascolto extends Thread {

    BufferedReader inputServer;

    public Ascolto(BufferedReader in) {
        this.inputServer = in;
    }

    public void run() {
        BufferedReader inputServer = this.inputServer;
        String messaggioRicevuto;
        try {
            do {
                System.out.println("avvio ascolto");
                messaggioRicevuto = inputServer.readLine();
                System.out.println(messaggioRicevuto);
            } while (!messaggioRicevuto.equals("X"));
        } catch (Exception e) {
            System.out.println("Errore - (Ascolto.java)");
        }
    }
}
