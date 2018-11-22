package br.edu.fei.server;

import br.edu.fei.auth.qrcode.QRCodeGenerator;
import br.edu.fei.auth.security.Encryption;
import br.edu.fei.auth.security.KeyStoreManagement;
import br.edu.fei.server.requests.ConfirmValidationCodeRequest;
import br.edu.fei.server.requests.RegisterUserRequest;
import com.google.gson.Gson;

import java.util.Base64;

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

            byte[] encryptedSessionKey = Base64.getDecoder( ).decode(encryptedPayload.sessionKey);
            byte[] encryptedContent = Base64.getDecoder().decode(encryptedPayload.content);

            byte[] sessionKey = EncryptionManager.decrypt(encryptedSessionKey, keyStoreManagement.getPrivateKey(), "RSA/ECB/PKCS1Padding");

            byte[] payload = EncryptionManager.decrypt(encryptedContent, sessionKey, "AES");

            System.out.println(new String(payload));

            RegisterUserRequest registerUserRequest = new Gson().fromJson(request.body(), RegisterUserRequest.class);
            new RegisterUserUseCase(new MailSenderImpl(), App.repository).execute(registerUserRequest);
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
