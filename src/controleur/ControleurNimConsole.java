package controleur;


import modele.*;
import vue.Ihm;
import java.util.*;


public class ControleurNimConsole extends ControleurNim{


    public ControleurNimConsole(Ihm ihm) {
        super(ihm);
    }


    @Override
    protected void enregistrementNom(){
        joueur1=new Humain(ihm.demanderNom(1));
        joueur2=new Ordinateur();

    }

    @Override
    protected Coup getCoupJoueur(Joueur j) throws CoupInvalideException{
        if( j instanceof Humain){
            return super.getCoupJoueur(j);
        }
        else {
            List<Coup> listeCoup = getListeCoup();
            Random rand = new Random();
            int tailleListe = listeCoup.size();
            int coupHasard;
            if (tailleListe > 0) {
                coupHasard = rand.nextInt(listeCoup.size());
            } else {
                throw new CoupInvalideException("Plus de coups");
            }

            return listeCoup.get(coupHasard);
        }
    }



    protected List<Coup> getListeCoup(){
       Tas tas=(Tas)plateau;
        List<Coup> lesCoups=new ArrayList<>();
        for (int i=1;i<=tas.getNbTas();i++)
        {
            if(tas.nbAllumettes(i)<=tas.getCoupMax()){
                for(int poss=1;poss<= tas.nbAllumettes(i);poss++){
                    Coup coup=new CoupNim(i,poss);
                    lesCoups.add(coup);
                }
            }
        }
        return lesCoups;

    }
    @Override
    protected void affichageDebutTour(Joueur joueur) {
        if(joueur instanceof Humain){
            super.affichageDebutTour(joueur);
        }

    }


}
