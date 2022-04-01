package main;

import controleur.*;
import vue.*;

import java.util.HashSet;
import java.util.Set;

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
                controleur=new ControleurPuissanceOrdi(ihm);
            }

        }
       /* Set<Integer> a=new HashSet<>();
        System.out.println(a.contains(2));

        System.out.println(controleur.fonction());
        if(controleur.fonction()!=null && !controleur.fonction().contains(2)){
            System.out.println("haha");
        }
        else{
            System.out.println("merde");
        }

        */
        controleur.jouer();

    }


}
