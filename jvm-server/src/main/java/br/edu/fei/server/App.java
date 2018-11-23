package br.edu.fei.server;

import br.edu.fei.auth.qrcode.QRCodeGenerator;
import br.edu.fei.auth.security.Encryption;
import br.edu.fei.auth.security.KeyStoreManagement;
import br.edu.fei.server.payloads.ConfirmValidationCodeRequest;
import br.edu.fei.server.payloads.RegisterDevicePayload;
import com.google.gson.Gson;

import static spark.Spark.post;
import static spark.Spark.get;

public class App {

    private static InMemoryRepositoryImpl<String, IdentifiedUserRegistrationRequest> repository = new InMemoryRepositoryImpl<>();
    private static KeyStoreManagement keyStoreManagement;
    private static Encryption encryption;

    public static void main(String[] args) {

        initializeSecurityModule();

        post("/registerDevice", (request, response) ->  {
            EncryptedPayload encryptedPayload = new Gson().fromJson(request.body(), EncryptedPayload.class);
            byte[] payload = EncryptionManager.decrypt(encryptedPayload, keyStoreManagement.getPrivateKey());

            RegisterDevicePayload registerDevicePayload = new Gson().fromJson(new String(payload), RegisterDevicePayload.class);
            new RegisterUserUseCase(new MailSenderImpl(), App.repository).execute(registerDevicePayload);
            return "";
        });

        post("/validateCode", (request, response) -> {
            ConfirmValidationCodeRequest confirmValidationCodeRequest = new Gson().fromJson(request.body(), ConfirmValidationCodeRequest.class);
            return new ConfirmValidationCodeUseCase(App.repository).execute(confirmValidationCodeRequest);
        });

        get("/pubkey", (request, response) -> keyStoreManagement.getPublicKeyAsString());
        get("/auth", (request, response) -> QRCodeGenerator.generate("Teste de biblioteca TOTP"));
    }

    private static void initializeSecurityModule() {
        try {
            keyStoreManagement = new KeyStoreManagement("./keys/serverKeyStore.jks", "totpserver", "123456");
            encryption = new Encryption(keyStoreManagement);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
