package com.example;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ListaClient {
    HashMap<String, Socket> lista;

    public ListaClient () {
        lista = new HashMap();
    }

    public void aggiungiClient(String nome, Socket socket) {
        lista.put(nome, socket);
    }

    public Socket getSocketDalNome (String nome) {
        return lista.get(nome);
    }
    public String getTuttiNomi () {
        String s = String.join("\n-", lista.keySet());
        return s;
    }

    public Socket getClient (String nome) {
        if (lista.containsKey(nome)) {
            return lista.get(nome);
        }
        return null;
    }
    
    public ArrayList<Socket> getTuttiClient() {
        return new ArrayList<Socket>(lista.values());
    }

    public void rimuoviClient(String name){
        lista.remove(name);
    }
}
