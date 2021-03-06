package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailsActivity extends SLRActivity {

    private DBHelper dbHelper;
    private TextView TW_itemName;
    private TextView TW_itemQuantity;
    private TextView TW_itemBougth;
    private EditText ET_itemName;
    private EditText ET_itemQuantity;
    private CheckBox CB_itemBought;
    private EditText ET_price;

    private int id;
    private String itemName;
    private int itemQuantity;
    private boolean itemBought;
    private int itemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        TW_itemName = (TextView) findViewById(R.id.tw_item_name);
        TW_itemQuantity = (TextView) findViewById(R.id.tw_item_quantity);
        TW_itemBougth = (TextView) findViewById(R.id.tw_item_bought);
        ET_itemName = (EditText) findViewById(R.id.id_et_item_name);
        ET_itemQuantity = (EditText) findViewById(R.id.id_et_item_quantity);
        CB_itemBought = (CheckBox) findViewById(R.id.id_cb_bought);
        ET_price = (EditText) findViewById(R.id.id_et_item_price);

        dbHelper = new DBHelper(this);
        Bundle dataBundle = getIntent().getExtras();

        if (dataBundle != null) {
            id = dataBundle.getInt("id");

            Cursor cursor = dbHelper.getData(id);
            cursor.moveToFirst();

            itemName = cursor.getString(cursor.getColumnIndex(DBHelper.TOBUY_COLUMN_NAME));
            itemQuantity = cursor.getInt(cursor.getColumnIndex(DBHelper.TOBUY_QUANTITY));
            itemBought = cursor.getInt(cursor.getColumnIndex(DBHelper.TOBUY_BOUGHT)) > 0;
            itemPrice = cursor.getInt(cursor.getColumnIndex(DBHelper.TOBUY_COLUMN_PRICE));

            ET_itemName.setText(itemName);
            ET_itemQuantity.setText(String.valueOf(itemQuantity));
            CB_itemBought.setChecked(itemBought);
            ET_price.setText(String.valueOf(itemPrice));
        }
    }

    public void deleteItem(View v) {
        dbHelper.deleteItem(id);
        startActivity(new Intent(this, ListActivity.class));
    }

    public void updateRecord(View v) {
        itemName = ET_itemName.getText().toString();
        itemQuantity = Integer.parseInt(ET_itemQuantity.getText().toString());
        itemBought = CB_itemBought.isChecked();
        itemPrice = Integer.parseInt(ET_price.getText().toString());

        String category = "Other";
        String deadline = "2137-04-20";

        dbHelper.updateItem(id, itemName, itemQuantity, itemBought, category, deadline, itemPrice);

        startActivity(new Intent(this, ListActivity.class));
    }
}
