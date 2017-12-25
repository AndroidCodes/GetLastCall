package com.meta.getlastcall;

import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {

    private java.sql.Connection conn;
    private java.sql.Statement stmt;

    TextView textView;
    TextView Cont;
    String urlService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        textView    =   (TextView)findViewById( R.id.PhoneNumber );
        Cont    =   (TextView)findViewById( R.id.Cont );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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


    public String LastCall() {
        String phNumber;
        StringBuffer sb = new StringBuffer();
        phNumber="";
        Cursor cur = getContentResolver().query( CallLog.Calls.CONTENT_URI,null, null,null, android.provider.CallLog.Calls.DATE + " DESC");

        int number = cur.getColumnIndex( CallLog.Calls.NUMBER );
        int duration = cur.getColumnIndex( CallLog.Calls.DURATION);
        sb.append("Call Details : \n");
        while ( cur.moveToNext() ) {
             phNumber = cur.getString( number );
            String callDuration = cur.getString( duration );
            sb.append( "\nPhone Number:"+phNumber);
            break;
        }
        cur.close();
        String str = phNumber;
        return str;
    }

public void getphone(View view) throws Exception {

String sa;
textView = (TextView) (findViewById(R.id.PhoneNumber));
    sa = LastCall();
    textView.setText(LastCall());

    GetText(sa);


}



    public  void  GetText(String Phn)  throws UnsupportedEncodingException
    {
        // Get user defined values
        String Name, Email, Login, Pass;
        String text111;
        Name = "Name";
        Email   = Phn;
        Login   = "2";
        Pass   = "3";
        text111="";

        StringBuilder sb = new StringBuilder();
       // String http = urlService.concat(urlOperation);

        // Create data variable for sent values to server

        String data = URLEncoder.encode("name", "UTF-8")
                + "=" + URLEncoder.encode(Name, "UTF-8");

        data += "&" + URLEncoder.encode("phone", "UTF-8") + "="
                + URLEncoder.encode(Email, "UTF-8");

        data += "&" + URLEncoder.encode("pwd", "UTF-8")
                + "=" + URLEncoder.encode(Login, "UTF-8");

        data += "&" + URLEncoder.encode("status", "UTF-8")
                + "=" + URLEncoder.encode(Pass, "UTF-8");

        BufferedReader reader=null;

        try
        {

            // Defined URL  where to send data
           // URL url = new URL("http://havana-crm.su/rest_signup.php");
            URL url = new URL("http://havana-crm.su/1.php");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb1 = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb1.append(line + "\n");
            }



            text111 = sb1.toString();
        }
        catch(Exception ex)


        {

            ex.printStackTrace();

        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {

                ex.printStackTrace();

            }
        }


        // Show response on activity
        Cont.setText(text111);

        Log.v("LOG_TAG", "Текст" + text111);

    }





}
