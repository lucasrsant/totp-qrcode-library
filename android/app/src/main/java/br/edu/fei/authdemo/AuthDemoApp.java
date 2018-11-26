package br.edu.fei.authdemo;

import android.app.Application;

import br.edu.fei.auth_library.AuthenticationLibrary;
import br.edu.fei.auth_library.AuthenticationLibraryConfiguration;

public class AuthDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AuthenticationLibrary.initialize(this,
                AuthenticationLibraryConfiguration.Builder.newBuilder()
                        .withPublicKeyEndpoint("/pubkey")
                        .withRegisterDeviceEndpoint("/registerDevice")
                        .withConfirmVerificationCodeEndpoint("/validateCode")
                        .withServerHost("http://192.168.15.12:4567")
                        .build());
    }
}
