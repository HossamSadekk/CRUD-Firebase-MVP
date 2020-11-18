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

import com.example.firebase_mvp_app.Activities.MainActivity;
import com.example.firebase_mvp_app.Model.Player;
import com.example.firebase_mvp_app.R;

public class CreatePlayerDialog extends AppCompatDialogFragment {
    createPlayerDialogListener listener;
    public EditText Name;
    public EditText Age;
    public EditText Position;
    public Button Save;

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
        builder.setTitle("Add new Player");

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
                    Player player = new Player(name,age,position);
                    listener.savePlayer(player);
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (createPlayerDialogListener) context;

    }

    public interface createPlayerDialogListener
    {
        void savePlayer(Player playerr);
    }

}
