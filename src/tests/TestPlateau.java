package tests;

import composants.Piece;
import composants.Plateau;
import grafix.interfaceGraphique.IG;

public class TestPlateau {
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
        //Placement des piece aleatoire
        Plateau plateau=new Plateau();
        Piece pieceHorsPlateau=plateau.placerPiecesAleatoierment();
        for (int i=0; i<7; i++) {
            for (int j=0; j<7; j++) {
                Piece piece = plateau.getPiece(i,j);
                IG.changerPiecePlateau(i,j,piece.getModelePiece(),piece.getOrientationPiece());
            }
        }
        IG.changerPieceHorsPlateau(pieceHorsPlateau.getModelePiece(),pieceHorsPlateau.getOrientationPiece());
        IG.miseAJourAffichage();
        IG.attendreClic();
        //calcul de l'ensemble des chemin entre la case (3,3) et les différente case du plateau
        int[][] longestPath = null;
        System.out.println("La liste des chemins trouvés à partir de la case (3,3) :");
        for (int i=0; i<7; i++) {
            for (int j=0; j<7; j++) {
                int[][] chemin = plateau.calculeChemin(3,3,i,j);
                if (chemin==null) {continue;}
                if (longestPath==null || chemin.length>longestPath.length) {longestPath=chemin;}
                System.out.print("Chemin entre les cases (3,3) et ("+i+","+j+") : ");
                for (int[] couple : chemin) {
                    System.out.print("("+couple[0]+","+couple[1]+") ");
                }
                System.out.println();
            }
        }
        //Affichage du plus long chemin
        if (longestPath == null) throw new AssertionError();
        for (int[] couple : longestPath) {
            IG.placerBilleSurPlateau(couple[0],couple[1],1,1,2);
        }
        //Procedure pour quitter
        message[2] = "Cliquez pour quitter ... ";
        IG.afficherMessage(message);
        IG.miseAJourAffichage();
        IG.attendreClic();
        IG.fermerFenetreJeu();
        System.exit(0);
    }
}
