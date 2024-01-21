package com.olivadevelop.lockmyphone;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class CustomDeviceAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
