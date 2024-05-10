package it.polimi.ingsw.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

// to test against console:
//  /usr/bin/nc 127.0.0.1 1234
// and type in console: server will receive.
// it will NOT block socket (for now..) when timeout.

public class ServerMain
{
    //TODO: ELEONORA
    public static void main(String[] args) {

        ServerManager manager = new ServerManager();
        ServerRMI obj = new ServerRMI(manager);

        try {
            Loggable stub = (Loggable) UnicastRemoteObject.exportObject(obj, Integer.parseInt(args[0]));

            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[0]));

            registry.bind("Loggable", stub);

            System.err.println("Server RMI ready");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //---------------

        ServerSKT serverSKT = new ServerSKT(Integer.parseInt(args[1]), manager);
        System.err.println("Server SKT ready");
        serverSKT.startServer();
    }



}
