package controleur;

import modele.*;
import vue.Ihm;
import vue.IhmPuissance;
import java.util.*;

public class ControleurPuissance extends Controleur{
    protected final Map<Joueur, Jeton> jetonDuJoueur=new HashMap<>();
    protected final Map<Joueur,Integer> nbRotation=new HashMap<>();

    public ControleurPuissance(Ihm ihm) {
        super(ihm);
    }

    @Override
    protected void enregistrementNom() {
        joueur1=new Humain(ihm.demanderNom(1));
        joueur2=new Humain(ihm.demanderNom(2));
    }

    /**
     * On redéfinit la méthode initJeu pour le puissance 4
     */
    @Override
    protected  void initJeu(){
        enregistrementNom();
    }

    /**
     * On redéfinit la méthode initialisationPartie pour le puissance 4
     * on associe à un joueur un couleur de jeton
     * on demande si on veut jouer le jeu avec une contrainte ou non
     */
    @Override
    protected void initialisationPartie(){
        plateau =new Grille();
        initialisationJoueur();
        String ROUGE="\u001B[31m●\u001B[0m";
        String JAUNE="\u001B[33m●\u001B[0m";
        jetonDuJoueur.put(joueur1,new Jeton (ROUGE));
        jetonDuJoueur.put(joueur2,new Jeton(JAUNE));
        int nbRotAutorise=-1;
        if(((IhmPuissance)ihm).demanderAjoutContrainte()){
            nbRotAutorise=4;
        }
        nbRotation.put(joueur1,nbRotAutorise);
        nbRotation.put(joueur2,nbRotAutorise);
        partie();
    }

    /**
     * On redéfinit la méthode affichageDebutTour pour le puissance 4
     * @param joueur est le joueur courant
     */
    @Override
    protected void affichageDebutTour(Joueur joueur) {
        ihm.afficherEtat(plateau.toString());
        ihm.afficherTour(jetonDuJoueur.get(joueur).toString()+" "+joueur.getNom());
    }

    @Override
    protected Coup getCoupJoueur(Joueur joueur){
        if(nbRotation.get(joueur)>=0){
            int choix=((IhmPuissance)ihm).choixMouvement(nbRotation.get(joueur));
            if(choix==1/*avec*/){
                return new CoupPuissance(-2,jetonDuJoueur.get(joueur));
                // -2 veut dire le joueur joue une rotation
            }
        }
        List<Integer> coup=(ihm.demanderCoup());
        Coup c=new CoupPuissance(coup.get(0),jetonDuJoueur.get(joueur));
        return c;
    }

    /**
     * On redéfinit la méthode traiterCoup pour le puissance 4
     * @param joueur est le joueur qui vient de jouer
     * @throws CoupInvalideException si le coup est invalide
     */
    @Override
    protected void traiterCoup (Joueur joueur)  throws CoupInvalideException {
        if(nbRotation.get(joueur)>=0){
            int choix=((IhmPuissance)ihm).choixMouvement(nbRotation.get(joueur));
            if(choix==1/*avec*/){
                traiterCoupavecRotation(joueur);
                return;
            }
        }
        List<Integer> coup=(ihm.demanderCoup());
        Coup c=new CoupPuissance(coup.get(0),jetonDuJoueur.get(joueur));
        plateau.gererCoup(c);
    }

    @Override
    protected void affichageFinTour(Joueur j,Coup coup) {
        CoupPuissance c=(CoupPuissance) coup;
        String s="une rotation";
        if(c.getColonne()>0) {
            s = Integer.toString(c.getColonne());
        }
        else{
            if(c.getColonne()==-1){
                s+=" à droite";
            }
            else{
                s+=" à gauche";
            }
        }
        ihm.afficherLeCoupJoue(j.getNom(),s);
    }

    /**
     * On appelle cette méthode si le joueur souhaite faire pivoter la grille dans le sens de rotation qu'il souhaite
     * @param joueur est le joueur qui va faire tourner la grille
     * @throws CoupInvalideException si le nombre de rotation du joueur est epuisé
     */
    private void traiterCoupavecRotation (Joueur joueur) throws CoupInvalideException {
        if(nbRotation.get(joueur)<=0){
            throw new CoupInvalideException("Vous ne pouvez plus faire une rotation");
        }
        else{
            int nbRot=nbRotation.get(joueur);
            nbRotation.put(joueur,--nbRot);
            int sens=((IhmPuissance)ihm).sensRotation();
            ((Grille)plateau).gererRotation(sens);
        }
    }

    /**
     * On redéfinit la méthode partie pour le puissance 4
     */
    @Override
    protected void partie(){
        Set<Jeton> lesJetonsGagnants;
        do{
            tour();
            lesJetonsGagnants= ((Grille)plateau).partieTerminee();
        }
        while(lesJetonsGagnants.isEmpty() && !((Grille)plateau).grilleRemplie());
        ihm.afficherEtat(plateau.toString());
        if (lesJetonsGagnants.size()==1)
        {
            ihm.afficherGagnant(gagnantPartie(lesJetonsGagnants).getNom());
        }
        else
            ((IhmPuissance)ihm).afficherPartieNulle();
    }

    /**
     * @param j jeton gagnant
     * @return le joueur qui a le jeton passé en paramètre
     */
    protected Joueur gagnantPartie(Set<Jeton> j) {
        for ( Jeton jeton : j ){
            for (Joueur jj : jetonDuJoueur.keySet()){
                if (jetonDuJoueur.get(jj).equals(jeton)){
                    jj.gagnePartie();
                    return jj;
                }
            }
        }
        return null;
    }

}
