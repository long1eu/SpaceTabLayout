package eu.long1.spacebuttonbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SpaceButtonView extends TabLayout {


    TextView actionLeftText;
    ImageView actionLeftImage;
    FloatingActionButton button;
    TextView actionRightText;
    ImageView actionRightImage;

    public SpaceButtonView(Context context) {
        super(context);
    }


    public SpaceButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public SpaceButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
