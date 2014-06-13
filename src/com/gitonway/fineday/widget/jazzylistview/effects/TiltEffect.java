package com.gitonway.fineday.widget.jazzylistview.effects;

import android.view.View;

import com.gitonway.fineday.widget.jazzylistview.JazzyEffect;
import com.gitonway.fineday.widget.jazzylistview.JazzyHelper;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class TiltEffect implements JazzyEffect {

    private static final float INITIAL_SCALE_FACTOR = 0.7f;

    @Override
    public void initView(View item, int position, int scrollDirection) {
        ViewHelper.setPivotX(item, item.getWidth() / 2);
        ViewHelper.setPivotY(item, item.getHeight() / 2);
        ViewHelper.setScaleX(item, INITIAL_SCALE_FACTOR);
        ViewHelper.setScaleY(item, INITIAL_SCALE_FACTOR);
        ViewHelper.setTranslationY(item, item.getHeight() / 2 * scrollDirection);
        ViewHelper.setAlpha(item, JazzyHelper.OPAQUE / 2);
    }

    @Override
    public void setupAnimation(View item, int position, int scrollDirection, ViewPropertyAnimator animator) {
        animator
            .translationYBy(-item.getHeight() / 2 * scrollDirection)
            .scaleX(1)
            .scaleY(1)
            .alpha(JazzyHelper.OPAQUE);
    }

}
