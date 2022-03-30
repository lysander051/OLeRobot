package controleur;

import modele.*;
import vue.Ihm;
import vue.IhmPuissance;

import java.util.List;
import java.util.Set;

public class ControleurPuissanceOrdi extends ControleurPuissance{


    public ControleurPuissanceOrdi(Ihm ihm) {
        super(ihm);
    }

    @Override
    protected void enregistrementNom(){
        joueur1=new Humain(ihm.demanderNom(1));
        joueur2=new Ordinateur();

    }
}
