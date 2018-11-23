package br.edu.fei.auth_library;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmVerificationCodePayload {
    private final String emailAddress;
    private final String deviceId;
    private final String verificationnCode;

    public ConfirmVerificationCodePayload(String emailAddress, String deviceId, String verificationnCode) {
        this.emailAddress = emailAddress;
        this.deviceId = deviceId;
        this.verificationnCode = verificationnCode;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject()
                    .put("emailAddress", emailAddress)
                    .put("deviceId", deviceId)
                    .put("verificationCode", verificationnCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
