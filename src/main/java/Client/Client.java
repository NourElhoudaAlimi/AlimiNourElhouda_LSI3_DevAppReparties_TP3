package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("127.0.0.1", 1234);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner sc = new Scanner(System.in);
        ) {
            System.out.println("Connecté au serveur.");
            System.out.println(in.readLine()); // message de bienvenue

            while (true) {
                System.out.print("Entrez une opération (ex: 4 + 5) ou 'exit' pour quitter : ");
                String msg = sc.nextLine();

                if (msg.equalsIgnoreCase("exit")) break;

                out.println(msg);
                System.out.println("→ Réponse du serveur : " + in.readLine());
            }

        } catch (IOException e) {
            System.out.println("Erreur de connexion au serveur.");
        }
    }
}
