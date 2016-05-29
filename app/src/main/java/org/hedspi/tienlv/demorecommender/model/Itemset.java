package org.hedspi.tienlv.demorecommender.model;

import java.util.ArrayList;
import java.util.List;

public class Itemset {
    public List<Integer> items;

    public Itemset() {
        this.items = new ArrayList<>();
    }

    public Itemset(List<String> labels) {
        this.items = new ArrayList<>();
        int a;
        for (String label : labels) {
            a = LabelList.getLabelId(label);
            if (a != -1) {
                items.add(a);
            }
        }
    }

    public String toString(){
        String str = "";

        for(Integer i: items){
            str += i + " ";
        }
        if(items.size() > 1){
            str = "(" + str + ")";
        }
        return str;
    }

}
