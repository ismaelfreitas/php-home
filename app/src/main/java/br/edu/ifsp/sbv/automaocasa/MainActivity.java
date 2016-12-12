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

import org.json.JSONException;
import org.json.JSONObject;

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

        switch (id) {

            case R.id.nav_reles:
                fragment = new RelesFragment();
                fragmentTitle = getString(R.string.title_fragment_reles);
                break;

            case R.id.nav_temperatura:
                fragment = new TemperatureFragment();
                fragmentTitle = getString(R.string.title_fragment_temperature);
                break;

            case R.id.nav_alarme:
                fragment = new AlarmFragment();
                fragmentTitle = getString(R.string.title_fragment_alarm);
                break;

            case R.id.nav_portao:
                fragment = new DoorFragment();
                fragmentTitle = getString(R.string.title_fragment_door);
                break;

            case R.id.nav_manage:

                fragment = new ConfigFragment();
                Bundle bundle = new Bundle();
                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);

                bundle.putString("ip", prefs.getString("ip", ""));
                fragment.setArguments(bundle);

                fragmentTitle = getString(R.string.title_fragment_config);

                break;

            default:
                fragment = new Fragment();
                findViewById(R.id.content_home).setVisibility(View.VISIBLE);
                fragmentTitle = getString(R.string.app_name);
                break;

        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        switch (id) {

            case R.id.nav_reles:
                new AllReleStatus().execute("");
                break;

            case R.id.nav_temperatura:
                new TemperaturaStatus().execute("");
                break;

            case R.id.nav_alarme:
                new AlarmStatus().execute("");
                break;

            case R.id.nav_portao:
                new DoorStatus().execute("");
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

        switch (v.getId()) {

            case R.id.pino03:
                pino = "3";
                break;
            case R.id.pino04:
                pino = "4";
                break;
            case R.id.pino05:
                pino = "5";
                break;
            case R.id.pino06:
                pino = "6";
                break;
            case R.id.pino07:
                pino = "7";
                break;
            case R.id.pino08:
                pino = "8";
                break;
            case R.id.pino09:
                pino = "9";
                break;
            case R.id.pino10:
                pino = "10";
                break;
            case R.id.pino12:
                pino = "12";
                break;
            case R.id.pino13:
                pino = "13";
                break;
            default:
                pino = "11";
                break;

        }

        if( pino != "12" && pino != "13" ){

            imgPino = (ImageView) findViewById(v.getId());

        }

        new ActiveRele().execute("");

    }

    public void savePreferences(View v) {

        EditText txtIp = (EditText) findViewById(R.id.ip);
        String ip = txtIp.getText().toString().toLowerCase();

        SharedPreferences.Editor editor = getSharedPreferences("CONFIGS", MODE_PRIVATE).edit();
        editor.putString("ip", ip.indexOf("http") == -1 ? "http://" + ip : ip);
        editor.commit();

        Toast.makeText(getApplicationContext(), "Configurações salvas", Toast.LENGTH_SHORT).show();

    }

    public void showEndereco() {

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

                resultado = ConnectHttpClient.executaHttpGet(ip + "?rele=all");

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();

            if (result == "") {

                Toast.makeText(getApplicationContext(), "Falha ao obter dados...", Toast.LENGTH_SHORT).show();

            } else {

                JSONObject response;

                try {
                    response = new JSONObject(result);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject error;
                try {

                    error = response.getJSONObject("error");
                    Toast.makeText(getApplicationContext(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                    return;

                } catch (JSONException e) { }

                JSONObject pins;
                try {
                    pins = response.getJSONObject("pins");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                ImageView iPino;

                try {

                    iPino = (ImageView) findViewById(R.id.pino03);
                    iPino.setImageResource(pins.getString("3") == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                    iPino.invalidate();

                } catch (JSONException e) { }

                try {

                    iPino = (ImageView) findViewById(R.id.pino04);
                    iPino.setImageResource(pins.getString("4") == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                    iPino.invalidate();

                } catch (JSONException e) { }

                try {

                    iPino = (ImageView) findViewById(R.id.pino05);
                    iPino.setImageResource(pins.getString("5") == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                    iPino.invalidate();

                } catch (JSONException e) { }

                try {

                    iPino = (ImageView) findViewById(R.id.pino06);
                    iPino.setImageResource(pins.getString("6") == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                    iPino.invalidate();

                } catch (JSONException e) { }

                try {

                    iPino = (ImageView) findViewById(R.id.pino07);
                    iPino.setImageResource(pins.getString("7") == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                    iPino.invalidate();

                } catch (JSONException e) { }

                try {

                    iPino = (ImageView) findViewById(R.id.pino08);
                    iPino.setImageResource(pins.getString("8") == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                    iPino.invalidate();

                } catch (JSONException e) { }

                try {

                    iPino = (ImageView) findViewById(R.id.pino09);
                    iPino.setImageResource(pins.getString("9") == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                    iPino.invalidate();

                } catch (JSONException e) { }

                try {

                    iPino = (ImageView) findViewById(R.id.pino10);
                    iPino.setImageResource(pins.getString("10") == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                    iPino.invalidate();

                } catch (JSONException e) { }

                try {

                    iPino = (ImageView) findViewById(R.id.pino11);
                    iPino.setImageResource(pins.getString("11") == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                    iPino.invalidate();

                } catch (JSONException e) { }

            }

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Obtendo dados...", true);

        }

        @Override
        protected void onProgressUpdate(Void... values) { }

    }

    private class TemperaturaStatus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip + "?temp=t");

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();

            if (result == "") {

                Toast.makeText(getApplicationContext(), "Falha ao obter dados...", Toast.LENGTH_SHORT).show();

            } else {

                JSONObject response;

                try {
                    response = new JSONObject(result);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject error;
                try {

                    error = response.getJSONObject("error");
                    Toast.makeText(getApplicationContext(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                    return;

                } catch (JSONException e) { }

                JSONObject temp;
                try {
                    temp = response.getJSONObject("temp");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                TextView txtTemp;
                try {

                    txtTemp = (TextView) findViewById(R.id.temperatura);
                    txtTemp.setText(temp.getString("t") + "ºC");

                    txtTemp = (TextView) findViewById(R.id.temperatura);
                    txtTemp.setText(temp.getString("u") + "%");

                } catch (JSONException e) { }

            }

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Obtendo dados...", true);

        }

        @Override
        protected void onProgressUpdate(Void... values) { }

    }

    private class AlarmStatus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip + "?rele=all");

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();

            if (result == "") {
                Toast.makeText(getApplicationContext(), "Falha ao obter dados...", Toast.LENGTH_SHORT).show();
            } else {

                JSONObject response;

                try {
                    response = new JSONObject(result);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject error;
                try {

                    error = response.getJSONObject("error");
                    Toast.makeText(getApplicationContext(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                    return;

                } catch (JSONException e) { }

                JSONObject pins;
                try {
                    pins = response.getJSONObject("pins");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    TextView statusAlarm = (TextView) findViewById(R.id.alarme);

                    if (statusAlarm != null) {

                        if (pins.getString("13") == "0") {

                            statusAlarm.setText("Desativado");
                            statusAlarm.setTextColor(Color.parseColor("#ff4444"));

                        } else {

                            statusAlarm.setText("Ativo");
                            statusAlarm.setTextColor(Color.parseColor("#669900"));

                        }

                    }

                } catch (JSONException e) { }

            }

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Obtendo dados...", true);

        }

        @Override
        protected void onProgressUpdate(Void... values) { }

    }

    private class DoorStatus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip + "?rele=all");

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();

            if (result == "") {
                Toast.makeText(getApplicationContext(), "Falha ao obter dados...", Toast.LENGTH_SHORT).show();
            } else {

                JSONObject response;

                try {
                    response = new JSONObject(result);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject error;
                try {

                    error = response.getJSONObject("error");
                    Toast.makeText(getApplicationContext(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                    return;

                } catch (JSONException e) { }

                JSONObject pins;
                try {
                    pins = response.getJSONObject("pins");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    TextView statusAlarm = (TextView) findViewById(R.id.alarme);

                    if (statusAlarm != null) {

                        if (pins.getString("12") == "0") {

                            statusAlarm.setText("Fechado");
                            statusAlarm.setTextColor(Color.parseColor("#ff4444"));

                        } else {

                            statusAlarm.setText("Aberto");
                            statusAlarm.setTextColor(Color.parseColor("#669900"));

                        }

                    }

                } catch (JSONException e) { }

            }

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Obtendo dados...", true);

        }

        @Override
        protected void onProgressUpdate(Void... values) { }

    }

    private class ActiveRele extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip + "?rele=" + pino);

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.hide();

            if (result == "") {

                Toast.makeText(getApplicationContext(), "Falha ao enviar dados...", Toast.LENGTH_SHORT).show();

            } else {

                JSONObject response;

                try {
                    response = new JSONObject(result);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject error;
                try {

                    error = response.getJSONObject("error");
                    Toast.makeText(getApplicationContext(), error.getString("msg"), Toast.LENGTH_SHORT).show();
                    return;

                } catch (JSONException e) { }

                JSONObject pins;
                try {
                    pins = response.getJSONObject("pins");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Resposta do servidor inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pino == "12") {
                    /* Portão */

                    TextView statusDoor = (TextView) findViewById(R.id.door);

                    try {

                        if (pins.getString("12") == "0") {

                            statusDoor.setText("Fechado");
                            statusDoor.setTextColor(Color.parseColor("#ff4444"));

                        } else {

                            statusDoor.setText("Aberto");
                            statusDoor.setTextColor(Color.parseColor("#669900"));

                        }

                    } catch (JSONException e) { }

                } else if (pino == "13") {
                    /* Alarme */

                    TextView statusAlarm = (TextView) findViewById(R.id.alarme);

                    try {

                        if (pins.getString("13") == "0") {

                            statusAlarm.setText("Desativado");
                            statusAlarm.setTextColor(Color.parseColor("#ff4444"));

                        } else {

                            statusAlarm.setText("Ativo");
                            statusAlarm.setTextColor(Color.parseColor("#669900"));

                        }

                    } catch (JSONException e) { }

                } else {

                    try {

                        imgPino.setImageResource(pins.getString(pino) == "1" ? R.drawable.lamp_on : R.drawable.lamp_off);
                        imgPino.invalidate();

                    } catch (JSONException e) { }

                }

            }

        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Enviando dados...", true);

        }

        @Override
        protected void onProgressUpdate(Void... values) { }

    }

    private class CheckOnline extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String resultado = "";

            try {

                SharedPreferences prefs = getSharedPreferences("CONFIGS", MODE_PRIVATE);
                String ip = prefs.getString("ip", "");

                resultado = ConnectHttpClient.executaHttpGet(ip);

                if (ConnectHttpClient.responseStatus == 200) {
                    resultado = "ok";
                }

            } catch (Exception e) { }

            return resultado;

        }

        @Override
        protected void onPostExecute(String result) {

            TextView statusConexao = (TextView) findViewById(R.id.conexao);

            if (statusConexao != null) {

                if (result == "") {

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
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Void... values) { }
    }

}
