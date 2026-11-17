package com.example.grazatrzymaj7;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView timerTextView;
    private TextView score;
    private Button start;
    private Button stop;
    private TextView info;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;
    private int counter = 0;
    private int n =0;
    private boolean isRunning = false;
    private int delay = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timerTextView = findViewById(R.id.timer);
        score = findViewById(R.id.score);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        info = findViewById(R.id.info);

        stop.setEnabled(false);


        start.setOnClickListener(v -> {
            if (isRunning) return;
            isRunning = true;
            counter = 0;
            stop.setEnabled(true);
            start.setEnabled(false);
            info.setText("");

            timerRunnable = new Runnable() {
                @Override
                public void run() {
                    if (isRunning) {
                        timerTextView.setText(String.valueOf(counter));
                        counter += 1;
                        handler.postDelayed(this, delay);
                        if(counter > 9.0){
                            counter = 0;
                        }
                    } else {
                        isRunning = false;
                        timerTextView.setText("9.0");
                    }
                }
            };
            handler.post(timerRunnable);
        });

        stop.setOnClickListener(v -> {
            isRunning = false;
            start.setEnabled(true);
            if(counter-1 == 7.0){
                n++;
                score.setText("Trafienia: " + n);
                info.setText("Trafienie!");
                if (delay>200){
                    delay-=50;
                }
            }
            else{
                info.setText("Pudlo zatrzymano na " + (counter-1));
            }
            if (timerRunnable != null) {
                handler.removeCallbacks(timerRunnable);
            }
            stop.setEnabled(false);
        });
    }
}