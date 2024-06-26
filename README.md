# Prova finale di Ingegneria del Software - AA 23/24


Implementazione del tavolo da gioco [Codex Naturalis](https://www.craniocreations.it/prodotto/codex-naturalis).


# Documentazione

### UML
Presso i seguenti link è possibile visionare l'UML iniziale che definisce la struttura iniziale del model e la cartella dove è presenta l'UML autogenerato da Intellij:

 1. [UML-initial](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables).
 2. [UML-final](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables).


### JavaDoc
Tutta la JavaDoc generata è presenta nella cartella [JavaDoc](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables/Javadoc).

### Sequence Diagram 

Il sequence diagram che abbiamo ideato e consegnato durante la second PeerReview è presente nella cartella [Sequence Diagram](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables). 


### PeerReview

Le due PeerReview sono invece disponibili nella cartella [PeerReview](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables). 

# Funzionalità

Di seguito sono riportate le funzionalità che abbiamo realizzato:


|  Funzionalità   | Base  | Avanzata |
|-----|---|---|
| Regole semplificate | ✅ | |
| Regole complete | ✅ | |
| Socket | ✅ | |
| RMI | ✅ | |
| TUI | ✅ | |
| GUI | ✅ | |
| Partite Multiple | | :x: |
| Persistenza | | :x: |
| Resilienza alle disconnessioni | | ✅ |
| Chat | | ✅ |


# Compilazione

I jar sono stati realizzati con l'utilizzo di Maven Assembly Plugin tramite il comando `mvn clean compile package`. I Jar sono disponibili alla cartella [Jar](https://github.com/lorenzodella/IS24-AM20/tree/main/Deliverables).



# Esecuzione
Per essere eseguito questo progetto ha bisogno di Java versione    . 

Per poter giocare bisogna innanzitutto eseguire il Jar del server, mentre quando si vuole giocare bisogna lanciare il Jar del client. 

### Server

Per poter eseguire il jar del server è necessario lanciare il jar da linea di comando, specificando come primo parametro il numero della porta dell'RMI (abbiamo deciso che per RMI la porta da specificare è 12345) e come secondo parametro il numero della porta di SKT (nel nostro caso è sempre 12346). Di seguito il comando per lanciare il jar correttamente:

```
java -jar ./AM20-server.jar 12345 12346
```

### Client

Per poter eseguire il client è necessario specificare 4 parametri:
1. scelta della UI: 1 per la TUI e 2 per la GUI
2. scelta della tipologia di connessione: 1 RMI e 2 SKT
3. indirizzo IP del server
4. numero di porta di SKT (12346) o RMI (12345)

Un esempio per lanciare il client con la scelta di GUI e RMI è:
```
java -jar ./AM20-client.jar 2 1 <ip> 12345
```


## Cose importanti da sapere

Il jar del Client può essere anche lanciato senza specificare nessun parametro. Infatti, quando verrà lanciato il jar, il MainThread chiederà all'utente la scelta riguardo UI e connesione, l'indirizzo IP del server e il numero di porta (SKT :12346 o RMI :12345).

>[!IMPORTANT]
>Il messaggio nella TUI va scritto tra doppi apici. Il comando da eseguire quindi è:
>```
>/chat <dest> "message"
>```

 
# Componenti del gruppo

+ [Lorenzo Della Matera](https://github.com/lorenzodella)
+ [Mattia Doro](https://github.com/mado002)
+ [Eleonora Ficarelli](https://github.com/EleonoraFicarelli)
+ [Irene Ferrente](https://github.com/Ireneeer)

