package course.examples.nt118;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import course.examples.nt118.model.RegisterRequest;
import course.examples.nt118.model.UserResponse;
import course.examples.nt118.network.ApiService;
import course.examples.nt118.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private TextView signInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ view từ XML
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        signInTextView = findViewById(R.id.signInTextView);

        // Khi người dùng bấm "Sign up"
        signUpButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirm = confirmPasswordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirm)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            registerUser(email, password);
        });

        // Khi người dùng bấm "Sign in"
        signInTextView.setOnClickListener(v -> {
            finish(); // Quay lại LoginActivity
        });
    }

    private void registerUser(String email, String password) {
        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        // Tạo avatar ngẫu nhiên (giống như backend sử dụng DiceBear)
        String avatarUrl = "https://api.dicebear.com/7.x/avataaars/svg?seed=" + email;
        String name = email.substring(0, email.indexOf('@')); // Dùng phần đầu email làm tên tạm

        RegisterRequest request = new RegisterRequest(name, email, password, avatarUrl);

        Call<UserResponse> call = api.register(request);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this,
                            "Đăng ký thành công! Vui lòng đăng nhập.",
                            Toast.LENGTH_LONG).show();
                    finish(); // Quay lại màn hình đăng nhập
                } else {
                    Toast.makeText(RegisterActivity.this,
                            "Email đã tồn tại hoặc lỗi đăng ký.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,
                        "Lỗi kết nối: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
