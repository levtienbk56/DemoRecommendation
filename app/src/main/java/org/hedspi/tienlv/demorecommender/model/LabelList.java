package org.hedspi.tienlv.demorecommender.model;

import java.util.Arrays;
import java.util.List;

public class LabelList {
    public static final String[] array = {
            "amusement_park",
            "aquarium",
            "art_gallery",
            "bar",
            "book_store",
            "bowling_alley",
            "cafe",
            "campground",
            "casino",
            "library",
            "movie_rental",
            "movie_theater",
            "museum",
            "night_club",
            "park",
            "rv_park",
            "spa",
            "stadium",
            "zoo",
            "food",
            "restaurant",
            "beauty_salon",
            "dentist",
            "doctor",
            "gym",
            "hair_care",
            "health",
            "hospital",
            "hindu_temple",
            "airport",
            "atm",
            "bank",
            "bus_station",
            "car_repair",
            "car_wash",
            "cemetery",
            "church",
            "courthouse",
            "electrician",
            "embassy",
            "establishment",
            "fire_station",
            "funeral_home",
            "gas_station",
            "general_contractor",
            "insurance_agency",
            "jewelry_store",
            "laundry",
            "lawyer",
            "liquor_store",
            "meal_delivery",
            "meal_takeaway",
            "mosque",
            "moving_company",
            "painter",
            "parking",
            "pet_store",
            "pharmacy",
            "physiotherapist",
            "place_of_worship",
            "plumber",
            "police",
            "real_estate_agency",
            "roofing_contractor",
            "storage",
            "subway_station",
            "synagogue",
            "taxi_stand",
            "train_station",
            "travel_agency",
            "veterinary_care",
            "local_government_office",
            "locksmith",
            "lodging",
            "bakery",
            "bicycle_store",
            "car_dealer",
            "car_rental",
            "clothing_store",
            "convenience_store",
            "department_store",
            "electronics_store",
            "florist",
            "furniture_store",
            "grocery_or_supermarket",
            "hardware_store",
            "home_goods_store",
            "shoe_store",
            "shopping_mall",
            "store",
            "accounting",
            "city_hall",
            "finance",
            "post_office",
            "school",
            "university"};
    public static final List<String> list = Arrays.asList(array);

    public static String[] getArray(){
        return array;
    }
    public static List<String> getList(){
        return list;
    }
    public static String getLabel(int id){
        if (id<0 ||id >= list.size()){
            return "";
        }
        return list.get(id);
    }
    public static int getLabelId(String label){
        return list.indexOf(label);
    }

    public static String getLabels(List<Integer> ids){
        String str = "";
        for(Integer id: ids){
            str += getLabel(id) + " ";
        }
        if(ids.size() > 1){
            str = "("+str + ")";
        }
        return str;
    }
}

