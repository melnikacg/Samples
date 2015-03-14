package com.melnikacg.instagrammaterial.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.melnikacg.instagrammaterial.R;
import com.squareup.picasso.Picasso;

public class ExImageView extends ImageView {

    public ExImageView(final Context context) {
        this(context, null);
    }

    public ExImageView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExImageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        /*
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExImageView);

        final String imageUrl = a.getString(R.styleable.ExImageView_imageUrl);
        setCustomImageURL(imageUrl);
        a.recycle();*/
    }

    public void setCustomImageUrl(final String Url) {
        Picasso.with(getContext()).load(Url).
                placeholder(R.drawable.instagram_glyph_on_white).into(this);
    }

}

