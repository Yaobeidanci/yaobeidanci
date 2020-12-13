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
    public static final String BASE_URL = "http://10.0.2.2:5000";
    /**
     * 回调函数
     */
    public interface MyCallback {
        /**
         * 请求成功时
         * @param result 请求结果
         */
        void onSuccess(Object result);

        /**
         * 请求失败时，没想好怎么用，暂时不要用
         * @param result
         */
        void onError(Object result);
    }

    public static final MediaType JSON_TYPE
            = MediaType.get("application/json; charset=utf-8");
    static OkHttpClient client = new OkHttpClient();

    // 异步，但是应该是无法更新UI线程，因此不使用
    public static void httpGetAsync(String url, JSONObject json, final MyCallback callback, final boolean isString) {
        String result = null;
        HttpUrl.Builder url_builder = HttpUrl.parse(url).newBuilder();
        try {

            for (Iterator<String> it = json.keys(); it.hasNext(); ) {
                String key = it.next();
                url_builder.addQueryParameter(key, json.getString(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url(url_builder.build())
                .build();

        Response response = null;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("http-get", "onFailure: ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (callback != null) {
                    if (isString) {
                        callback.onSuccess(response.body().string());
                    } else {
                        byte[] bytes = response.body().bytes();
                        callback.onSuccess(bytes);
                    }

                }
            }
        });
    }

    // 异步，但是应该是无法更新UI线程，因此不使用
    public static void httpPostAsync(final String url, JSONObject json, final boolean isForm, final MyCallback callback) {
        try {
            Request request;
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

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("http-post", "onFailure: ");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (callback != null) {
                        callback.onSuccess(response.body().string());
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用okhttp和AsyncTask封装的http-get异步请求
     *
     * @param url      请求的url
     * @param json     传入的查询参数
     * @param callback 回调
     * @param isString 返回是否为字符串，如果是，则返回字符串，否则返回字节数组
     */
    public static void httpGet(final String url, final JSONObject json, final MyCallback callback, final boolean isString) {
        new AsyncTask<Void, Void, Object>() {

            @Override
            protected void onPostExecute(Object s) {
                if (callback != null) {
                    callback.onSuccess(s);
                }
            }

            @Override
            protected Object doInBackground(Void... voids) {
                Object result = null;
                // 拼接url
                HttpUrl.Builder url_builder = HttpUrl.parse(url).newBuilder();
                try {
                    for (Iterator<String> it = json.keys(); it.hasNext(); ) {
                        String key = it.next();
                        url_builder.addQueryParameter(key, json.getString(key));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // okhttp请求
                Request request = new Request.Builder()
                        .url(url_builder.build())
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (isString) {
                        result = response.body().string();
                    } else {
                        result = response.body().bytes();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPostExecute(String s) {
                if (callback != null) {
                    callback.onSuccess(s);
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result = null;
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
                    result = new String(bytes, "utf-8");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
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
}
