package com.example.ap4_e5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Test extends AppCompatActivity {

    private TextView qrCodeValueTextView;
    private TextView startScanButton;

    private ActivityResultLauncher<Intent> resultLauncher;

    private void updateQrCodeTextView(String data) {
        if (data != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    qrCodeValueTextView.setText(data);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        qrCodeValueTextView = findViewById(R.id.qr_code_value_tv);
        startScanButton = findViewById(R.id.start_scan_button);
        initButtonClickListener();

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    String qrCodeData = data.getStringExtra(ScanQrCodeActivity.QR_CODE_KEY);
                    updateQrCodeTextView(qrCodeData);
                }
            }
        });
    }

    private void initButtonClickListener() {
        startScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Test.this, ScanQrCodeActivity.class);
                resultLauncher.launch(intent);
            }
        });
    }
}
