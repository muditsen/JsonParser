# JsonParser
To parse and convert a Java Model file to Json Object and vice versa. This is very light version of Gson provided by google. May contain some bugs too. 

The project is really easy to understand and explaining the use of annotations in java.


## Which files to add??

Add JsonParser and JsonKey file to your project.


## How to use??

Lets assume we have a json object say

Product Info :
```
{
    "product_name":"My Product Name",
    "product_price":100,
    "extra":{
        "type":"Class A",
        "rating":5.0
    }
}
```

Then create a model file for Product

```
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

```

And another model file for Extra info

```
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
 ```


In your MainActivity.java file
    
```
    JsonParser.parse(productBean, jsonObject);
    
    //OR
    
    JsonParser.parse(ProductBean.class, jsonObject);
```
In your MainActivity.java file
    
```
JsonParser.parse(productBean, jsonObject);
```

Note: The functionality is same as the library of Gson.


