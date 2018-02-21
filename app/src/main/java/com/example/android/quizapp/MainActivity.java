package com.example.android.quizapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //array for images that will be displayed in ImageView imageNrOne. Hopefully ;-)
    int[] imageArray = {R.drawable.gaso1, R.drawable.gaso2, R.drawable.gaso3, R.drawable.gaso4};
    // int where i will store Your points ^_^
    int points = 0;
    //here i declared some global variables that we will use later. In first try it was much messier T_T
    RadioGroup groupNrOne, groupNrTwo;
    RadioButton galerie, naples;
    EditText nameHere, country;
    ImageView imageNrOne;
    Handler handler = new Handler();
    CheckBox checkGaso, checkFrance, checkVienna, checkMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ok, here we will find some important stuff for right answers ^_^
        galerie = findViewById(R.id.galerie);
        nameHere = findViewById(R.id.nameHere);
        imageNrOne = findViewById(R.id.imageNrOne);
        checkGaso = findViewById(R.id.checkGasometer);
        checkFrance = findViewById(R.id.checkFrance);
        checkVienna = findViewById(R.id.checkVienna);
        checkMovie = findViewById(R.id.checkMovies);
        country = findViewById(R.id.country);
        naples = findViewById(R.id.naples);
        groupNrOne = findViewById(R.id.groupNrOne);
        groupNrTwo = findViewById(R.id.groupNrTwo);
        //This will change image in ImageView called ImageNrOne every 3 sec. o_O
        //Solution in based on: https://stackoverflow.com/questions/5167541/how-to-change-images-on-imageview-after-some-interval
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                imageNrOne.setImageResource(imageArray[i]);
                i++;
                //from 0 to ArrayLength -1, because arrays starts at 0 ^_^
                if (i > imageArray.length - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    //intent that displays map for user to show him location :-)
    public void showMapForUser(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //here we will use zoom 17, 23 is max possible. At my first attempt i set it to 250, good times XD
        intent.setData(Uri.parse("geo:50.847217, 4.354629?z=17"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //Now i begin to check questions and add points. First question is for radiobutton
    private void firstQuestion() {
        if (galerie.isChecked()) {
            points++;
        }
    }

    //Here i will check for answers from checkboxes
    private void secondQuestion() {
        if (checkFrance.isChecked()) {
            return;
        } else if (checkGaso.isChecked() && checkVienna.isChecked() && checkMovie.isChecked()) {
            points++;
        }
    }

    //function for 3rd question
    private void thirdQuestion() {
        //comparing user input to answer
        if (country.getText().toString().toLowerCase().equals("poland") || country.getText().toString().toLowerCase().equals("polska")) {
            points++;
        }
    }

    //function for 4th question
    private void fourthQuestion() {
        if (naples.isChecked()) {
            points++;
        }
    }

    public void checkScores(View view) {
        //ok, so i will check if user provided any name getting text from EditText
        if (nameHere.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "You didn't enter Your name", Toast.LENGTH_SHORT).show();
        } else {
            String nameOfUser = nameHere.getText().toString();
            firstQuestion();
            secondQuestion();
            thirdQuestion();
            fourthQuestion();
            String endMessage = nameOfUser + " You have " + points + " out of 4 points";
            Toast.makeText(getApplicationContext(), endMessage, Toast.LENGTH_SHORT).show();
            //resetting scores and other for new quiz ^_^
            points = 0;
            groupNrOne.clearCheck();
            groupNrTwo.clearCheck();
            checkGaso.setChecked(false);
            checkFrance.setChecked(false);
            checkVienna.setChecked(false);
            checkMovie.setChecked(false);
            //I didn't clear name field on purpose
            country.setText("");
        }

    }

}
