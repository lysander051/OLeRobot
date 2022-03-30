package controleur;

import modele.*;
import vue.Ihm;
import java.util.Set;

public class ControleurPuissanceOrdi extends ControleurPuissance{


    public ControleurPuissanceOrdi(Ihm ihm) {
        super(ihm);
    }

    @Override
    protected void enregistrementNom(){
        joueur1=new Humain(ihm.demanderNom(1));
        joueur2=new Ordinateur();

    }
    @Override
    protected Coup getCoupJoueur (Joueur joueur)  throws CoupInvalideException {
        Coup coup;
        if (joueur instanceof Humain) {
            coup=super.getCoupJoueur(joueur);
        } else {
            Grille grilleVirtuelle= ((Grille)plateau).constructionVirtuelle();
            if(nbRotation.get(joueur)>0) {
                for (int i = 0; i < 2; i++) {
                    Set<Jeton> lesJetons = grilleVirtuelle.testRotation(i);
                    if (lesJetons!=null && gagnantPartie(lesJetons).equals(joueur)) {
                        ((Grille) plateau).gererRotation(i);
                         coup=new CoupPuissance(-i,jetonDuJoueur.get(joueur));
                    }
                }
            }
             coup =grilleVirtuelle.gererCoupRobot(nbRotation.get(joueur1),nbRotation.get(joueur2));
        }
        return coup;
    }

    @Override
    protected void affichageDebutTour(Joueur joueur) {
        if(joueur instanceof Humain){
            super.affichageDebutTour(joueur);
        }

    }

}
