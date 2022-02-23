package com.example.tiktaktoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    int chipColor =0 ;  // 0: yellow  // 1: red

    // 0: yellow  // 1: red  // 2: empty
    int activePlayer = chipColor;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},  //rows
                                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},  //colomns
                                {0, 4, 8}, {2, 4, 6}};            //diagonal

    boolean gameActive = true;

    int redWins = 0;
    int yellowWins = 0;

    //boolean flag; //for draw

    public void dropIn(View view){

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive) {  // to not change the color in the same place && stop when someone win

            gameState[tappedCounter] = activePlayer;


            counter.setTranslationY(-1500);
            if (activePlayer == 0) {

                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;

            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1500).rotation(36000).setDuration(300);

            for (int[] winningPositions : winningPositions) {

                if (gameState[winningPositions[0]] == gameState[winningPositions[1]] && gameState[winningPositions[1]] == gameState[winningPositions[2]] && gameState[winningPositions[0]] != 2) {
                    //someone has won

                    TextView redWinsTV = (TextView) findViewById(R.id.redWins);

                    TextView yellowWinsTV = (TextView) findViewById(R.id.yellowWins);


                    gameActive = false;

                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "Yellow";
                        yellowWins++;
                        yellowWinsTV.setText("Yellow : "+yellowWins);
                    } else {
                        winner = "Red";
                        redWins++;
                        redWinsTV.setText("Red : "+redWins);
                    }

                    TextView winTV = (TextView) findViewById(R.id.winnerTextView);
                    winTV.setText(winner + " won!");
                    winTV.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    public void reset(View view){

        TextView winTV = (TextView) findViewById(R.id.winnerTextView);
        winTV.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i<gridLayout.getChildCount(); i++){

            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }


        Arrays.fill(gameState, 2); //this function
        /*for(int i = 0; i<gameState.length; i++){
            gameState[i]=2;
        }*/

         activePlayer = 1;
         gameActive = true;
         //chooseColor();
    }




    //to choose color
    public void chooseColor(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose your color :");

        builder.setMessage("What color you want?")
                .setCancelable(false)
                .setPositiveButton("Red", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        chipColor = 1;
                    }
                })
                .setNegativeButton("Yellow", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //dialog.cancel();
                        chipColor = 0;
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chooseColor();



        /*Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x/3;
        int screenHeight = size.y/3;*/



    }
}
