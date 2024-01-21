package com.olivadevelop.lockmyphone;

import android.app.PendingIntent;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class AppWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        // Asignar un clic en el botón para bloquear el dispositivo
        views.setOnClickPendingIntent(R.id.btnLock, getPendingSelfIntent(context, "LOCK_DEVICE"));
        // Actualizar todos los widgets
        ComponentName componentName = new ComponentName(context, AppWidget.class);
        appWidgetManager.updateAppWidget(componentName, views);
    }
    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if ("LOCK_DEVICE".equals(intent.getAction())) {
            lockDevice(context);
        }
    }
    private void lockDevice(Context context) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, CustomDeviceAdminReceiver.class);
        if (devicePolicyManager != null && devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.lockNow();
        }
    }
}
