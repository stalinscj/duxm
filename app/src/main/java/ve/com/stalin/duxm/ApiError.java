package ve.com.stalin.duxm;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;

public class ApiError {

    private int httpCode;
    private ResponseBody responseBody;
    private ArrayList<String> errors;


    public ApiError(int httpCode, ResponseBody responseBody) {
        this.httpCode = httpCode;
        this.responseBody = responseBody;
        this.errors = this.responseToArrayList();
    }

    private ArrayList<String> responseToArrayList(){

        ArrayList<String> errors = new ArrayList<String>();

        try {
            JSONObject json = new JSONObject(this.responseBody.string());

            Iterator<String> keys = json.keys();

            while(keys.hasNext()) {
                String key = keys.next();

                List<String> tempErrors = new ArrayList<String>();

                JSONArray keyErrors = json.getJSONArray(key);
                for (int i = 0; i < keyErrors.length(); i++) {
                    tempErrors.add(keyErrors.getString(i));
                }

                String error = key + ": " +TextUtils.join(", ", tempErrors);
                errors.add(error);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return errors;
    }


    public ArrayList<String> getErrors(){
        return this.errors;
    }

    public int getHttpCode(){
        return this.httpCode;
    }
}
