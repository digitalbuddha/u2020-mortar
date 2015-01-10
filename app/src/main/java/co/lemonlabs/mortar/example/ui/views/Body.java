package co.lemonlabs.mortar.example.ui.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.CycleInterpolator;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import co.lemonlabs.mortar.example.ui.screens.NestedScreen;
import mortar.Mortar;

public class Body extends FrameLayout {

     @Inject
     NestedScreen.ChildPresenter2 presenter;

    @Inject Context context;


    private ObjectAnimator animator;

    public Body(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        presenter.takeView(this);
        ButterKnife.inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        createAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
        animator = null;
        presenter.dropView(this);
    }

    private void createAnimation() {
        animator = ObjectAnimator.ofFloat(this, "rotation", 180);
        animator.setDuration(3600);
        animator.setInterpolator(new CycleInterpolator(5));
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
    }

    public void toggleAnimation() {
        if (animator != null) {
            if (animator.isRunning()) {
                animator.end();
            } else {
                animator.start();
            }
        }
    }
}

