package com.hoangviet.diemdanh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hoangviet.diemdanh.Fragments.AdapterFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Trang chủ");
        actionBar.hide();
        actionBar.setDisplayHomeAsUpEnabled(true);
        viewPager=findViewById(R.id.viewPage);
        bottomNavigationView=findViewById(R.id.button_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.thongbao:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.trangchu:
                    viewPager.setCurrentItem(1);
                    break;
                case  R.id.canhan:
                    viewPager.setCurrentItem(2);
                    break;


            }

            return true;
        });
        AdapterFragment adapterFragment=new AdapterFragment(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapterFragment);
        vuot();
        bottomNavigationView.setSelectedItemId(R.id.trangchu);
    }
    private void vuot() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.thongbao).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.trangchu).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.canhan).setChecked(true);
                        break;



                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}