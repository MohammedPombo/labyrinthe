package tests;

import composants.Objet;
import composants.Piece;
import composants.Plateau;
import grafix.interfaceGraphique.IG;
import joueurs.Joueur;
import partie.ElementsPartie;

public class  TestElementsPartie {
    public static void main(String[] args) {
        Object parametresJeu[];
        parametresJeu = IG.saisirParametres();
        int nbJoueurs = ((Integer) parametresJeu[0]).intValue();
        IG.creerFenetreJeu("- TestElementsPartie", nbJoueurs);
        Joueur joueurs[] = Joueur.nouveauxJoueurs(parametresJeu);
        ElementsPartie elementsPartie = new ElementsPartie(joueurs);
        Plateau plateau = elementsPartie.getPlateau();
        String[] message = new String[]{"", "", "", ""};
        message[0] = "";
        message[1] = "";
        message[2] = "Cliquez pour commencez ... ";
        message[3] = "";
        // création du plateau
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Piece piece = plateau.getPiece(i, j);
                IG.changerPiecePlateau(i, j, piece.getModelePiece(), piece.getOrientationPiece());
            }

        }
        IG.changerPieceHorsPlateau(elementsPartie.getPieceLibre().getModelePiece(), elementsPartie.getPieceLibre().getOrientationPiece());

        //création des objets
        for (Objet objets : elementsPartie.getObjets()) {
            IG.placerObjetPlateau(objets.getNumObjet(), objets.getPosLignePlateau(), objets.getPosColonnePlateau());
        }
        IG.miseAJourAffichage();
        //création des joueurs
        for (Joueur joueur : elementsPartie.getJoueurs()) {
            IG.placerJoueurSurPlateau(joueur.getNumJoueur(), joueur.getPosLigne(), joueur.getPosColonne());
        }
        for (int numJoueur = 0; numJoueur < nbJoueurs; numJoueur++) {
            String nomJoueur = (String) parametresJeu[numJoueur * 3 + 1];
            String categorieJoueur = (String) parametresJeu[numJoueur * 3 + 2];
            int numImageJoueur = (Integer) parametresJeu[numJoueur * 3 + 3];
            IG.changerNomJoueur(numJoueur, nomJoueur + "(" + categorieJoueur + ")");
            IG.changerImageJoueur(numJoueur, numImageJoueur);
        }
        //attribuation des objets au joueurs
        for (Joueur joueur : joueurs) {
            for (int i = 0; i < joueur.getObjetsJoueur().length; i++) {
                Objet objet = joueur.getObjetsJoueur()[i];
                IG.changerObjetJoueur(joueur.getNumJoueur(), objet.getNumObjet(), i);
            }

        }

        IG.afficherMessage(message);
        IG.rendreVisibleFenetreJeu();
        IG.attendreClic();
        for (int x=1;x<5;x++){

            message[0]="choisissez l'orientation";
            message[1]=" de la piece "+x;
            message[2]="et insérez la ";
            IG.afficherMessage(message);
            IG.miseAJourAffichage();
            int fleche=IG.attendreChoixEntree();
            elementsPartie.getPieceLibre().setOrientation(IG.recupererOrientationPieceHorsPlateau());
            elementsPartie.insertionPieceLibre(fleche);



            for (int i=0; i<7; i++){
                for (int j=0; j<7; j++){
                    Piece piece = elementsPartie.getPlateau().getPiece(i,j);
                    IG.changerPiecePlateau(i,j,piece.getModelePiece(),piece.getOrientationPiece());
                }
            }
            Piece pieceLibre = elementsPartie.getPieceLibre();
            IG.changerPieceHorsPlateau(pieceLibre.getModelePiece(),pieceLibre.getOrientationPiece());
            for (int i=0; i<7; i++){
                for (int j=0; j<7; j++){
                    IG.enleverObjetPlateau(i,j);
                }
            }
            for (Objet objet : elementsPartie.getObjets()){

                IG.placerObjetPlateau(objet.getNumObjet(),objet.getPosLignePlateau(),objet.getPosColonnePlateau());
            }
            for (Joueur player : elementsPartie.getJoueurs()){
                IG.placerJoueurSurPlateau(player.getNumJoueur(),player.getPosLigne(),player.getPosColonne());
            }

            IG.pause(50);
            IG.deselectionnerFleche();
            IG.miseAJourAffichage();


        }
        message[0]="C'est terminé ! ";
        message[1]="Cliquer pour quitter ...";
        message[2]="";
        IG.afficherMessage(message);
        IG.miseAJourAffichage();
        IG.attendreClic();
        IG.fermerFenetreJeu();
        System.exit(0);







    }
}