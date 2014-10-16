/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superserial;

import java.util.Comparator;

/**
 * This class is used in comparisons between individuals, currently it just uses
 * the compareTo method from the Individual class.  This sorts them by ascending 
 * fitness values.
 * @author watkinsscotttx
 */
public class IndCompare implements Comparator<Individual> {

    /**
     * Compares two individuals
     * @param A first individual to compare
     * @param B second individual to compare
     * @return 1, 0, or -1
     */
    @Override
    public int compare(Individual A, Individual B) {
        return A.compareTo(B);
    }
}
