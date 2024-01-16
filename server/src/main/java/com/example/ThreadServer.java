package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadServer extends Thread {
    Socket client;
    BufferedReader inputDalClient;
    DataOutputStream outputVersoClient;
    ListaClient listaClient;
    String nomeClient;

    public ThreadServer(Socket client, ListaClient listaClient) {
        this.client = client;
        this.listaClient = listaClient;
        this.nomeClient = "null";
    }

    public void inviaMessaggio(Socket client, String messaggio) {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeBytes(nomeClient +  messaggio + "\n");
            System.out.println("\n" + client.getInetAddress() + ": " + messaggio);
        } catch (Exception e) {
            System.out.println("errore nell'invio del messaggio");
        }
    }

    public void inviaMessaggioDalServer(String messaggio) {
        try {
            System.out.println(outputVersoClient);
            outputVersoClient.writeBytes("server: " + messaggio + "\n");
        } catch (Exception e) {
            System.out.println("errore nell'invio del messaggio");
        }
    }

    public void broadcast(String messaggio) {
        for (Socket client : listaClient.getTuttiClient()) {
            if (this.client == client) {
                continue;
            }
            inviaMessaggio(client,messaggio);
        }
    }

    public void run() {
        Socket client = this.client;
        try {
            // creao i tubi
            this.inputDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.outputVersoClient = new DataOutputStream(client.getOutputStream());
            String richiesta;
            String messaggio;
            System.out.println("chiedo al utente il uo nome");
            do {
                System.out.println("interno do");
                inviaMessaggioDalServer("Inserisci un nome utente...");
                richiesta = inputDalClient.readLine();
                if (listaClient.getClient(richiesta) != null) {
                    inviaMessaggioDalServer("Nome gia' esistente");
                    continue;
                }
                if (richiesta.equals("") || richiesta.equals("SERVER") || richiesta.equals("TUTTI")) {
                    inviaMessaggioDalServer("Nome non valido");
                }
                this.nomeClient = richiesta;
                listaClient.aggiungiClient(this.nomeClient, client);
                broadcast( nomeClient + " si e' aggiunto");
                break;
            } while (true);
            do {
                inviaMessaggioDalServer("\n Fai una scelta...");
                richiesta = inputDalClient.readLine();
                richiesta = richiesta.toUpperCase();
                switch (richiesta) {
                    case "X":
                        System.out.println("client " + client.getInetAddress() + " ha chiesto di chiudere");
                        broadcast("il client "+ client.getInetAddress() + " ha chiuso");
                        listaClient.rimuoviClient(nomeClient);
                        break;
                    case "C":
                        inviaMessaggioDalServer("I client disponibili sono: " + listaClient.getTuttiNomi());
                        break;
                    case "TUTTI":
                        inviaMessaggioDalServer("Il tuo messaggio broadcast sara': ...");
                        messaggio = inputDalClient.readLine();
                        if (messaggio.equals("") || messaggio.equals("NULL")) {
                            break;
                        }
                        System.out.println("invio brodcast da parte di: " + nomeClient);
                        this.broadcast(messaggio);
                        break;
                    default:
                        Socket c = listaClient.getClient(richiesta);
                        if (c == null) {
                            inviaMessaggioDalServer("Il client digitato non esiste");
                            break;
                        }

                        inviaMessaggioDalServer("Cosa gli vuoi dire?...");
                        messaggio = inputDalClient.readLine();
                        if (messaggio.equals("") || messaggio.equals("NULL")) {
                            break;
                        }
                        inviaMessaggio(c, messaggio);
                        break;
                }
            } while (!richiesta.equals("X"));
        } catch (Exception e) {
            System.out.println("\t\t  errore " + e.getMessage());
        }
    }
}


