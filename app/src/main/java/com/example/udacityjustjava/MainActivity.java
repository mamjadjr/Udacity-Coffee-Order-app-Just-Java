package com.example.udacityjustjava;

import static java.net.Proxy.Type.HTTP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    TextView quantityTextView, orderSummaryTextView;
    Button orderButton;
    int quantity=0;

    CheckBox choclateCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantityTextView = findViewById(R.id.quantityTextView);
        orderSummaryTextView = findViewById(R.id.orderSummaryTextView);

        choclateCheckBox = findViewById(R.id.choclateCheckBox);
    }

    public void submitOrder(View view) {
        CheckBox wippedCreamCheckBox = findViewById(R.id.wippedCreamCheckBox);
        boolean hasWippedCream = wippedCreamCheckBox.isChecked();
        boolean hasChoclate = choclateCheckBox.isChecked();

        EditText nameField = findViewById(R.id.userNameEditText);
        String name = nameField.getText().toString();

        int price = calculatePrice(hasWippedCream, hasChoclate);

        String priceMessage = displayMessageSummary(name, price, hasWippedCream, hasChoclate);
//        displayMessage(priceMessage);

//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:"));
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order by: "+name);
//        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
//        if(intent.resolveActivity(getPackageManager()) != null){
//            startActivity(intent);
//        }
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        try {
            startActivity(whatsappIntent);}
        catch (ActivityNotFoundException ex)
        {
            Toast.makeText(this, "Whatsapp in not install", Toast.LENGTH_SHORT).show();
        }



    }

    public void increment(View view) {
        if(quantity<100){
            quantity = quantity+1;
            display(quantity);
        }else{
            return;
        }

    }
    public void decrement(View view) {
        if(quantity==0){
            return;
        }else{
            quantity -= 1;
            display(quantity);
        }

    }
    private void display(int quantity){
        quantityTextView.setText(String.valueOf(quantity));
    }
    private int calculatePrice(boolean addIceCream , boolean addChoclate){
        int basePrice =5;
        if(addIceCream){
            basePrice +=1;
        }
        if(addChoclate){
            basePrice +=2;
        }
        return quantity*basePrice;
    }
    private String displayMessageSummary(String name, int price, boolean addIceCream, boolean addChoclate){
        String priceMessage = "Name : "+name+ "\n";
        priceMessage = priceMessage + "Your order "+quantity+"\n";
        priceMessage += "Add Ice Cream: "+ addIceCream+"\n";
        priceMessage += "Add Chocolate: "+ addChoclate+"\n";
        priceMessage = priceMessage + "Your Total bill is $"+price+"\n";
        priceMessage = priceMessage + "Thank you!!!";
        return priceMessage;
    }


//    private void displayMessage(String message){
//
//        orderSummaryTextView.setText(message);
//    }
}