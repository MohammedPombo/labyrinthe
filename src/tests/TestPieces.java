package tests;

import composants.Piece;
import grafix.interfaceGraphique.IG;

public class TestPieces {
    //main
    public static void main(String[] args) {
        //création de la fenêtre de jeu
        Object[] parametres = IG.saisirParametres();
        int nbJoueurs = (Integer)parametres[0];
        IG.creerFenetreJeu("Démo Librairie IG version 1.9", nbJoueurs);
        //initialisation des messages
        String[] message = new String[]{"", "", "", ""};
        message[0] = "";
        message[1] = "";
        message[2] = "Cliquez pour continuer ... ";
        message[3] = "";
        IG.afficherMessage(message);
        IG.rendreVisibleFenetreJeu();
        IG.attendreClic();

        //Insertion des pièces avec la méthode nouvellesPieces
        Piece[] terrain=Piece.nouvellesPieces();
        for (int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                Piece piece= terrain[(i+1)*(j+1)-1]; // (i+1)*(j+1)-1 equivaut au compteur n suivant (int n=0; for (int i=0;i<7;i++) {for (int j=0;j<7;j++) {n++;}})
                IG.changerPiecePlateau(i,j,piece.getModelePiece(),piece.getOrientationPiece());
            }
        }
        IG.changerPieceHorsPlateau(terrain[49].getModelePiece(),terrain[49].getOrientationPiece());
        IG.miseAJourAffichage();

        //rotation des piece pour les 4 clic qui suivent
        for (int y =0;y<4;y++){
            IG.attendreClic();
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    terrain[(i+1)*(j+1)-1].rotation();
                    IG.changerPiecePlateau(i, j,terrain[(i+1)*(j+1)-1].getModelePiece(),terrain[(i+1)*(j+1)-1].getOrientationPiece());
                }
            }
            terrain[49].rotation();
            IG.changerPieceHorsPlateau(terrain[49].getModelePiece(),terrain[49].getOrientationPiece());
            System.out.println(terrain[49]);
            IG.miseAJourAffichage();
        }
        IG.attendreClic();
        IG.fermerFenetreJeu();
        System.exit(0);
    }
}