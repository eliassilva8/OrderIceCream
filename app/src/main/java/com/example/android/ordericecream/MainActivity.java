package com.example.android.ordericecream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order ice cream.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 2;
    private int pricePerCoffee = 5;
    private String name;
    private boolean hasWippedCream;
    private boolean hasChocolate;
    private boolean hasCaramel;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.flavour_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.listOfFlavours, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        hasWippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate);
        hasChocolate = chocolate.isChecked();

        CheckBox caramel = (CheckBox)findViewById(R.id.caramel);
        hasCaramel = caramel.isChecked();

        EditText editText = (EditText)findViewById(R.id.set_name);
        name = editText.getText().toString();

        //Intent to send a email with the order summary
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_coffee));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Increments the quantity by 1.
     */
    public void increment(View view) {
        if(quantity < 10) {
            quantity = quantity + 1;
            displayQuantity(quantity);

        } else {
            Toast.makeText(this, getString(R.string.toast_more_than), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Decrements the quantity by 1.
     */
    public void decrement(View view) {
        if(quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);

        } else {
            Toast.makeText(this, getString(R.string.toast_less_than), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the total price of the order
     * @return price to pay
     */
    private double calculatePrice(){
        int totalToPay = pricePerCoffee;

        if (hasChocolate) {
            totalToPay += 1;
        }

        if (hasWippedCream){
            totalToPay += 1;
        }

        if (hasCaramel) {
            totalToPay += 1;
        }

        return quantity * totalToPay;
    }

    /**
     * Create a summary of the order
     * @return text summary
     */
    private String createOrderSummary(){
        String orderSummary = getString(R.string.name) + " " + name;
        orderSummary = orderSummary + "\n" + getString(R.string.quantity) + " " + quantity;
        orderSummary = orderSummary + "\n" + getString(R.string.add_whipped_cream) + " " + hasWippedCream;
        orderSummary = orderSummary + "\n" + getString(R.string.add_chocolate) + " " + hasChocolate;
        orderSummary = orderSummary + "\n" + getString(R.string.add_caramel) + " " + hasCaramel;
        orderSummary = orderSummary + "\n" + getString(R.string.icecream_flavour) + " " + spinner.getSelectedItem().toString();
        orderSummary = orderSummary + "\nTotal: " + calculatePrice() + "$";
        orderSummary = orderSummary + "\n" + getString(R.string.thank_you);
        return orderSummary;
    }
}