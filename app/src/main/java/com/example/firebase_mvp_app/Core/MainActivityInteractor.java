package com.example.firebase_mvp_app.Core;

import android.renderscript.Sampler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebase_mvp_app.Model.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivityInteractor implements MainActivityContract.Interactor {

    private MainActivityContract.onOperationListener operationListener;
    ArrayList<Player> players = new ArrayList<>();
    public MainActivityInteractor(MainActivityContract.onOperationListener operationListener) {
        this.operationListener = operationListener;
    }

    @Override
    public void performCreatePlayer(DatabaseReference dbref, Player player) {
        operationListener.onStart();
        dbref.child(player.getKey()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    operationListener.onSuccess();
                    operationListener.onEnd();
                }
                else
                {
                    operationListener.onFaliure();
                    operationListener.onEnd();
                }
            }
        });
    }

    @Override
    public void performReadPlayer(DatabaseReference dbref) {
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player player = dataSnapshot.getValue(Player.class);
                players.add(player);
                operationListener.onRead(players);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player player = dataSnapshot.getValue(Player.class);
                operationListener.onUpdate(player);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Player player = dataSnapshot.getValue(Player.class);
                operationListener.onDelete(player);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void performUpdatePlayer(DatabaseReference dbref, Player player) {
        operationListener.onStart();
        dbref.child(player.getKey()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    operationListener.onEnd();
                }
                else
                {
                    operationListener.onEnd();
                }
            }
        });
    }

    @Override
    public void performDeletePlayer(DatabaseReference dbref, Player player) {
        operationListener.onStart();
        dbref.child(player.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    operationListener.onEnd();
                }
                else
                {
                    operationListener.onEnd();
                }
            }
        });
    }


}
