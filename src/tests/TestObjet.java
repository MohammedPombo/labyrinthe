package tests;

import composants.Objet;
import grafix.interfaceGraphique.IG;

public class TestObjet {
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
        message[2] = "Cliquez pour quitter ... ";
        message[3] = "";
        IG.afficherMessage(message);
        Objet[] tabObjet= Objet.nouveauxObjets();
        for (Objet objet : tabObjet) {
            IG.placerObjetPlateau(objet.getNumObjet(),objet.getPosLignePlateau(),objet.getPosColonnePlateau());
            System.out.println(objet.getNumObjet());
        }
        IG.miseAJourAffichage();
        IG.rendreVisibleFenetreJeu();
        IG.attendreClic();
        IG.fermerFenetreJeu();
        System.exit(0);
    }
}
