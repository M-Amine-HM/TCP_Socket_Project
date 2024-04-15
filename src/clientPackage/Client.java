package clientPackage;

import commonPackage.Calculatrice;

import java.util.Scanner;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class Client {
    public static void main(String[] args) {
        try {

            //Connexion au serveur à l'adresse "localhost" et au port 12345
            Socket socket = new Socket("localhost", 12345);

            // Envoi et Réception d'un Octet
            // Envoi d'un octet saisie par l'utilsateur
            System.out.println("-----Envoi et Réception d'un Octet-------");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez un entier pour l'envoyer au serveur : ");
            int intClient = scanner.nextInt();
            socket.getOutputStream().write(intClient);
            // Lecture de l'octet envoyé par le serveur
            int receivedByte = socket.getInputStream().read();
            System.out.println("Octet reçu du serveur : " + receivedByte);
            System.out.println("--------------------------------------------");

            // Envoi et Réception d'une Chaîne de Caractères
            System.out.println("------Envoi et Réception d'une Chaîne de Caractères---------");
            PrintWriter stringWriter = new PrintWriter(socket.getOutputStream(), true);
            // Envoi de la chaîne au serveur
            stringWriter.println("Bonjour du client!");
            String receivedString = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
            System.out.println("Message reçu du serveur : " + receivedString);
            System.out.println("--------------------------------------------\n");

            System.out.println("-----Envoi et Réception d'un Objet Sérialisé------------------");
            // Envoi et Réception d'un Objet Sérialisé
            //exemple de calculatrice
            //le client va choisir deux entiers et une operation et le serveur va faire la calcul

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // Lecture du premier entier
            System.out.print("Entrez le premier entier : ");
            int entier1 = scanner.nextInt();

            // Lecture du deuxième entier
            System.out.print("Entrez le deuxième entier : ");
            int entier2 = scanner.nextInt();

            // Lecture de l'operation
            System.out.print("Entrez l'operation (+, -, *, /)  : ");
            char op = scanner.next().charAt(0);
            // Création et envoi de l'objet Calculatrice sérialisé
            objectOutputStream.writeObject(new Calculatrice(op, entier1,entier2));

            // Réception du résultat du serveur
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            int resultFromServer = objectInputStream.readInt();
            System.out.println("Résultat du serveur : " + resultFromServer);
            System.out.println("--------------------------------------------");

            //affichage des informations sur le Socket du client

            System.out.println("-------Affichage des informations sur le Socket du client---");
            InetAddress clientAddress = socket.getInetAddress();
            System.out.println("socket getLocalAddress:  "+socket.getLocalAddress());
            System.out.println("host name: "+clientAddress.getHostName());
            System.out.println("socket LocalPort: "+socket.getLocalPort());
            System.out.println("--------------------------------------------");

            /// Fermeture du socket et des flux
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }