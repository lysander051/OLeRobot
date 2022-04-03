package vue;

public class IhmNimOrdi extends IhmNim{
    @Override
    public void afficherGagnant(String nom){
        if(nom.equals("IA")) {
            System.out.println(spacing);
            System.out.println(nom+ " a gagné la partie\n");
        }
        else {
            super.afficherGagnant(nom);
        }
    }

    public void afficherGagnantJeu(String nom1,String nom2,int partiegagne1,int partiegagne2,String gagnant){

        System.out.println(spacing);
        String s="Nombre de victoire : \n "+ nom1+" : "+partiegagne1+"\n "+ nom2+" : "+partiegagne2+"\n \n";
        if(gagnant.equals("ex-aequo")){
            s+="Vous êtes à "+gagnant;
        }
        else{
            if(gagnant.equals("IA")){
                s+="Désolé, vous avez perdu";
            }
            else
                s+="Félicitations "+gagnant+", vous avez gagné";
        }
        System.out.println(s);
    }

}
