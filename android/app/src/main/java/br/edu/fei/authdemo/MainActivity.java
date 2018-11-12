package br.edu.fei.authdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonRegisterDevice).setOnClickListener(onButtonRegisterDeviceClick);
    }

    private View.OnClickListener onButtonRegisterDeviceClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
