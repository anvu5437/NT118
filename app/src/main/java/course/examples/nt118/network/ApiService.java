package course.examples.nt118.network;

import course.examples.nt118.model.LoginRequest;
import course.examples.nt118.model.RegisterRequest;
import course.examples.nt118.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/auth/login")
    Call<UserResponse> login(@Body LoginRequest request);

    @POST("/api/auth/register")
    Call<UserResponse> register(@Body RegisterRequest request);

}

