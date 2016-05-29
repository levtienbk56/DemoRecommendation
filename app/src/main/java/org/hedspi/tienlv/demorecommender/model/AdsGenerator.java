package org.hedspi.tienlv.demorecommender.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tienlv.hust on 5/15/2016.
 */
public class AdsGenerator {

    public List<Ads> generate(){
        List<Ads> listAds = new ArrayList<>();

        Ads ads1 = new Ads();
        ads1.setContent("Siêu giảm giá tại siêu thị Big C the Garden");
        ads1.addLabel("shop");
        ads1.addLabel("store");
        ads1.addLabel("food");
        ads1.addLabel("shopping_mall");
        ads1.addLabel("home_goods_store");

        Ads ads2 = new Ads();
        ads2.setContent("Mở bán trung cư siêu rẻ chỉ từ 10 tỷ");
        ads2.addLabel("bank");
        ads2.addLabel("finance");

        Ads ads3 = new Ads();
        ads3.setContent("chăm sóc cơ thể toàn diện hiệu quả ngay từ buổi đầu tiên");
        ads3.addLabel("spa");
        ads3.addLabel("beauty_salon");
        ads3.addLabel("hair_care");
        ads3.addLabel("health");

        Ads ads4 = new Ads();
        ads1.setContent("Ăn thả ga với Buffet giá siêu rẻ");
        ads1.addLabel("food");
        ads1.addLabel("restaurant");

        listAds.add(ads1);
        listAds.add(ads2);
        listAds.add(ads3);
        listAds.add(ads4);
        return  listAds;
    }

}
