package course.examples.nt118;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Liên kết với file giao diện activity_home.xml
        setContentView(R.layout.activity_home);
    }
}
