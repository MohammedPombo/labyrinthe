package partie;

import composants.Objet;
import composants.Piece;
import composants.Plateau;
import grafix.interfaceGraphique.IG;
import joueurs.Joueur;
import joueurs.JoueurOrdinateur;

public class Partie {
	static double version=0.0;


	private ElementsPartie elementsPartie; // Les éléments de la partie.

	/**
	 * 
	 * A Faire (02/06/2021 MC Finie)
	 * 
	 * Constructeur permettant de créer et d'initialiser une nouvelle partie.
	 */
	public Partie(){
		// Initialisation de la partie
		parametrerEtInitialiser();

		// On affiche l'ensemble des éléments
		Joueur[] joueurs=elementsPartie.getJoueurs();
		//placement des joueurs
		for (Joueur joueur : joueurs) {
			IG.changerNomJoueur(joueur.getNumJoueur(),joueur.getNomJoueur()+" ("+joueur.getCategorie()+")");
			IG.changerImageJoueur(joueur.getNumJoueur(),joueur.getNumeroImagePersonnage());
			IG.placerJoueurSurPlateau(joueur.getNumJoueur(),joueur.getPosLigne(),joueur.getPosColonne());
		}
		//placement des objets sur le plateau
		for (Objet objet : elementsPartie.getObjets()){
			IG.placerObjetPlateau(objet.getNumObjet(),objet.getPosLignePlateau(),objet.getPosColonnePlateau());
		}
		//affichage des objet a recuperer sous le nom des joueur
		for (Joueur joueur : joueurs){
			for (int i=0; i<joueur.getObjetsJoueur().length; i++){
				Objet objet = joueur.getObjetsJoueur()[i];
				IG.changerObjetJoueur(joueur.getNumJoueur(),objet.getNumObjet(),i);
			}
		}
		//placement des pieces du plateau
		for (int i=0; i<7; i++) {
			for (int j=0; j<7; j++) {
				Piece piece = elementsPartie.getPlateau().getPiece(i,j);
				IG.changerPiecePlateau(i,j,piece.getModelePiece(),piece.getOrientationPiece());
			}
		}
		IG.changerPieceHorsPlateau(elementsPartie.getPieceLibre().getModelePiece(),elementsPartie.getPieceLibre().getOrientationPiece());
		//creation et affichage du message : cliquez pour continuer
		String[] message = new String[]{"", "", "", ""};
		message[0] = "";
		message[1] = "";
		message[2] = "Cliquez pour commencer ... ";
		message[3] = "";
		IG.afficherMessage(message);

		IG.rendreVisibleFenetreJeu();
	}

	/**
	 * Méthode permettant de paramètrer et initialiser les éléments de la partie.
	 */
	private void parametrerEtInitialiser(){
		// Saisie des différents paramètres
		Object[] parametresJeu;
		parametresJeu=IG.saisirParametres();
		int nombreJoueurs= (Integer) parametresJeu[0];
		IG.creerFenetreJeu("- Version "+version, nombreJoueurs);
		// Création des joueurs
		Joueur[] joueurs=Joueur.nouveauxJoueurs(parametresJeu);
		// Création des éléments de la partie
		elementsPartie=new ElementsPartie(joueurs);
	}


	/**
	 * 
	 * A Faire (02/06/2021 MC En cours)
	 * 
	 * Méthode permettant de lancer une partie.
	 */
	public void lancer(){
		IG.attendreClic();
		int numJoueur=0;
		String[] message = new String[]{"", "", "", ""};
		while (true){
			Joueur joueur = elementsPartie.getJoueurs()[numJoueur];
			// Affichage d'un message disant c'est au tour de quelle joueur
			message[1]="Au tour de "+joueur.getNomJoueur();
			if (joueur.getCategorie().equals("Humain")) {message[2]="Orienter et introduire la piece libre";}
			else {message[2]="...";}
			IG.afficherMessage(message);
			IG.miseAJourAffichage();
			// Orientation de la piece libre
			int[] tab = joueur.choisirOrientationEntree(elementsPartie);
			elementsPartie.getPieceLibre().setOrientation(tab[0]);
			//insertion piece
			elementsPartie.insertionPieceLibre(tab[1]);
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
				if (!objet.surPlateau()) {continue;}
				IG.placerObjetPlateau(objet.getNumObjet(),objet.getPosLignePlateau(),objet.getPosColonnePlateau());
			}
			for (Joueur player : elementsPartie.getJoueurs()){
				IG.placerJoueurSurPlateau(player.getNumJoueur(),player.getPosLigne(),player.getPosColonne());
			}
			IG.pause(50);
			IG.deselectionnerFleche();
			IG.miseAJourAffichage();
			//deplacement du joueur
			if (joueur.getCategorie().equals("Humain")) {
				message[2]="Choisir la case d'arriver";
				IG.afficherMessage(message);
				IG.miseAJourAffichage();
			}
			int[][] chemin = null;
			while (chemin==null) {
				int[] caseArrivee = joueur.choisirCaseArrivee(elementsPartie);
				chemin = elementsPartie.getPlateau().calculeChemin(joueur.getPosLigne(),joueur.getPosColonne(),caseArrivee[0],caseArrivee[1]);
				IG.pause(50);
				IG.deselectionnerPiecePlateau();
			}
			int[][] cheminDetaille = elementsPartie.getPlateau().calculeCheminDetaille(chemin,joueur.getNumJoueur());
			if (joueur.getCategorie().equals("Humain")) {
				message[2]="Deplacement...";
				IG.afficherMessage(message);
				IG.miseAJourAffichage();
			}
			for (int[] coord : cheminDetaille){
				IG.placerBilleSurPlateau(coord[0],coord[1],coord[2],coord[3],joueur.getNumJoueur());
				IG.placerJoueurPrecis(joueur.getNumJoueur(),coord[0],coord[1],coord[2],coord[3]);
				IG.miseAJourAffichage();
				IG.pause(10);
			}
			for (int[] coord : cheminDetaille){
				IG.supprimerBilleSurPlateau(coord[0],coord[1],coord[2],coord[3]);
				IG.miseAJourAffichage();
			}
			joueur.setPosition(chemin[chemin.length-1][0],chemin[chemin.length-1][1]);
			//verifie si le joeur a atteint son objet a recuperer, si oui recupere l'objet...
			Objet objetArecupere = joueur.getObjetsJoueur()[joueur.getNombreObjetsRecuperes()];
			if (objetArecupere.getPosLignePlateau()==joueur.getPosLigne() && objetArecupere.getPosColonnePlateau()==joueur.getPosColonne()){
				IG.enleverObjetPlateau(objetArecupere.getPosLignePlateau(), objetArecupere.getPosColonnePlateau());
				IG.changerObjetJoueurAvecTransparence(joueur.getNumJoueur(),objetArecupere.getNumObjet(),joueur.getNombreObjetsRecuperes());
				objetArecupere.enleveDuPlateau();
				joueur.recupererObjet();
			}
			// si le joueur a gagner (ramasser tous les objet) alors mettre l'ecran de fin
			if (joueur.getNombreObjetsRecuperes()==joueur.getObjetsJoueur().length){
				IG.afficherGagnant(joueur.getNumJoueur());
				message[1]="Bravo "+joueur.getNomJoueur();
				message[2]="Vous avez gagner la partie !";
				message[3]="cliquez pour quitter";
				IG.afficherMessage(message);
				IG.miseAJourAffichage();
				IG.attendreClic();
				IG.fermerFenetreJeu();
				System.exit(0);
			}
			//sinon passe au joueur suivant
			numJoueur++;
			if (numJoueur>=elementsPartie.getNombreJoueurs()){numJoueur=0;}
		}
	}

	/**
	 * 
	 * Programme principal permettant de lancer le jeu.
	 * 
	 * @param args Les arguments du programmes.
	 */
	public static void main(String[] args) {
		while(true){
			Partie partie=new Partie();
			partie.lancer();
		}
	}

}
