package com.example.skyvoyage;

import android.content.Intent;
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

        btnProceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePayment();
            }
        });
    }

    private void setSelectedPaymentMethod(int paymentMethod, MaterialCardView card, ImageView radio) {
        selectedPaymentMethod = paymentMethod;
        card.setCardBackgroundColor(getResources().getColor(R.color.selected_color));
        radio.setBackgroundResource(R.drawable.checked);
    }

    private void setDeselectedPaymentMethod(MaterialCardView card, ImageView radio) {
        card.setCardBackgroundColor(getResources().getColor(R.color.deselected_color));
        radio.setBackgroundResource(R.drawable.unchecked);
    }


    private void handlePayment() {
        if (selectedPaymentMethod == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        String paymentMethod = selectedPaymentMethod == 0 ? "PayPal" : "Apple Pay";


        // Implement the payment processing logic here
        // For the purpose of this example, we will just show a toast message
        Toast.makeText(this, "Payment successful with " + paymentMethod, Toast.LENGTH_SHORT).show();

        // You may also want to navigate to a confirmation page or close the activity
        // finish(); // Uncomment to close the activity after payment

        Intent intent = new Intent(PaymentActivity.this, FlightListingsActivity.class);
        startActivity(intent);
    }

}
