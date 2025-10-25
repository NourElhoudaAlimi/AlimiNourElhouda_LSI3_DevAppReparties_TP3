package Server;

import java.io.*;
import java.net.*;

public class Server {
    private static int compteurGlobal = 0;

    public static synchronized void incrementerCompteur() {
        compteurGlobal++;
        System.out.println("Compteur global : " + compteurGlobal);
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("=== Serveur Multi-Threads Démarré sur le port 1234 ===");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nouveau client connecté : " + socket.getInetAddress());

                // Créer un thread pour chaque client
                ClientProcess clientThread = new ClientProcess(socket);
                new Thread(clientThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

