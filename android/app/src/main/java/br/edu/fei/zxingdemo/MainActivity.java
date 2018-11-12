package br.edu.fei.zxingdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.edu.fei.lite_zxing.IntentIntegrator;
import br.edu.fei.lite_zxing.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonScan).setOnClickListener(onButtonScanClick);
    }

    private View.OnClickListener onButtonScanClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
            integrator.initiateScan(MainActivity.this);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            Toast.makeText(this, scanResult.getContents(), Toast.LENGTH_LONG).show();
        }
    }
}
