package org.hedspi.tienlv.demorecommender.model;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
    public List<Itemset> itemsets;
    private int sup;

    public Pattern(){
        this.itemsets = new ArrayList<>();
        this.sup = 0;
    }

    public int getSup(){
        return this.sup;
    }
    public void setSup(int sup) {
        this.sup = sup;
    }

    public String toString(){
        String str= "";
        for(Itemset itemset: itemsets){
            str += LabelList.getLabels(itemset.items);
        }
        return  str;
    }
}
