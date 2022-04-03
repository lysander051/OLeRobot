package vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IhmNim extends Ihm{

    public IhmNim(){
        super();
    }

    /**
     * On demande aux joueurs le nombre de tas qu'ils souhaitent pour leur partie
     * @return le nombre de tas saisi
     */
    public int demanderNbTas(){
        scanner = new Scanner(System.in);
        int nb = -1;
        String msg="Entrer un nombre de tas positif: ";
        System.out.print(msg);
        while(scanner.hasNextLine()){
            String ligne = scanner.nextLine();
            Scanner scLoc = new Scanner(ligne);
            if(!ligne.contains(" ") && scLoc.hasNextInt()){
                nb=scLoc.nextInt();
                if(nb>=1){
                    System.out.println(spacing);
                    break;
                }
            }
            System.out.println("Erreur: le tas doit être un entier positif supérieur à 1 \n ");
            System.out.print(msg);
        }
        return nb;
    }

    /**
     * Demande aux joueurs le nombre d'allumettes maximum qu'ils peuvent retirés sur leur partie
     * @return le coup max
     */
    public int demanderCoupMax(){
        scanner = new Scanner(System.in);
        int nb = -1;
        String msg="Entrer un nombre de coup max ou 0: ";
        System.out.println(spacing);
        System.out.print(msg);
        while(scanner.hasNextLine()){
            String ligne = scanner.nextLine();
            Scanner scLoc = new Scanner(ligne);
            if(!ligne.contains(" ") && scLoc.hasNextInt()){
                nb=scLoc.nextInt();
                if(nb>=0){
                    break;
                }
            }
            System.err.println("Erreur: le coup max doit être un entier positif \n ");
            System.out.print(msg);
        }
        return nb;
    }

    /**
     * On redéfinit la méthode afficherTour pour le jeu de Nim
     * @param nom correspond au nom du joueur courant
     */
    @Override
    public void afficherTour(String nom){
        System.out.println(nom+": à vous de jouer un coup sous la forme 'm n' " +
                "\n où m est le tas choisi et n le nombre d'allumettes " +
                "\n à retirer dans ce tas\n");
    }

    /**
     * On redéfinit la méthode demanderCoup pour le jeu de Nim
     * @return le coup saisi par le joueur le numéro du tas et le nombre d'allumettes à retirer
     */
    @Override
    public List<Integer> demanderCoup() {
        scanner =new Scanner(System.in);
        String msg="Entrer votre coup : ";
        List<Integer> l=new ArrayList<>();
        System.out.print(msg);
        while (scanner.hasNextLine()) {
            Scanner scInt = new Scanner(scanner.nextLine());
            while (scInt.hasNext()) {
                if (scInt.hasNextInt()) {
                    l.add(scInt.nextInt());
                }
                else {
                    scInt.next();
                    l.clear();
                    break;
                }
            }
            if(l.size()!=2){
                l.clear();
                System.out.println("Il faut entrer 2 entiers, rejouez\n");
                System.out.print(msg);
            }
            else {
                break;
            }
        }
        return l;
    }

}
