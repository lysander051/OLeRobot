package modele;

import java.util.*;

public class Grille extends Plateau{
    private final int taille = 7;
    private int nbJeton;
    private Jeton[][] grille = new Jeton[taille][taille];
    private Jeton[][] grillecopie = new Jeton[taille][taille];
    private int[] dernierJeton = new int[2];
    private SortedMap<Integer, List<Integer>> strategieRobot = new TreeMap<>();

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
                dernierJeton[0]=coup;
                dernierJeton[1]=i;
                break;
            }
        }
    }

    public int[] nbAligneEssai (){
        int[] resultat=new int[9];

        int indice=0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int  res = analyseVictoire(dernierJeton[0] + j, dernierJeton[1] + i, j, i)
                        + analyseVictoire(dernierJeton[0] - j, dernierJeton[1] - i, -j, -i)
                        + 1;

                resultat[indice]=res;
                indice++;

            }
        }
        return resultat;
    }


    /*
    MAXIME : IL FAUT QUE TU RENVOIES UN RANDOM POUR LE COUP JOUE (
    genre dans score 5 il y a 4 coup tu dois faire un random d'un des 4 coups
    */

    public Jeton[][] copyGrille(Jeton[][] j){
        Jeton[][] copie=new Jeton[8][8];
        for(int i=0;i<taille;i++){
            copie[i]=Arrays.copyOf(j[i],taille);
        }
        return copie;
    }

    public Coup gererCoupRobot(int rotationHumain, int rotationOrdinateur) throws CoupInvalideException{

        Jeton jaune = new Jeton("\u001B[33m●\u001B[0m");
        Jeton rouge = new Jeton("\u001B[31m●\u001B[0m");
        grillecopie= copyGrille(grille);
       // System.out.println("grille copie\n"+affichagetab(grillecopie));
       /* if (0<rotationOrdinateur) {
            for (int i = 0; i <= 1; i++) {
                grille = grillecopie;
                if(testRotation(i)!=null && testRotation(i).contains(jaune) ){
                    gererRotation(i);


                }
                    //retourne la rotation
            }

            grille copie = vraie grille
            grille = grille copie
            grille.gererCoup = grille + 1
            priority= nb de pion aligné avec le coup joué
            rotation à la fin
        }*/
        //dernierJeton[0]=0;
       // dernierJeton[1]=0;
        for (int a = 1; a <= taille; a++) {
            //System.out.println(i);
            CoupPuissance coup = new CoupPuissance(a, jaune);
            grille = copyGrille(grillecopie);
           // System.out.println("grille\n"+affichagetab(grille));
            try {
                gererCoup(coup);
            }
            catch (CoupInvalideException e){
                continue;
            }
          // System.out.println("grille apres coup\n"+affichagetab(grille));
           // System.out.println("grille copie\n"+affichagetab(grillecopie));
            int priority = nbAligne()*2-1;
            if(7<priority)
                priority=7;
          /*  if (0<rotationOrdinateur) {
                Set<Jeton> test=testRotation(0);
                System.out.println(test.contains(rouge));

                if(test.contains(rouge))
                    continue;
            }*/
            grille = copyGrille(grillecopie);
         //   System.out.println("grille nouvelle\n"+affichagetab(grille));
            try {
                gererCoup(coup);
            }
            catch (CoupInvalideException e){
                continue;
            }
           // System.out.println("grille apres coup\n"+affichagetab(grille));
           /* if (0<rotationOrdinateur) {
                Set<Jeton> test=testRotation(1);
                if(test.contains(rouge))
                    continue;
            }
           /* dernierJeton[0]=a-1;
            dernierJeton[1]= grille[a-1].length;*/
            List<Integer> liste=new ArrayList<>();
            if (strategieRobot.get(priority)!=null )
                liste = strategieRobot.get(priority);
            if(!liste.contains(coup.getColonne())){
                liste.add(coup.getColonne());
                strategieRobot.put((priority),liste);
            }

        }

        for (int i = 1; i <= taille; i++) {
            CoupPuissance coup = new CoupPuissance(i, rouge);
            grille = copyGrille(grillecopie);
            try {
                gererCoup(coup);
            }

            catch (CoupInvalideException e){
                continue;
            }
           // System.out.println("grille apres coup\n"+affichagetab(grille));
            int priority = nbAligne()*2-2;
            System.out.println("priority  "+priority);
            if(6<priority) priority=6;
           // if(priority<1) priority=1;
            if(priority<1) continue;

            /*if (0<rotationOrdinateur) {
                Set<Jeton> test=testRotation(0);
                if(test.contains(rouge) && priority!=6)
                    continue;
            }*/
            grille = copyGrille(grillecopie);
            try {
                gererCoup(coup);
            }
            catch (CoupInvalideException e){
                continue;
            }
          /*  if (0<rotationOrdinateur) {
                Set<Jeton> test=testRotation(1);
                if(test.contains(rouge) && priority!=6)
                    continue;
            }*/
           /* dernierJeton[0]=i-1;
            dernierJeton[1]= grille[i-1].length;*/
            List<Integer> liste=new ArrayList<>();
            if (strategieRobot.get(priority)!=null )
                liste = strategieRobot.get(priority);
            if(!liste.contains(coup.getColonne())){
                liste.add(coup.getColonne());
                strategieRobot.put((priority),liste);
            }
        }
        System.out.println("haha");
        //System.out.println(strategieRobot);
        // affichage map
        for (int i : strategieRobot.keySet()){
            System.out.println(i+"  "+strategieRobot.get(i).toString());
        }
        int indice=7;
        while(indice>0)
        {
            if(strategieRobot.get(indice)!=null){
                List<Integer> listeCoup=strategieRobot.get(indice);
                Random rand = new Random();
                int coupHasard = rand.nextInt(listeCoup.size());
                CoupPuissance coupAJoue=new CoupPuissance(listeCoup.get(coupHasard),jaune);
                listeCoup.remove(coupHasard);
                if(CoupAPrendre(rotationHumain,rouge))
                    return coupAJoue;

                if(listeCoup.size()==0){
                    indice--;
                }

            }
            else {
                indice--;
            }


        }

       return null;
    }

    public boolean CoupAPrendre (int nbRotHumain,Jeton rouge) throws CoupInvalideException {
        if(nbRotHumain>0) {
            for (int i = 0; i < 2; i++) {
                Set<Jeton> test=testRotation(i);
                if(test.contains(rouge))
                    return false;
                }
            }
        return true;

    }

    public String affichagetab(Jeton[][]a){
        String affichage="";
        for (int i=1;i<=taille;i++){
            affichage+=" "+i;
        }
        affichage+="\n";
        for (int i=taille-1; 0<=i; i--) {
            affichage+="|";
            for (int j=0; j<taille; j++) {
                if (a[j][i]==null){
                    affichage+="_|";
                }
                else{
                    affichage+=a[j][i].toString()+"|";
                }
            }
            affichage+="\n";
        }
        return affichage;
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
                if (grille[x][y] != null) {
                    int res = nbAligne();
                    if (4 <= res) {
                        jetons.add(grille[x][y]);
                        break;
                    }
                }

            }
        }
        return jetons;
    }

    public int nbAligne (){
       int resMax=0;


        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               int  res = analyseVictoire(dernierJeton[0] + j, dernierJeton[1] + i, j, i)
                        + analyseVictoire(dernierJeton[0] - j, dernierJeton[1] - i, -j, -i)
                        + 1;

                if(res>resMax){
                    resMax=res;
                }

            }
        }
        return resMax;
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
            Set<Jeton> lesJetons=new HashSet<>();
            if(partieTerminee().size()==1) {
               lesJetons=partieTerminee();
        }
       return lesJetons;
    }


    public Grille constructionVirtuelle(){
        Grille virt=new Grille();
        virt.nbJeton=this.nbJeton;
        virt.grille=Arrays.copyOf(this.grille,taille);
        return virt;
    }

}
