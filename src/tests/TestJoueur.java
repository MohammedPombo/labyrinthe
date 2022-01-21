package tests;

import composants.Piece;
import composants.Plateau;
import grafix.interfaceGraphique.IG;
import joueurs.Joueur;

public class TestJoueur {
    //main
    public static void main(String[] args) {
        //initialisation des variable nécessaire (parametre, plateau, joueurs, ...)
        Object[] parametresJeu = IG.saisirParametres();
        Plateau plateau=new Plateau();
        Piece pieceHorsPlateau=plateau.placerPiecesAleatoierment();
        Joueur[] joueurs = Joueur.nouveauxJoueurs(parametresJeu);
        //Creation de la fenetre du jeu
        IG.creerFenetreJeu("TestJoueur Powered by IGlib24",joueurs.length);
        //placement des joueurs
        for (Joueur joueur : joueurs) {
            IG.changerNomJoueur(joueur.getNumJoueur(),joueur.getNomJoueur()+" ("+joueur.getCategorie()+")");
            IG.changerImageJoueur(joueur.getNumJoueur(),joueur.getNumeroImagePersonnage());
            IG.placerJoueurSurPlateau(joueur.getNumJoueur(),joueur.getPosLigne(),joueur.getPosColonne());
        }
        //placement des pieces du plateau
        for (int i=0; i<7; i++) {
            for (int j=0; j<7; j++) {
                Piece piece = plateau.getPiece(i,j);
                IG.changerPiecePlateau(i,j,piece.getModelePiece(),piece.getOrientationPiece());
            }
        }
        IG.changerPieceHorsPlateau(pieceHorsPlateau.getModelePiece(),pieceHorsPlateau.getOrientationPiece());
        //creation et affichage du message : cliquez pour continuer
        String[] message = new String[]{"", "", "", ""};
        message[0] = "";
        message[1] = "";
        message[2] = "Cliquez pour continuer ... ";
        message[3] = "";
        IG.afficherMessage(message);
        IG.rendreVisibleFenetreJeu();
        IG.attendreClic();
        //Faire deplacer a tour de role les joueur
        for (Joueur joueur : joueurs) {
            //affichage d'un message pour savoir c'est a qui de jouer
            message[1]="Au tour de "+joueur.getNomJoueur();
            if (joueur.getCategorie()=="Humain") {message[2]="Sélectionner une case...";}
            else {message[2]="...";}
            IG.afficherMessage(message);
            IG.miseAJourAffichage();
            //recuperation d'une case d'arrivé valide
            int[][] chemin=null;
            while (chemin==null) {
                int[] caseArrivee = joueur.choisirCaseArrivee(null);
                chemin = plateau.calculeChemin(joueur.getPosLigne(), joueur.getPosColonne(), caseArrivee[0], caseArrivee[1]);
            }
            //message pour patienter
            message[2]="...";
            IG.afficherMessage(message);
            IG.miseAJourAffichage();
            //deplacement du joueur avec délai d'attente pour animation (animation a revoir)
            for (int[] coord : chemin) {
                IG.placerBilleSurPlateau(coord[0],coord[1],1,1,joueur.getNumJoueur());
                IG.placerJoueurSurPlateau(joueur.getNumJoueur(),coord[0],coord[1]);
                IG.miseAJourAffichage();
                IG.pause(100); // attendre 100ms
            }
        }
        //message de fin
        message[1]="C'est terminé !";
        message[2]="Cliquez pour quitter ...";
        IG.afficherMessage(message);
        IG.miseAJourAffichage();
        IG.attendreClic();
        //fermeture du programme
        IG.fermerFenetreJeu();
        System.exit(0);
    }
}
