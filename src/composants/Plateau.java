package composants;

import java.util.*;

/**
 * Cette classe permet de gérer un plateau de jeu constitué d'une grille de pièces (grille de 7 lignes sur 7 colonnes).
 *
 */
public class Plateau {

	private Piece[][] plateau; // La grille des pièces.
	private Object Piece;

	/**
	 * A Faire (16/05/21 TN Finalisée)
	 * 
	 * Constructeur permettant de construire un plateau vide (sans pièces) et d'une taille de 7 lignes sur 7 colonnes.
	 */

	public Plateau() {
	plateau = new Piece[7][7];
	}

	/**
	 * A Faire (16/05/21 TN Finalisée)
	 *
	 * Méthode permettant de placer une pièce sur le plateau.
	 *
	 * @param piece La pièce à placer.
	 * @param lignePlateau La ligne du plateau sur laquelle sera placée la pièce (un entier entre 0 et 6).
	 * @param colonnePlateau La colonne du plateau sur laquelle sera placée la pièce (une entier entre 0 et 6).
	 */
	public void positionnePiece(Piece piece,int lignePlateau,int colonnePlateau){
		plateau[lignePlateau][colonnePlateau] = piece;
	}

	/**
	 * A Faire (16/05/21 TN Finalisée)
	 *
	 * Méthode retournant une pièce se trouvant sur le plateau à un emplacement spécifique.
	 *
	 * @param lignePlateau La ligne du plateau  (un entier entre 0 et 6).
	 * @param colonnePlateau La colonne du plateau (un entier entre 0 et 6).
	 * @return La pièce se trouvant sur la ligne lignePlateau et la colonne colonnePlateau. Si il n'y a pas de pièce, null est retourné.
	 */
	public Piece getPiece(int lignePlateau,int colonnePlateau){
		return plateau[lignePlateau][colonnePlateau];
	}

	/**
	 * 
	 * A Faire (16/05/21 TN Finalisée)
	 *  
	 * Méthode permettant de placer aléatoirment 49 pièces du jeu sur le plateau.
	 * L'orientation des pièces est aléatoire. Les pièces utilisées doivent être des nouvelles pièces générées à partir de la méthode Piece.nouvellesPieces.
	 * Parmi les 50 pièces du jeu, la pièce qui n'a pas été placée sur le plateau est retournée par la méthode.
	 * 
	 * @return La seule pièce qui n'a pas été placée sur le plateau
	 */
	public Piece placerPiecesAleatoierment(){
		Piece[] listePiece = composants.Piece.nouvellesPieces();
		int compteur = 0 ;
		for(int i = 0 ; i < plateau.length ; i++){
			for(int j = 0 ; j < plateau[i].length ; j++){
				plateau[i][j] = listePiece[compteur];
				compteur++;
			}
		}
		return listePiece[49];
	}

	/**
	 * 
	 * Méthode utilitaire permettant de tester si les positions passées en paramètre sont les positions de deux cases différentes et adjacentes d'une grille de 7 lignes sur 7 colonnes.
	 *  
	 * @param posLigCase1 Un entier quelconque.
	 * @param posColCase1 Un entier quelconque.
	 * @param posLigCase2 Un entier quelconque.
	 * @param posColCase2 Un entier quelconque.
	 * @return true si les les positions passées en paramètre sont les positions de deux cases différentes et adjacentes d'une grille de 7 lignes sur 7 colonnes, false sinon.
	 */
	private static boolean casesAdjacentes(int posLigCase1,int posColCase1,int posLigCase2,int posColCase2){
		if ((posLigCase1<0)||(posLigCase2<0)||(posLigCase1>6)||(posLigCase2>6)) return false;
		if ((posColCase1<0)||(posColCase2<0)||(posColCase1>6)||(posColCase2>6)) return false;
		int distLigne=posLigCase1-posLigCase2;
		if (distLigne<0) distLigne=-distLigne;
		int distColonne=posColCase1-posColCase2;
		if (distColonne<0) distColonne=-distColonne;
		return (distLigne <= 1) && (distColonne <= 1) && ((distColonne + distLigne) == 1);
	}

	/**
	 * 
	 * A Faire (16/05/21 TN Finalisée)
	 * 
	 * Méthode permettant de tester si les positions passées en paramètre sont les positions de deux cases différentes et adjacentes 
	 * de la grille de jeu et qu'il est possible de passer d'une cas à l'autre compte tenu des deux pièces posées sur les deux cases du plateau.
	 * 
	 * @param posLigCase1 Un entier quelconque.
	 * @param posColCase1 Un entier quelconque.
	 * @param posLigCase2 Un entier quelconque.
	 * @param posColCase2 Un entier quelconque.
	 * @return true si les positions passées en paramètre sont les positions de deux cases différentes et adjacentes de la grille de jeu et qu'il est possible de passer d'une cas à l'autre compte tenu des deux pièces posées sur les deux cases du plateau, false sinon.
	 */
	private boolean passageEntreCases(int posLigCase1,int posColCase1,int posLigCase2,int posColCase2){
		if (!casesAdjacentes(posLigCase1,posColCase1,posLigCase2,posColCase2)){
			return false ;
		}
		Piece piece1 = plateau[posLigCase1][posColCase1];
		Piece piece2 = plateau[posLigCase2][posColCase2];
		if(piece1.getPointEntree(0) && piece2.getPointEntree(2) && posLigCase2==posLigCase1-1){
			return true ;
		}
		if(piece1.getPointEntree(2) && piece2.getPointEntree(0) && posLigCase1==posLigCase2-1){
			return true ;
		}
		if(piece1.getPointEntree(1) && piece2.getPointEntree(3) && posColCase2==posColCase1+1){
			return true ;
		}
		return piece1.getPointEntree(3) && piece2.getPointEntree(1) && posColCase2 == posColCase1 - 1;
	}

	/**
	 * 
	 * A Faire (18/05/21 MC Finie)
	 * 
	 * Méthode permettant de retourner un éventuel chemin entre deux cases du plateau compte tenu des pièces posées sur le plateau.
	 * Dans le cas où il n'y a pas de chemin entre les deux cases, la valeur null est retournée.
	 * Dans le cas où il existe un chemin, un chemin possible est retourné sous forme d'un tableau d'entiers à deux dimensions.
	 * La première dimension correspond aux cases du plateau à emprunter pour aller de la case de départ à la case d'arrivée.
	 * Dans ce tableau, chaque case est un tableau de deux entiers avec le premier entier qui correspond à la ligne de la case et
	 * le second entier qui correspond à la colonne de la case. La première case d'un chemin retourné correspond toujours 
	 * à la case (posLigCaseDep,posColCaseDep) et la dernière case correspond toujours à la case (posLigCaseArr,posColCaseArr).
	 *
	 * @param posLigCaseDep La ligne de la case de départ (un entier compris entre 0 et 6).
	 * @param posColCaseDep La colonne de la case de départ (un entier compris entre 0 et 6).
	 * @param posLigCaseArr La ligne de la case d'arrivée (un entier compris entre 0 et 6).
	 * @param posColCaseArr La colonne de la case d'arrivée (un entier compris entre 0 et 6).
	 * @return null si il n'existe pas de chemin entre les deux case, un chemin sinon.
	 */
	public int[][] calculeChemin(int posLigCaseDep,int posColCaseDep,int posLigCaseArr,int posColCaseArr){
		// ATTENTION : Cette methode contient des variable dont le nom contient piece sans pour autant être de la class Piece (se seront les coordonnée de la piece), voir le type de la variable
		//Initialisation du tableau des chemins
		ArrayList<ArrayList<int[]>> chemins = new ArrayList<>(); // une list de chemin, un chemin étant une list de piece, une piece est representer par ces coordonnée sur le plateau (getPiece pour obtenir la veritable piece)
		chemins.add(new ArrayList<>()); // ajout d'un chemin vide
		chemins.get(0).add(new int[]{posLigCaseDep,posColCaseDep}); //ajout d'une piece (ces coordonnée) dans le chemin 0
		//Calcul du chemin
		while (chemins.size()>0) {
			//Recherche du plus petit chemin
			ArrayList<int[]> shortestPath = chemins.get(0); // chemin le plus court
			for (ArrayList<int[]> chemin : chemins) {
				if (chemin.size()<shortestPath.size()) {
					shortestPath=chemin;
				}
			}
			//Si le chemin le plus court (shortestPath) arrive jusque la case d'arriver alors retourner shortestPath sous le bon format
			int[] lastPiece = shortestPath.get(shortestPath.size()-1); //Dernier piece du chemin
			if (lastPiece[0]==posLigCaseArr && lastPiece[1]==posColCaseArr) {
				int[][] resultat = new int[shortestPath.size()][2];
				for (int i=0; i<shortestPath.size(); i++) {
					int[] piece = shortestPath.get(i);
					resultat[i]=new int[]{piece[0],piece[1]};
				}
				return resultat;
			}
			//Prolongement du shortestPath
			int[][] coordForPieceAdj = {{-1,0},{1,0},{0,-1},{0,1}};
			for (int[] coordToAdd : coordForPieceAdj) { // sert a parcourir toute les piece candidate au prolongement
				//initialisation de la piece candidate
				int[] piece2 = {lastPiece[0]+coordToAdd[0],lastPiece[1]+coordToAdd[1]};
				if (!passageEntreCases(lastPiece[0],lastPiece[1],piece2[0],piece2[1])) {continue;} //Si la piece candidate n'est pas connecter a la lastPiece alors passer a la piece candidate suivante
				//Verifie que la piece candidate n'est pas déjà dans le chemin (que le serpent ne se mord pas la queue) sinon passe a la suivante
				boolean test=false;
				for (int i=0; i<shortestPath.size()-1; i++) {
					int[] piece3 = shortestPath.get(i);
					if(piece2[0]==piece3[0] && piece2[1]==piece3[1]) {test=true;break;}
				}
				if (test) {continue;}
				//Vérifie si la prochaine arrivé (piece2) n'est pas déjà une arrivé d'un autre chemin, le chemin le plus long des deux est supprimé !
				test=false;
				for (ArrayList<int[]> chemin : chemins) {
					if (chemin.get(chemin.size()-1)==piece2) {
						if (chemin.size()>shortestPath.size()+1) {chemins.remove(chemin);}
						else {test=true;}
						break;
					}
				}
				if (test) {continue;}
				//creation du nouveaux chemin
				ArrayList<int[]> chemin = new ArrayList<>(shortestPath);
				chemin.add(piece2);
				chemins.add(chemin);
			}
			chemins.remove(shortestPath);
		}
		return null;
	}



	/**
	 * 
	 * Méthode permettant de calculer un chemin détaillé (chemin entre sous-cases) à partir d'un chemin entre cases.
	 *  
	 * @param chemin Un tableau représentant un chemin de cases.
	 * @param numJoueur Le numéro du joueur pour lequel nous souaitons construire un chemin détaillé.
	 * 
	 * @return Le chemin détaillé correspondant au chemin de positions de pièces données en paramètre et pour le numéro de joueur donné.
	 */
	public int[][] calculeCheminDetaille(int[][] chemin,int numJoueur){
		if (chemin.length==1)
			return new int[0][0];
		int[][] cheminDetaille=new int[chemin.length*5][4];
		int pos=0;
		int col,lig,colS,ligS;
		for (int i=0;i<chemin.length-1;i++){
			lig=chemin[i][0];
			col=chemin[i][1];
			ligS=chemin[i+1][0];
			colS=chemin[i+1][1];
			if (ligS<lig){
				cheminDetaille[pos][0]=lig;
				cheminDetaille[pos][1]=col;
				cheminDetaille[pos][2]=1;
				cheminDetaille[pos++][3]=1;
				cheminDetaille[pos][0]=lig;
				cheminDetaille[pos][1]=col;
				cheminDetaille[pos][2]=0;
				cheminDetaille[pos++][3]=1;
				cheminDetaille[pos][0]=ligS;
				cheminDetaille[pos][1]=colS;
				cheminDetaille[pos][2]=2;
				cheminDetaille[pos++][3]=1;
			}
			else if (ligS>lig){
				cheminDetaille[pos][0]=lig;
				cheminDetaille[pos][1]=col;
				cheminDetaille[pos][2]=1;
				cheminDetaille[pos++][3]=1;
				cheminDetaille[pos][0]=lig;
				cheminDetaille[pos][1]=col;
				cheminDetaille[pos][2]=2;
				cheminDetaille[pos++][3]=1;
				cheminDetaille[pos][0]=ligS;
				cheminDetaille[pos][1]=colS;
				cheminDetaille[pos][2]=0;
				cheminDetaille[pos++][3]=1;
			} else if (colS<col){
				cheminDetaille[pos][0]=lig;
				cheminDetaille[pos][1]=col;
				cheminDetaille[pos][2]=1;
				cheminDetaille[pos++][3]=1;
				cheminDetaille[pos][0]=lig;
				cheminDetaille[pos][1]=col;
				cheminDetaille[pos][2]=1;
				cheminDetaille[pos++][3]=0;
				cheminDetaille[pos][0]=ligS;
				cheminDetaille[pos][1]=colS;
				cheminDetaille[pos][2]=1;
				cheminDetaille[pos++][3]=2;
			} else if (colS>col){
				cheminDetaille[pos][0]=lig;
				cheminDetaille[pos][1]=col;
				cheminDetaille[pos][2]=1;
				cheminDetaille[pos++][3]=1;
				cheminDetaille[pos][0]=lig;
				cheminDetaille[pos][1]=col;
				cheminDetaille[pos][2]=1;
				cheminDetaille[pos++][3]=2;
				cheminDetaille[pos][0]=ligS;
				cheminDetaille[pos][1]=colS;
				cheminDetaille[pos][2]=1;
				cheminDetaille[pos++][3]=0;
			}
		}
		cheminDetaille[pos][0]=chemin[chemin.length-1][0];
		cheminDetaille[pos][1]=chemin[chemin.length-1][1];
		cheminDetaille[pos][2]=1;
		cheminDetaille[pos++][3]=1;

		int debut=0;
		if ((numJoueur==0)&&((cheminDetaille[pos-2][2]==0)||(cheminDetaille[pos-2][3]==2))) pos--;
		if ((numJoueur==1)&&((cheminDetaille[pos-2][2]==2)||(cheminDetaille[pos-2][3]==2))) pos--;
		if ((numJoueur==2)&&((cheminDetaille[pos-2][2]==2)||(cheminDetaille[pos-2][3]==0))) pos--;
		if ((numJoueur==0)&&((cheminDetaille[1][2]==0)||(cheminDetaille[0][3]==2))) debut++;
		if ((numJoueur==1)&&((cheminDetaille[1][2]==2)||(cheminDetaille[0][3]==2))) debut++;
		if ((numJoueur==2)&&((cheminDetaille[1][2]==2)||(cheminDetaille[0][3]==0))) debut++;

		int[][] resultat=new int[pos-debut][4];
		for (int i=debut;i<pos;i++)
			System.arraycopy(cheminDetaille[i], 0, resultat[i - debut], 0, 4);
		return resultat;	
	}

	/**
	 * 
	 * Méthode retournant une copie du plateau avec des copies de ses pièces.
	 * 
	 * @return Une copie du plateau avec une copie de toutes ses pièces.
	 */
	public Plateau copy(){
		Plateau plateau=new Plateau();
		for (int ligne=0;ligne<7;ligne++)
			for (int colonne=0;colonne<7;colonne++)
				plateau.positionnePiece((this.plateau[ligne][colonne]).copy(), ligne, colonne);
		return plateau;
	}

}
