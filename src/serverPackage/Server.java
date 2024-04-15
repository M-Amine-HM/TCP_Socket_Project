package serverPackage;
import commonPackage.Calculatrice;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            //Création d'un ServerSocket écoutant sur le port 12345
            ServerSocket serverSocket = new ServerSocket(12345);

            //// Attente de la connexion d'un client
            System.out.println("Le serveur attend la connexion du client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connecté");

            // Envoi et Réception d'un Octet
            // Envoi d'un octet saisie par l'utilsateur
            System.out.println("-----Envoi et Réception d'un Octet-------");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez un entier pour l'envoyer au client : ");
            int intServer = scanner.nextInt();
            clientSocket.getOutputStream().write(intServer);
            // Lecture de l'octet envoyé par le client
            int receivedByte = clientSocket.getInputStream().read();
            System.out.println("Octet reçu du client : " + receivedByte);
            System.out.println("-------------------------------------------");

            // Envoi et Réception d'une Chaîne de Caractères
            System.out.println("------Envoi et Réception d'une Chaîne de Caractères---------");
            PrintWriter stringWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            // Envoi de la chaîne au client
            stringWriter.println("Bonjour du serveur!");
            String receivedString = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();
            System.out.println("Message reçu du client : " + receivedString);
            System.out.println("--------------------------------------------");

            // Envoi et Réception d'un Objet Sérialisé
            System.out.println("-----Envoi et Réception d'un Objet Sérialisé------------------");
            //le serveur va faire une operation mathematique selon loperation et les entiers choisis par le client

            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            // Lecture de l'objet sérialisé
            Calculatrice receivedObject = (Calculatrice) objectInputStream.readObject();
            int resultat = receivedObject.calculer();
            System.out.println("Résultat : " + resultat);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeInt(resultat);
            objectOutputStream.flush();
            System.out.println("--------------------------------------------");

            //affichage des informations sur le Socket du client

            System.out.println("----affichage des informations sur le ServerSocket utilisé par le serveur---");
            System.out.println("le numéro de port local utilisé par le serveur.: "+serverSocket.getLocalPort());
            System.out.println("Le nom d'hôte du serveur.: "+serverSocket.getInetAddress().getHostName());

            // Ferme les flux et les sockets
            objectInputStream.close();
            objectOutputStream.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}



