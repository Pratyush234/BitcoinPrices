package com.example.praty.bitcoinprices;

import org.json.JSONException;
import org.json.JSONObject;

public class BtcGet {
    private String mPrice;

    public static BtcGet fromJson(JSONObject jsonObject){
        BtcGet btcGet= new BtcGet();
        try {
            btcGet.mPrice = jsonObject.getString("last");


            return btcGet;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getmPrice() {
        return mPrice;
    }
}
