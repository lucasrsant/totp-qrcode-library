package br.edu.fei.auth_library;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import br.edu.fei.lite_volley.Request;
import br.edu.fei.lite_volley.RequestQueue;
import br.edu.fei.lite_volley.Response;
import br.edu.fei.lite_volley.VolleyError;
import br.edu.fei.lite_volley.toolbox.StringRequest;
import br.edu.fei.lite_volley.toolbox.Volley;
import br.edu.fei.lite_zxing.CaptureActivity;
import br.edu.fei.lite_zxing.IntentIntegrator;

public class AuthenticationLibrary {

    public static final int SCAN_SESSION_REQUEST_CODE = IntentIntegrator.REQUEST_CODE;
    public static final String EXTRA_SCAN_SESSION_ID = CaptureActivity.EXTRA_SCAN_CONTENT;

    private static AuthenticationLibraryConfiguration libraryConfiguration;
    private static RequestQueue requestQueue;

    private AuthenticationLibrary() { }

    public static void initialize(Context context, AuthenticationLibraryConfiguration configuration) {
        requestQueue = Volley.newRequestQueue(context);
        libraryConfiguration = configuration;
        StorageManager.initialize(context);
        KeyManager.initialize(context, configuration);
    }

    public static void registerDevice(String emailAddress)
    {
        String deviceId = DeviceManager.getDeviceId();
        String publicDeviceKey = KeyManager.getDevicePublicKeyAsString();

        JSONObject jsonObject = new RegisterDevicePayload(emailAddress, deviceId, publicDeviceKey).toJson();

        Log.d("RegisterDevice", String.format("decrypted payload: %s", jsonObject.toString()));

        EncryptedPayload encryptedPayload = EncryptionManager.encrypt(jsonObject.toString().getBytes());

        Log.d("RegisterDevice", String.format("encrypted payload: %s", encryptedPayload.toJson().toString()));

        StringRequest jsonRequest = new StringRequest(Request.Method.POST,
                libraryConfiguration.getRegisterDeviceUrl(),
                encryptedPayload.toJson().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RegisterDevice", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RegisterDevice", Log.getStackTraceString(error));
                    }
                }) {
        };

        requestQueue.add(jsonRequest);
    }

    public static void confirmVerificationCode(String emailAddress, String verificationCode) {
        String deviceId = DeviceManager.getDeviceId();

        JSONObject jsonObject = new ConfirmVerificationCodePayload(emailAddress, deviceId, verificationCode).toJson();

        Log.d("ConfirmVerificationCode", String.format("decrypted payload: %s", jsonObject.toString()));

        EncryptedPayload encryptedPayload = EncryptionManager.encrypt(jsonObject.toString().getBytes());

        Log.d("ConfirmVerificationCode", String.format("encrypted payload: %s", encryptedPayload.toJson().toString()));

        StringRequest jsonRequest = new StringRequest(Request.Method.POST,
                libraryConfiguration.getConfirmVerificationCodeEndpoint(),
                encryptedPayload.toJson().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ConfirmVerificationCode", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ConfirmVerificationCode", Log.getStackTraceString(error));
                    }
                });

        requestQueue.add(jsonRequest);
    }

    public static void scanAuthenticationSession(Activity activity) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
        intentIntegrator.initiateScan(activity);
    }

    public static void authenticate(String sessionId) {
        Log.d("Authenticate", "sessionId = " + sessionId);
    }
}