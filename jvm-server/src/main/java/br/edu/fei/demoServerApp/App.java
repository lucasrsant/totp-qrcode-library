package br.edu.fei.demoServerApp;

import br.edu.fei.serverAuthLibrary.*;
import com.google.gson.Gson;
import spark.Request;

import static spark.Spark.post;
import static spark.Spark.get;

public class App {

    private static InMemoryRepositoryImpl<String, IdentifiedUserRegistration> userRepository = new InMemoryRepositoryImpl<>();
    private static InMemoryRepositoryImpl<String, AuthenticationSession> authSessionRepository = new InMemoryRepositoryImpl<>();
    private static KeyStoreManagement keyStoreManagement;

    public static void main(String[] args) {

        initializeSecurityModule();

        post("/registerDevice", (request, response) ->  {
            RegisterDevicePayload registerDevicePayload = decryptRequest(request, RegisterDevicePayload.class);
            return new RegisterUserUseCase(new MailSenderImpl(), App.userRepository).execute(registerDevicePayload);
        });

        post("/validateCode", (request, response) -> {
            ConfirmValidationCodeRequest confirmValidationCodeRequest = decryptRequest(request, ConfirmValidationCodeRequest.class);
            return new ConfirmValidationCodeUseCase(App.userRepository).execute(confirmValidationCodeRequest);
        });

        get("/createAuthenticationSession", (request, response) -> new CreateAuthenticationSessionUseCase(App.authSessionRepository).execute(300));

        post("/authenticateSession", (request, response) -> {
            AuthenticateSessionPayload authenticateSessionRequest = decryptRequest(request, AuthenticateSessionPayload.class);
            return new AuthenticateSessionUseCase(App.authSessionRepository, App.userRepository).execute(authenticateSessionRequest);
        });

        get("/pubkey", (request, response) -> keyStoreManagement.getPublicKeyAsString());
    }

    private static void initializeSecurityModule() {
        try {
            keyStoreManagement = new KeyStoreManagement("./keys/serverKeyStore.jks", "totpserver", "123456");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> T decryptRequest(Request request, Class<T> clazz) {
        EncryptedPayload encryptedPayload = new Gson().fromJson(request.body(), EncryptedPayload.class);
        byte[] payload = EncryptionManager.decrypt(encryptedPayload, keyStoreManagement.getPrivateKey());
        return new Gson().fromJson(new String(payload), clazz);
    }
}
