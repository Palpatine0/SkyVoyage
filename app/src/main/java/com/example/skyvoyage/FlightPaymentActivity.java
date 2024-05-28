package com.example.skyvoyage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class FlightPaymentActivity extends AppCompatActivity {

    private Button btnProceedToPayment, btnAddCreditCard;
    private MaterialCardView paypalCard, applePayCard;
    private TextView paypalText, applePayText;
    private ImageView paypalRadio, applePayRadio;

    private int selectedPaymentMethod = -1;
    private String selectedCardName = "";
    private LinearLayout paymentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_payment);

        int flightId = getIntent().getIntExtra("FLIGHT_ID", -1);

        btnProceedToPayment = findViewById(R.id.btnProceedToPayment);
        btnAddCreditCard = findViewById(R.id.btnAddCreditCard);
        paymentContainer = findViewById(R.id.paymentContainer);

        paypalCard = findViewById(R.id.paypalCard);
        applePayCard = findViewById(R.id.applePayCard);

        // Set text and image for PayPal
        paypalText = paypalCard.findViewById(R.id.itemText);
        paypalText.setText("PayPal");
        paypalCard.findViewById(R.id.itemImage).setBackgroundResource(R.drawable.paypal);
        paypalRadio = paypalCard.findViewById(R.id.itemRadio);

        // Set text and image for Apple Pay
        applePayText = applePayCard.findViewById(R.id.itemText);
        applePayText.setText("Apple Pay");
        applePayCard.findViewById(R.id.itemImage).setBackgroundResource(R.drawable.apple_pay);
        applePayRadio = applePayCard.findViewById(R.id.itemRadio);

        paypalCard.setOnClickListener(v -> {
            setSelectedPaymentMethod(0, paypalCard, paypalRadio);
            setDeselectedPaymentMethod(applePayCard, applePayRadio);
            deselectAllBankCards();
            selectedCardName = "PayPal";
        });

        applePayCard.setOnClickListener(v -> {
            setSelectedPaymentMethod(1, applePayCard, applePayRadio);
            setDeselectedPaymentMethod(paypalCard, paypalRadio);
            deselectAllBankCards();
            selectedCardName = "Apple Pay";
        });

        btnAddCreditCard.setOnClickListener(v -> showBankCardDialog());

        btnProceedToPayment.setOnClickListener(v -> handlePayment(flightId));
    }

    private void addBankCard(String cardType) {
        // Check if the card is already added, if so, do nothing
        for (int i = 0; i < paymentContainer.getChildCount(); i++) {
            View view = paymentContainer.getChildAt(i);
            TextView itemText = view.findViewById(R.id.itemText);
            if (itemText != null && itemText.getText().toString().equals(cardType)) {
                return; // Card already added, do nothing
            }
        }

        // Remove the "Add Credit Card" button if it exists
        if (btnAddCreditCard.getParent() != null) {
            ((ViewGroup) btnAddCreditCard.getParent()).removeView(btnAddCreditCard);
        }

        // Inflate the new card view and set its properties
        View bankCardView = LayoutInflater.from(this).inflate(R.layout.selectable_item, paymentContainer, false);
        TextView itemText = bankCardView.findViewById(R.id.itemText);
        ImageView itemImage = bankCardView.findViewById(R.id.itemImage);
        ImageView itemRadio = bankCardView.findViewById(R.id.itemRadio);

        itemText.setText(cardType);
        switch (cardType) {
            case "VISA":
                itemImage.setImageResource(R.drawable.bc_visa); // Ensure the correct drawable resource name
                break;
            case "American Express":
                itemImage.setImageResource(R.drawable.bc_amex); // Ensure the correct drawable resource name
                break;
            case "MasterCard":
                itemImage.setImageResource(R.drawable.bc_mastercard); // Ensure the correct drawable resource name
                break;
        }

        bankCardView.setOnClickListener(v -> {
            setSelectedPaymentMethod(2, (MaterialCardView) v, itemRadio);
            setDeselectedPaymentMethod(paypalCard, paypalRadio);
            setDeselectedPaymentMethod(applePayCard, applePayRadio);
            deselectAllBankCardsExcept(itemText.getText().toString());
            selectedCardName = cardType;
        });

        // Add the new card view at the top of the container
        paymentContainer.addView(bankCardView, 0);

        // Re-add the "Add Credit Card" button at the end
        paymentContainer.addView(btnAddCreditCard);
    }

    private void deselectAllBankCards() {
        for (int i = 0; i < paymentContainer.getChildCount(); i++) {
            View view = paymentContainer.getChildAt(i);
            ImageView itemRadio = view.findViewById(R.id.itemRadio);
            if (itemRadio != null) {
                itemRadio.setImageResource(R.drawable.unchecked);
            }
        }
    }

    private void deselectAllBankCardsExcept(String cardType) {
        for (int i = 0; i < paymentContainer.getChildCount(); i++) {
            View view = paymentContainer.getChildAt(i);
            TextView itemText = view.findViewById(R.id.itemText);
            ImageView itemRadio = view.findViewById(R.id.itemRadio);
            if (itemText != null && itemRadio != null && !itemText.getText().toString().equals(cardType)) {
                itemRadio.setImageResource(R.drawable.unchecked);
                ((MaterialCardView) view).setCardBackgroundColor(ContextCompat.getColor(this, R.color.deselected_color));
            }
        }
    }

    private void showBankCardDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_bank_card);

        Button btnVisa = dialog.findViewById(R.id.btnVisa);
        Button btnAmex = dialog.findViewById(R.id.btnAmex);
        Button btnMastercard = dialog.findViewById(R.id.btnMastercard);

        View.OnClickListener listener = v -> {
            String cardType = ((Button) v).getText().toString();
            addBankCard(cardType);
            dialog.dismiss(); // Ensure the dialog is dismissed
        };

        btnVisa.setOnClickListener(listener);
        btnAmex.setOnClickListener(listener);
        btnMastercard.setOnClickListener(listener);

        dialog.show();
    }

    private void setSelectedPaymentMethod(int paymentMethod, MaterialCardView card, ImageView radio) {
        selectedPaymentMethod = paymentMethod;
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.selected_color));
        radio.setImageResource(R.drawable.checked);
    }

    private void setDeselectedPaymentMethod(MaterialCardView card, ImageView radio) {
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.deselected_color));
        radio.setImageResource(R.drawable.unchecked);
    }

    private void handlePayment(int flightId) {
        if (selectedPaymentMethod == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update flight ticket count in the database
        FlightMySQLiteOpenHelper dbHelper = new FlightMySQLiteOpenHelper(this);
        Flight flight = dbHelper.queryFromDbById(flightId).get(0);
        int updatedCount = flight.getCount() - 1;
        flight.setCount(updatedCount);
        dbHelper.updateData(flight);

        if (selectedPaymentMethod == 0 || selectedPaymentMethod == 1) {
            showQrCodeDialog(flightId);
        } else {
            // Simulate payment success for credit card payment
            String paymentMethod = selectedCardName; // Use the name of the selected card
            Toast.makeText(this, "Payment successful with " + paymentMethod, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FlightPaymentActivity.this, FlightListingsActivity.class);
            intent.putExtra("FLIGHT_ID", flightId);
            startActivity(intent);
        }
    }

    private void showQrCodeDialog(int flightId) {
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
            Intent intent

                    = new Intent(FlightPaymentActivity.this, FlightListingsActivity.class);
            intent.putExtra("FLIGHT_ID", flightId);
            startActivity(intent);
        }, 5000); // 10000 milliseconds = 10 seconds
    }
}