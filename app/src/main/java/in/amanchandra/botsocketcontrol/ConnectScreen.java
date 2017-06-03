package in.amanchandra.botsocketcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedWriter;

public class ConnectScreen extends AppCompatActivity {

    MyReciever myReciever;
    private Button connect;
    EditText Port;
    EditText IP;
    String Data;
    BufferedWriter b = null;
    String host;
    int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_screen);

        connect = (Button)findViewById(R.id.connect_btn);
        Port = (EditText) findViewById(R.id.Port);
        IP = (EditText) findViewById(R.id.IP);
        IP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IP.setText("");
            }
        });
        Port.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Port.setText("");
            }
        });
        if(runtime_permissions())
            enableButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        runtime_permissions();
    }

    private void enableButton(){
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                host = IP.getText().toString();
                if(host.isEmpty()){
                    Toast.makeText(ConnectScreen.this, "Enter valid IP and Port", Toast.LENGTH_SHORT).show();
                }
                else {
                    port = Integer.parseInt(Port.getText().toString());
                    Intent control = new Intent(ConnectScreen.this, Interface.class);
                    control.putExtra("IP",host);
                    control.putExtra("Port",port);
                    startActivity(control);
                }
            }
        });
    }
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23){
            return true;
        }
        else
            return false;
    }
    class MyReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Data = (String) intent.getExtras().get("received");
        }
    }
}
