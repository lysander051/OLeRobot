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
        String msg="Voulez vous ajouter une contrainte? (O)ui ou (N)on : ";
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
            System.out.println("Il vous reste " + movement + ": \n 0 pas de rotation \n 1 faire une rotation");
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
     * Si le joueur demande à faire tourner la grille, on lui demande dans quel sens il veut la rotation
     * @return la réponse joueur: 0 pour la rotation vers la gauche, 1 pour la droite
     */
    public int sensRotation() {
        scanner = new Scanner(System.in);
        int nb = -1;
        System.out.println("Entrer \n 0 pour tourner à gauche \n 1 pour tourner à droite ");
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
     * Affiche un message indiquant au joueur courant que son tour est arrivé
     * @param joueur est le joueur courant
     */
    @Override
    public void afficherTour(String joueur) {
        System.out.println(joueur+ ": à vous de jouer\n");
    }

    /**
     * Demande au joueur de saisir un coup pour son tour
     * @return le coup saisi par le joueur : le numéro de la colonne où il veut placer son jeton
     */
    @Override
    public List<Integer> demanderCoup(){
        scanner = new Scanner(System.in);
        List<Integer> l=new ArrayList<>();
        int nb = -1;
      //  String joueur = couleur + " " + nom+ ": à vous de jouer";
        String msg="Entrer un numéro de grille entre 1 et 7: ";
        //System.out.println(joueur);
        System.out.print(msg);
        while(scanner.hasNextLine()){
            String ligne = scanner.nextLine();
            Scanner scLoc = new Scanner(ligne);
            if(!ligne.contains(" ") && scLoc.hasNextInt()){
                nb=scLoc.nextInt();
                break;
            }
            System.out.println("Erreur: le numéro de grille doit être un entier entre 1 et 7 \n");
            System.out.print(msg);
        }
        l.add(nb);
        return l;
    }

    @Override
    public void afficherLeCoupJoue(String nom, String coup) {

    }

    /**
     * Affiche un message si la partie se fini sur une égalité entre les joueurs
     */
    public void afficherPartieNulle(){
        System.out.println(spacing);
        System.out.println("La partie est nulle");
    }

}
