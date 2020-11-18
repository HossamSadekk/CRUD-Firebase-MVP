package com.example.firebase_mvp_app.Core;

import android.provider.ContactsContract;

import com.example.firebase_mvp_app.Model.Player;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface MainActivityContract {

    interface View
    {
        void onCreatePlayerSuccessful();
        void onCreatePlayerFaliure();
        void onProcessStart();
        void onProcessEnd();
        void onPlayerRead(ArrayList<Player> players);
        void onPlayerUpdate(Player player);
        void onPlayerDeleted(Player player);

    }

    interface Presenter
    {
        void createNewPlayer(DatabaseReference dbref, Player player);
        void readNewPlayer(DatabaseReference dbref);
        void updatePlayer(DatabaseReference dbref,Player player);
        void deletePlayer(DatabaseReference dbtref,Player player);
    }

    interface Interactor
    {
        void performCreatePlayer(DatabaseReference dbref, Player player);
        void performReadPlayer(DatabaseReference dbref);
        void performUpdatePlayer(DatabaseReference dbref,Player player);
        void performDeletePlayer(DatabaseReference dbref,Player player);
    }

    interface onOperationListener
    {
        void onSuccess();
        void onFaliure();
        void onStart();
        void onEnd();
        void onRead(ArrayList<Player> players);
        void onUpdate(Player player);
        void onDelete(Player player);
    }
}
