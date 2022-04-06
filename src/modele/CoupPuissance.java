package modele;

public class CoupPuissance extends Coup{
    private int colonne;
    private Jeton jeton;

    /**
     * Initialise un coup du puissance 4
     * @param colonne est la colonne dans laquelle sera le jeton
     * @param jeton est le jeton du joeur
     */
    public CoupPuissance(int colonne, Jeton jeton) {
        this.colonne = colonne;
        this.jeton = jeton;
    }

    /**
     * Permet de récupérer la colonne
     * @return la colonne
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Permet de récupérer le jeton
     * @return le jeton
     */
    public Jeton getJeton() {
        return jeton;
    }

    @Override
    public String toString(){
        return Integer.toString(colonne);
    }
}
