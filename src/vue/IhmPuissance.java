package vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IhmPuissance extends Ihm{

    public IhmPuissance(){
        super();
    }
    /**
     * Demande aux joueurs s'ils souhaitent ajouter des contraintes sur leur partie
     * @return true si ils veulent false sinon
     */
    public boolean demanderAjoutContrainte(){
        scanner =new Scanner(System.in);
        boolean rep=false;
        System.out.println(spacing);
        String msg="Voulez vous ajouter une rotation? (O)ui ou (N)on : ";
        System.out.print(msg);
        while(scanner.hasNextLine()){
            String valeur= scanner.nextLine();
            if(valeur.equals("O") || valeur.equals("o")){
                rep= true;
                break;
            }
            if(valeur.equals("N") || valeur.equals("n")){
                break;
            }
            System.out.println("Erreur: Entrer O ou N\n");
            System.out.print(msg);
        }
        return rep;
    }

    /**
     * Demande au joueur courant s'il souhaite placer un jeton ou faire tourner la grille
     * @return le choix du joueur: 0 pour mettre un jeton 1 pour tourner la grille
     */
    public int choixMouvement(int movement) {
        scanner = new Scanner(System.in);
        int nb = -1;
        if (0 < movement) {
            System.out.println("Il vous reste " + movement + " rotation(s) : \n 0 pas de rotation \n 1 faire une rotation");
            String msg = "Votre choix :";
            System.out.print(msg);
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                Scanner scLoc = new Scanner(ligne);
                if (!ligne.contains(" ") && scLoc.hasNextInt()) {
                    nb = scLoc.nextInt();
                    if (nb == 0 || nb == 1) {
                        break;
                    }
                }
                System.out.println("Erreur: Entrer 0 ou 1 \n ");
                System.out.print(msg);
            }
        }
        return nb;
    }

    /**
     * Si le joueur demande ?? faire tourner la grille, on lui demande dans quel sens il veut la rotation
     * @return la r??ponse joueur: 0 pour la rotation vers la gauche, 1 pour la droite
     */
    public int sensRotation() {
        scanner = new Scanner(System.in);
        int nb = -1;
        System.out.println("Entrer \n 0 pour tourner ?? gauche \n 1 pour tourner ?? droite ");
        String msg = "Votre choix : ";
        System.out.print(msg);
        while (scanner.hasNextLine()) {
            String ligne = scanner.nextLine();
            Scanner scLoc = new Scanner(ligne);
            if (!ligne.contains(" ") && scLoc.hasNextInt()) {
                nb = scLoc.nextInt();
                if (nb == 0 || nb == 1) {
                    break;
                }
            }
            System.out.println("Erreur: Entrer 0 ou 1 \n ");
            System.out.print(msg);
        }
        return nb;
    }

    /**
     * Affiche un message indiquant au joueur courant que son tour est arriv??
     * @param joueur est le joueur courant
     */
    @Override
    public void afficherTour(String joueur) {
        System.out.println(joueur+ ": ?? vous de jouer\n");
    }

    /**
     * Demande au joueur de saisir un coup pour son tour
     * @return le coup saisi par le joueur : le num??ro de la colonne o?? il veut placer son jeton
     */
    @Override
    public List<Integer> demanderCoup(){
        scanner = new Scanner(System.in);
        List<Integer> l=new ArrayList<>();
        int nb = -1;
        String msg="Entrer un num??ro de grille entre 1 et 7: ";
        System.out.print(msg);
        while(scanner.hasNextLine()){
            String ligne = scanner.nextLine();
            Scanner scLoc = new Scanner(ligne);
            if(!ligne.contains(" ") && scLoc.hasNextInt()){
                nb=scLoc.nextInt();
                break;
            }
            System.out.println("Erreur: le num??ro de grille doit ??tre un entier entre 1 et 7 \n");
            System.out.print(msg);
        }
        l.add(nb);
        return l;
    }


    /**
     * Affiche un message si la partie se fini sur une ??galit?? entre les joueurs
     */
    public void afficherPartieNulle(){
        System.out.println(spacing);
        System.out.println("La partie est nulle");
    }

}
