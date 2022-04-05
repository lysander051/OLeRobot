package controleur;


import modele.Joueur;
import modele.Coup;
import modele.Humain;
import modele.Ordinateur;
import modele.CoupInvalideException;
import modele.Tas;

import vue.Ihm;
import java.util.*;


public class ControleurNimOrdi extends ControleurNim{

    public ControleurNimOrdi(Ihm ihm) {
        super(ihm);
    }

    /**
     * Enregistre le nom du joueur 1 et créer un oridnateur pour un jeu contre l'ordinateur
     */
    @Override
    protected void enregistrementNom(){
        joueur1=new Humain(ihm.demanderNom(1));
        joueur2=new Ordinateur();
    }

    /**
     * On teste si c'es tau joueur de jouer, dans ce cas on lui propose de jouer son coup, sinon l'ordinateur choisit un coup
     * aléatoire qu'il peut jouer en prenant en compte si besoin les contraintes de jeu
     * @param j correspond au joueur qui va jouer son coup
     * @return le coup joué par le joueur ou l'ordinateur
     */
    @Override
    protected Coup getCoupJoueur(Joueur j){
        if( j instanceof Humain){
            return super.getCoupJoueur(j);
        }
        else {
            List<Coup> listeCoup =((Tas)plateau).getListeCoup();
            Random rand = new Random();
            int tailleListe = listeCoup.size();
            int coupHasard=0;
            if (tailleListe > 0) {
                coupHasard = rand.nextInt(listeCoup.size());
            }
            return listeCoup.get(coupHasard);
        }
    }

    /**
     * Affiche le message indiquant que c'est au joueur de jouer son coup si c'est le cas
     * @param joueur est le joueur courant
     */
    @Override
    protected void affichageDebutTour(Joueur joueur) {
        if(joueur instanceof Humain){
            super.affichageDebutTour(joueur);
        }
    }

}
