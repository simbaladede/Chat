# ChatRettoriMasiMolla

### Componenti del gruppo
- Rettori Lorenzo
- Xhuliano Molla
- Niccolò Masi

# Chat 

Realizzazione di una chat client-server.

## <a id="funzionamento" /> Il funzionamento

La chat funziona in questo modo: 
1. il client, dopo la connessione al server, deve inviare come prima informazione il proprio nome;  
2. il client, dopo aver inviato il nome, può inviare messaggi ad un singolo destinatario oppure a tutti; 
3. il server può inoltrare messaggi agli altri client (singolo o tutti);  
4. il client gestisce attraverso un messaggio speciale la chiusura della connessione verso il server.

## <a id="casiClient" /> Casi d'uso del client

Il client viene utilizzato per scambiare messaggi con gli altri client.

> **Nota:** se il client si è connesso per la prima volta deve inviare obbligatoriamente il proprio nome prima di poter comunicare con gli altri, cosi da metterlo nella lista Client

## <a id="casiServer" /> Casi di uso del server

Il server viene utilizzato per gestire la comunicazione tra i client connessi.

>  **Nota:** il server è in grado di gestire eventuali errori o situazioni inattesse come per esempio se il client inserisce il nome di un client insistente.

##  <a id="protocollo" /> Il protocollo

Ecco il protocollo che abbiamo utilizzato:

|Client                         |Server                         		|
|-------------------------------|---------------------------------------|
|`TUTTI`						|"Invio il messaggio a tutti"			|
|`C`						   	|"Mostra i client collegati"		|
|`nome`        					|"Ti ho registrato"   					|
|`nome di un altro client`      |"Dimmi il messaggio che devo inviargli"|
|`messaggio`      				|"Invio il messagio al 'nomeClient'"	|
|`X`      						|"Chiudo connessione"					|
|`NULL` (per non inviare niente)|										|

## <a id="diagramma" /> Diagramma delle Classi

Ecco il diagramma uml delle classi della nostra chat:


```mermaid
classDiagram

class Server {
- lista: ListaClient
- serverSocket: ServerSocket
- th: ThreadServer
}

class ListaClient {
- lista: HashMap<String, Socket>
+ ListaClient()
+ aggiungiSocket()
+ getSocketDalNome()
+ getTuttiNomi()
+ getClient()
+ getTuttiClient()
}

class ThreadServer {
- socket: Socket
- inputDalClient: BufferedReader
- outputDalClient: DataOutputStream
- listaClient: ListaClient
- nomeClient
+ ThreadServer()
+ inviaMessaggio()
+ run()
}

Server --> ListaClient
Server --> ThreadServer
ThreadServer --> ListaClient

class Client {
- socket : Socket
- inputDalServer: BufferedReader
- outputVersoServer: DataOutputStream
- leggiTastiera: BufferedReader
- ascolto: Ascolto
}

class Ascolto {
- inputDalServer: BufferedReader
+ Ascolto()
+ run()
}

Client .. Server :  Comunicano
Client --> Ascolto
