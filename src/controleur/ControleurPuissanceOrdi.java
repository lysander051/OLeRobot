package controleur;

import modele.Coup;
import modele.CoupInvalideException;
import modele.CoupPuissance;
import modele.Grille;
import modele.Joueur;
import modele.Humain;
import modele.Ordinateur;
import modele.Jeton;
import vue.Ihm;
import java.util.*;

public class ControleurPuissanceOrdi extends ControleurPuissance{

    public ControleurPuissanceOrdi(Ihm ihm) {
        super(ihm);
    }

    /**
     * On redéfinit la méthode enregistrementNom pour le jeu du puissance 4 a un joueur contre l'IA
     */
    @Override
    protected void enregistrementNom(){
        joueur1=new Humain(ihm.demanderNom(1));
        joueur2=new Ordinateur();
    }

    /**
     * On redéfinit la méthode traiterCoup pour le puissance 4 a 1 joueur contre l'IA
     * @param joueur est le joueur qui vient de jouer
     * @throws CoupInvalideException si le coup saisi est invalide
     */
    @Override
    protected void traiterCoup (Joueur joueur)  throws CoupInvalideException {
       Coup coup;
        if (joueur instanceof Humain) {
            super.traiterCoup(joueur);
        } else {
            Grille grilleVirtuelle=constructionVirtuelle();
            if(nbRotation.get(joueur)>0) {
                int sens= gererRotationOrdi(grilleVirtuelle);
                if(sens!=-2){
                    ((Grille) plateau).gererRotation(sens);
                    coup=new CoupPuissance(-sens,jetonDuJoueur.get(joueur));
                    affichageFinTour(joueur,coup);
                    return;
                }
            }
            grilleVirtuelle=constructionVirtuelle();
            coup= gererCoupOrdi(grilleVirtuelle);
            plateau.gererCoup(coup);
            affichageFinTour(joueur,coup);
        }
    }

    /**
     * On redéfinit la méthode affichageDebutTour pour le puissance 4 à 1 joueur contre l'IA
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
     * obtenir un coup de l'ordinateur
     * on teste pour chaque position le score qu'implique cette position
     * on choisit au hasard un coup parmi la liste d'un score partant du score le plus haut
     * on vérifie si le joueur humain ne gagne pas en faisant une rotation
     * si le joueur humain peut gagner, on choisit un autre coup
     * s'il n'y a plus de coup donc le joueur humain gagne n'importe le coup joué
     * alors on reprend le premier coup choisi
     * @param grilleVirtuelle une grille virtuelle pour tester les coups
     * @return un coup
     * */
    private Coup gererCoupOrdi(Grille grilleVirtuelle) throws CoupInvalideException{
        SortedMap<Integer, List<Integer>> strategieRobot = new TreeMap<>();
        Jeton jaune = jetonDuJoueur.get(joueur2);
        Jeton rouge = jetonDuJoueur.get(joueur1);
       Jeton[][] grillecopie=grilleVirtuelle.copierGrille();

        for (int a = 1; a <= grilleVirtuelle.getTaille(); a++) {
            CoupPuissance coup = new CoupPuissance(a, jaune);
            grilleVirtuelle.setGrille(grillecopie);
            try {
                grilleVirtuelle.gererCoup(coup);
            }
            catch (CoupInvalideException e){
                continue;
            }
            int priority = grilleVirtuelle.nbAligne()*2-1;
            if(7<priority)
                priority=7;

            List<Integer> liste=new ArrayList<>();
            if (strategieRobot.get(priority)!=null )
                liste = strategieRobot.get(priority);
            if(!liste.contains(coup.getColonne())){
                liste.add(coup.getColonne());
                strategieRobot.put((priority),liste);
            }
        }

        for (int i = 1; i <= grilleVirtuelle.getTaille(); i++) {
            CoupPuissance coup = new CoupPuissance(i, rouge);
            grilleVirtuelle.setGrille(grillecopie);
            try {
                grilleVirtuelle.gererCoup(coup);
            }

            catch (CoupInvalideException e){
                continue;
            }
            int priority = grilleVirtuelle.nbAligne()*2-2;
            if(6<priority) priority=6;
            if(priority<1) continue;

            List<Integer> liste=new ArrayList<>();
            if (strategieRobot.get(priority)!=null )
                liste = strategieRobot.get(priority);
            if(!liste.contains(coup.getColonne())){
                liste.add(coup.getColonne());
                strategieRobot.put((priority),liste);
            }
        }
        grilleVirtuelle.setGrille(grillecopie);
        int coupSauvegarde=-1;
        int first=0;
        int indice=7;
        //System.out.println(strategieRobot);
        while(indice>0)
        {
            if(strategieRobot.get(indice)!=null){
                grilleVirtuelle.setGrille(grillecopie);
                List<Integer> listeCoup=strategieRobot.get(indice);
                Random rand = new Random();
                int coupHasard = rand.nextInt(listeCoup.size());
                if(first==0){
                    coupSauvegarde=listeCoup.get(coupHasard);
                    first=1;
                }
                CoupPuissance coupAJoue=new CoupPuissance(listeCoup.get(coupHasard),jaune);
                listeCoup.remove(coupHasard);
                grilleVirtuelle.gererCoup(coupAJoue);
                if(coupAPrendre(grilleVirtuelle)) {
                    return coupAJoue;
                }
                if(listeCoup.size()==0){
                    indice--;
                }
            }
            else {
                indice--;
            }
        }
        return new CoupPuissance(coupSauvegarde,jaune);
    }

    /**
     * on regarde si le joueur humain peut gagner en faisant une rotation s'il peut le faire
     * @param grilleVirtuelle pour tester les rotations
     * @return true si le joueur humain ne peut pas gagner false sinon
     * */
    private boolean coupAPrendre (Grille grilleVirtuelle) throws CoupInvalideException {
        if(nbRotation.get(joueur1)>0) {
            for (int i = 0; i < 2; i++) {
                Set<Jeton> test=grilleVirtuelle.testRotation(i);
                if(test.contains(jetonDuJoueur.get(joueur1)))
                    return false;
            }
        }
        return true;
    }

    /**
     * @return une copie de la grille
     */
    private Grille constructionVirtuelle(){
        Grille vraiGrille=(Grille) plateau;
        Jeton[][] copie= vraiGrille.copierGrille();
        Grille virt=new Grille();
        virt.setNbJeton(vraiGrille.getNbJeton());
        virt.setGrille(copie);
        return virt;
    }

    /**
     * gerer la rotation de l'ordinateur
     * @param grilleVirtuelle pour tester les rotations
     * @return -2 si il ne peut pas gagner en faisant une rotation sinon le sens du rotation ( 0 ou 1)
     * */
    private int gererRotationOrdi(Grille grilleVirtuelle) throws CoupInvalideException {
        int sens=-2;
        Jeton jaune =jetonDuJoueur.get(joueur2);
       Jeton[][] grillecopie = grilleVirtuelle.copierGrille();
        for (int i = 0; i <= 1; i++) {
            grilleVirtuelle.setGrille(grillecopie);
            if (grilleVirtuelle.testRotation(i).contains(jaune)) {
                return i;
            }
        }
        return sens;
    }

}
