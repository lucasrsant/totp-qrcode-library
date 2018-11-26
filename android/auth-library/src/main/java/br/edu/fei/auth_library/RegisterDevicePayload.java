package br.edu.fei.auth_library;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterDevicePayload {

    private final String emailAddress;
    private final String deviceId;
    private final String devicePublicKey;

    /*package*/ RegisterDevicePayload(String emailAddress, String deviceId, String publicDeviceKey) {
        this.emailAddress = emailAddress;
        this.deviceId = deviceId;
        this.devicePublicKey = publicDeviceKey;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject()
                    .put("emailAddress", emailAddress)
                    .put("deviceId", deviceId)
                    .put("devicePublicKey", devicePublicKey);
        } catch (JSONException e) {
            return null;
        }
    }
}