package com.example.administrator.sportsspeaker;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity   {
    @Override
    protected void onStart() {
        super.onStart();

    }

    private EditText textViewKickSpeed,textViewKickPower;
    private Button button,btnGetAllData;
    private TextView getDataText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewKickSpeed = findViewById(R.id.textKickSpeed);
        textViewKickPower = findViewById(R.id.textKickPower);
        button = findViewById(R.id.submitbutton);
        getDataText = findViewById(R.id.txtGetdata);
        btnGetAllData = findViewById(R.id.getAllDataBtn);
        buttonClicked();

    }
    private void buttonClicked(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject kickBoxing = new ParseObject("KickBoxing");
                kickBoxing.put("KickSpeed",Integer.parseInt(textViewKickSpeed.getText().toString()));
                kickBoxing.put("KickPower",Integer.parseInt(textViewKickPower.getText().toString()));
                kickBoxing.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){

                            FancyToast.makeText(MainActivity.this,"Successfully uploaded ",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true)
                                    .show();
                        }else{
                            FancyToast.makeText(MainActivity.this,"uploaded failed",FancyToast.LENGTH_LONG,FancyToast.ERROR,true)
                                    .show();
                        }
                    }
                });
            }
        });
        getDataText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxing");
                parseQuery.getInBackground("3OSLrjVKMW", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object != null && e == null){

                                getDataText.setText(object.get("KickSpeed").toString());

                        }
                    }
                });
            }
        });
        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> getAllData = ParseQuery.getQuery("KickBoxing");
                getAllData.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        String message = "";
                        if(e == null && objects.size()>0){
                            for(ParseObject parseObject:objects){
                                message = message + parseObject.get("KickSpeed") +" "+parseObject.get("KickPower")+"\n";
                            }
                            FancyToast.makeText(MainActivity.this,message,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        }
                    }
                });
            }
        });
    }

}
