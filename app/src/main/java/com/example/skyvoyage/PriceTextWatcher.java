package com.example.skyvoyage;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class PriceTextWatcher implements TextWatcher {
    private EditText editText;
    private boolean isEditing;

    public PriceTextWatcher(EditText editText) {
        this.editText = editText;
        this.isEditing = false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (isEditing) return;

        isEditing = true;

        String input = s.toString().replace("$", "");
        if (!input.isEmpty()) {
            editText.setText("$" + input);
            editText.setSelection(editText.getText().length());
        }

        isEditing = false;
    }
}
