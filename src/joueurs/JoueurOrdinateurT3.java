package joueurs;

import composants.Objet;
import composants.Utils;
import partie.ElementsPartie;

/**
 * 
 * Cette classe permet de représenter un joueur ordinateur de type T3.
 * 
 * @author Jean-François Condotta - 2021
 *
 */

public class JoueurOrdinateurT3 extends JoueurOrdinateur {

	/**
	 * Constructeur permettant de créer un joueur.
	 * 
	 * @param numJoueur Le numéro du joueur.
	 * @param nomJoueur Le nom du joueur.
	 * @param numeroImagePersonnage Le numéro de l'image représentant le joueur.
	 * @param posLignePlateau La ligne du plateau sur laquelle est positionnée le joueur.
	 * @param posColonnePlateau La colonne du plateau sur laquelle est positionnée le joueur.

	 */
	public JoueurOrdinateurT3(int numJoueur,String nomJoueur, int numeroImagePersonnage,int posLignePlateau,int posColonnePlateau) {
				super(numJoueur,nomJoueur, numeroImagePersonnage,posLignePlateau,posColonnePlateau);
	}

	@Override
	public String getCategorie() {
		return "OrdiType3";
	}

	@Override
	public int[] choisirOrientationEntree(ElementsPartie elementsPartie) {
		if (Utils.genererEntier(10)==10){
			JoueurOrdinateurT2 OrdiType2 = new JoueurOrdinateurT2(getNumJoueur(), getNomJoueur(), getNumeroImagePersonnage(), getPosLigne(), getPosColonne());
			OrdiType2.setObjetsJoueur(this.getObjetsJoueurGeneral(getObjetsJoueur()));
			while (OrdiType2.getNombreObjetsRecuperes()!=this.getNombreObjetsRecuperes())
				OrdiType2.recupererObjet();
			return OrdiType2.choisirOrientationEntree(elementsPartie);
		}
		// sinon passer en revue toute les possibilité de coup et si il y en a une qui fait en sorte
		// que le joueur avec le plus d'objet recuperer ne peut gagner une fois à son tour de jouer, alors la return
		for (int i=0; i<4; i++){
			for (int j=0; j<28; j++){
				ElementsPartie elemPartieCopy = elementsPartie.copy();
				elemPartieCopy.getPieceLibre().setOrientation(i);
				elemPartieCopy.insertionPieceLibre(j);
				Joueur[] joueurs = elemPartieCopy.getJoueurs();
				Joueur joeurEnnemi = joueurs[0];
				if (joeurEnnemi == this){
					joeurEnnemi = joueurs[1];
				}
				for (Joueur joueur : joueurs) {
					if (joueur.getNombreObjetsRecuperes() > joeurEnnemi.getNombreObjetsRecuperes() && joueur != this) {
						joeurEnnemi = joueur;
					}
				}
				Objet objetEnnemi = joeurEnnemi.getObjetsJoueur()[joeurEnnemi.getNombreObjetsRecuperes()];
				if (elemPartieCopy.getPlateau().calculeChemin(joeurEnnemi.getPosLigne(),joeurEnnemi.getPosColonne(),objetEnnemi.getPosLignePlateau(),objetEnnemi.getPosColonnePlateau())!=null){
					continue;
				}
				boolean t=true;
				for (int k=0; k<4; k++){
					for (int l=0; l<28; l++){
						ElementsPartie elemPartieCopy2 = elemPartieCopy.copy();
						elemPartieCopy2.getPieceLibre().setOrientation(i);
						elemPartieCopy2.insertionPieceLibre(j);
						joueurs = elemPartieCopy2.getJoueurs();
						joeurEnnemi = joueurs[0];
						if (joeurEnnemi == this){
							joeurEnnemi = joueurs[1];
						}
						for (Joueur joueur : joueurs) {
							if (joueur.getNombreObjetsRecuperes() > joeurEnnemi.getNombreObjetsRecuperes() && joueur != this) {
								joeurEnnemi = joueur;
							}
						}
						objetEnnemi = joeurEnnemi.getObjetsJoueur()[joeurEnnemi.getNombreObjetsRecuperes()];
						if (elemPartieCopy2.getPlateau().calculeChemin(joeurEnnemi.getPosLigne(),joeurEnnemi.getPosColonne(),objetEnnemi.getPosLignePlateau(),objetEnnemi.getPosColonnePlateau())!=null){
							t=false;
							break;
						}
					}
					if (!t){
						break;
					}
				}
				if (t){
					return new int[]{i,j};
				}
			}
		}
		// si pas possible return un choix aleatoire
		return new int[]{Utils.genererEntier(3),Utils.genererEntier(27)};
	}

	@Override
	public int[] choisirCaseArrivee(ElementsPartie elementsPartie) {
		Objet objetARecup = getObjetsJoueur()[getNombreObjetsRecuperes()];
		// si l'objet a recuperer est accesible retourner les coordonne de l'objet
		if (elementsPartie.getPlateau().calculeChemin(getPosLigne(), getPosColonne() ,objetARecup.getPosLignePlateau(), objetARecup.getPosColonnePlateau()) != null){
			return new int[]{objetARecup.getPosLignePlateau(), objetARecup.getPosColonnePlateau()};
		}
		int[] caseArriver = new int[]{getPosLigne(), getPosColonne()};
		// parcours toute les piece du plateau
		for (int i=0; i<7; i++){
			for (int j=0; j<7; j++){
				// si la piece de coordonnée (i,j) n'est pas accesible passer a la piece suivante
				if (elementsPartie.getPlateau().calculeChemin(getPosLigne(), getPosColonne(), i, j) == null){continue;}
				int distanceActuelle = Math.abs(caseArriver[0]-objetARecup.getPosLignePlateau())+Math.abs(caseArriver[1]-objetARecup.getPosColonnePlateau());
				int distanceTeste = Math.abs(i-objetARecup.getPosLignePlateau()) + Math.abs(j-objetARecup.getPosColonnePlateau());
				// si la piece est plus proche que la case arrivée actuelle alors elle devient la case d'arrivée
				if (distanceTeste<distanceActuelle){
					caseArriver[0]=i;
					caseArriver[1]=j;
				}
			}
		}
		return caseArriver;
	}

	@Override
	public Joueur copy(Objet[] objets){
		Joueur nouveauJoueur=new JoueurOrdinateurT3(getNumJoueur(),getNomJoueur(), getNumeroImagePersonnage(),getPosLigne(),getPosColonne());
		nouveauJoueur.setObjetsJoueur(this.getObjetsJoueurGeneral(objets));
		while (nouveauJoueur.getNombreObjetsRecuperes()!=this.getNombreObjetsRecuperes())
			nouveauJoueur.recupererObjet();
		return nouveauJoueur;
	}

}
