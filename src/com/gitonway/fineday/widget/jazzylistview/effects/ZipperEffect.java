package com.gitonway.fineday.widget.jazzylistview.effects;

import android.view.View;

import com.gitonway.fineday.widget.jazzylistview.JazzyEffect;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class ZipperEffect implements JazzyEffect {

    @Override
    public void initView(View item, int position, int scrollDirection) {
        boolean isEven = position % 2 == 0;
        ViewHelper.setTranslationX(item, (isEven ? -1 : 1) * item.getWidth());
    }

    @Override
    public void setupAnimation(View item, int position, int scrollDirection, ViewPropertyAnimator animator) {
        animator.translationX(0);
    }

}
