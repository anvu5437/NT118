package course.examples.nt118;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import course.examples.nt118.model.LoginRequest;
import course.examples.nt118.model.UserResponse;
import course.examples.nt118.network.ApiService;
import course.examples.nt118.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signInButton;
    private TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ view từ layout
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpTextView = findViewById(R.id.signUpTextView);

        // 👉 Khi bấm "Sign in"
        signInButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email, password);
        });

        // 👉 Khi bấm "Sign up" (chuyển sang RegisterActivity)
        signUpTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        LoginRequest request = new LoginRequest(email, password);

        Call<UserResponse> call = api.login(request);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getUser() != null) {
                    UserResponse.User user = response.body().getUser();
                    Toast.makeText(LoginActivity.this,
                            "Đăng nhập thành công: " + user.getName(),
                            Toast.LENGTH_LONG).show();

                    // TODO: chuyển sang màn hình chính sau khi login (HomeActivity)
                    // Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    // intent.putExtra("username", user.getName());
                    // startActivity(intent);
                    // finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Sai email hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


