package br.edu.fei.server;

import br.edu.fei.auth.qrcode.QRCodeGenerator;
import br.edu.fei.auth.security.KeyStoreManagement;
import br.edu.fei.server.payloads.ConfirmValidationCodeRequest;
import br.edu.fei.server.payloads.RegisterDevicePayload;
import com.google.gson.Gson;
import spark.Request;

import static spark.Spark.post;
import static spark.Spark.get;

public class App {

    private static InMemoryRepositoryImpl<String, IdentifiedUserRegistration> userRepository = new InMemoryRepositoryImpl<>();
    private static InMemoryRepositoryImpl<String, AuthenticationSession> authSessionRepository = new InMemoryRepositoryImpl<>();
    private static KeyStoreManagement keyStoreManagement;
    //private static Encryption encryption;

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

        get("/createAuthenticationSession", (request, response) -> {
            AuthenticationSession authenticationSession = new AuthenticationSession();
            return QRCodeGenerator.generate(authenticationSession.getId());
        });

        get("/pubkey", (request, response) -> keyStoreManagement.getPublicKeyAsString());
        //get("/auth", (request, response) -> QRCodeGenerator.generate("Teste de biblioteca TOTP"));
    }

    private static void initializeSecurityModule() {
        try {
            keyStoreManagement = new KeyStoreManagement("./keys/serverKeyStore.jks", "totpserver", "123456");
            //encryption = new Encryption(keyStoreManagement);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> T decryptRequest(Request request, Class<T> clazz) throws Exception {
        EncryptedPayload encryptedPayload = new Gson().fromJson(request.body(), EncryptedPayload.class);
        byte[] payload = EncryptionManager.decrypt(encryptedPayload, keyStoreManagement.getPrivateKey());
        return new Gson().fromJson(new String(payload), clazz);
    }
}
