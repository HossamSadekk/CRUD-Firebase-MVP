package com.example.firebase_mvp_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebase_mvp_app.Core.MainActivityContract;
import com.example.firebase_mvp_app.Core.MainActivityPresenter;
import com.example.firebase_mvp_app.Model.Player;
import com.example.firebase_mvp_app.R;
import com.example.firebase_mvp_app.Utils.Config;
import com.example.firebase_mvp_app.Utils.CreatePlayerDialog;
import com.example.firebase_mvp_app.Utils.RecyclerViewAdapter;
import com.example.firebase_mvp_app.Utils.UpdatePlayerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CreatePlayerDialog.createPlayerDialogListener , MainActivityContract.View
        , UpdatePlayerDialog.UpdatePlayerDialogListener , RecyclerViewAdapter.onPlayerClickListener {
@BindView(R.id.floatingActionButton)
FloatingActionButton floatingActionButton;
@BindView(R.id.progress_bar)
ProgressBar progressBar;
@BindView(R.id.recyclerview)
RecyclerView recyclerView;

private DatabaseReference dbref;
private MainActivityPresenter presenter;
private RecyclerViewAdapter recyclerViewAdapter;
private ArrayList<Player> mPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // presenter
        presenter = new MainActivityPresenter(this);

        //Firebase realtime database
        dbref = FirebaseDatabase.getInstance().getReference().child(Config.USER_NODE);
        presenter.readNewPlayer(dbref);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

            private void openDialog() {
                CreatePlayerDialog createPlayerDialog = new CreatePlayerDialog();
                createPlayerDialog.show(getSupportFragmentManager(),"created dialog");
            }
        });

    }


    // (((((( Function of Dialog class ))))))
    @Override
    public void savePlayer(Player playerr) {
        String key = dbref.push().getKey();
        Player newPlayer = new Player(playerr.getName(),playerr.getAge(),playerr.getPosition(),key);
        presenter.createNewPlayer(dbref,newPlayer);
    }


    // (((((( Function of View Interface ))))))

    @Override
    public void onCreatePlayerSuccessful() {
        Toast.makeText(MainActivity.this, "New Player Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreatePlayerFaliure() {
        Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProcessEnd() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPlayerRead(ArrayList<Player> players) {
        this.mPlayers = players;
        recyclerViewAdapter = new RecyclerViewAdapter(players,this);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);


    }

    @Override
    public void onPlayerUpdate(Player player) {
        int index = getIndex(player);
        mPlayers.set(index,player);
        recyclerViewAdapter.notifyItemChanged(index);
    }

    @Override
    public void onPlayerDeleted(Player player) {
        int index = getIndex(player);
        mPlayers.remove(index);
        recyclerViewAdapter.notifyItemChanged(index);
    }

    // UpdatePlayerDialog Listener
    @Override
    public void updatePlayer(Player playerr) {
        presenter.updatePlayer(dbref,playerr);
    }

    @Override
    public void onPlayerUpdateClick(int position) {
        Player player = mPlayers.get(position);
        UpdatePlayerDialog updatePlayerDialog = new UpdatePlayerDialog(player);
        updatePlayerDialog.show(getSupportFragmentManager(),"update dialog");

    }

    @Override
    public void onPlayerDeleteClick(int position) {
        Player player = mPlayers.get(position);
        presenter.deletePlayer(dbref,player);
    }

    public int getIndex(Player player)
    {
        int index = 0;
        for(Player countPlayer : mPlayers)
        {
            if(countPlayer.getKey().equals(player.getKey()))
            {
                break;
            }
            index++;
        }
        return index;
    }
}