package vue;

public class IhmPuissanceOrdi extends IhmPuissance {

    /**
     * Affiche le nom du gagant en prenant l'IA comme joueur
     * @param nom correspond au nom du gagnant
     */
    @Override
    public void afficherGagnant(String nom) {
        if (nom.equals("IA")) {
            System.out.println(spacing);
            System.out.println(nom + " a gagné la partie\n");
        } else {
            super.afficherGagnant(nom);
        }
    }

    /**
     * Affiche si le joueur a plus de victoire que l'IA ou non et un message en conséquence
     * @param nom1 correspond au nom du joueur 1
     * @param nom2 correspond au nom du joueur 2
     * @param partiegagne1 correspond au nombre de parties gagnées par le joueur 1
     * @param partiegagne2 correspond au nombre de parties gagnées par le joueur 2
     * @param gagnant correspond au nom du gagnant de la partie ou ex-aequo en cas d'égalité
     */
    public void afficherGagnantJeu(String nom1, String nom2, int partiegagne1, int partiegagne2, String gagnant) {
        System.out.println(spacing);
        String s = "Nombre de victoire : \n " + nom1 + " : " + partiegagne1 + "\n " + nom2 + " : " + partiegagne2 + "\n \n";
        if (gagnant.equals("ex-aequo")) {
            s += "Vous êtes à " + gagnant;
        } else {
            if (gagnant.equals("IA")) {
                s += "Désolé, vous avez perdu";
            } else
                s += "Félicitations " + gagnant + ", vous avez gagné";
        }
        System.out.println(s);
    }

}


