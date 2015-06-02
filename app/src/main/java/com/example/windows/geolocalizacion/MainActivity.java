package com.example.windows.geolocalizacion;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private LocationManager locManager;
    private LocationListener locListener;
    Button prender, apagar, map;
    TextView latitud, longitud,presicion, estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitud = (TextView)findViewById(R.id.lblPosLatitud);
        longitud = (TextView)findViewById(R.id.lblPosLongitud);
        presicion = (TextView)findViewById(R.id.lblPresicion);
        estado = (TextView)findViewById(R.id.lblEstado);
        prender=(Button) findViewById(R.id.prendiendo);
        apagar=(Button) findViewById(R.id.apagando);
       // map=(Button) findViewById(R.id.mapa);
        prender.setOnClickListener(this);
        apagar.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            //Acciones a ejecutar en caso de clic en el boton prender
            case R.id.prendiendo:
                latitud.setText("Latitud");
                longitud.setText("Longitud");
                presicion.setText("Presicion");
                Toast.makeText(this, "Comenzando localizacion..." , Toast.LENGTH_SHORT).show();
                comienzaLocalizacion();
                prender.setText("Actualizar");
                break;
            //Acciones a ejecutar en caso de clic en el boton apagar
            case R.id.apagando:
                latitud.setText("Latitud");
                longitud.setText("Longitud");
                presicion.setText("Presicion");
                apagaLocalizacion();
                Toast.makeText(this, "Finalizando localizacion..." , Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void apagaLocalizacion() {
        //Establece el TextView de estado como apagado
        estado.setText("Estatus del sensor: Apagado");
        locManager.removeUpdates(locListener);
    }

    private void comienzaLocalizacion() {
        //Reseteamos los TextView
        estado.setText("Estatus del sensor: Encendido");
        //Obtenemos una referencia al servicio de localizacion del sistema
        locManager =  (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //Asignamos la ubicacion obtenida de la red y el GPS
        Location ubicacion = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location ubicacion2 = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mostrarUbicacion(ubicacion, ubicacion2);

        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarUbicacion(location,location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onProviderEnabled(String provider) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onProviderDisabled(String provider) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        //intervalo de localizacion
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 1,locListener);
        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 1,locListener);
    }
    private void mostrarUbicacion(Location location, Location location2) {
        if (location != null){
            //Asignamos a los TextView los valores obtenidos de la localizacion en el caso de la red
            longitud.setText(String.valueOf("Longitud: "+location.getLongitude()));
            latitud.setText("Latitud: "+location.getLatitude() + "");
            presicion.setText("Precicion: "+location.getAccuracy()+"");

        }
        //En caso de no obtener datos
        else{
            if (location == null){
                //Asignamos a los TextView los valores obtenidos de la localizacion en el caso de la red
                longitud.setText(String.valueOf("Longitud: "+location2.getLongitude()));
                latitud.setText("Latitud: "+location2.getLatitude() + "");
                presicion.setText("Presicion: "+location2.getAccuracy()+"");
            }
            //En caso de no obtenr datos de ningun servicio mostramos el mensage
            else {
                Toast.makeText(this, "No existe servicio localizacion en su sistema, ENCIENDA RED o GPS", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void mostrarUbicacion2(Location location) {
        if (location != null){
        }else{Toast.makeText(this, "No existe servicio localizacion en su sistema2, ENCIENDA RED o GPS" , Toast.LENGTH_SHORT).show();}
    }
}