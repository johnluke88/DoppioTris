package com.example.doppiotris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GameGridFragment.OnButtonPressedListener {

    private Button btn;
    private int roundCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.player1, GameGridFragment.newInstance("X", true));
        ft.add(R.id.player2, GameGridFragment.newInstance("O", false));
        ft.commit();
    }

    @Override
    public void onButtonPressed(int id, Player player) {
        String name = player.getName();
        if (name.equalsIgnoreCase("X")) {
            View v = findViewById(R.id.player2);
            btn = (Button) v.findViewById(id);
            btn.setTag("premuto");
            if (btn.getTag() == null || btn.getTag().toString().equals("premuto")) {
                roundCount++;
                btn.setText(name);
                // Prendo riferimento alla griglia del primo giocatore
                GameGridFragment grid1 = (GameGridFragment) getSupportFragmentManager().findFragmentById(R.id.player1);
                if (grid1.checkForWin()) {
                    Toast.makeText(getApplicationContext(), "IL Giocatore X ha vinto!!", Toast.LENGTH_LONG).show();
                    onReset();
                }
                else if (roundCount == 9) {
                    Toast.makeText(getApplicationContext(), "PAREGGIO!!", Toast.LENGTH_LONG).show();
                    onReset();
                }
                else {
                    player.setTurn(!player.isTurn());
                    GameGridFragment grid2 = (GameGridFragment) getSupportFragmentManager().findFragmentById(R.id.player2);
                    Player player2 = grid2.getPlayer();
                    player2.setTurn(!player2.isTurn());
                }
            }

        } else if (name.equalsIgnoreCase("O")) {
            View v = findViewById(R.id.player1);
            btn = (Button) v.findViewById(id);
            btn.setTag("premuto");
            if (btn.getTag() == null || btn.getTag().toString().equals("premuto")) {
                roundCount++;
                btn.setText(name);
                // Prendo riferimento alla griglia del primo giocatore
                GameGridFragment grid2 = (GameGridFragment) getSupportFragmentManager().findFragmentById(R.id.player2);
                if (grid2.checkForWin()) {
                    Toast.makeText(getApplicationContext(), "IL Giocatore O ha vinto!!", Toast.LENGTH_LONG).show();
                    onReset();
                }
                else if (roundCount == 9) {
                    Toast.makeText(getApplicationContext(), "PAREGGIO!!", Toast.LENGTH_LONG).show();
                    onReset();
                }
                else {
                    player.setTurn(!player.isTurn());
                    GameGridFragment grid1 = (GameGridFragment) getSupportFragmentManager().findFragmentById(R.id.player1);
                    Player player1 = grid1.getPlayer();
                    player1.setTurn(!player1.isTurn());
                }
            }
        }

    }

    public void onReset() {
        View view = findViewById(R.id.player1);

        ArrayList<View> allBtn = view.getTouchables();
        for(View elem: allBtn) {
            if(elem!= null) {
                Button btn = (Button) elem;
                btn.setText("");
            }
        }

        View view2 = findViewById(R.id.player2);

        ArrayList<View> allBtn2 = view2.getTouchables();
        for(View elem: allBtn2) {
            if(elem!= null) {
                Button btn = (Button) elem;
                btn.setText("");
            }
        }
    }

}
