package tests;

import grafix.interfaceGraphique.IG;

public class MaDemoIG {
    //main
    public static void main(String[] args) {
        //création de la fenêtre de jeu
        Object[] parametres = IG.saisirParametres();
        int nbJoueurs = (Integer)parametres[0];
        IG.creerFenetreJeu("Démo Librairie IG version 1.9", nbJoueurs);
        //remplir le plateau
        for(int i=0; i<7; i++){
            for(int j=0;j<7;j++) {
                IG.changerPiecePlateau(i, j, 2, 0);
            }
        }
        IG.changerPieceHorsPlateau(1, 0);
        //initialisation des joueur
        for (int numJoueur=0; numJoueur<nbJoueurs; numJoueur++) {
            String nomJoueur = (String) parametres[numJoueur*3+1];
            String categorieJoueur = (String) parametres[numJoueur*3+2];
            int numImageJoueur = (Integer) parametres[numJoueur*3+3];
            IG.changerNomJoueur(numJoueur,nomJoueur + "(" + categorieJoueur + ")");
            IG.changerImageJoueur(numJoueur,numImageJoueur);
            switch (numJoueur) {
                case 0 :
                    IG.placerJoueurSurPlateau(numJoueur,0,0);
                case 1 :
                    IG.placerJoueurSurPlateau(numJoueur,0,6);
                case 2 :
                    IG.placerJoueurSurPlateau(numJoueur,6,6);
            }
        }
        //Positionnement spécial des joueur 1 et 2
        IG.placerJoueurPrecis(0,3,0,1,0);
        IG.placerJoueurPrecis(1,3,6,1,2);
        //Positionner les objets sur le plateau
        int y = 0;
        int x = 0;
        int objet = 0;
        int nbObjetParJoueur = 6; // 3 joueur
        if (nbJoueurs == 2) {
            nbObjetParJoueur = 9; // 2 joueur
        }
        for (int numJoueur=0; numJoueur<nbJoueurs; numJoueur++) {
            for (int obj=0; obj<nbObjetParJoueur; obj++) {
                IG.changerObjetJoueur(numJoueur, objet, obj);
                IG.placerObjetPlateau(objet, x, y);
                if (y==6) {
                    x++;
                    y=-1;
                }
                y++;
                objet++;
            }
        }
        //initialisation des messages
        String[] message = new String[]{"", "", "", ""};
        message[0] = "";
        message[1] = "Bonjour !";
        message[2] = "Cliquez pour continuer ... ";
        message[3] = "";
        IG.afficherMessage(message);
        IG.rendreVisibleFenetreJeu();
        //Rotation des pièces à chaque clics
        int incr=1;
        int col = 0; // colonne du joueur 1
        int col1 =6; // colonne du joueur 2
        int avance = 0; // sous colonne du joueur 1
        int recule = 2; // sous colonne du joeur 2
        int tmp =0;
        x = 0;
        y =0;
        while(incr <5) {
            IG.miseAJourAffichage();
            IG.attendreClic();
            //rotation piece
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    IG.changerPiecePlateau(i, j, 2, incr);
                }
            }
            IG.changerPieceHorsPlateau(1, incr);
            //Déplacement joueurs + gestion billes
            IG.placerBilleSurPlateau(3, col, 1, avance, 0); // bille joueur 1
            IG.placerBilleSurPlateau(3, col1, 1, recule, 0); // bille joueur 2
            avance++;
            recule--;
            IG.placerJoueurPrecis(0,3,col,1,avance); //deplace joueur 1
            IG.placerJoueurPrecis(1,3,col1,1,recule); //deplace joueur 2
            if (avance == 3 && recule == -1) {
                avance = 0;
                recule = 2;
                col++;
                col1--;
            }
            //Gestion objets plateau
            IG.changerObjetJoueurAvecTransparence(0,tmp,tmp);
            IG.enleverObjetPlateau(x,y);
            y++;
            tmp++;
            message[1] = "Après le clic " + incr;
            IG.afficherMessage(message);
            incr++;
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                IG.changerPiecePlateau(i, j, 2, 0);
           }
        }
        IG.changerPieceHorsPlateau(1, 0);
        IG.miseAJourAffichage();
        IG.attendreClic();
        IG.afficherGagnant((Integer) parametres[3]); // prametres[3] recup le numero de l'image du joueur 1 (parametres[0*3+3])
        message[1] = "Cliquez sur une flèche";
        message[2] = "pour quitter !";
        IG.afficherMessage(message);
        IG.miseAJourAffichage();
        IG.fermerFenetreJeu();
        System.exit(0);
    }
}