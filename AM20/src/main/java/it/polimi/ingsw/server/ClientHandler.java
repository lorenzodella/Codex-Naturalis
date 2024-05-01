package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;


import com.sun.security.ntlm.Server;
import it.polimi.ingsw.clientmessage.ClientMessage;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.controller.messages.Message;

/* Threac che ascolta, su un certo socket, e ogni volta che riceve un messaggio lo interpreta e compie
   ciò che gli viene detto di fare  (invocando un metodo del controller)
*/
public class ClientHandler implements Runnable{
    
    private ServerManager manager;
    private Socket socket;

    private ObjectInputStream objectInputStream;



    public ClientHandler(Socket socket, ServerManager manager) throws IOException{
        this.socket = socket;
        this.manager = manager;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    
    @Override
    public void run() {
        while(true){
            try {
                ClientMessage message = (ClientMessage) objectInputStream.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }




    }
}
