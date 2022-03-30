package controleur;

import modele.*;
import vue.Ihm;
import vue.IhmPuissance;

import java.util.Arrays;
import java.util.List;
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

    protected boolean testRotation(Grille g,Joueur j) throws CoupInvalideException {
        boolean test=false;
        for(int i=0;i<2;i++){
            g.gererRotation(i);
            Set<Jeton> lesJetons=g.partieTerminee();
            if(lesJetons.size()==1 &&  gagnantPartie(lesJetons).equals(j) ){
                test=true;
                break;
            }
        }
        return test;
    }
    @Override
    protected void traiterCoup (Joueur joueur)  throws CoupInvalideException {
        if (joueur instanceof Humain) {
            super.traiterCoup(joueur);
        } else {
            Grille grilleVirtuelle= ((Grille)plateau).constructionVirtuelle();
            if(nbRotation.get(joueur)>0) {
                for (int i = 0; i < 2; i++) {
                    Set<Jeton> lesJetons = grilleVirtuelle.testRotation(i);
                    if (lesJetons!=null && gagnantPartie(lesJetons).equals(joueur)) {
                        ((Grille) plateau).gererRotation(i);
                        return;
                    }
                }
            }

            Coup coup =grilleVirtuelle.gererCoupRobot(nbRotation.get(joueur1),nbRotation.get(joueur2));
            plateau.gererCoup(coup);

            return;
        }
    }

}
