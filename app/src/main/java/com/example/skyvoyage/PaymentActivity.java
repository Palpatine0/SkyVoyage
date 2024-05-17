package com.example.skyvoyage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup rgPaymentOptions;
    private EditText etCardNumber, etExpiryDate, etCVV;
    private Button btnProceedToPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCVV = findViewById(R.id.etCVV);
        btnProceedToPayment = findViewById(R.id.btnProceedToPayment);

        btnProceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePayment();
            }
        });
    }

    private void handlePayment() {
        int selectedPaymentMethodId = rgPaymentOptions.getCheckedRadioButtonId();
        RadioButton selectedPaymentMethod = findViewById(selectedPaymentMethodId);

        if (selectedPaymentMethod == null) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        String paymentMethod = selectedPaymentMethod.getText().toString();
        String cardNumber = etCardNumber.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();
        String cvv = etCVV.getText().toString().trim();

        if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            Toast.makeText(this, "Please fill in all payment details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Implement the payment processing logic here
        // For the purpose of this example, we will just show a toast message
        Toast.makeText(this, "Payment successful with " + paymentMethod, Toast.LENGTH_SHORT).show();

        // You may also want to navigate to a confirmation page or close the activity
        // finish(); // Uncomment to close the activity after payment
    }
}
