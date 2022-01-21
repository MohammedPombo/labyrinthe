package joueurs;

import composants.Objet;
import composants.Piece;
import composants.Utils;
import partie.ElementsPartie;

/**
 * 
 * Cette classe permet de représenter un joueur ordinateur de type T2.
 * 
 * @author Jean-François Condotta - 2021
 *
 */

public class JoueurOrdinateurT2 extends JoueurOrdinateur {

	/**
	 * Constructeur permettant de créer un joueur.
	 * 
	 * @param numJoueur Le numéro du joueur.
	 * @param nomJoueur Le nom du joueur.
	 * @param numeroImagePersonnage Le numéro de l'image représentant le joueur.
	 * @param posLignePlateau La ligne du plateau sur laquelle est positionnée le joueur.
	 * @param posColonnePlateau La colonne du plateau sur laquelle est positionnée le joueur.

	 */
	public JoueurOrdinateurT2(int numJoueur,String nomJoueur, int numeroImagePersonnage,int posLignePlateau,int posColonnePlateau) {
				super(numJoueur,nomJoueur, numeroImagePersonnage,posLignePlateau,posColonnePlateau);
	}

	@Override
	public String getCategorie() {
		return "OrdiType2";
	}

	@Override
	public int[] choisirOrientationEntree(ElementsPartie elementsPartie) {
		Objet objetARecup = getObjetsJoueur()[getNombreObjetsRecuperes()];
		// si objet déjà accesible alors ...
		if (elementsPartie.getPlateau().calculeChemin(getPosLigne(),getPosColonne(),objetARecup.getPosLignePlateau(),objetARecup.getPosColonnePlateau())!=null){
			for (int i=0; i<28; i++){
				for (int j=0; j<4; j++) {
					ElementsPartie elemPartieCopy = elementsPartie.copy();
					elemPartieCopy.getPieceLibre().setOrientation(j);
					elemPartieCopy.insertionPieceLibre(i);
					if (elemPartieCopy.getPlateau().calculeChemin(getPosLigne(), getPosColonne(), objetARecup.getPosLignePlateau(), objetARecup.getPosColonnePlateau()) != null) {
						return new int[]{j,i};
					}
				}
			}
			return new int[]{Utils.genererEntier(3),Utils.genererEntier(27)};
		}
		Piece piece1 = elementsPartie.getPlateau().getPiece(getPosLigne(), getPosColonne()); // piece où se situe le joueur
		Piece piece2 = elementsPartie.getPlateau().getPiece(objetARecup.getPosLignePlateau(), objetARecup.getPosColonnePlateau()); // piece où se situe l'objet a recup
		// si les 2 piece ne peuvent pas se connecter en étant cote a cote, alors ...
		if(!(piece1.getPointEntree(0) && piece2.getPointEntree(2) || piece1.getPointEntree(2) && piece2.getPointEntree(0) || piece1.getPointEntree(1) && piece2.getPointEntree(3) || piece1.getPointEntree(3) && piece2.getPointEntree(1))){
			// si la piece où se situe l'objet n'est pas sur un bord du plateau, alors la raprocher du bord le plus proche
			if (!(objetARecup.getPosLignePlateau()==0 || objetARecup.getPosLignePlateau()==6 || objetARecup.getPosColonnePlateau()==0 || objetARecup.getPosColonnePlateau()==6)){
				// si plus pres du bas du plateau
				if (6-objetARecup.getPosLignePlateau() < objetARecup.getPosLignePlateau() && 6-objetARecup.getPosLignePlateau() <= 6-objetARecup.getPosColonnePlateau() && 6-objetARecup.getPosLignePlateau() <= objetARecup.getPosColonnePlateau()){
					return new int[]{Utils.genererEntier(3),objetARecup.getPosColonnePlateau()};
				}
				// si plus pres du haut du plateau
				if (objetARecup.getPosLignePlateau() < 6-objetARecup.getPosLignePlateau() && objetARecup.getPosLignePlateau() <= 6-objetARecup.getPosColonnePlateau() && objetARecup.getPosLignePlateau() <= objetARecup.getPosColonnePlateau()){
					return new int[]{Utils.genererEntier(3),20-objetARecup.getPosColonnePlateau()};
				}
				// si plus pres de la droite du plateau
				if (6-objetARecup.getPosColonnePlateau() < objetARecup.getPosColonnePlateau() && 6-objetARecup.getPosColonnePlateau() <= 6-objetARecup.getPosLignePlateau() && 6-objetARecup.getPosColonnePlateau() <= objetARecup.getPosLignePlateau()){
					return new int[]{Utils.genererEntier(3),27-objetARecup.getPosLignePlateau()};
				}
				// sinon (plus pres de la gauche)
				return new int[]{Utils.genererEntier(3),7+objetARecup.getPosLignePlateau()};
			}
			// orienter la piece hors plateau de maniere a quelle puisse se connecter a la piece du joueur
			piece2 = elementsPartie.getPieceLibre();
			while (!(piece1.getPointEntree(0) && piece2.getPointEntree(2) || piece1.getPointEntree(2) && piece2.getPointEntree(0) || piece1.getPointEntree(1) && piece2.getPointEntree(3) || piece1.getPointEntree(3) && piece2.getPointEntree(1))){
				piece2.rotation();
			}
			// insertion de la piece de maniere a mettre l'objet dessus
			if (objetARecup.getPosLignePlateau()==0){ // piece en haut du plateau
				return new int[]{piece2.getOrientationPiece(), 20-objetARecup.getPosColonnePlateau()};
			}
			if (objetARecup.getPosLignePlateau()==6){ // piece en bas du plateau
				return new int[]{piece2.getOrientationPiece(), objetARecup.getPosColonnePlateau()};
			}
			if (objetARecup.getPosColonnePlateau()==0){ // piece a gauche du plateau
				return new int[]{piece2.getOrientationPiece(), 7+objetARecup.getPosLignePlateau()};
			}
			return new int[]{piece2.getOrientationPiece(), 27-objetARecup.getPosLignePlateau()};
		}
		// Raprocher la piece de l'objet de la piece du joueur...
		// Obtenir les coordonnée de destination de la piece de l'objet (rappel : piece1 où se situe le joueur et piece 2 où se situe l'objet)
		int[] caseDest;
		if (piece1.getPointEntree(0) && piece2.getPointEntree(2)){ // Haut -> Bas
			caseDest = new int[]{getPosLigne()-1,getPosColonne()};
		} else if (piece1.getPointEntree(2) && piece2.getPointEntree(0)){ // Bas -> Haut
			caseDest = new int[]{getPosLigne()+1, getPosColonne()};
		} else if (piece1.getPointEntree(1) && piece2.getPointEntree(3)){ // Droite -> Gauche
			caseDest = new int[]{getPosLigne(), getPosColonne()+1};
		} else { // Gauche -> Droite
			caseDest = new int[]{getPosLigne(), getPosColonne()-1};
		}
		// si destination hors plateau alors deplacer joueur en consequences
		if (caseDest[0]==-1){
			return new int[]{Utils.genererEntier(3), caseDest[1]};
		} else if (caseDest[0]==7){
			return new int[]{Utils.genererEntier(3), 20-caseDest[1]};
		} else if (caseDest[1]==-1){
			return new int[]{Utils.genererEntier(3), 27-caseDest[0]};
		} else if (caseDest[1]==7){
			return new int[]{Utils.genererEntier(3), 7+caseDest[0]};
		}
		// deplacer piece de l'objet
		if (objetARecup.getPosLignePlateau() != caseDest[0] && !(caseDest[0]==getPosLigne() && objetARecup.getPosColonnePlateau()!=caseDest[1])){
			if (objetARecup.getPosColonnePlateau()==getPosColonne()){
				return new int[]{Utils.genererEntier(3), 7+objetARecup.getPosLignePlateau()};
			}
			if (caseDest[0]<getPosLigne()){
				return new int[]{Utils.genererEntier(3), 20-objetARecup.getPosColonnePlateau()};
			}
			return new int[]{Utils.genererEntier(3), objetARecup.getPosColonnePlateau()};
		} else if(objetARecup.getPosColonnePlateau() != caseDest[1] && !(caseDest[1]==getPosColonne() && objetARecup.getPosLignePlateau()!=caseDest[0])) {
			if (objetARecup.getPosLignePlateau()==getPosLigne()){
				return new int[]{Utils.genererEntier(3), objetARecup.getPosColonnePlateau()};
			}
			if (caseDest[1]<getPosColonne()){
				return new int[]{Utils.genererEntier(3), 7+objetARecup.getPosLignePlateau()};
			}
			return new int[]{Utils.genererEntier(3), 27-objetARecup.getPosLignePlateau()};
		}
		// Si aucune des condition d'avant n'as retourner quelque chose alors afficher un message d'avertissement dans le cmd et retourner une valeur aleatoire
		System.out.println("Avertissement : l'éxécution de choisirOrientatioEntree du joueur OrdiType2 \""+getNomJoueur()+"\" n'as pu trouver un deplacement adequat !");
		System.out.println("Il retourne donc un choix completement aleatoire");
		return new int[]{Utils.genererEntier(3), Utils.genererEntier(27)};
	}

	@Override
	public int[] choisirCaseArrivee(ElementsPartie elementsPartie) {
		Objet objetARecup = getObjetsJoueur()[getNombreObjetsRecuperes()];
		if (elementsPartie.getPlateau().calculeChemin(getPosLigne(), getPosColonne() ,objetARecup.getPosLignePlateau(), objetARecup.getPosColonnePlateau()) == null){
			return new int[]{getPosLigne(), getPosColonne()};
		}
		return new int[]{objetARecup.getPosLignePlateau(), objetARecup.getPosColonnePlateau()};
	}

	@Override
	public Joueur copy(Objet[] objets){
		Joueur nouveauJoueur=new JoueurOrdinateurT2(getNumJoueur(),getNomJoueur(), getNumeroImagePersonnage(),getPosLigne(),getPosColonne());
		nouveauJoueur.setObjetsJoueur(this.getObjetsJoueurGeneral(objets));
		while (nouveauJoueur.getNombreObjetsRecuperes()!=this.getNombreObjetsRecuperes())
			nouveauJoueur.recupererObjet();
		return nouveauJoueur;
	}

}
