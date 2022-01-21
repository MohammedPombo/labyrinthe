package joueurs;

import composants.Objet;
import composants.Utils;
import partie.ElementsPartie;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 * 
 * Cette classe permet de représenter un joueur ordinateur de type T1.
 * 
 * @author Jean-François Condotta - 2021
 *
 */

public class JoueurOrdinateurT1 extends JoueurOrdinateur {

	/**
	 * 
	 * Constructeur permettant de créer un joueur.
	 * 
	 * @param numJoueur Le numéro du joueur.
	 * @param nomJoueur Le nom du joueur.
	 * @param numeroImagePersonnage Le numéro de l'image représentant le joueur.
	 * @param posLignePlateau La ligne du plateau sur laquelle est positionnée le joueur.
	 * @param posColonnePlateau La colonne du plateau sur laquelle est positionnée le joueur.

	 */
	public JoueurOrdinateurT1(int numJoueur,String nomJoueur, int numeroImagePersonnage,int posLignePlateau,int posColonnePlateau) {
				super(numJoueur,nomJoueur, numeroImagePersonnage,posLignePlateau,posColonnePlateau);
	}

	@Override
	public String getCategorie() {
		return "OrdiType1";
	}

	@Override
	public int[] choisirOrientationEntree(ElementsPartie elementsPartie) {
		return new int[]{Utils.genererEntier(3),Utils.genererEntier(27)};
	}

	@Override
	public int[] choisirCaseArrivee(ElementsPartie elementsPartie) {
		Objet objetARecup = getObjetsJoueur()[getNombreObjetsRecuperes()];
		if (elementsPartie.getPlateau().calculeChemin(getPosLigne(), getPosColonne() ,objetARecup.getPosLignePlateau(), objetARecup.getPosColonnePlateau()) == null) {
			Vector<Vector<Integer>> ListPieceValide = new Vector<>();
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					// si la piece de coordonnée (i,j) n'est pas accesible passer a la piece suivante
					if (elementsPartie.getPlateau().calculeChemin(getPosLigne(), getPosColonne(), i, j) == null) {
						continue;
					}
					// sinon ajouter les coordonne a la liste des piece valide
					Vector<Integer> ListCoord = new Vector<>();
					ListCoord.add(i);
					ListCoord.add(j);
					ListPieceValide.add(ListCoord);
				}
			}
			Collections.shuffle(ListPieceValide);
			return new int[]{ListPieceValide.get(0).get(0), ListPieceValide.get(0).get(1)};
		}
		return new int[]{objetARecup.getPosLignePlateau(),objetARecup.getPosColonnePlateau()};
	}

	@Override
	public Joueur copy(Objet[] objets){
		Joueur nouveauJoueur=new JoueurOrdinateurT1(getNumJoueur(),getNomJoueur(), getNumeroImagePersonnage(),getPosLigne(),getPosColonne());
		nouveauJoueur.setObjetsJoueur(this.getObjetsJoueurGeneral(objets));
		while (nouveauJoueur.getNombreObjetsRecuperes()!=this.getNombreObjetsRecuperes())
			nouveauJoueur.recupererObjet();
		return nouveauJoueur;
	}

}
