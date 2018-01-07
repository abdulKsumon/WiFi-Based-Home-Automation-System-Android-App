package com.example.sumon.wifihomeautomation;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {


    final Context context = this;
    private EditText ipAddress;
   // private Button ledOn1, ledOff1 ,ledOn2,ledOff2 ,about;
    private ImageButton ledOn1,ledOff1,ledOn2,ledOff2 ,about;;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ipAddress = (EditText) findViewById(R.id.edt_ip);
        ledOn1 = (ImageButton) findViewById(R.id.btn_ledOn);
        ledOff1 = (ImageButton) findViewById(R.id.btn_ledOff);
        ledOn2 = (ImageButton) findViewById(R.id.btn_ledOn2);
        ledOff2 = (ImageButton) findViewById(R.id.btn_ledOn2);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.supervisior) {
            Toast.makeText(getApplicationContext(),"supervisior activity clicked",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(MainActivity.this,Super.class);
            startActivity(intent);

        } else if (id == R.id.patnars) {
            Toast.makeText(getApplicationContext(),"patnars activity clicked",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(MainActivity.this,patnars.class);
            startActivity(intent);

        }
        return true;
    }


    /* When the button clicks this method executes**/

    public void onClick(View view) {
        String ledStatus;

        if (ipAddress.getText().toString().equals(""))
           Toast.makeText(MainActivity.this, "Please enter the ip address...", Toast.LENGTH_SHORT).show();

        else {
            if (view == ledOn1)
                ledStatus = "A";

            else
                ledStatus = "a";

            //Connect to default port number. Ex: http://IpAddress:80
            String serverAdress = ipAddress.getText().toString() + ":" + "80";
            HttpRequestTask requestTask = new HttpRequestTask(serverAdress);
            requestTask.execute(ledStatus);
        }
    }




    public void onClick2(View view) {
        String ledStatus;

        if (ipAddress.getText().toString().equals(""))
            Toast.makeText(MainActivity.this, "Please enter the ip address...", Toast.LENGTH_SHORT).show();

        else {
            if (view == ledOn2)
                ledStatus = "B";

            else
                ledStatus = "b";

            //Connect to default port number. Ex: http://IpAddress:80
            String serverAdress = ipAddress.getText().toString() + ":" + "80";
            HttpRequestTask requestTask = new HttpRequestTask(serverAdress);
            requestTask.execute(ledStatus);
        }
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {

        private String serverAdress;
        private String serverResponse = "";
        private AlertDialog dialog;

        public HttpRequestTask(String serverAdress) {
            this.serverAdress = serverAdress;

            dialog = new AlertDialog.Builder(context)
                    .setTitle("HTTP Response from Ip Address:")
                    .setCancelable(true)
                    .create();
        }

        @Override
        protected String doInBackground(String... params) {
            dialog.setMessage("Data sent , waiting response from server...");

            if (!dialog.isShowing())
                dialog.show();

            String val = params[0];
            final String url = "http://" + serverAdress + "/led/" + val;




            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet();
                getRequest.setURI(new URI(url));

                HttpResponse response = client.execute(getRequest);

                InputStream inputStream = null;
                inputStream = response.getEntity().getContent();
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream));

                serverResponse = bufferedReader.readLine();
                inputStream.close();

            } catch (URISyntaxException e) {
                e.printStackTrace();
                serverResponse = e.getMessage();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                serverResponse = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                serverResponse = e.getMessage();
            }

            return serverResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.setMessage(serverResponse);

            if (!dialog.isShowing())
                dialog.show();
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Sending data to server, please wait...");

            if (!dialog.isShowing())
                dialog.show();
        }
    }
}
