package com.example.skyvoyage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class PaymentActivity extends AppCompatActivity {

    private EditText etCardNumber, etExpiryDate, etCVV;
    private Button btnProceedToPayment;
    private MaterialCardView paypalCard, applePayCard;
    private TextView paypalText, applePayText;
    private ImageView paypalRadio, applePayRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnProceedToPayment = findViewById(R.id.btnProceedToPayment);

        paypalCard = findViewById(R.id.paypalCard);
        applePayCard = findViewById(R.id.applePayCard);

        // Set text and image for PayPal
        paypalText = paypalCard.findViewById(R.id.itemText);
        paypalText.setText("PayPal");
        paypalCard.findViewById(R.id.itemImage).setBackgroundResource(R.drawable.paypay);
        paypalRadio = paypalCard.findViewById(R.id.itemRadio);

        // Set text and image for Apple Pay
        applePayText = applePayCard.findViewById(R.id.itemText);
        applePayText.setText("Apple Pay");
        applePayCard.findViewById(R.id.itemImage).setBackgroundResource(R.drawable.apple_pay);
        applePayRadio = applePayCard.findViewById(R.id.itemRadio);

        paypalCard.setOnClickListener(v -> {
            setSelectedPaymentMethod(paypalCard, paypalRadio);
            setDeselectedPaymentMethod(applePayCard, applePayRadio);
        });

        applePayCard.setOnClickListener(v -> {
            setSelectedPaymentMethod(applePayCard, applePayRadio);
            setDeselectedPaymentMethod(paypalCard, paypalRadio);
        });

        btnProceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePayment();
            }
        });
    }

    private void setSelectedPaymentMethod(MaterialCardView card, ImageView radio) {
        card.setChecked(true);
        radio.setBackgroundResource(R.drawable.checked);
    }

    private void setDeselectedPaymentMethod(MaterialCardView card, ImageView radio) {
        card.setChecked(false);
        radio.setBackgroundResource(R.drawable.unchecked);
    }

    private void handlePayment() {
        if (!paypalCard.isChecked() && !applePayCard.isChecked()) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        String paymentMethod = paypalCard.isChecked() ? "PayPal" : "Apple Pay";
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
