package org.hedspi.tienlv.demorecommender.modul.patternmatching;

import android.util.Log;

import org.hedspi.tienlv.demorecommender.model.Itemset;
import org.hedspi.tienlv.demorecommender.model.Pattern;
import org.hedspi.tienlv.demorecommender.model.PatternDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by trungtran.vn on 5/26/2016.
 */
public class PatternMatching {
    Pattern userSequence;
    PatternDB patternDB;
    /* co 3 tham so:
     * pattern
     * int 1: matching point (higher is better). 1 matching item = 1 point
     * int 2: index of current label, so useful labels begin from (index+1) to last
     */
    List<Triplet<Pattern, Integer, Integer>> patternRanking;

    public PatternMatching(Pattern userSequence, PatternDB patternDB) {
        this.userSequence = userSequence;
        this.patternDB = patternDB;
        patternRanking = new ArrayList<>();
    }

    public List<Triplet<Pattern, Integer, Integer>> match() {
        int k, i, j;
        // travel all pattern in patternDB
        for (k = 0; k < patternDB.patterns.size(); k++) {
            Pattern pattern = patternDB.patterns.get(k);            // list itemset
            int index = -1;
            int count = 0;
            Map<Integer, Integer> markMap = new HashMap<>();
            count = 0;

            // backwards traverse both userSequence (by j) & Pattern (by i)
            j = userSequence.itemsets.size() - 1;
            for (i = pattern.itemsets.size() - 2; i >= 0 && j >= 0; i--) {
                Itemset itemset = pattern.itemsets.get(i);          // list item
                Itemset uItemset = userSequence.itemsets.get(j);    // list item of user
                if (!Collections.disjoint(itemset.items, uItemset.items)) {
                    count += 1;     // increase 1 point
                    if (j == userSequence.itemsets.size() - 1) {
                        index = i;
                    }
                    j--;
                }
            }

            // we have done with this pattern.
            Triplet<Pattern, Integer, Integer> triplet = new Triplet<>(pattern, count, index);
            patternRanking.add(triplet);
        }

        sort();
        print();
        return patternRanking;
    }

    /**
     * descending order
      */

    private void sort() {
        Collections.sort(patternRanking, new Comparator<Triplet<Pattern, Integer, Integer>>() {
            @Override
            public int compare(Triplet<Pattern, Integer, Integer> o1, Triplet<Pattern, Integer, Integer> o2) {
                Pattern p1 = o1.getA();
                Pattern p2 = o2.getA();
                Integer count1 = o1.getB();
                Integer count2 = o2.getB();

                if (count1 != count2) {
                    return count2 - count1;
                }
                return p2.getSup() - p1.getSup();
            }
        });
    }

    public static class Triplet<T, U, V> {
        T a;
        U b;
        V c;

        Triplet(T a, U b, V c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public T getA() {
            return a;
        }

        public U getB() {
            return b;
        }

        public V getC() {
            return c;
        }

    }

    private void print(){
        for(PatternMatching.Triplet<Pattern, Integer, Integer> patternRanked : patternRanking) {
            Pattern pattern = patternRanked.getA();
            int count = patternRanked.getB();
            int index = patternRanked.getC();
            Log.d("Pattern Matching", pattern.toString() + ", count:" + count + ", index:" + index);
        }
    }
}
