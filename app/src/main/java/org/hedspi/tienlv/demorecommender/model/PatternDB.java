package org.hedspi.tienlv.demorecommender.model;

import android.content.Context;
import android.util.Log;

import org.hedspi.tienlv.demorecommender.activity.MapsActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PatternDB {
    public List<Pattern> patterns;
    Context context;

    public PatternDB(Context context) {
        this.context = context;
        this.patterns = new ArrayList<>();
    }

    public void loadPatternDB(){
        try {
            InputStream iS = context.getAssets().open("prefixspan_integer.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(iS));

            if (reader == null) {
                return;
            }
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                i++;
                Pattern pattern = new Pattern();
                String[] arr1 = line.split(" #SUP: ");
                pattern.setSup(Integer.parseInt(arr1[1]));

                String[] arr2 = arr1[0].split("-1");
                for(String str : arr2){  // xet 1 itemset
                    String[] arr3 = str.split(" ");
                    Itemset itemset = new Itemset();
                    for (String str2 : arr3){
                        if(!str2.equals(null) && !str2.equals("")){
                            itemset.items.add(Integer.parseInt(str2));
                        }
                    }
                    if(!itemset.items.isEmpty()) {
                        pattern.itemsets.add(itemset);
                    };
                }
                if(!pattern.itemsets.isEmpty()){
                    this.patterns.add(pattern);
                    Log.d("Pattern DB", pattern.toString());
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
