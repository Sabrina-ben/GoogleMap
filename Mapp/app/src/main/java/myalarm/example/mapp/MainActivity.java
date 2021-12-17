package myalarm.example.mapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.*;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spType;
    Button btsearch;
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spType = findViewById(R.id.sp_type);
        btsearch = findViewById(R.id.bt_search);
        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this::onMapReady);

        //Initialize array of place type
        String[] placeTypeList ={"shop center","restaurant","jewelry shop","coffee shop","shoe shop","gym"};
        //Initialize array of place name
        String[] placeNameList = {"shop center","restaurant","jewelry shop","coffee shop","shoe shop","gym"};

        //set adapter on spinner
        spType.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,placeNameList));

        //Initialize fused Location provider client
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            //when permission granted
            //call method
            getCurrentLocation();
        }else {
            //when permission denied
            //Request permission
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }










        btsearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String text = spType.getSelectedItem().toString();

                if(text == "shop center") {
                    map.clear();
                    LatLng TopShop = new LatLng(36.4877941, 2.8047183);
                    map.addMarker(new MarkerOptions().position(TopShop).title("Top Shop"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(TopShop));

                    LatLng FamilyShop = new LatLng(36.4944882, 2.8411024);
                    map.addMarker(new MarkerOptions().position(FamilyShop).title("Family Shop"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(FamilyShop));
                }
                if(text == "restaurant") {
                    map.clear();
                    LatLng DarNouar = new LatLng(36.4893468, 2.8006011);
                    map.addMarker(new MarkerOptions().position(DarNouar).title("Dar Nouar Restaurant"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(DarNouar));

                    LatLng IlyesDélice = new LatLng(36.4893468, 2.8006011);
                    map.addMarker(new MarkerOptions().position(IlyesDélice).title("Ilyes Délice Pizzeria"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(IlyesDélice));

                    LatLng LaNoché = new LatLng(36.4816121, 2.7301497);
                    map.addMarker(new MarkerOptions().position(LaNoché).title("La Noché Restaurant"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(LaNoché));

                    LatLng Pacanier = new LatLng(36.4816121, 2.7301497);
                    map.addMarker(new MarkerOptions().position(Pacanier).title("Pacanier Restaurant"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Pacanier));

                    LatLng Mistral = new LatLng(36.4616358, 2.7813403);
                    map.addMarker(new MarkerOptions().position(Mistral).title("Le Mistral Restaurant"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Mistral));

                    LatLng BonPlatTraditinal = new LatLng(36.4698484, 2.7465048);
                    map.addMarker(new MarkerOptions().position(BonPlatTraditinal).title("Le Bon plat tradionnel"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(BonPlatTraditinal));
                }

                if(text == "jewelry shop") {
                    map.clear();
                    LatLng Luxor = new LatLng(36.4710277, 2.7941469);
                    map.addMarker(new MarkerOptions().position(Luxor).title("Bijouterie Luxor"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Luxor));

                    LatLng AlAmal = new LatLng(36.4710277, 2.7941469);
                    map.addMarker(new MarkerOptions().position(AlAmal).title("Bijouterie al amal"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(AlAmal));

                    LatLng Elhilal = new LatLng(36.4710277, 2.7941469);
                    map.addMarker(new MarkerOptions().position(Elhilal).title("Bijouterie Elhilal"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Elhilal));

                    LatLng Elmanara = new LatLng(36.4560704, 2.7710907);
                    map.addMarker(new MarkerOptions().position(Elmanara).title("Bijouterie El manara"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Elmanara));
                }
                if(text == "coffee shop") {
                    map.clear();
                    LatLng LasChicas = new LatLng(36.4762001, 2.8157238);
                    map.addMarker(new MarkerOptions().position(LasChicas).title("Las Chicas Coffee Shop"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(LasChicas));

                    LatLng Avenida = new LatLng(36.4657546, 2.8085715);
                    map.addMarker(new MarkerOptions().position(Avenida).title("Avenida cafè"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Avenida));

                    LatLng bellevue = new LatLng(36.4616745, 2.8021033);
                    map.addMarker(new MarkerOptions().position(bellevue).title("Café belle vue"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(bellevue));

                    LatLng Owel = new LatLng(36.4781369, 2.81674);
                    map.addMarker(new MarkerOptions().position(Owel).title("Owel coffee"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Owel));
                }
                if(text == "shoe shop") {
                    map.clear();
                    LatLng Blanc = new LatLng(36.4798187,2.7958105);
                    map.addMarker(new MarkerOptions().position(Blanc).title("Blanc shoes"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Blanc));

                    LatLng Stepmode = new LatLng(36.4490638,2.7902173);
                    map.addMarker(new MarkerOptions().position(Stepmode).title("Stepmode Blida"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Stepmode));

                    LatLng Tchico = new LatLng(36.4490638,2.7902173);
                    map.addMarker(new MarkerOptions().position(Tchico).title("Tchico shoes"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Tchico));

                    LatLng Skechers= new LatLng(36.4490638,2.7902173);
                    map.addMarker(new MarkerOptions().position(Skechers).title("Skechers"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(Skechers));
                }
                if(text == "gym") {
                    map.clear();
                    LatLng PromoSim= new LatLng(36.4816688,2.8191661);
                    map.addMarker(new MarkerOptions().position(PromoSim).title("Complexe Sportif PROMOSIM"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(PromoSim));


                    LatLng FitnessLover= new LatLng(36.4817135,2.8054501);
                    map.addMarker(new MarkerOptions().position(FitnessLover).title("Fitness Lovers"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(FitnessLover));

                    LatLng sportZone= new LatLng(36.4631115,2.8103817);
                    map.addMarker(new MarkerOptions().position(sportZone).title("Salle SportZone"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(sportZone));
                }


            }
        });



    }





    public void onMapReady(GoogleMap googleMap){
        map = googleMap;
        LatLng Blida = new LatLng(36.481377,2.7301499);
        map.addMarker(new MarkerOptions().position(Blida).title("Blida"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Blida));
    }












    private void getCurrentLocation(){
        //Initialize task Location
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>(){
            @Override
            public void onSuccess(Location location){
                //when Success
                if (location != null){
                    //when location is not null
                    currentLat = location.getLatitude();
                    //Get current longitude
                    currentLong = location.getLongitude();
                    //Sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //when map is ready
                            map = googleMap;
                            //Zoom current Location on map
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLat,currentLong),10
                            ));
                        }
                    });



                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //when permission granted
                //call methode
                getCurrentLocation();
            }
        }
    }










    private class PlaceTask extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings){
            String data = null;
            try {
                //Initialize data
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }


        @Override
        protected void onPostExecute(String s){
            //Execute parser task
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string)throws IOException
    {
        //Initialize url
        URL url = new URL(string);
        //Initialize connection
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        //connect connection
        connection.connect();
        //Initialize input stream
        InputStream stream = connection.getInputStream();
        //Intialize buffer reader
        BufferedReader reader= new BufferedReader(new InputStreamReader(stream));
        //Initialize string builder
        StringBuilder builder = new StringBuilder();
        //Initialize string variable
        String line= "";
        //Use while loop
        while ((line = reader.readLine()) != null){
            //Append line
            builder.append(line);
        }

        //Get append Data
        String data = builder.toString();
        //close reader
        reader.close();
        //Return data
        return data;
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>>{
        @Override
        protected List<HashMap<String,String>>doInBackground(String... strings){
            //craete json parser class
            JsonParser jsonParser = new JsonParser();
            //Initialize hash map list
            List<HashMap<String,String>> mapList = null;
            JSONObject object = null;
            try {
                //Initialize json object
                object = new JSONObject(strings[0]);
                //Parse json object
                mapList = jsonParser.parseResult(object);
            }catch (JSONException e){
                e.printStackTrace();
            }
            //Return map list
            return mapList;

        }

        @Override
        protected void onPostExecute(List<HashMap<String,String>> hashMaps){
            //Clean Map
            map.clear();
            //Use for loop
            for (int i=0; i<hashMaps.size(); i++){
                //Initialize hash map
                HashMap<String,String> hashMapList = hashMaps.get(i);
                //Get latitude
                double lat = Double.parseDouble(hashMapList.get("lat"));
                //Get longitude
                double lng = Double.parseDouble(hashMapList.get("lng"));
                //Get name
                String name = hashMapList.get("name");
                //Concat latitude and longitude
                LatLng latLng = new LatLng(lat,lng);
                //Initialize marker options
                MarkerOptions options = new MarkerOptions();
                //Set position
                options.position(latLng);
                //Set title
                options.title(name);
                //add marker on map
                map.addMarker(options);


            }
        }

    }
}