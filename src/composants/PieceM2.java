package composants;

/**
 *
 * Cette classe permet de représenter les pièces du jeu de modèle 2.
 *
 */
public class PieceM2 extends Piece {

    /**
     * A Faire (27/04/2021 MC Finie)
     *
     * Constructeur permettant de construire une pièce de modèle 2 et d'orientation 0.
     */
    public PieceM2() {
        super(2,true,true,false,true);
    }
    /**
     * A Faire (27/04/2021 MC Finie)
     *
     * Méthode permettant de créer une copie de la pièce (un nouvelle objet Java).
     * @return Une copie de la pièce.
     */
    public Piece copy(){
        Piece piece = new PieceM2();
        piece.setOrientation(getOrientationPiece());
        return piece;
    }
}