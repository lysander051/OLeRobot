package controleur;

import modele.*;
import vue.Ihm;
import vue.IhmNim;
import java.util.List;

public class ControleurNim extends Controleur{

    public ControleurNim(Ihm ihm) {
        super(ihm);
    }

    /**
     * On enregistre le nombre de tas souhaité pour jouer le jeu de Nim
     */
    private void enregistrerNbTas(){
        int nbTas= ((IhmNim)ihm).demanderNbTas();
        plateau = new Tas(nbTas);
    }

    /**
     * On redéfinit la méthode initJeu pour le jeu de Nim
     * on enregistre le nombre de tas et les noms des joueurs
     */
    @Override
    protected void initJeu(){
        enregistrerNbTas();
        enregistrementNom();
    }
    @Override
    protected void enregistrementNom(){
        joueur1=new Humain(ihm.demanderNom(1));
        joueur2=new Humain(ihm.demanderNom(2));
    }

    /**
     * On redéfinit la méthode initialisationPartie pour le jeu de Nim
     * on définit un coup max qui est le nombre d'allumettes maximum à retirer ou 0 si on ne veut pas de nombre d'allumettes

     * */
    @Override
    protected void initialisationPartie() {
        ((Tas)plateau).initialiser();
        int coupMax=((IhmNim)ihm).demanderCoupMax();
        ((Tas)plateau).setCoupMax(coupMax);
        initialisationJoueur();
        partie();
    }

    @Override
    protected Coup getCoupJoueur(Joueur j){
        List<Integer> l=ihm.demanderCoup();
        CoupNim coup = new CoupNim(l.get(0),l.get(1));
        return coup;
    }
    /**
     * On redéfinit la méthode traiterCoup pour le jeu de Nim
     * @param joueur est le joueur courant
     * @throws CoupInvalideException si le coup saisi est incorrect
     */
    @Override
    protected void traiterCoup(Joueur joueur) throws CoupInvalideException {
       Coup coup=getCoupJoueur(joueur);
        plateau.gererCoup(coup);
        affichageFinTour(joueur,coup);
    }

    @Override
    protected void affichageFinTour(Joueur j,Coup coup) {
        CoupNim c=(CoupNim)coup;
        String s=c.getNumeroTas() +"  "+ c.getNbAllumettes();
        ihm.afficherLeCoupJoue(j.getNom(),s);

    }

    /**
     * On redéfinit la méthode affichageDebutTour pour le jeu de Nim
     * @param joueur est le joueur courant
     */
   @Override
   protected void affichageDebutTour(Joueur joueur) {
        ihm.afficherEtat(plateau.toString());
        ihm.afficherTour(joueur.getNom());
   }

    /**
     * @return le gagnant de la partie
     */
    private Joueur gagnantPartie() {
        joueurSuivant();
        Joueur gagnant=joueurSuivant();
        gagnant.gagnePartie();
        return gagnant;
    }

    /**
     * On redéfinit la méthode partie pour le jeu de Nim
     */
    @Override
    protected void partie() {
        while(!((Tas)plateau).partieTerminee()){
            tour();
        }
        ihm.afficherEtat(plateau.toString());
        ihm.afficherGagnant(gagnantPartie().getNom());
    }

}
