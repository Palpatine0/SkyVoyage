package com.example.skyvoyage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.card.MaterialCardView;

public class PaymentActivity extends AppCompatActivity {

    private Button btnProceedToPayment;
    private MaterialCardView paypalCard, applePayCard;
    private TextView paypalText, applePayText;
    private ImageView paypalRadio, applePayRadio;

    private int selectedPaymentMethod = -1;

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
            setSelectedPaymentMethod(0, paypalCard, paypalRadio);
            setDeselectedPaymentMethod(applePayCard, applePayRadio);
        });

        applePayCard.setOnClickListener(v -> {
            setSelectedPaymentMethod(1, applePayCard, applePayRadio);
            setDeselectedPaymentMethod(paypalCard, paypalRadio);
        });

        btnProceedToPayment.setOnClickListener(v -> handlePayment());
    }

    private void setSelectedPaymentMethod(int paymentMethod, MaterialCardView card, ImageView radio) {
        selectedPaymentMethod = paymentMethod;
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.selected_color));
        radio.setBackgroundResource(R.drawable.checked);
    }

    private void setDeselectedPaymentMethod(MaterialCardView card, ImageView radio) {
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.deselected_color));
        radio.setBackgroundResource(R.drawable.unchecked);
    }

    private void handlePayment() {
        if (selectedPaymentMethod == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        showQrCodeDialog();
    }

    private void showQrCodeDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_qr_code);

        ImageView ivQrCode = dialog.findViewById(R.id.ivQrCode);
        if (selectedPaymentMethod == 0) {
            ivQrCode.setImageResource(R.drawable.qr_code_payment_paypal);
        } else {
            ivQrCode.setImageResource(R.drawable.qr_code_payment_apple);
        }

        dialog.show();

        // Delay the processing for 10 seconds
        new Handler().postDelayed(() -> {
            dialog.dismiss();
            // Simulate payment success
            String paymentMethod = selectedPaymentMethod == 0 ? "PayPal" : "Apple Pay";
            Toast.makeText(this, "Payment successful with " + paymentMethod, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PaymentActivity.this, FlightListingsActivity.class);
            startActivity(intent);
        }, 5000); // 10000 milliseconds = 10 seconds
    }
}