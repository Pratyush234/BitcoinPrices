package com.example.praty.bitcoinprices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    TextView mPriceText;
    Spinner mSpinner;
    ArrayAdapter<CharSequence> adapter;
    private final String URLID="https://apiv2.bitcoinaverage.com/indices/global/ticker/";
    String appid="YjZmMWNlYmVmMjcxNDc0OGE5ODY0YjYyMWFiMzdkOTM";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceText= (TextView) findViewById(R.id.priceText);
        mSpinner=(Spinner) findViewById(R.id.Spinner);


        adapter = ArrayAdapter.createFromResource(this,
                R.array.base_currency, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("btc","onItemSelected() has been called BTC"+parent.getItemAtPosition(position));
                String url=URLID+"BTC"+parent.getItemAtPosition(position);
                Log.d("btc",url);
                letsDoSomeNetworking(url);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("btc","onNothingSelected() HAS been called");
            }
        });


    }


    public void letsDoSomeNetworking(String url){
        AsyncHttpClient client= new AsyncHttpClient();
        client.setURLEncodingEnabled(false);
        client.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("btc","Success:"+response.toString());
                BtcGet btcGet=BtcGet.fromJson(response);
                updateUI(btcGet);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("btc","Request failed");
                Log.e("btc",throwable.toString());
            }
        });
    }

    public void updateUI(BtcGet obj){
        mPriceText.setText(obj.getmPrice());
    }
}
