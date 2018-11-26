package br.edu.fei.auth_library;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import br.edu.fei.lite_volley.Request;
import br.edu.fei.lite_volley.RequestQueue;
import br.edu.fei.lite_volley.Response;
import br.edu.fei.lite_volley.VolleyError;
import br.edu.fei.lite_volley.toolbox.StringRequest;
import br.edu.fei.lite_volley.toolbox.Volley;

public class KeyManager {

    private static final String DEVICE_PUBLIC_KEY_STORAGE_KEY = "br.edu.fei.auth_library.KeyManager.DEVICE_PUBLIC_KEY";
    private static final String DEVICE_PRIVATE_KEY_STORAGE_KEY = "br.edu.fei.auth_library.KeyManager.DEVICE_PRIVATE_KEY";
    private static final String SERVER_PUBLIC_KEY_STORAGE_KEY = "br.edu.fei.auth_library.KeyManager.SERVER_PUBLIC_KEY";

    /*package*/ static void initialize(Context context, AuthenticationLibraryConfiguration configuration) {
        if(hasDeviceKeyPair() == false)
            generateDeviceKeyPair();

        if(hasServerPublicKey() == false)
            retrieveServerPublicKey(context, configuration.getServerPublicKeyUrl());
    }

    /*package*/ static String getDevicePublicKeyAsString() {
        return StorageManager.getString(DEVICE_PUBLIC_KEY_STORAGE_KEY);
    }

    /*package*/ static PublicKey getServerPublicKey() {
        String base64EncodedKey = StorageManager.getString(SERVER_PUBLIC_KEY_STORAGE_KEY);
        byte[] encodedKey = Base64.decode(base64EncodedKey, Base64.NO_WRAP);

        KeySpec keySpec = new X509EncodedKeySpec(encodedKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void retrieveServerPublicKey(Context context, String serverPublicKeyUrl) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverPublicKeyUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ServerPublicKey", response);
                StorageManager.persistString(SERVER_PUBLIC_KEY_STORAGE_KEY, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ServerPublicKey", error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    private static boolean hasServerPublicKey() {
        return StorageManager.containsKey(SERVER_PUBLIC_KEY_STORAGE_KEY) && StorageManager.getString(SERVER_PUBLIC_KEY_STORAGE_KEY).isEmpty() == false;
    }

    private static boolean hasDeviceKeyPair() {
        return StorageManager.containsKey(DEVICE_PRIVATE_KEY_STORAGE_KEY) && StorageManager.containsKey(DEVICE_PUBLIC_KEY_STORAGE_KEY);
    }

    private static void generateDeviceKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            String base64PublicKey = Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.NO_WRAP);
            String base64PrivateKey = Base64.encodeToString(keyPair.getPrivate().getEncoded(), Base64.NO_WRAP);

            StorageManager.persistString(DEVICE_PUBLIC_KEY_STORAGE_KEY, base64PublicKey);
            StorageManager.persistString(DEVICE_PRIVATE_KEY_STORAGE_KEY, base64PrivateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
