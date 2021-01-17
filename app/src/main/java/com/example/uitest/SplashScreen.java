package com.example.uitest;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SplashScreen extends AppCompatActivity {

    ImageView img_Background , img_Logo;

    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private ScreenSliderPagerAdapter pagerAdapter;
    Animation animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img_Background = findViewById(R.id.imageViewBackground);
        img_Logo = findViewById(R.id.imageLogo);

        img_Background.animate().translationY(-2000).setDuration(700).setStartDelay(2000).start();
        img_Logo.animate().translationY(2000).setDuration(700).setStartDelay(2000).start();

        viewPager = findViewById(R.id.liquidPager);
        pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        animation = AnimationUtils.loadAnimation(this , R.anim.onboarding_animation);
        viewPager.startAnimation(animation);
    }
    private class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter{

        public ScreenSliderPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                    return tab1;

                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;

                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
