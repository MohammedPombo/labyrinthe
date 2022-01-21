package partie;

import composants.Objet;
import composants.Piece;
import composants.Plateau;
import composants.Utils;
import joueurs.Joueur;

import java.util.Arrays;

/**
 *
 * Cette classe permet de représenter un ensemble d'élements composant une partie de jeu.
 *
 */
public class ElementsPartie {

	private Joueur[] joueurs; 	// Les joueurs de la partie.
	private Objet[] objets; 	// Les 18 objets de la partie dans l'ordre de leurs numéros.
	private Plateau plateau; 	// Le plateau des pièces.
	private Piece pieceLibre; 	// La pièce libre.
	private int nombreJoueurs; 	// Le nombre de joueurs.

	/**
	 *
	 * A Faire (de TN le 01/06/2021 Fonctionnel)
	 *
	 * Constructeur permettant de générer et d'initialiser l'ensemble des éléments d'une partie (sauf les joueurs qui sont donnés en paramètres).
	 *
	 * Un plateau est créé en placant 49 pièces de manière aléatoire (utilisation de la méthode placerPiecesAleatoierment de la classe Plateau).
	 * La pièce restante (celle non présente sur le plateau) est affectée à la pièce libre.
	 * Les 18 objets sont créés avec des positions aléatoires sur le plateau (utilisation de la méthode Objet.nouveauxObjets)
	 * et distribuées aux différents joueurs (utilisation de la méthode attribuerObjetsAuxJoueurs).
	 *
	 * @param joueurs Les joueurs de la partie. Les objets des joueurs ne sont pas encore attribués (c'est au constructeur de le faire).
	 */
	public ElementsPartie(Joueur[] joueurs) {

		this.joueurs=joueurs ;
		objets = Objet.nouveauxObjets();
		plateau = new Plateau();
		pieceLibre = plateau.placerPiecesAleatoierment();
		nombreJoueurs = this.joueurs.length;
		attribuerObjetsAuxJoueurs();
	}

	/**
	 * Un simple constructeur.
	 *
	 * @param joueurs Les joueurs de la partie.
	 * @param objets Les 18 objets de la partie.
	 * @param plateau Le plateau de jeu.
	 * @param pieceLibre La pièce libre (la pièce hors plateau).
	 */
	public ElementsPartie(Joueur[] joueurs,Objet[] objets,Plateau plateau,Piece pieceLibre) {
		this.joueurs=joueurs;
		nombreJoueurs=joueurs.length;
		this.objets=objets;
		this.plateau=plateau;
		this.pieceLibre=pieceLibre;
	}

	/**
	 * A Faire (de TN le 01/06/2021 Fonctionnel)
	 *
	 * Méthode permettant d'attribuer les objets aux différents joueurs de manière aléatoire.
	 */
	private void attribuerObjetsAuxJoueurs(){
		int compteur=0;
		for(int i = 0 ; i < nombreJoueurs ;i++){
			Objet[] objetJoueur = new Objet[18/nombreJoueurs];
			for(int j = 0; j<(18/nombreJoueurs); j++){
				objetJoueur[j] = objets[compteur];
				compteur++;
			}
			joueurs[i].setObjetsJoueur(objetJoueur);
		}
	}

	/**
	 * A Faire (de TN le 01/06/2021 Fonctionnel)
	 *
	 * Méthode permettant de récupérer les joueurs de la partie.
	 * @return Les joueurs de la partie.
	 */
	public Joueur[] getJoueurs() {
		return joueurs;
	}


	/**
	 * A Faire (de TN le 01/06/2021 Fonctionnel)
	 *
	 * Méthode permettant de récupérer les pièces de la partie.
	 * @return Les objets de la partie.
	 */
	public Objet[] getObjets() {
		return objets;
	}


	/**
	 * A Faire (de TN le 01/06/2021 Fonctionnel)
	 *
	 * Méthode permettant de récupérer le plateau de pièces de la partie.
	 * @return Le plateau de pièces.
	 */
	public Plateau getPlateau() {
		return plateau;
	}


	/**
	 * A Faire (de TN le 01/06/2021 Fonctionnel)
	 *
	 * Méthode permettant de récupérer la pièce libre de la partie.
	 * @return La pièce libre.
	 */
	public Piece getPieceLibre() {
		return pieceLibre;
	}


	/**
	 * A Faire (de TN le 01/06/2021 Fonctionnel)
	 *
	 * Méthode permettant de récupérer le nombre de joueurs de la partie.
	 * @return Le nombre de joueurs.
	 */
	public int getNombreJoueurs() {
		return nombreJoueurs;
	}


	/**
	 * Fait (de MC le 02/06/2021 Finie et Fonctionnel)
	 *
	 * Méthode modifiant les différents éléments de la partie suite à l'insertion de la pièce libre dans le plateau.
	 *
	 * @param choixEntree L'entrée choisie pour réaliser l'insertion (un nombre entre 0 et 27).
	 */
	public void insertionPieceLibre(int choixEntree){
		// recuperation des coordonnée de la case correspondante et de la direction (sens) de la fleche
		int[] coord;
		int[] sens;
		if (choixEntree<7) {
			coord = new int[]{0, choixEntree%7};
			sens = new int[]{-1, 0};
		} else if(choixEntree<14){
			coord = new int[]{choixEntree%7, 6};
			sens = new int[]{0, 1};
		} else if(choixEntree<21){
			coord = new int[]{6, 6-(choixEntree%7)};
			sens = new int[]{1, 0};
		} else {
			coord = new int[]{6-(choixEntree%7), 0};
			sens = new int[]{0, -1};
		}
		// initialisation des bords des 2 boucle nécessaire a la recuperation des coordonnée des case a deplacer
		int debI;
		int debJ;
		int finI;
		int finJ;
		//limite premier boucle
		if (sens[0]==0) {debI=coord[0]; finI=coord[0];}
		else if(sens[0]==-1) {debI=6; finI=0;}
		else {debI=0; finI=6;}
		//limite deuxiéme boucle
		if (sens[1]==0) {debJ=coord[1]; finJ=coord[1];}
		else if(sens[1]==-1) {debJ=6; finJ=0;}
		else {debJ=0; finJ=6;}
		// recuperation des coordonnée des case a deplacer
		int[][] cases = new int[7][2];
		int index=0;
		for (int i = debI; i>=0 && i<=6; i+=sens[0]){
			for (int j=debJ; j>=0 && j<=6; j+=sens[1]){
				cases[index]=new int[]{i,j};
				index++;
				if (debJ==finJ){break;}
			}
			if (debI==finI){break;}
		}
		//deplacement des case
		Piece nouvellePieceLibre = plateau.getPiece(cases[0][0],cases[0][1]);
		for (int i=1; i<7; i++){
			plateau.positionnePiece(plateau.getPiece(cases[i][0],cases[i][1]),cases[i-1][0],cases[i-1][1]);
		}
		plateau.positionnePiece(pieceLibre,cases[6][0],cases[6][1]);
		pieceLibre=nouvellePieceLibre;
		//deplacement des objets
		for (Objet objet : objets){
			if (Arrays.equals(cases[0], new int[]{objet.getPosLignePlateau(), objet.getPosColonnePlateau()})){
				objet.positionneObjet(cases[6][0],cases[6][1]);
				continue;
			}
			for (int i=1; i<7; i++){
				if (Arrays.equals(cases[i], new int[]{objet.getPosLignePlateau(), objet.getPosColonnePlateau()})){
					objet.positionneObjet(cases[i-1][0],cases[i-1][1]);
				}
			}
		}
		//deplacement des joueur
		for (Joueur joueur : joueurs){
			if (Arrays.equals(cases[0], new int[]{joueur.getPosLigne(),joueur.getPosColonne()})){
				joueur.setPosition(cases[6][0],cases[6][1]);
				continue;
			}
			for (int i=1; i<7; i++){
				if (Arrays.equals(cases[i], new int[]{joueur.getPosLigne(),joueur.getPosColonne()})){
					joueur.setPosition(cases[i-1][0],cases[i-1][1]);
				}
			}
		}
	}

	/**
	 * Méthode retournant une copie.
	 *
	 * @return Une copie des éléments.
	 */
	public ElementsPartie copy(){
		Objet[] nouveauxObjets=new Objet[(this.objets).length];
		for (int i=0;i<objets.length;i++)
			nouveauxObjets[i]=(this.objets[i]).copy();
		Joueur[] nouveauxJoueurs=new Joueur[nombreJoueurs];
		for (int i=0;i<nombreJoueurs;i++)
			nouveauxJoueurs[i]=(this.joueurs[i]).copy(objets);
		Plateau nouveauPlateau=(this.plateau).copy();
		Piece nouvellePieceLibre=(this.pieceLibre).copy();
		return new  ElementsPartie(nouveauxJoueurs,nouveauxObjets,nouveauPlateau,nouvellePieceLibre);
	}


}
