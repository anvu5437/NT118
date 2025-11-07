package course.examples.nt118.network;

import java.util.List;
import java.util.Map;

import course.examples.nt118.model.UserResponse;
import course.examples.nt118.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // ========== AUTH ROUTES ==========
    @POST("auth/register")
    Call<Map<String, String>> registerUser(@Body Map<String, String> body);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body Map<String, String> body);

    @GET("auth/me")
    Call<Map<String, Object>> checkToken();

    @POST("auth/logout")
    Call<Map<String, String>> logout();

    @POST("auth/forgot-password")
    Call<Map<String, String>> forgotPassword(@Body Map<String, String> body);

    @POST("auth/verify-otp")
    Call<Map<String, String>> verifyOtp(@Body Map<String, String> body);

    @POST("auth/reset-password")
    Call<Map<String, String>> resetPassword(@Body Map<String, String> body);

    // ========== USER ROUTES ==========
    @GET("users")
    Call<List<UserResponse>> getAllUsers();

    @GET("users/{id}")
    Call<UserResponse> getUserById(@Path("id") String id);

    @POST("users/editProfile/{id}")
    Call<UserResponse> editProfile(@Path("id") String id, @Body Map<String, Object> body);
}
