package edu.ggc.epatter1.morsecodetransmitter;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    private Integer isDone = 0;
    final Button transmitBtn = (Button)findViewById(R.id.transmitButton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Button aboutBtn = (Button) findViewById(R.id.aboutButton);
        aboutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, About.class));
            }
        });

        initTransmitButton();
    }


    private void initTransmitButton() {
        final EditText editTextField = (EditText) findViewById(R.id.editTextTransmit);
        transmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (editTextField.getText().toString() != null)
                 doTransmit();
                //TODO don't make button invisible if there is no text to trasmit
//                while (editTextTransmit != null) {
//                    transmitButton.setVisibility(View.INVISIBLE);
//                }

            }
        });
//
    }

    private void doTransmit() {
        //Call My AsyncTask
        TextView flashTextView = (TextView) findViewById(R.id.flashTextView);
        EditText editTextField = (EditText) findViewById(R.id.editTextTransmit);
        String[] userInput = new String[1];
        userInput[0] = editTextField.getText().toString();
        TransmitTask transmitTask = new TransmitTask(flashTextView);

        //TODO may need to change to serial

        try {
            isDone = transmitTask.execute(userInput).get();
            if (isDone == 1) {
                transmitBtn.setVisibility(View.VISIBLE);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
