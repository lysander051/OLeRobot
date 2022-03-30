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
            ihm=new IhmNim();
            if(mode==1) {
                controleur = new ControleurNim(ihm);
            }
            else{
                controleur = new ControleurNimConsole(ihm);
            }
        }
        else {
            ihm=new IhmPuissance();
            if(mode==1){
                controleur=new ControleurPuissance(ihm);
            }
            else{
                controleur=new ControleurPuissance(ihm);
            }

        }
        controleur.jouer();

    }


}
