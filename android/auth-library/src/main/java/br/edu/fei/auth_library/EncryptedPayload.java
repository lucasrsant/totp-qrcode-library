package br.edu.fei.auth_library;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/*package*/ class EncryptedPayload {
    public byte[] content;
    public byte[] sessionKey;

    public JSONObject toJson() {
        try {
            return new JSONObject()
                    .put("content", Base64.encodeToString(content, Base64.NO_WRAP))
                    .put("sessionKey", Base64.encodeToString(sessionKey, Base64.NO_WRAP));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}