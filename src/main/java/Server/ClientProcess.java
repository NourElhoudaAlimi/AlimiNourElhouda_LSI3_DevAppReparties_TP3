package Server;

import java.io.*;
import java.net.*;

public class ClientProcess implements Runnable {
    private Socket socket;

    public ClientProcess(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            out.println("Bienvenue sur le serveur de calcul !");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message reçu du client : " + message);

                // Exemple de calcul : "4 + 2"
                String[] parts = message.split(" ");
                if (parts.length == 3) {
                    double op1 = Double.parseDouble(parts[0]);
                    String op = parts[1];
                    double op2 = Double.parseDouble(parts[2]);
                    double res = 0;

                    switch (op) {
                        case "+": res = op1 + op2; break;
                        case "-": res = op1 - op2; break;
                        case "*": res = op1 * op2; break;
                        case "/": res = op2 != 0 ? op1 / op2 : Double.NaN; break;
                        default: out.println("Opérateur invalide !"); continue;
                    }

                    Server.incrementerCompteur();
                    out.println("Résultat = " + res);
                } else {
                    out.println("Format invalide ! Utilisez : nombre1 opérateur nombre2");
                }
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Client déconnecté : " + socket.getInetAddress());
        }
    }
}
