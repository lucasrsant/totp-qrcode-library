package br.edu.fei.auth_library;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthenticateSessionPayload {

    private final String sessionId;
    private final String deviceId;

    public AuthenticateSessionPayload(String sessionId, String deviceId) {
        this.sessionId = sessionId;
        this.deviceId = deviceId;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject()
                    .put("sessionId", sessionId)
                    .put("deviceId", deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
