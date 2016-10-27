package eu.long1.spacebuttonbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SpaceButtonView extends LinearLayout {

    TextView actionLeft;
    ImageView button;
    TextView actionRight;

    public SpaceButtonView(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpaceButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpaceButtonView, 0, 0);
        int buttonIcon = a.getResourceId(R.styleable.SpaceButtonView_icon, R.drawable.ic_fail);
        a.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.space_button_layout, this, true);


        actionLeft = (TextView) getChildAt(0);
        button = (ImageView) getChildAt(1);
        actionRight = (TextView) getChildAt(2);

        button.setImageResource(buttonIcon);
    }

    public SpaceButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpaceButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setButtonImage(@DrawableRes int resource) {
        button.setImageResource(resource);
    }


}
