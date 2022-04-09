package controleur;


import modele.Joueur;
import modele.Humain;
import modele.Ordinateur;
import modele.Tas;
import modele.Coup;
import modele.CoupNim;
import vue.Ihm;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class ControleurNimOrdi extends ControleurNim{

    public ControleurNimOrdi(Ihm ihm) {
        super(ihm);
    }

    /**
     * Enregistre le nom du joueur 1 et crée un ordinateur pour un jeu contre l'ordinateur
     */
    @Override
    protected void enregistrementNom(){
        joueur1=new Humain(ihm.demanderNom(1));
        joueur2=new Ordinateur();
    }

    /**
     * On teste si c'est au joueur de jouer, dans ce cas on lui propose de jouer son coup, sinon l'ordinateur choisit un coup
     * aléatoire qu'il peut jouer en prenant en compte si besoin les contraintes du jeu
     * @param j correspond au joueur qui va jouer son coup
     * @return le coup joué par le joueur ou l'ordinateur
     */
    @Override
    protected Coup getCoupJoueur(Joueur j){
        if( j instanceof Humain){
            return super.getCoupJoueur(j);
        }
        else {
            List<Coup> listeCoup =getListeCoup();
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
        else{
           ihm.separation();
        }
    }

    /**
     * Ajoute dans la liste des coups le coup possible à chaque tas
     * @return la liste des coups possibles
     */
    private List<Coup> getListeCoup(){
        Tas tas=(Tas)plateau;
        List<Coup> lesCoups=new ArrayList<>();
        for (int i=1;i<=tas.getNbTas();i++)
        {
            for(int poss=1;poss<=tas.nbAllumettes(i);poss++){
                if(poss<=tas.getCoupMax()) {
                    Coup coup = new CoupNim(i, poss);
                    lesCoups.add(coup);
                }
            }
        }
        return lesCoups;
    }

}
