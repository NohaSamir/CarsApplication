package net.noor.carapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nsamir on 6/10/2019.
 */

public class CarsPagerAdapter extends PagerAdapter {

    @Bind(R.id.container)
    ConstraintLayout container;

    private Context context;

    private int mPagesNum;
    private List<Car> cars;
    private PagerClickListener mListener;

    public CarsPagerAdapter(Context context, int pagesNum, List<Car> cars , PagerClickListener listener) {
        this.context = context;
        this.mPagesNum = pagesNum;
        this.cars = cars;
        mListener = listener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_car_header, null);
        container.addView(view);
        ButterKnife.bind(this, view);

        Car car = cars.get(position);
        ViewCompat.setTransitionName(view, String.valueOf(car.getId()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPageSelected(cars.get(position), v);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mPagesNum;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    interface PagerClickListener
    {
        void onPageSelected(Car car , View v);
    }

}
