package com.olivadevelop.lockmyphone;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inicializar el administrador de políticas del dispositivo
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, CustomDeviceAdminReceiver.class);
        ImageButton btnLock = findViewById(R.id.btnLock);
        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockDevice();
            }
        });
    }
    private void lockDevice() {
        // Verificar si la aplicación es un administrador de dispositivos activo
        if (devicePolicyManager.isAdminActive(componentName)) {
            // Bloquear el dispositivo
            devicePolicyManager.lockNow();
        } else {
            // Si no es un administrador de dispositivos activo, solicitar activación
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.required_lock_permission));
            startActivityForResult(intent, 1);
        }
    }
}