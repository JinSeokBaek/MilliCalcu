package com.js980112.millicalcu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager vp;

    Button btn1,btn2;

    FragmentAdapter fragmentAdapter;

    String[] title={"밀리시타 이벤트 계산기","투어 계산기","시어터 계산기","판 수에 따른 계산(공사중)","★담당돌★"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp=findViewById(R.id.vp);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);

        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addItem(new MainFragment());
        fragmentAdapter.addItem(new TourFragment());
        fragmentAdapter.addItem(new TheaterFragment());
        fragmentAdapter.addItem(new BeforeFragment());
        fragmentAdapter.addItem(new EndFragment());
        vp.setAdapter(fragmentAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(title[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(2);
            }
        });

    }
    class FragmentAdapter extends FragmentStatePagerAdapter {

        // ViewPager에 들어갈 Fragment들을 담을 리스트
        private ArrayList<Fragment> fragments = new ArrayList<>();

        // 필수 생성자
        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        // List에 Fragment를 담을 함수
        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }

        void editItem(Fragment fragment, int postion) {
            fragments.set(postion, fragment);
        }
    }
}
