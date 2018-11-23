package br.edu.fei.authdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.edu.fei.auth_library.AuthenticationLibrary;

public class MainActivity extends AppCompatActivity {

    private EditText editTextConfirmationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonRegisterDevice).setOnClickListener(onButtonRegisterDeviceClick);
        findViewById(R.id.buttonConfirmVerificationCode).setOnClickListener(onButtonConfirmVerificationCodeClick);

        this.editTextConfirmationCode = findViewById(R.id.editTextConfirmationCode);
    }

    private View.OnClickListener onButtonRegisterDeviceClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AuthenticationLibrary.registerDevice(MainActivity.this, "lucasrsant@gmail.com");
        }
    };

    private View.OnClickListener onButtonConfirmVerificationCodeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AuthenticationLibrary.confirmVerificationCode(MainActivity.this, "lucasrsant@gmail.com", editTextConfirmationCode.getText().toString());
        }
    };
}
