package course.examples.nt118.network;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import course.examples.nt118.utils.TokenManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://10.0.2.2:3000/api/";
    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

    private static CookieManager cookieManager;

    // Lưu SharedPreferences để giữ cookie
    private static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = context.getSharedPreferences("MY_APP_PREFS", Context.MODE_PRIVATE);

        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        // Nếu có cookie đã lưu, load vào CookieManager
        String cookieStr = prefs.getString("COOKIE", null);
        if (cookieStr != null) {
            for (String c : cookieStr.split(";")) {
                if (!c.trim().isEmpty()) {
                    cookieManager.getCookieStore().add(URI.create(BASE_URL), HttpCookie.parse(c).get(0));
                }
            }
        }

        // Logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .addInterceptor(logging)
                // ✅ Interceptor tự động gắn token vào Authorization header
                .addInterceptor((Interceptor) chain -> {
                    Request original = chain.request();

                    String token = TokenManager.getToken(context);
                    Request.Builder requestBuilder = original.newBuilder();

                    if (token != null) {
                        requestBuilder.addHeader("Authorization", "Bearer " + token);
                    }

                    return chain.proceed(requestBuilder.build());
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getApiService() {
        return apiService;
    }

    // Ghi cookie vào SharedPreferences sau khi login
    public static void saveCookies() {
        if (cookieManager != null && prefs != null) {
            List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
            StringBuilder sb = new StringBuilder();
            for (HttpCookie cookie : cookies) {
                sb.append(cookie.toString()).append(";");
            }
            prefs.edit().putString("COOKIE", sb.toString()).apply();
        }
    }
}
