package com.example.tiktactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton playerStatus;
    private ImageButton[] buttons = new ImageButton[9];
    private Button resetGame;
    private ImageView resultmark;

    private int rountCount;
    boolean activePlayer;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},//rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},//cols
            {0, 4, 8}, {2, 4, 6}//cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerStatus = (ImageButton) findViewById(R.id.playerStatus);
        playerStatus.setImageResource(R.drawable.xplay);
        resetGame = (Button) findViewById(R.id.restButton);
        resultmark = (ImageView) findViewById(R.id.resulrMark);
        rountCount = 0;
        activePlayer = true;

        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn" + i;
            int resourceId = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (ImageButton) findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(rountCount > 8){
            return;
        }
        int gameStatePointer = Integer.parseInt(view.getTag().toString());

        if (gameState[gameStatePointer] != 2) {
            return;
        }

        if (activePlayer) {
            ((ImageButton) view).setImageResource(R.drawable.x);
            gameState[gameStatePointer] = 0;
        } else {
            ((ImageButton) view).setImageResource(R.drawable.o);
            gameState[gameStatePointer] = 1;
        }

        rountCount++;

        if(checkWinner()){
            if(activePlayer){
                ((ImageButton) playerStatus).setImageResource(R.drawable.xwin);
            }else{
                ((ImageButton) playerStatus).setImageResource(R.drawable.owin);

            }
        }else if(rountCount == 9){
            ((ImageButton) playerStatus).setImageResource(R.drawable.nowin);
        }else{
            activePlayer = !activePlayer;
            if(activePlayer){
                ((ImageButton) playerStatus).setImageResource(R.drawable.xplay);
            }else{
                ((ImageButton) playerStatus).setImageResource(R.drawable.oplay);
            }
        }

    }

    public boolean checkWinner() {
        boolean winnerResult = false;

        for (int i=0;i<winningPositions.length;i++) {
            int [] winningPosition = winningPositions[i];
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                winnerResult = true;
                String imageId = "mark" + i;
                int resourceId = getResources().getIdentifier(imageId,"drawable",getPackageName());
                ((ImageView) resultmark).setImageResource(resourceId);
                resultmark.bringToFront();
                rountCount =9;
            }
        }

        return winnerResult;
    }

    public void playAgain() {
        rountCount = 0;
        activePlayer = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setImageResource(R.drawable.empty);
        }
        playerStatus.setImageResource(R.drawable.xplay);
        resultmark.setImageResource(R.drawable.empty);
    }
}