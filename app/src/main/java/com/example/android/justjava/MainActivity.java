package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.StringCharacterIterator;

//import static com.example.android.justjava.R.id.order_summary;
import static android.widget.Toast.makeText;
import static com.example.android.justjava.R.id.quantity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Initialize global variables.
     */
    int quantity = 1;    // Refers to number of coffees.

    /**
     * Declare the Views.
     */
    TextView quantityTextView;
    //TextView orderSummaryTextView;
    CheckBox checkBoxWhippedCream;
    CheckBox checkBoxChocolate;
    EditText nameEditText;

    /**
     * Initialize Toast Messages.
     */
    Toast toastUpperBoundMessage;
    Toast toastLowerBoundMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Initialize the Views.
         */
        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        //orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        checkBoxWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        checkBoxChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
    }

    /**
     * This method checks if the customer want
     * whipped cream or not.
     * @param view: whipped_cream_checkbox
     */
    public void checkBox(View view) {
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Find the user's name.
        String customerName = nameEditText.getText().toString();

        // Figure out if the user wants whipped cream.
        boolean hasWhippedCream = checkBoxWhippedCream.isChecked();
        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        // Figure out if the user wants chocolate.
        boolean hasChocolate = checkBoxChocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String orderSummaryMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, customerName);
        String emailSubject = getString(R.string.order_summary_email_subject, customerName);
        // Log.v("MainActivity", "Order Summary Message: \n" + orderSummaryMessage);
        // Log.v("MainActivity", "Email Subject " + emailSubject);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(orderSummaryMessage);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity ++;
        // User can't have more that 100 coffees.
        if (quantity == 101) {
            quantity = 100;
            // Show an error message as a toast.
            String toastMessage1 = getString(R.string.toast_message_1);
            toastUpperBoundMessage.makeText(getApplicationContext(), toastMessage1, Toast.LENGTH_LONG).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity--;
        // User can't have less than one coffee.
        if (quantity == 0) {
            quantity = 1;
            //Show an error message as a toast.
            String toastMessage2 = getString(R.string.toast_message_2);
            toastLowerBoundMessage.makeText(getApplicationContext(), toastMessage2, Toast.LENGTH_LONG).show();
        }
        displayQuantity(quantity);
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return total price of the order
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of 1 cup of coffee.
        int baseCoffeePrice = 5;

        // Add $1 if the user wants whipped cream.
        if (addWhippedCream) {
            baseCoffeePrice += 1;
        }

        // Add %2 if the user wants chocolate.
        if (addChocolate) {
            baseCoffeePrice += 2;
        }

        // Calculate the total order price by multiplying by quantity.
        return quantity * baseCoffeePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param customerName is the name of the customer
     * @return text summary
     */
    private String createOrderSummary (int price, boolean addWhippedCream, boolean addChocolate, String customerName){
        String priceMessage = getString(R.string.order_summary_name, customerName);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    /*private void displayMessage(String message) {
        //orderSummaryTextView.setText(message);
    }*/
}