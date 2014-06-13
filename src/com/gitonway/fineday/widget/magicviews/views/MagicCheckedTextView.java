package com.gitonway.fineday.widget.magicviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import com.gitonway.fineday.widget.magicviews.utils.FontUtils;

/**
 * Created by ivankocijan on 19.05.2014..
 */
public class MagicCheckedTextView extends CheckedTextView {

    public MagicCheckedTextView (Context context) {
        super(context);
    }

    public MagicCheckedTextView (Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context, attrs);
        }

    }

    public MagicCheckedTextView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (!isInEditMode()) {
            init(context, attrs);
        }

    }


    private void init (Context ctx, AttributeSet attrs) {

        FontUtils.setTypeface(ctx, attrs, this);

    }

}
