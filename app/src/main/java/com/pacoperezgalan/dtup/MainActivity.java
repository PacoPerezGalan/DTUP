package com.pacoperezgalan.dtup;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private String[] nomsItemsDrawer;
    private DrawerLayout mDrawerLayout;
    private GridView mDrawergrid;
    ArrayAdapter<String> adapterStringDrawer;
    ActionBarDrawerToggle mDrawerToggle;


    Button carregarActors;
    Button btn_dialeg;
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //navigation
        nomsItemsDrawer = new String[]{"1","2","3","4","5","6"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawergrid = (GridView) findViewById(R.id.gridDrawer);

        adapterStringDrawer = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nomsItemsDrawer);


        mDrawergrid.setAdapter(adapterStringDrawer);

        mDrawergrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tv=(TextView) view;
                Toast.makeText(getApplicationContext(),"Has tocat el item "+tv.getText()+" ("+position+")",Toast.LENGTH_SHORT).show();


                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());

                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.dtup_image));
                mBuilder.setContentTitle("Notificacio de DTUP");
                mBuilder.setContentText("Has tocat el item "+tv.getText()+" ("+position+")");
                Uri sonit= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                mBuilder.setSound(sonit);
                mBuilder.setVibrate(new long[]{50,50,50,100,50,150,50,200,50,250});

                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);

                NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(1,mBuilder.build());
            }
        });


        //principal
        carregarActors=(Button) findViewById(R.id.btn_carregar);

        carregarActors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i=new Intent(getApplicationContext(),ActivityActorsJSON.class);

            startActivity(i);

            }
        });

        btn_dialeg=(Button) findViewById(R.id.btn_dialeg);

        btn_dialeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());


                builder.setTitle("DIALEG").setMessage("Este es el fantastic dialeg");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Has seleccionat OK",Toast.LENGTH_SHORT).show();
                    }});

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Has seleccionat Cancelar",Toast.LENGTH_SHORT).show();
                    }});

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.obrint,R.string.tancant) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getSupportActionBar().setTitle(R.string.obrint);

                mDrawerToggle.syncState();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getSupportActionBar().setTitle(R.string.tancant);

                mDrawerToggle.syncState();
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.ajustes) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

