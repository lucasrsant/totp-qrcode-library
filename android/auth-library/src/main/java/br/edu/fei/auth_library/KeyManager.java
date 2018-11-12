package br.edu.fei.auth_library;

import android.content.Context;
import android.util.Log;

import br.edu.fei.lite_volley.Request;
import br.edu.fei.lite_volley.RequestQueue;
import br.edu.fei.lite_volley.Response;
import br.edu.fei.lite_volley.VolleyError;
import br.edu.fei.lite_volley.toolbox.StringRequest;
import br.edu.fei.lite_volley.toolbox.Volley;

public class KeyManager {
    public static void retrieveServerPublicKey(Context context, String serverPublicKeyUrl) {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverPublicKeyUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ServerPublicKey", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ServerPublicKey", error.getMessage());
            }
        });

        queue.add(stringRequest);
    }
}
