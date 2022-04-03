package main;

import controleur.*;
import vue.*;

public class Main {
    public static void main(String[] args) {
      int jeu=Ihm.selectionJeu();
        int mode=Ihm.selectionMode();
        Controleur controleur;
        Ihm ihm;
        if (jeu == 1){
            if(mode==1) {
                ihm=new IhmNim();
                controleur = new ControleurNim(ihm);
            }
            else{
                ihm=new IhmNimOrdi();
                controleur = new ControleurNimOrdi(ihm);
            }
        }
        else {
            if(mode==1){
                ihm=new IhmPuissance();
                controleur=new ControleurPuissance(ihm);
            }
            else{
                ihm=new IhmPuissanceOrdi();
                controleur=new ControleurPuissanceOrdi(ihm);
            }
        }
        controleur.jouer();
    }
}
