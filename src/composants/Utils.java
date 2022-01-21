package composants;

import java.util.*;

/**
 *
 * Classe contenant quelques fonctions utilitaires.
 *
 */
public class Utils {

    private static final Random generateur=new Random((new Date().getTime()));

    /**
     * A Faire (27/04/2021 MC Finie)
     *
     * Méthode permettant de générer aléatoirement un nombre entier.
     *
     * @param max Le nombre entier maximal pouvant être retourné.
     * @return Un nombre entier compris entre 0 et max (inclus).
     */
    public static int genererEntier(int max){
        return generateur.nextInt(max+1);
    }
    /**
     * A Faire (27/04/2021 MC Finie)
     *
     * Méthode permettant de générer un tableau d'entiers dont la longueur longTab est donnée en paramétre.
     * Le tableau généré doit contenir chaque entier compris entre 0 et longTab-1. La position de ces entiers
     * dans le tableau doit être aléatoire.
     *
     * @param longTab La longueur du tableau.
     * @return Un tableau contenant les entiers 0,...,longTab-1 placés aléatoirement dans le tableau.
     */
    public static int[] genereTabIntAleatoirement(int longTab){
        //init tab
        Integer[] tab = new Integer[longTab];
        for (int i=0; i<longTab; i++) {
            tab[i]=i;
        }
        List<Integer> list = Arrays.asList(tab);
        Collections.shuffle(list);
        list.toArray(tab);
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
    /**
     * Programme testant la méthode genereTabIntAleatoirement.
     * @param args arguments du programme
     */
    public static void main(String[] args) {
        // Un petit test ...
        int[] tab = genereTabIntAleatoirement(18);
        for (int j : tab) System.out.print(j + " ");
    }
}