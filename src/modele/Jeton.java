package modele;

import java.util.Objects;

public class Jeton {
    private String couleur;

    /**
     * Initialise le jeton
     * @param couleur la couleur du jeton
     * */
    public Jeton (String couleur){
        this.couleur=couleur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jeton jeton = (Jeton) o;
        return couleur.equals(jeton.couleur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couleur);
    }

    @Override
    public String toString() {
        return couleur;
    }
}
