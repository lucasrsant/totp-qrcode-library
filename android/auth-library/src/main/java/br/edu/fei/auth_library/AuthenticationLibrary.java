package br.edu.fei.auth_library;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import br.edu.fei.lite_volley.Request;
import br.edu.fei.lite_volley.RequestQueue;
import br.edu.fei.lite_volley.Response;
import br.edu.fei.lite_volley.VolleyError;
import br.edu.fei.lite_volley.toolbox.ByteArrayRequest;
import br.edu.fei.lite_volley.toolbox.JsonObjectRequest;
import br.edu.fei.lite_volley.toolbox.JsonRequest;
import br.edu.fei.lite_volley.toolbox.Volley;

public class AuthenticationLibrary {

    private static AuthenticationLibraryConfiguration libraryConfiguration;
    private static AuthenticationLibrary instance;

    private AuthenticationLibrary() { }

    public static void initialize(Context context, AuthenticationLibraryConfiguration configuration) {
        libraryConfiguration = configuration;
        StorageManager.initialize(context);
        KeyManager.initialize(context, configuration);
    }

    public static void registerDevice(Context context, String emailAddress)
    {
        String deviceId = DeviceManager.getDeviceId();
        String publicDeviceKey = KeyManager.getDevicePublicKeyAsString();

        JSONObject jsonObject = new RegisterDevicePayload(emailAddress, deviceId, publicDeviceKey).toJson();
        EncryptedPayload encryptedData = EncryptionManager.encrypt(jsonObject.toString().getBytes());

        JsonObjectRequest byteArrayRequest = new JsonObjectRequest(Request.Method.POST,
                libraryConfiguration.getRegisterDeviceUrl(),
                encryptedData.toJson(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("RegisterDevice", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RegisterDevice", Log.getStackTraceString(error));
                    }
                }) {
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(byteArrayRequest);
    }
}
