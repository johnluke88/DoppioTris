package com.example.doppiotris;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class GameGridFragment extends Fragment implements View.OnClickListener{

    private Player player;
    private Button [][] buttons = new Button [3][3];

    private OnButtonPressedListener listener;

    public interface OnButtonPressedListener {
        public void onButtonPressed(int id, Player name);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public GameGridFragment() {
        // Required empty public constructor
    }

    public static GameGridFragment newInstance(String name, boolean player1Turn){
        GameGridFragment ggf = new GameGridFragment();
        Bundle args = new Bundle();
        args.putString("player" , name);
        args.putBoolean("player1Turn" , player1Turn);
        ggf.setArguments(args);
        return ggf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String name = getArguments().getString("player", "");
        boolean turn = getArguments().getBoolean("player1Turn");

        player = new Player(name, turn);

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.game_grid, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for(int i = 0; i< 3; i++) {
            for(int j = 0; j< 3; j++) {
                String idButton = "c" + i + j;
                int resId = getResources().getIdentifier(idButton, "id", getActivity().getPackageName());
                Log.d("MY", "Il valore di res id e'" + resId);
                buttons[i][j] = view.findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }

    public void onClick(View v) {

        if(!((Button) v).getText().equals("")) {
            return;
        }

        String namePlayer = player.getName();
        boolean turnPlayer = player.isTurn();

        if(namePlayer.equalsIgnoreCase("X") && turnPlayer == true) {
            ((Button)v).setText("X");
            ((Button)v).setTag("premuto");
            int id = ((Button)v).getId();
            listener.onButtonPressed(id, player);
        }
        else if (namePlayer.equalsIgnoreCase("O") && turnPlayer == true){
            ((Button) v).setText("O");
            ((Button) v).setTag("premuto");
            int id = ((Button) v).getId();
            listener.onButtonPressed(id, player);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof OnButtonPressedListener) {
            listener = (OnButtonPressedListener) context;
        }
        else {
            throw new ClassCastException(context.toString() + "Non può esserci il cast a OnButtonPressedListener");
        }
    }
    public boolean checkForWin() {
        String[][] cells = new String [3][3];
        for(int i = 0; i< 3; i++) {
            for(int j = 0; j< 3; j++) {
                cells[i][j] = buttons[i][j].getText().toString();
            }
        }

        for(int i = 0; i< 3; i++) {
            if(cells[i][0].equals(cells[i][1]) && cells[i][0].equals(cells[i][2]) && !cells[i][0].equals(""))
                return true;
        }

        for(int i = 0; i< 3; i++) {
            if(cells[0][i].equals(cells[1][i]) && cells[0][i].equals(cells[2][i]) && !cells[0][i].equals(""))
                return true;
        }

        if(cells[0][0].equals(cells[1][1]) && cells[0][0].equals(cells[2][2]) && !cells[0][0].equals(""))
            return true;

        if(cells[0][2].equals(cells[1][1]) && cells[0][2].equals(cells[2][0]) && !cells[0][2].equals(""))
            return true;

        return false;
    }
    /*
    public void draw() {
        Toast.makeText(getActivity().getApplicationContext(), "Pareggio!",  Toast.LENGTH_LONG).show();
        for(int i = 0; i< 3; i++) {
            for(int j = 0; j< 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
    }*/
}