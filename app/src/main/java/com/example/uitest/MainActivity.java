package com.example.uitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tab_Layout;
    ViewPager view_Pager;
    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab_Layout = findViewById(R.id.tabLayout);
        view_Pager = findViewById(R.id.viewPager);

        final CustomAdapter customAdapter = new CustomAdapter(getSupportFragmentManager());
        customAdapter.addFragment(new LoginFragment() , "Login");
        customAdapter.addFragment(new SignUpFragment() , "Sign Up");
        view_Pager.setAdapter(customAdapter);

        tab_Layout.setupWithViewPager(view_Pager);
    }
}