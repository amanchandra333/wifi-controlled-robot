package in.amanchandra.botsocketcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

/**
 * Created by root on 3/6/17.
 */

public class Interface extends AppCompatActivity {
    String IP;
    int port;
    PrintWriter out;
    BufferedReader in;
    String confirm = null;
    private Button forward,backward,left,right,armx,army,armz;
    private Switch onOffSwitch;
    private SeekBar seekbar;
    public int pwm = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);
        Bundle bundle = getIntent().getExtras();
        IP = bundle.getString("IP");
        port = bundle.getInt("Port");
        Client client = new Client(this,IP,port);
        ReaderClient reader = new ReaderClient(this,IP,port);
        try {
            client.execute();
            reader.execute();
            out = client.get();
            in = reader.get();
        } catch (InterruptedException | ExecutionException e) {
            Toast.makeText(this, "Can't Connect to Server.", Toast.LENGTH_SHORT).show();
        }
        if(out != null) {
            Toast.makeText(this, "Successfully Connected to Server", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Failed to connect to Server.\n" +
                    "        Try Again !!", Toast.LENGTH_SHORT).show();
        Thread control = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if(in != null)
                            confirm = in.readLine();
                        Thread.sleep(20);
                    }
                } catch (IOException e) {
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        control.start();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onStart(){
        super.onStart();
        forward = (Button)findViewById(R.id.button2);
        backward = (Button)findViewById(R.id.button5);
        left = (Button)findViewById(R.id.button3);
        right = (Button)findViewById(R.id.button4);
        armx = (Button)findViewById(R.id.button6);
        army = (Button)findViewById(R.id.button7);
        armz = (Button)findViewById(R.id.button8);
        onOffSwitch = (Switch)findViewById(R.id.switch1);
        seekbar = (SeekBar)findViewById(R.id.seekBar);

        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    forward.setEnabled(true);
                    backward.setEnabled(true);
                    left.setEnabled(true);
                    right.setEnabled(true);
                    armx.setEnabled(true);
                    army.setEnabled(true);
                    armz.setEnabled(true);
                    seekbar.setVisibility(View.VISIBLE);
                }
                else{
                    forward.setEnabled(false);
                    backward.setEnabled(false);
                    left.setEnabled(false);
                    right.setEnabled(false);
                    armx.setEnabled(false);
                    army.setEnabled(false);
                    armz.setEnabled(false);
                    seekbar.setVisibility(View.GONE);
                }
            }

        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seek, int progress, boolean fromUser) {
                pwm=progress;
            }
        });

        forward.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (out != null) {
                    out.print("F"+pwm);
                    out.flush();
                }
            }
        }));
        backward.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (out != null) {
                    out.print("B"+pwm);
                    out.flush();
                }
            }
        }));
        left.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (out != null) {
                    out.print("L"+pwm);
                    out.flush();
                }
            }
        }));
        right.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (out != null) {
                    out.print("R"+pwm);
                    out.flush();
                }
            }
        }));
        armx.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (out != null) {
                    out.print("X");
                    out.flush();
                }
            }
        }));
        army.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (out != null) {
                    out.print("Y");
                    out.flush();
                }
            }
        }));
        armz.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (out != null) {
                    out.print("Z");
                    out.flush();
                }
            }
        }));
    }
}
