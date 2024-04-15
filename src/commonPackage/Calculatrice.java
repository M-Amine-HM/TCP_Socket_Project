package commonPackage;

import java.io.Serializable;

//Dans la classe calculatrice il y a trois attributs entier1 ,entier2 et operation de type caractére
public class Calculatrice implements Serializable {
    private static final long serialVersionUID = 1L;
    private char operation;
    private int int1;
    private int int2;
    public Calculatrice(char operation, int int1, int int2) {
        this.operation = operation;
        this.int1 = int1;
        this.int2 = int2;
    }
    public int calculer() {
        int resultat = 0;

        switch (operation) {
            case '+':
                resultat = int1 + int2;
                break;
            case '-':
                resultat = int1 - int2;
                break;
            case '*':
                resultat = int1 * int2;
                break;
            case '/':
                if (int2 != 0) {
                    resultat = int1 / int2;
                } else {
                    System.out.println("Erreur : Division par zéro.");
                }
                break;
            default:
                System.out.println("Opération non supportée.");
        }

        return resultat;
    }

}
