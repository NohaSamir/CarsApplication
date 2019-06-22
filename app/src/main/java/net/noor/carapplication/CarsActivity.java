package net.noor.carapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CarsActivity extends AppCompatActivity {

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Bind(R.id.shadow1)
    View shadow1;

    @Bind(R.id.shadow2)
    View shadow2;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    private Context mContext;
    private CarsPagerAdapter mCarsAdapter;

    private final int START_YEAR = 2000;
    private final int END_YEAR = 2019;

    private final int PAGES_NUM = END_YEAR - START_YEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);

        ButterKnife.bind(this);

        mContext = this;

        mCarsAdapter = new CarsPagerAdapter(mContext, PAGES_NUM, Car.getCarList(), new CarsPagerAdapter.PagerClickListener() {
            @Override
            public void onPageSelected(Car car, View v) {
                //ToDo: On item selected => timeline  slide up
                CarDetailsActivity.start(mContext, car, v);
            }
        });
        viewPager.setAdapter(mCarsAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer(shadow1, shadow2));
        viewPager.setCurrentItem(0);

        setupYearsTabLayout();

    }

    private void setupYearsTabLayout() {
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < PAGES_NUM; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.unselected_text);
            ((TextView) tab.getCustomView()).setText(String.valueOf(i + START_YEAR));

            if (i == 0) {
                ((TextView) tab.getCustomView()).setTextColor(Color.BLACK);
                ((TextView) tab.getCustomView()).setTextSize(30);
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(Color.BLACK);
                ((TextView) tab.getCustomView()).setTextSize(30);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(Color.WHITE);
                ((TextView) tab.getCustomView()).setShadowLayer(3f, 0f, 0f, Color.BLACK);
                ((TextView) tab.getCustomView()).setTextSize(18);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tabLayout.setSelected(true);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                int tabLayoutWidth = tabLayout.getWidth();

                int deviceWidth = Utils.getWindowWidthInPixel(mContext);

                if (tabLayoutWidth < deviceWidth) {
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                } else {
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                }
            }
        });
    }
}
