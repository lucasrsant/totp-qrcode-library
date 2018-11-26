package br.edu.fei.auth_library;

import java.util.UUID;

/*package*/ class DeviceManager {

    private static final String DEVICE_ID_STORAGE_KEY = "br.edu.auth_library.DeviceManager.DEVICE_ID";

    /*package*/ static String getDeviceId()
    {
        String deviceId = StorageManager.getString(DEVICE_ID_STORAGE_KEY);

        if(deviceId.isEmpty()) {
            deviceId = UUID.randomUUID().toString();
            StorageManager.persistString(DEVICE_ID_STORAGE_KEY, deviceId);
        }

        return deviceId;
    }
}
