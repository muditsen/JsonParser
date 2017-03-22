package muditse.jsonparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("product_name", "First Product");
            jsonObject.put("product_price", 100);

            JSONObject jExtra = new JSONObject();
            jExtra.put("type", "Class A");
            jExtra.put("rating", 5.0);

            jsonObject.put("extra", jExtra);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ProductBean productBean = new ProductBean();

        Log.e("muditLog", JsonParser.getJsonObjectFrom(productBean).toString());

        JsonParser.parse(productBean, jsonObject);

        Log.e("muditLog", productBean.toString());


    }

    @JsonKey("product")
    public class ProductBean {

        @JsonKey("product_name")
        String name;

        @JsonKey("product_price")
        int price;

        @JsonKey("extra")
        Extra extra;

        public void sayHello() {
            Log.e("muditLog", "hello annotation");
        }

        @Override
        public String toString() {
            return "the name is " + name + " & " + price + " " + extra.toString();
        }
    }

    public static class Extra {

        public Extra() {

        }

        @JsonKey("type")
        String type;

        @JsonKey("rating")
        double rating;

        @Override
        public String toString() {
            return type + " " + rating;
        }
    }
}
