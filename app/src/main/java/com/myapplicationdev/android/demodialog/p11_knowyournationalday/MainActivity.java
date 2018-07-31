package com.myapplicationdev.android.demodialog.p11_knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.LocaleDisplayNames;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv_national;
    ArrayList<String> al;
    ArrayAdapter<String> aa;
    String [] list = new String[] { "Singapore National Day is on 9 Aug", "Singapore is 52 years old", "Theme is '#OneNationTogether'"};
    String access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_national = findViewById(R.id. lv_national);
        al = new ArrayList<String>();
        for(int i = 0; i < list.length; i++){
            al.add(list[i]);
            aa = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, al);
            lv_national.setAdapter(aa);
        }

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout accessCode =
                (LinearLayout) inflater.inflate(R.layout.activtity_access_code, null);
        final EditText etAccessCode = (EditText) accessCode
                .findViewById(R.id.editTextAccessCode);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please login")
                .setView(accessCode)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(etAccessCode.getText().toString().equals("738964")){
                            access = etAccessCode.getText().toString();
                            Toast.makeText(MainActivity.this, "You have access " +
                                    etAccessCode.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Wrong access code", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                })
                .setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this, "Please visit RP website",
                            Toast.LENGTH_LONG).show();
                        }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        lv_national.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String [] send = new String[] { "Email", "SMS"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select the way to enrich your friend")
                        // Set the list of items easily by just supplying an
                        //  array of the items
                        .setItems(send, new DialogInterface.OnClickListener() {
                            // The parameter "which" is the item index
                            // clicked, starting from 0
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    String message = al.get(position);

                                    Intent email = new Intent(Intent.ACTION_SEND);

                                    email.putExtra(Intent.EXTRA_EMAIL,
                                            new String[]{"jason_lim@rp.edu.sg"});
                                    email.putExtra(Intent.EXTRA_SUBJECT,
                                            "Hi faci, this is the email dialog");
                                    email.putExtra(Intent.EXTRA_TEXT,
                                            message);
                                    email.setType("message/rfc822");
                                    startActivity(Intent.createChooser(email,
                                            "Choose an Email client :"));
                                    Snackbar sb = Snackbar.make(lv_national, "Email send", Snackbar.LENGTH_SHORT);
                                    sb.show();
                                }else {
                                    Intent intent = new Intent("com.example.broadcast.SEND_BROADCAST");
                                    sendBroadcast(intent);

                                    SmsManager smsManager = SmsManager.getDefault();
                                    //String to = etTo.getText().toString();
                                    String content = al.get(position);
                                    smsManager.sendTextMessage("5554", null, content, null, null);
                                    Snackbar sb = Snackbar.make(lv_national, "SMS send", Snackbar.LENGTH_SHORT);
                                    sb.show();
                                }
                                finish();

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access", access);
        editor.commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String access_code = prefs.getString("access", "");
        al.clear();
        al = new ArrayList<String>();
        for(int i = 0; i < list.length; i++){
            al.add(list[i]);
            aa = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, al);
            lv_national.setAdapter(aa);
        }

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout accessCode =
                (LinearLayout) inflater.inflate(R.layout.activtity_access_code, null);
        final EditText etAccessCode = (EditText) accessCode
                .findViewById(R.id.editTextAccessCode);

        if(access_code.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please login")
                    .setView(accessCode)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if(etAccessCode.getText().toString().equals("738964")){
                                access = etAccessCode.getText().toString();
                                Toast.makeText(MainActivity.this, "You have access " +
                                        etAccessCode.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Wrong access code", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    })
                    .setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "Please visit RP website",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            lv_national.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    String [] send = new String[] { "Email", "SMS"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Select the way to enrich your friend")
                            // Set the list of items easily by just supplying an
                            //  array of the items
                            .setItems(send, new DialogInterface.OnClickListener() {
                                // The parameter "which" is the item index
                                // clicked, starting from 0
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        String message = al.get(position);

                                        Intent email = new Intent(Intent.ACTION_SEND);

                                        email.putExtra(Intent.EXTRA_EMAIL,
                                                new String[]{"jason_lim@rp.edu.sg"});
                                        email.putExtra(Intent.EXTRA_SUBJECT,
                                                "Hi faci, this is the email dialog");
                                        email.putExtra(Intent.EXTRA_TEXT,
                                                message);
                                        email.setType("message/rfc822");
                                        startActivity(Intent.createChooser(email,
                                                "Choose an Email client :"));
                                        Snackbar sb = Snackbar.make(lv_national, "Email send", Snackbar.LENGTH_SHORT);
                                        sb.show();
                                    }else {
                                        Intent intent = new Intent("com.example.broadcast.SEND_BROADCAST");
                                        sendBroadcast(intent);

                                        SmsManager smsManager = SmsManager.getDefault();
                                        //String to = etTo.getText().toString();
                                        String content = al.get(position);
                                        smsManager.sendTextMessage("5554", null, content, null, null);
                                        Snackbar sb = Snackbar.make(lv_national, "SMS send", Snackbar.LENGTH_SHORT);
                                        sb.show();
                                    }
                                    finish();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }else{

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sendToFriend) {
            String [] send = new String[] { "Email", "SMS"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(send, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                String message = "";

                                Intent email = new Intent(Intent.ACTION_SEND);

                                email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"jason_lim@rp.edu.sg"});
                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "Hi faci, this is the email dialog");
                                for(int i = 0; i < list.length; i++) {
                                    message += list[i] + "\n";
                                }
                                email.putExtra(Intent.EXTRA_TEXT,
                                        message);
                                email.setType("message/rfc822");
                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));
                                Snackbar sb = Snackbar.make(lv_national, "Email send", Snackbar.LENGTH_SHORT);
                                sb.show();
                            }else {
                                Intent intent = new Intent("com.example.broadcast.SEND_BROADCAST");
                                sendBroadcast(intent);

                                SmsManager smsManager = SmsManager.getDefault();
                                //String to = etTo.getText().toString();
                                String content="";
                                for(int i = 0; i < list.length; i++) {
                                    content += list[i] + "\n";
                                }
                                smsManager.sendTextMessage("5554", null, content, null, null);
                                Snackbar sb = Snackbar.make(lv_national, "SMS send", Snackbar.LENGTH_SHORT);
                                sb.show();
                            }
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if(item.getItemId() == R.id.quiz){
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout quiz =
                    (LinearLayout) inflater.inflate(R.layout.activity_quiz, null);
            //final EditText etAccessCode = (EditText) quiz.findViewById(R.id.editTextAccessCode);
            final RadioGroup rg1 = quiz.findViewById(R.id. rg1);
            final RadioGroup rg2 = quiz.findViewById(R.id. rg2);
            final RadioGroup rg3 = quiz.findViewById(R.id. rg3);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(quiz)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //rg1
                            int selectedButtonId1 = rg1.getCheckedRadioButtonId();
                            RadioButton rb1 = quiz.findViewById(selectedButtonId1);
                            String rbAns1 = rb1.getText().toString();
                            Log.e("rbAns", rb1.getText().toString());
                            //rg2
                            int selectedButtonId2 = rg2.getCheckedRadioButtonId();
                            RadioButton rb2 = quiz.findViewById(selectedButtonId2);
                            String rbAns2 = rb2.getText().toString();
                            Log.e("rbAns", rb2.getText().toString());
                            //rg3
                            int selectedButtonId3 = rg3.getCheckedRadioButtonId();
                            RadioButton rb3 = quiz.findViewById(selectedButtonId3);
                            String rbAns3 = rb3.getText().toString();
                            Log.e("rbAns", rb3.getText().toString());

                            if (rbAns1.equals("No") && rbAns2.equals("Yes") && rbAns3.equals("Yes")){
                                Toast.makeText(MainActivity.this, "You've score full points!", Toast.LENGTH_SHORT).show();
                            }else if (rbAns1.equals("Yes") && rbAns2.equals("Yes") && rbAns3.equals("Yes")|| rbAns1.equals("No") && rbAns2.equals("No") && rbAns3.equals("Yes") || rbAns1.equals("No") && rbAns2.equals("Yes") && rbAns3.equals("No")){
                                Toast.makeText(MainActivity.this, "You've scored 2 points!", Toast.LENGTH_SHORT).show();
                            }else if (rbAns1.equals("No") && rbAns2.equals("No") && rbAns3.equals("No")|| rbAns1.equals("Yes") && rbAns2.equals("Yes") && rbAns3.equals("No") || rbAns1.equals("Yes") && rbAns2.equals("No") && rbAns3.equals("Yes")){
                                Toast.makeText(MainActivity.this, "You've scored 1 points!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "You've score 0 points...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Don't know lah", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "Don't worry, you can try again later.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if(item.getItemId() == R.id. quit){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "You clicked yes", Toast.LENGTH_LONG).show();
                            access = "";
                            finish();
                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent("com.example.broadcast.SEND_BROADCAST");
                            sendBroadcast(intent);

                            SmsManager smsManager = SmsManager.getDefault();
                            //String to = etTo.getText().toString();
                            //String content = etContent.getText().toString();
                            smsManager.sendTextMessage("5554", null, "P11 C347 SMS", null, null);
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return true;
    }
}
