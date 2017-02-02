package com.pacoperezgalan.dtup;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
public class ActivityActorsJSON extends AppCompatActivity {
    RecyclerView recycler;
    LinearLayoutManager manager;
    ArrayList<Actor> actorsList;
    AdaptadorRecycler adapterRecycler;

    ProgressDialog pd;
    boolean hiHaXarxa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors_json);

        recycler=(RecyclerView) findViewById(R.id.recycler);

        manager=new LinearLayoutManager(this);
        actorsList=new ArrayList<Actor>();
        adapterRecycler=new AdaptadorRecycler(actorsList);

        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapterRecycler);

        pd=new ProgressDialog(this);
        pd.setMessage("Parsejant Actors de JSON");
        pd.setCancelable(false);

        comprovaConnexio();

    }

    protected boolean comprovaConnexio() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new ConectaURL().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");
            return true;
        } else {

            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.recycler), "No hi ha connexió a la Xarxa", Snackbar.LENGTH_INDEFINITE);

            mySnackbar.setAction("Tornar a provar", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hiHaXarxa = comprovaConnexio();
                }
            });
            mySnackbar.show();
            return false;
        }
    }


    private class ConectaURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();

        }

        @Override
        protected String doInBackground(String... url) {

            String documentJSON = conectaURL(url[0]);

            long tempsInicial = System.currentTimeMillis();

            parsejaJSON(documentJSON);

            long tempsFinal = System.currentTimeMillis();

            return ((tempsFinal-tempsInicial)+" ms.");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapterRecycler.notifyDataSetChanged();

            pd.dismiss();
        }
    }


    private String conectaURL(String llocAConnectar){
        URL url;
        String resposta=null;
        try {
            Log.d("tag","Iniciant la connexió: (");

            url = new URL(llocAConnectar);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            int response = conn.getResponseCode();
            Log.d("tag", "Rebent dades des del Servidor en streaming: ");

            InputStream is = new BufferedInputStream(conn.getInputStream());
            Log.d("tag","Convertint l'streaming en un String: ");

            resposta = converteixStreamAString(is);

            Log.d("tag","Resposta: ("+response+")"+resposta);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            return resposta;
        }
    }

    private String converteixStreamAString(InputStream is) {

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void parsejaJSON(String documentJSON) {

        Log.d("tag", "Resposta2: " + documentJSON);

        if (documentJSON != null) {
            try {
                JSONObject json=new JSONObject(documentJSON);
                JSONArray actors = json.getJSONArray("actors") ;

                for (int i = 0; i < actors.length(); i++) {
                    JSONObject jsonObj = actors.getJSONObject(i);

                    String nom = jsonObj.getString("name");
                    String descripcio = jsonObj.getString("description");
                    String naixement = jsonObj.getString("dob");
                    String ciutat = jsonObj.getString("country");
                    String altura = jsonObj.getString("height");
                    String matrimoni = jsonObj.getString("spouse");
                    String fills = jsonObj.getString("children");

                    String urlImage = jsonObj.getString("image");
                    Bitmap image=BitmapDeString(urlImage);


                    Actor unActor = new Actor();

                    unActor.setNom(nom);
                    unActor.setDescricio(descripcio);
                    unActor.setNaixement(naixement);
                    unActor.setCiutat(ciutat);
                    unActor.setAltura(altura);
                    unActor.setMatrimoni(matrimoni);
                    unActor.setFills(fills);
                    unActor.setImage(image);


                    Log.d("tag",unActor.toString());
                    actorsList.add(unActor);
                }
            } catch (final JSONException e) {
                Log.e("tag", "Error parsejant Json: " + e.getMessage());
                Snackbar.make(findViewById(R.id.recycler),
                        "Error parsejant Json", Snackbar.LENGTH_LONG).show();
            }
        } else {
            Log.e("tag", "Error intentant rebre el Json.");
            Snackbar.make(findViewById(R.id.recycler),
                    "Error intentant rebre el Json.", Snackbar.LENGTH_LONG).show();


        }

    }

    public Bitmap BitmapDeString(String builder){

        Bitmap bitmap;
        try{
            URL url = new URL(builder);
            URLConnection conexion = url.openConnection();
            conexion.connect();

            InputStream is = conexion.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
