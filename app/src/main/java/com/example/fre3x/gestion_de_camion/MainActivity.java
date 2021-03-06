package com.example.fre3x.gestion_de_camion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button boutonStop = (Button) findViewById(R.id.buttonStop);
        boutonStop.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(longitude==0.0 && latitude==0.0){
                    //Si la longitude et latitude est egale à zero, on envoie un message d'erreur.
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    Toast toast=Toast.makeText(context,"Gps non activé, veuillez reessayer avec une connexion internet ou gps active.",duration);
                    toast.show();

                }else {
                    //Si c'est bon, on prepare le message à envoyer, et on appel la classe qui va envoyer le message.
                    //On envoie un toast à l'utilisateur pour lui prevenir que le message est envoyé.
                    String message="coordonnesGps," +" 0613884724, "+longitude+", "+latitude;

                    new EnvoieMessage("0672425178",message);
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    Toast toast=Toast.makeText(context,"Le message a été envoyé",duration);
                    toast.show();

                }
              }
            });

    }

    protected void onResume() {
        super.onResume();
        //cette fonction va chercher les coordonnées gps
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        //on applique aux variables les coordonnées actuelles
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        //ici on va demander l'actualisation des coordonnes si on a besoin de nouveau des coordonnes et qu'on n'est pas au même endroit
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        if (lm.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
