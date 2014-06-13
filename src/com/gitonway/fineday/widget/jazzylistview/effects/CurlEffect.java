package com.gitonway.fineday.widget.jazzylistview.effects;

import android.view.View;

import com.gitonway.fineday.widget.jazzylistview.JazzyEffect;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class CurlEffect implements JazzyEffect {

    private static final int INITIAL_ROTATION_ANGLE = 90;

    @Override
    public void initView(View item, int position, int scrollDirection) {
        ViewHelper.setPivotX(item, 0);
        ViewHelper.setPivotY(item, item.getHeight() / 2);
        ViewHelper.setRotationY(item, INITIAL_ROTATION_ANGLE);
    }

    @Override
    public void setupAnimation(View item, int position, int scrollDirection, ViewPropertyAnimator animator) {
        animator.rotationYBy(-INITIAL_ROTATION_ANGLE);
    }

}
