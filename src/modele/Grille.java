package modele;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Grille extends Plateau{
    private final int taille = 7;
    private int nbJeton;
    private  Jeton[][] grille = new Jeton[taille][taille];
    private int[] dernierJeton = new int[2];

    public Grille(){ }

    /**
     * Teste si le coup souhaité par le joueur est réalisable et s'il est bien compris entre 1 et 7
     * si oui, on le coup est enregistré dans dernierJeton
     * @param leCoup correspond au coup saisit par le joueur
     * @throws CoupInvalideException si le coup saisit est invalide
     */
    @Override
    public void gererCoup(Coup leCoup) throws CoupInvalideException {
        CoupPuissance coupPuissance=(CoupPuissance) leCoup;
       int coup=coupPuissance.getColonne()-1;
        if(coup<0 || taille<=coup) {
            throw new CoupInvalideException("Nombre invalide");
        }
        for (int i=0; i<=taille; i++) {
            if (i==taille){
                throw new CoupInvalideException("Colonne complete");
            }
            if (grille[coup][i]==null){
                nbJeton++;
                grille[coup][i]=coupPuissance.getJeton();
                break;
            }
        }
    }


    /**
     * On crée une nouvelle grille identique a la première et en ensuite on appelle les méthodes servant a faire tourner la grille
     * @param sens correspond au sens de rotation
     * @throws CoupInvalideException car utilise la methode gererCoup
     */
    public void gererRotation(int sens) throws CoupInvalideException {
        Jeton[][] nouv = Arrays.copyOf(grille,taille);
        grille = new Jeton[taille][taille];
        if(sens==0) {
            rotationGauche(nouv);
        }
        else{
            rotationDroite(nouv);
        }
    }

    /**
     * On crée une nouvelle grille après la rotation vers la droite de la grille initiale
     * @param nouv correspond a la copie de la grille
     */
    private void rotationDroite(Jeton[][] nouv) throws CoupInvalideException {
        for(int c=taille-1;c>=0;c--){
            for(int l=taille-1;l>=0;l--){
                if(nouv[c][l]!=null) {
                    Coup coup=new CoupPuissance(l+1
                            , nouv[c][l]);
                        gererCoup(coup);
                }
            }
        }
    }

    /**
     * On crée une nouvelle grille après la rotation vers la gauche de la grille initiale
     * @param nouv correspond a la copie de la grille
     */
    private void rotationGauche(Jeton[][] nouv) throws CoupInvalideException {
        for(int c=0;c<taille;c++){
            for(int l=0;l<taille;l++){
                if(nouv[c][l]!=null) {
                    Coup coup=new CoupPuissance(taille-l, nouv[c][l]);
                        gererCoup(coup);
                }
            }
        }
    }

    /**
     * Teste les alignements des jetons dans les directions possibles afin de savoir s'il y en a 4 d'alignés ou non
     * @return un ensemble de jetons gagnants
     */

    public Set<Jeton> partieTerminee(){
        Set<Jeton> jetons = new HashSet<>();
        for (int x = 0; x < taille-1; x++) {
            for (int y = 0; y < taille - 1; y++) {
                dernierJeton[0] = x;
                dernierJeton[1] = y;
                if(grille[x][y]!=null) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int res = analyseVictoire(dernierJeton[0] + j, dernierJeton[1] + i, j, i)
                                    + analyseVictoire(dernierJeton[0] - j, dernierJeton[1] - i, -j, -i)
                                    + 1;
                            if (4 <= res)
                                jetons.add(grille[x][y]);
                        }
                    }
                }
            }
        }
        return jetons;
    }

    /**
     *
     * @param jx correspond à l'abscisse du jeton en cours d'analyse
     * @param jy correspond à l'ordonnée du jeton en cours d'analyse
     * @param dx correspond à l'abscisse du prochain jeton analysé
     * @param dy correspond à l'ordonnée du prochain jeton analysé
     * @return le nombre de jetons de même couleur dans une direction partant du jeton d'origine
     */
    private int analyseVictoire(int jx, int jy, int dx, int dy){
        if ((dx==0 && dy==0) || jx < 0 || jy < 0 || taille-1 < jx || taille-1 < jy || grille[jx][jy]==null)
            return 0;
        if (grille[dernierJeton[0]][dernierJeton[1]].equals(grille[jx][jy]))
            return 1 + analyseVictoire(jx+dx, jy+dy, dx, dy);
        return 0;
    }

    /**
     *
     * @return si la grille est remplie ou non
     */
    public boolean grilleRemplie(){
        return taille*taille <= nbJeton;
    }

    @Override
    public String toString() {
        String affichage="";
        for (int i=1;i<=taille;i++){
            affichage+=" "+i;
        }
        affichage+="\n";
        for (int i=taille-1; 0<=i; i--) {
            affichage+="|";
            for (int j=0; j<taille; j++) {
                if (grille[j][i]==null){
                    affichage+="_|";
                }
                else{
                    affichage+=grille[j][i].toString()+"|";
                }
            }
            affichage+="\n";
        }
        return affichage;
    }

    public Set<Jeton> testRotation(int sens) throws CoupInvalideException {
            gererRotation(sens);
            Set<Jeton> lesJetons=partieTerminee();
            if(lesJetons.size()==1) {
               return lesJetons;
        }
        return null;
    }


    public Grille constructionVirtuelle(){
        Grille virt=new Grille();
        virt.nbJeton=this.nbJeton;
        virt.grille=Arrays.copyOf(this.grille,taille);
        return virt;
    }

}
