package assignment1.com.restaurantMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ArrayList for person names, email Id's and mobile numbers
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<String> itemPrices = new ArrayList<>();
    ArrayList<String> itemDescriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            // fetch JSONArray named users
            JSONArray itemArray = obj.getJSONArray("items");
            // implement for loop for getting users list data
            for (int i = 0; i < itemArray.length(); i++) {
                // create a JSONObject for fetching single user data
                JSONObject itemDetail = itemArray.getJSONObject(i);
                // fetch email and name and store it in arraylist
                itemNames.add(itemDetail.getString("name"));
                itemPrices.add(itemDetail.getString("price"));
//                // create a object for getting contact data from JSONObject
//                JSONObject contact = itemDetail.getJSONObject("contact");
                // fetch mobile number and store it in arraylist
                itemDescriptions.add(itemDetail.getString("description"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, itemNames, itemPrices, itemDescriptions);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("restaurant_menu.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
