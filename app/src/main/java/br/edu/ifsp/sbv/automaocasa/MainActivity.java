package br.edu.ifsp.sbv.automaocasa;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dialog = ProgressDialog.show(MainActivity.this, "",
                "Obtendo dados...", true);

        new GetAllStatus().execute("");

    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String fragmentTitle = null;

        switch ( id ) {

            case R.id.nav_reles :
                fragment = new RelesFragment();
                fragmentTitle = getString(R.string.title_fragment_reles);
                break;

            case R.id.nav_temperatura :
                fragment = new TemperatureFragment();
                fragmentTitle = getString(R.string.title_fragment_temperature);
                break;

            case R.id.nav_alarme :
                fragment = new AlarmFragment();
                fragmentTitle = getString(R.string.title_fragment_alarm);
                break;

            case R.id.nav_manage :
                fragment = new ConfigFragment();
                fragmentTitle = getString(R.string.title_fragment_config);
                break;

            default :
                Toast.makeText(getApplicationContext(), "Aeeeeeeeeeee", Toast.LENGTH_SHORT).show();
                break;

        }

        if( fragment != null ){

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            setTitle(fragmentTitle);

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void toggleRele(View v) {

        String pino = "";
        ImageView imgPino;

        switch ( v.getId() ) {

            case R.id.pino03 :

                pino = "pino03";
                imgPino = (ImageView) findViewById(R.id.pino03);

                break;


            case R.id.pino04 :

                pino = "pino04";
                imgPino = (ImageView) findViewById(R.id.pino04);

                break;

            case R.id.pino05 :

                pino = "pino05";
                imgPino = (ImageView) findViewById(R.id.pino05);

                break;

            case R.id.pino06 :

                pino = "pino06";
                imgPino = (ImageView) findViewById(R.id.pino06);

                break;

            case R.id.pino07 :

                pino = "pino07";
                imgPino = (ImageView) findViewById(R.id.pino07);

                break;

            case R.id.pino08 :

                pino = "pino08";
                imgPino = (ImageView) findViewById(R.id.pino08);

                break;

            case R.id.pino09 :

                pino = "pino09";
                imgPino = (ImageView) findViewById(R.id.pino09);

                break;

            case R.id.pino10 :

                pino = "pino10";
                imgPino = (ImageView) findViewById(R.id.pino10);

                break;

            case R.id.pino11 :

                pino = "pino11";
                imgPino = (ImageView) findViewById(R.id.pino11);

                break;

            case R.id.pino12 :

                pino = "pino12";
                imgPino = (ImageView) findViewById(R.id.pino12);

                break;

            case R.id.pino13 :

                pino = "pino13";
                imgPino = (ImageView) findViewById(R.id.pino13);

                break;

            default :

                pino = "pino14";
                imgPino = (ImageView) findViewById(R.id.pino14);

                break;

        }

        if ( pino != "" ) {

            imgPino.setImageResource(R.drawable.lamp_off);
            imgPino.invalidate();

        }

    }

    private class GetAllStatus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "ok";

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();
            drawer.openDrawer(GravityCompat.START);

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
