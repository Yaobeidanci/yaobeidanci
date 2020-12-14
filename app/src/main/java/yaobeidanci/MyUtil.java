package yaobeidanci;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络工具包
 */
public class MyUtil {
//    public static final String BASE_URL = "http://192.168.43.82:5000";
    public static final String BASE_URL = "http://10.0.2.2:5000";
    public static String user_uid;
    enum RESULT {SUCCESS, FAIL}

    public static class Res{
        Object data;
        RESULT result;
        String msg;
    }
    /**
     * 回调函数
     */
    public interface MyCallback {
        /**
         * 请求成功时
         * @param result 请求结果
         */
        void onSuccess(Res result);

        /**
         * 请求失败时
         * @param result 请求结果
         */
        void onError(Res result);
    }

    public static final MediaType JSON_TYPE
            = MediaType.get("application/json; charset=utf-8");
    static OkHttpClient client = new OkHttpClient();

    /**
     * 使用okhttp和AsyncTask封装的http-get异步请求
     *
     * @param url      请求的url
     * @param json     传入的查询参数
     * @param callback 回调
     * @param isString 返回是否为字符串，如果是，则返回字符串，否则返回字节数组
     */
    public static void httpGet(final String url, final JSONObject json, final MyCallback callback, final boolean isString) {
        new AsyncTask<Void, Void, Res>() {

            @Override
            protected void onPostExecute(Res s) {
                if (callback != null) {
                    switch (s.result){
                        case SUCCESS:
                            callback.onSuccess(s);
                            break;
                        case FAIL:
                            callback.onError(s);
                            break;
                    }
                }
            }

            @Override
            protected Res doInBackground(Void... voids) {
                Res result = new Res();
                // 拼接url
                HttpUrl.Builder url_builder = HttpUrl.parse(url).newBuilder();
                try {
                    for (Iterator<String> it = json.keys(); it.hasNext(); ) {
                        String key = it.next();
                        url_builder.addQueryParameter(key, json.getString(key));
                    }
                    // okhttp请求
                    Request request = new Request.Builder()
                            .url(url_builder.build())
                            .build();

                    Response response;
                    response = client.newCall(request).execute();
                    if (isString) {
                        result.data = response.body().string();
                    } else {
                        result.data = response.body().bytes();
                    }
                    result.result = RESULT.SUCCESS;
                } catch (IOException e1) {
                    e1.printStackTrace();
                    result.result = RESULT.FAIL;
                    result.msg = "网络异常";
                }catch (JSONException e2){
                    e2.printStackTrace();
                    result.result = RESULT.FAIL;
                    result.msg = "JSON错误";
                }
                return result;
            }
        }.execute();
    }

    /**
     * 使用okhttp和AsyncTask封装的http-post异步请求
     *
     * @param url      请求的url
     * @param json     发送的数据
     * @param isForm   是否为form格式，传入true
     * @param callback 回调，返回字符串
     */
    public static void httpPost(final String url, final JSONObject json, final boolean isForm, final MyCallback callback) {
        new AsyncTask<Void, Void, Res>() {

            @Override
            protected void onPostExecute(Res s) {
                if (callback != null) {
                    switch (s.result){
                        case SUCCESS:
                            callback.onSuccess(s);
                            break;
                        case FAIL:
                            callback.onError(s);
                            break;
                    }
                }
            }

            @Override
            protected Res doInBackground(Void... voids) {
                Res result = new Res();
                try {
                    Request request;
                    // 加载数据
                    if (isForm) {
                        FormBody.Builder form_builder = new FormBody.Builder();
                        for (Iterator<String> it = json.keys(); it.hasNext(); ) {
                            String key = it.next();
                            form_builder.add(key, json.getString(key));
                        }
                        FormBody formBody = form_builder.build();
                        request = new Request.Builder()
                                .url(url)
                                .post(formBody)
                                .build();
                    } else {
                        RequestBody requestBody = RequestBody.create(json.toString(), JSON_TYPE);
                        request = new Request.Builder()
                                .url(url)
                                .post(requestBody)
                                .build();
                    }
                    // okhttp请求
                    Response response = client.newCall(request).execute();
                    byte[] bytes = response.body().bytes();
                    result.data = new String(bytes, "utf-8");
                    result.result = RESULT.SUCCESS;
                } catch (IOException e1) {
                    e1.printStackTrace();
                    result.result = RESULT.FAIL;
                    result.msg = "网络异常";
                }catch (JSONException e2){
                    e2.printStackTrace();
                    result.result = RESULT.FAIL;
                    result.msg = "JSON错误";
                }
                return result;
            }
        }.execute();
    }

    public static void writeFile(Context context, String filename, String data) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Context context, String filename) {
        byte[] data = new byte[1024*1024*5];
        String result = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            int res = fis.read(data);
            result = new String(data, 0, res);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getUid(){
        return user_uid;
    }

    public static void setUid(String uid){
        user_uid = uid;
    }

}
