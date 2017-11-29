package com.cxz.particle_animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.ScaleModifier;

public class MainActivity extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.v_index);
        new ParticleSystem(this, 500, R.mipmap.ic_launcher_round, 5000)
                .setAcceleration(0.00003f, 270)
                .addModifier(new ScaleModifier(0, 1.2f, 1000, 4000))
                .setFadeOut(5000)
                .setRotationSpeedRange(0,100)
                .emit(view,50);
    }
}
