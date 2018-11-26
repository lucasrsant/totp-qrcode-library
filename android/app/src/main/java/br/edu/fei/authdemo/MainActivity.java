package br.edu.fei.authdemo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.edu.fei.auth_library.AuthenticationLibrary;
import br.edu.fei.lite_zxing.IntentIntegrator;

public class MainActivity extends AppCompatActivity {

    private EditText editTextConfirmationCode;
    private EditText editTextEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonRegisterDevice).setOnClickListener(onButtonRegisterDeviceClick);
        findViewById(R.id.buttonConfirmVerificationCode).setOnClickListener(onButtonConfirmVerificationCodeClick);
        findViewById(R.id.buttonAuthenticate).setOnClickListener(onButtonAuthenticateClick);

        this.editTextConfirmationCode = findViewById(R.id.editTextConfirmationCode);
        this.editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AuthenticationLibrary.SCAN_SESSION_REQUEST_CODE)
            AuthenticationLibrary.authenticate(data.getStringExtra(AuthenticationLibrary.EXTRA_SCAN_SESSION_ID));
    }

    private View.OnClickListener onButtonRegisterDeviceClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AuthenticationLibrary.registerDevice(editTextEmailAddress.getText().toString());
        }
    };

    private View.OnClickListener onButtonConfirmVerificationCodeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailAddress = editTextEmailAddress.getText().toString();
            String confirmationCode = editTextConfirmationCode.getText().toString();
            AuthenticationLibrary.confirmVerificationCode(emailAddress, confirmationCode);
        }
    };

    private View.OnClickListener onButtonAuthenticateClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AuthenticationLibrary.scanAuthenticationSession(MainActivity.this);
        }
    };
}
