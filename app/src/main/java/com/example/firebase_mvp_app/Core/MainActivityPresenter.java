package com.example.firebase_mvp_app.Core;

import com.example.firebase_mvp_app.Model.Player;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivityPresenter implements MainActivityContract.Presenter , MainActivityContract.onOperationListener {

    private MainActivityContract.View viewListener;
    private MainActivityInteractor interactor;

    public MainActivityPresenter(MainActivityContract.View viewListener) {
        this.viewListener = viewListener;
        interactor = new MainActivityInteractor(this);
    }

    @Override
    public void createNewPlayer(DatabaseReference dbref, Player player) {
        interactor.performCreatePlayer(dbref,player);
    }

    @Override
    public void readNewPlayer(DatabaseReference dbref) {
        interactor.performReadPlayer(dbref);
    }

    @Override
    public void updatePlayer(DatabaseReference dbref, Player player) {
        interactor.performUpdatePlayer(dbref,player);

    }

    @Override
    public void deletePlayer(DatabaseReference dbtref, Player player) {
        interactor.performDeletePlayer(dbtref,player);
    }

    @Override
    public void onSuccess() {
        viewListener.onCreatePlayerSuccessful();
    }

    @Override
    public void onFaliure() {
        viewListener.onCreatePlayerFaliure();
    }

    @Override
    public void onStart() {
        viewListener.onProcessStart();
    }

    @Override
    public void onEnd() {
        viewListener.onProcessEnd();
    }

    @Override
    public void onRead(ArrayList<Player> players) {
     viewListener.onPlayerRead(players);
    }
    // operation
    @Override
    public void onUpdate(Player player) {
        viewListener.onPlayerUpdate(player);
    }

    @Override
    public void onDelete(Player player) {
        viewListener.onPlayerDeleted(player);
    }
}
