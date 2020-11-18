package com.example.firebase_mvp_app.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.firebase_mvp_app.Model.Player;
import com.example.firebase_mvp_app.R;

public class UpdatePlayerDialog extends AppCompatDialogFragment {

    UpdatePlayerDialog.UpdatePlayerDialogListener listener;
    public EditText Name;
    public EditText Age;
    public EditText Position;
    public Button Save;

    public Player player;

    public UpdatePlayerDialog(Player player) {
        this.player = player;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //getActivity() in a Fragment returns the Activity the Fragment is currently associated with
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog,null);

        Name = v.findViewById(R.id.et_Name);
        Age = v.findViewById(R.id.et_Age);
        Position = v.findViewById(R.id.et_Position);
        Save = v.findViewById(R.id.btn_Save);
        builder.setView(v);
        builder.setTitle("Update Player");

        Name.setText(player.getName());
        Age.setText(player.getAge());
        Position.setText(player.getPosition());

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString();
                String age = Age.getText().toString();
                String position = Position.getText().toString();

                if(name.isEmpty() || age.isEmpty() || position.isEmpty())
                {
                    Toast.makeText(getActivity(), "Empty Field", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Player updatePlayer = new Player(name,age,position,player.getKey());
                    listener.updatePlayer(updatePlayer);
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (UpdatePlayerDialog.UpdatePlayerDialogListener) context;

    }

    public interface UpdatePlayerDialogListener
    {
        void updatePlayer(Player playerr);
    }

}
