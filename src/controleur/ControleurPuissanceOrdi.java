package controleur;

import modele.*;
import vue.Ihm;

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
    protected void traiterCoup (Joueur joueur)  throws CoupInvalideException {
       Coup coup;
        if (joueur instanceof Humain) {
            super.traiterCoup(joueur);
        } else {
            Grille grilleVirtuelle= ((Grille)plateau).constructionVirtuelle();
            if(nbRotation.get(joueur)>0) {
                int sens=grilleVirtuelle.gererRotationRobot();
                if(sens!=-2){
                    ((Grille) plateau).gererRotation(sens);
                    coup=new CoupPuissance(-sens,jetonDuJoueur.get(joueur));
                    affichageFinTour(joueur,coup);
                    return;
                }
            }
            grilleVirtuelle= ((Grille)plateau).constructionVirtuelle();
            coup= grilleVirtuelle.gererCoupRobot(nbRotation.get(joueur1));
            plateau.gererCoup(coup);
            affichageFinTour(joueur,coup);
        }
    }

    @Override
    protected void affichageDebutTour(Joueur joueur) {
        if(joueur instanceof Humain){
            super.affichageDebutTour(joueur);
        }
    }

}
