package net.noor.carapplication;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CarDetailsActivity extends AppCompatActivity {

    private static final int BACKGROUND_ANIMATION_DURATION = 2000;
    private static int START_ANIMATE_TITLE_AT;
    private static int START_ANIMATE_Date_View_AT;
    private static int START_ANIMATE_CAR_View_AT;

    private static int REVERSE_ANIMATE_TITLE_AT;
    private static int REVERSE_ANIMATE_Date_View_AT;
    private static int REVERSE_ANIMATE_CAR_View_AT;

    public static final String CAR = "car";

    @Bind(R.id.backgroundImageView)
    ImageView backgroundImageView;

    @Bind(R.id.carImageView)
    ImageView carImageView;

    @Bind(R.id.container)
    ConstraintLayout container;

    @Bind(R.id.fromTextView)
    TextView fromTextView;

    @Bind(R.id.toTextView)
    TextView toTextView;

    @Bind(R.id.titleTextView)
    TextView titleTextView;

    @Bind(R.id.detailsView)
    View detailsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        ButterKnife.bind(this);

        Car mCar = (Car) getIntent().getSerializableExtra(CAR);


        /*Handel Transition between activities if sdk after lollipop*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            container.setTransitionName(String.valueOf(mCar.getId()));
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    calculateRequiredParameters();
                    startAnimation();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        } else {
            calculateRequiredParameters();
            startAnimation();
        }
    }

    //******************************************************************************

    /**
     * On view appear -> start all animations
     * 1- Slide up background from black to white
     * 2- when background slide up arrive to bottom of date -> start animate date background from white to black
     * 3- when background slide down arrive to bottom of car image -> zoom in car image
     * 4- when background slide down arrive to bottom of title -> start animate title background from white to black
     * 5- At the end of background slide animation -> details view slide up
     */

    private void startAnimation() {

        container.setBackgroundColor(Color.TRANSPARENT);

        slideUpBackgroundImageViewAndDetailsView();

        titleTextView.postDelayed(new Runnable() {
            public void run() {
                animateTextViewColor(titleTextView, Color.WHITE, Color.BLACK);

            }
        }, START_ANIMATE_TITLE_AT);


        fromTextView.postDelayed(new Runnable() {
            public void run() {
                animateTextViewColor(fromTextView, Color.WHITE, Color.BLACK);
                toTextView.setTextColor(Color.WHITE);
                toTextView.setShadowLayer(3f, 0f, 0f, Color.BLACK);

            }
        }, START_ANIMATE_Date_View_AT);

        carImageView.postDelayed(new Runnable() {
            public void run() {
                zoomInCarImageView();
            }
        }, START_ANIMATE_CAR_View_AT);
    }

    private void slideUpBackgroundImageViewAndDetailsView() {

        //ToDo: Background slide up in a circle gradient background
        Animation animationUtils = AnimationUtils.loadAnimation(this, R.anim.slide_up_center_parent);
        backgroundImageView.startAnimation(animationUtils);
        animationUtils.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                slideDetailsView();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void animateTextViewColor(TextView textView, int fromColor, int toColor) {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(textView, "textColor",
                fromColor, toColor);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setDuration(500);
        colorAnim.start();
    }

    private void slideDetailsView() {
        detailsView.setVisibility(View.VISIBLE);
        Animation animationUtils = AnimationUtils.loadAnimation(this, R.anim.slide_up_center_parent);
        animationUtils.setDuration(1000);
        detailsView.startAnimation(animationUtils);
    }

    private void zoomInCarImageView() {
        carImageView.animate().scaleX(1.5f).scaleY(1.5f).translationX(300).setDuration(1000);
    }


    //*****************************************************************************

    /**
     * On back pressed -> reverse all animation
     * 1- Slide down background from white to black
     * 2- when background slide down arrive to top of date -> start animate date background from black to white
     * 3- when background slide down arrive to top of car image -> zoom out car image
     * 4- when background slide down arrive to top of title -> start animate title background from black to white and hide details view
     * 5- At the end of background slide animation -> finish the view
     */
    private void reverseAnimation() {

        slideDownBackgroundImageViewAndFinish();

        titleTextView.postDelayed(new Runnable() {
            public void run() {
                animateTextViewColor(titleTextView, Color.BLACK, Color.WHITE);
                detailsView.clearAnimation();
                detailsView.setVisibility(View.GONE);
            }
        }, REVERSE_ANIMATE_TITLE_AT);


        fromTextView.postDelayed(new Runnable() {
            public void run() {
                animateTextViewColor(fromTextView, Color.BLACK, Color.WHITE);
                toTextView.setTextColor(Color.BLACK);
                toTextView.setShadowLayer(3f, 0f, 0f, Color.WHITE);

            }
        }, REVERSE_ANIMATE_Date_View_AT);

        carImageView.postDelayed(new Runnable() {
            public void run() {
                zoomOutCarImageView();
            }
        }, REVERSE_ANIMATE_CAR_View_AT);
    }

    private void slideDownBackgroundImageViewAndFinish() {

        //ToDo: Background slide down in a circle gradient background
        Animation animationUtils = AnimationUtils.loadAnimation(this, R.anim.slide_down_center_parent);
        backgroundImageView.startAnimation(animationUtils);

        animationUtils.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                CarDetailsActivity.this.overridePendingTransition(0, R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void zoomOutCarImageView() {
        carImageView.animate().scaleX(1f).scaleY(1f).translationX(0).setDuration(1000);
    }
    //******************************************************************************

    /**
     * Start car details view
     *
     * @param context context
     * @param car     car object
     * @param view    animated view
     */
    public static void start(Context context, Car car, View view) {
        Intent intent = new Intent(context, CarDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(CAR, car);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) context,
                view,
                ViewCompat.getTransitionName(view));

        context.startActivity(intent, options.toBundle());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        detailsView.clearAnimation();
        detailsView.setVisibility(View.GONE);
        calculateRequiredParameters();
        startAnimation();
    }


    @Override
    public void onBackPressed() {
        reverseAnimation();
    }

    //******************************************************************************

    /**
     * On Background animation become at bottom of text view , TextView background start animate
     * we need to calculate when we start animate text background
     * <p>
     * Total Background animation Duration -> Screen Height
     * time at ?               -> Bottom View Position
     * <p>
     * Time = (Total duration * Bottom view position ) / screen height
     * <p>
     * First : calculate when title text view background animation will start
     * Second: calculate when date text view background animation will start
     */
    private void calculateRequiredParameters() {
        int SCREEN_HEIGHT = Utils.getWindowHeightInPixel(this);

        int titleBottomPositionFromParentTop = Utils.getViewPosition(titleTextView)[1];
        int titleBottomPositionFromParentBottom = SCREEN_HEIGHT - Utils.getViewPosition(titleTextView)[1] - titleTextView.getHeight();
        START_ANIMATE_TITLE_AT = (titleBottomPositionFromParentBottom * BACKGROUND_ANIMATION_DURATION) / SCREEN_HEIGHT;
        REVERSE_ANIMATE_TITLE_AT = (titleBottomPositionFromParentTop * BACKGROUND_ANIMATION_DURATION) / SCREEN_HEIGHT;

        int dateBottomPositionFromParentTop = Utils.getViewPosition(fromTextView)[1];
        int dateBottomPositionFromParentBottom = SCREEN_HEIGHT - Utils.getViewPosition(fromTextView)[1] - fromTextView.getHeight();
        START_ANIMATE_Date_View_AT = (dateBottomPositionFromParentBottom * BACKGROUND_ANIMATION_DURATION) / SCREEN_HEIGHT;
        REVERSE_ANIMATE_Date_View_AT = (dateBottomPositionFromParentTop * BACKGROUND_ANIMATION_DURATION) / SCREEN_HEIGHT;

        int carImageBottomPositionFromParentTop = Utils.getViewPosition(carImageView)[1];
        int carImageBottomPositionFromParentBottom = SCREEN_HEIGHT - Utils.getViewPosition(carImageView)[1] - carImageView.getHeight();
        START_ANIMATE_CAR_View_AT = (carImageBottomPositionFromParentBottom * BACKGROUND_ANIMATION_DURATION) / SCREEN_HEIGHT;
        REVERSE_ANIMATE_CAR_View_AT = (carImageBottomPositionFromParentTop * BACKGROUND_ANIMATION_DURATION) / SCREEN_HEIGHT;

    }


}
