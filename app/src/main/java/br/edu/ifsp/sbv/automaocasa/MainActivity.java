package br.edu.ifsp.sbv.automaocasa;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ProgressDialog dialog;

    String pino = "";
    ImageView imgPino;

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

        new CheckOnline().execute("");

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

        findViewById(R.id.content_home).setVisibility(View.GONE);

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
                Bundle bundle = new Bundle();
                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);

                bundle.putString("ip", prefs.getString("ip", ""));
                fragment.setArguments(bundle);

                fragmentTitle = getString(R.string.title_fragment_config);

                break;

            default :
                fragment = new Fragment();
                findViewById(R.id.content_home).setVisibility(View.VISIBLE);
                fragmentTitle = getString(R.string.app_name);
                break;

        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        switch ( id ) {

            case R.id.nav_reles :
                new AllReleStatus().execute("");
                break;

            case R.id.nav_temperatura :
                new TemperaturaStatus().execute("");
                break;

            case R.id.nav_alarme :
                new AlarmStatus().execute("");
                break;

            case R.id.nav_home:
                new CheckOnline().execute("");
                break;

        }

        setTitle(fragmentTitle);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void toggleRele(View v) {

        switch ( v.getId() ) {

            case R.id.pino03 :
                pino = "3";
                imgPino = (ImageView) findViewById(R.id.pino03);
                break;


            case R.id.pino04 :
                pino = "4";
                imgPino = (ImageView) findViewById(R.id.pino04);
                break;

            case R.id.pino05 :
                pino = "5";
                imgPino = (ImageView) findViewById(R.id.pino05);
                break;

            case R.id.pino06 :
                pino = "6";
                imgPino = (ImageView) findViewById(R.id.pino06);
                break;

            case R.id.pino07 :
                pino = "7";
                imgPino = (ImageView) findViewById(R.id.pino07);
                break;

            case R.id.pino08 :
                pino = "8";
                imgPino = (ImageView) findViewById(R.id.pino08);
                break;

            case R.id.pino09 :
                pino = "9";
                imgPino = (ImageView) findViewById(R.id.pino09);
                break;

            case R.id.pino10 :
                pino = "10";
                imgPino = (ImageView) findViewById(R.id.pino10);
                break;

            case R.id.pino11 :
                pino = "11";
                imgPino = (ImageView) findViewById(R.id.pino11);
                break;

            case R.id.pino12 :
                pino = "12";
                imgPino = (ImageView) findViewById(R.id.pino12);
                break;

            case R.id.pino13 :
                pino = "13";
                imgPino = (ImageView) findViewById(R.id.pino13);
                break;

            default :
                pino = "14";
                imgPino = (ImageView) findViewById(R.id.pino14);
                break;

        }

        new ActiveRele().execute("");

    }

    public void savePreferences(View v){

        EditText txtIp = (EditText) findViewById(R.id.ip);
        String ip = txtIp.getText().toString().toLowerCase();

        SharedPreferences.Editor editor = getSharedPreferences("CONFIGS", MODE_PRIVATE).edit();
        editor.putString("ip", ip.indexOf("http") == -1 ? "http://" + ip : ip);
        editor.commit();

        Toast.makeText(getApplicationContext(), "Configurações salvas", Toast.LENGTH_SHORT).show();

    }

    public  void showEndereco(){

        SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
        String ip = prefs.getString("ip", null);
        if (ip == null) {
            ip = "";
        }

        TextView txtIp = (TextView) findViewById(R.id.endereco);
        txtIp.setText(ip);

    }

    private class AllReleStatus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip + "/rele/all");

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();

            if ( result == "" ) {
                Toast.makeText(getApplicationContext(), "Falha ao obter dados...", Toast.LENGTH_SHORT).show();
            } else {

                //@TODO mudar imagem reles

            }

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Obtendo dados...", true);

        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private class TemperaturaStatus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip + "/temperature/all");

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();

            if ( result == "" ) {
                Toast.makeText(getApplicationContext(), "Falha ao obter dados...", Toast.LENGTH_SHORT).show();
            } else {

                //@TODO mudar temperatura e umidade

            }

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Obtendo dados...", true);

        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private class AlarmStatus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip + "/alarm/all");

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();

            if ( result == "" ) {
                Toast.makeText(getApplicationContext(), "Falha ao obter dados...", Toast.LENGTH_SHORT).show();
            } else {

                //@TODO mudar o status do alarme

            }

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Obtendo dados...", true);

        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private class ActiveRele extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip + "/rele/" + pino);

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();

            if ( result == "" ) {
                Toast.makeText(getApplicationContext(), "Falha ao enviar dados...", Toast.LENGTH_SHORT).show();
            } else {

                //@TODO mudar o status do rele

//            imgPino.setImageResource(R.drawable.lamp_on);
//            imgPino.setImageResource(R.drawable.lamp_off);
//            imgPino.invalidate();

            }

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Enviando dados...", true);

        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private class CheckOnline extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip);

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            TextView statusConexao = (TextView) findViewById(R.id.conexao);

            if ( statusConexao != null ) {

                if( result == "" ){

                    statusConexao.setText("Offline");
                    statusConexao.setTextColor(Color.parseColor("#ff4444"));

                } else {

                    statusConexao.setText("Online");
                    statusConexao.setTextColor(Color.parseColor("#669900"));

                }

                showEndereco();

            }

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
