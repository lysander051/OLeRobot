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

    @Override
    protected void enregistrementNom(){
        joueur1=new Humain(ihm.demanderNom(1));
        joueur2=new Ordinateur();
    }

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

    @Override
    protected void affichageDebutTour(Joueur joueur) {
        if(joueur instanceof Humain){
            super.affichageDebutTour(joueur);
        }
    }

}
