package autosms.ankur.com.multitouch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NumberCollector extends AppCompatActivity {

    private Spinner spin ;
    private Button goButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_collector);

        spin = (Spinner)findViewById(R.id.spin) ;
        goButton = (Button)findViewById(R.id.goButton) ;
        String[] ints = {"Select One","2", "3","4","5","6"};
        List<String> categories = new ArrayList<String>();
        for(int i = 0; i < ints.length; i++){
            categories.add(ints[i]) ;
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin.setAdapter(dataAdapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = (String) spin.getSelectedItem();
                if(!message.equals("Select One")) {
                    final Intent intent = new Intent(NumberCollector.this, MainActivity.class);
                    //Toast.makeText(NumberCollector.this, "Selected item = " + message, Toast.LENGTH_SHORT).show();
                    intent.putExtra("NUMBER", message);

                    if (Integer.parseInt(Build.VERSION.SDK) >= 7) {
                        PackageManager pm = NumberCollector.this.getPackageManager();
                        boolean hasMultitouch =
                                pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
                        if (hasMultitouch) {
                            new AlertDialog.Builder(NumberCollector.this)
                                    .setTitle("Number of touches")
                                    .setMessage("You are allowed maximum of four touches on screen")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        } else {
                            new AlertDialog.Builder(NumberCollector.this)
                                    .setTitle("No multitouch")
                                    .setMessage("Your phone does not support Multi touch")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                                    .show();
                        }
                    } else {
                        // set zoom buttons
                    }


                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
