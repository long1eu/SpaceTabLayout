/*
 * Copyright (c) 2016 Lung Razvan
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package eu.long1.spacetablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SpaceTabLayout extends RelativeLayout implements ViewPager.OnPageChangeListener {

    Context context;

    TabLayout tabLayout;

    private Tab leftTab;
    private Tab centerTab;
    private Tab rightTab;

    private RelativeLayout parentLayout;
    private RelativeLayout selectedTabLayout;
    private FloatingActionButton actionButton;
    private ImageView backgroundImage;
    private ImageView backgroundImage2;

    private TextView leftTextView;
    private TextView centerTextView;
    private TextView rightTextView;

    private ImageView leftImageView;
    private ImageView centerImageView;
    private ImageView rightImageView;

    List<Tab> tabs = new ArrayList<>();
    private List<Integer> tabSize = new ArrayList<>();

    private int currentPosition = 1;

    private View.OnClickListener leftActionOnClickListener;
    private View.OnClickListener centerActionOnClickListener;
    private View.OnClickListener rightActionOnClickListener;

    private Drawable defaultLeftButtonIcon;
    private Drawable defaultCenterButtonIcon;
    private Drawable defaultRightButtonIcon;

    private int defaultTextColor;

    private int defaultButtonColor;
    private int defaultTabColor;

    private String left_text;
    private String center_text;
    private String right_text;

    public SpaceTabLayout(Context context) {
        super(context);
        this.context = context;
        init();

    }

    public SpaceTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        initArrts(attrs);
    }

    public SpaceTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        initArrts(attrs);
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.activity_space_button, this);

        parentLayout = (RelativeLayout) findViewById(R.id.tabLayout);

        selectedTabLayout = (RelativeLayout) findViewById(R.id.selectedTabLayout);

        backgroundImage = (ImageView) findViewById(R.id.backgroundImage);
        backgroundImage2 = (ImageView) findViewById(R.id.backgroundImage2);

        actionButton = (FloatingActionButton) findViewById(R.id.fab);

        tabLayout = (TabLayout) findViewById(R.id.spaceTab);

        defaultLeftButtonIcon = context.getResources().getDrawable(R.drawable.ic_left_action);
        defaultCenterButtonIcon = context.getResources().getDrawable(R.drawable.ic_center_action);
        defaultRightButtonIcon = context.getResources().getDrawable(R.drawable.ic_right_action);

        defaultTextColor = ContextCompat.getColor(context, android.R.color.primary_text_dark);

        defaultButtonColor = ContextCompat.getColor(context, R.color.colorAccent);

        defaultTabColor = ContextCompat.getColor(context, R.color.colorPrimary);
    }

    private void initArrts(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpaceTabLayout);
        for (int i = 0; i < a.getIndexCount(); ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SpaceTabLayout_tab_color) {
                defaultTabColor = a.getColor(attr, context.getResources().getIdentifier("colorAccent", "color", context.getPackageName()));

            } else if (attr == R.styleable.SpaceTabLayout_button_color) {
                defaultButtonColor = a.getColor(attr, context.getResources().getIdentifier("colorPrimary", "color", context.getPackageName()));

            } else if (attr == R.styleable.SpaceTabLayout_text_color) {
                defaultTextColor = a.getColor(attr, ContextCompat.getColor(context, android.R.color.primary_text_dark));

            } else if (attr == R.styleable.SpaceTabLayout_left_icon) {
                defaultLeftButtonIcon = a.getDrawable(attr);

            } else if (attr == R.styleable.SpaceTabLayout_center_icon) {
                defaultCenterButtonIcon = a.getDrawable(attr);

            } else if (attr == R.styleable.SpaceTabLayout_right_icon) {
                defaultRightButtonIcon = a.getDrawable(attr);

            } else if (attr == R.styleable.SpaceTabLayout_left_text) {
                left_text = a.getString(attr);
            } else if (attr == R.styleable.SpaceTabLayout_center_text) {
                center_text = a.getString(attr);
            } else if (attr == R.styleable.SpaceTabLayout_right_text) {
                right_text = a.getString(attr);
            }
        }

    }


    public void initialize(ViewPager viewPager, FragmentManager fragmentManager, List<Fragment> fragments, final Bundle savedInstanceState) {
        viewPager.setAdapter(new PagerAdapter(fragmentManager, fragments));

        tabLayout.setupWithViewPager(viewPager);

        getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        tabSize.add(leftTab.getCustomView().getWidth());
                        tabSize.add(centerTab.getCustomView().getWidth());
                        tabSize.add(rightTab.getCustomView().getWidth());
                        tabSize.add(getWidth());
                        tabSize.add(backgroundImage.getWidth());

                        if (savedInstanceState != null) moveTab(tabSize, currentPosition);

                        if (Build.VERSION.SDK_INT < 16)
                            getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        else getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });


        viewPager.setCurrentItem(currentPosition);
        viewPager.addOnPageChangeListener(this);

        leftTab = tabLayout.getTabAt(0);
        leftTab.setCustomView(R.layout.left_tab_layout);

        centerTab = tabLayout.getTabAt(1);
        centerTab.setCustomView(R.layout.center_tab_layout);

        rightTab = tabLayout.getTabAt(2);
        rightTab.setCustomView(R.layout.right_tab_layout);

        tabs.add(leftTab);
        tabs.add(centerTab);
        tabs.add(rightTab);

        leftTextView = (TextView) findViewById(R.id.leftTextView);
        centerTextView = (TextView) findViewById(R.id.centerTextView);
        rightTextView = (TextView) findViewById(R.id.rightTextView);

        leftImageView = (ImageView) findViewById(R.id.leftImageView);
        centerImageView = (ImageView) findViewById(R.id.centerImageView);
        rightImageView = (ImageView) findViewById(R.id.rightImageView);

        selectedTabLayout.bringToFront();
        tabLayout.setSelectedTabIndicatorHeight(0);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "hei", Toast.LENGTH_SHORT).show();
            }
        });


        setAttrs();
    }

    private void setAttrs() {
        setTabColor(defaultTabColor);

        setButtonColor(defaultButtonColor);

        setLeftTextColor(defaultTextColor);
        setCenterTextColor(defaultTextColor);
        setRightTextColor(defaultTextColor);

        setLeftIcon(defaultLeftButtonIcon);
        setCenterIcon(defaultCenterButtonIcon);
        setRightIcon(defaultRightButtonIcon);

        if (left_text != null) setLeftText(left_text);
        if (center_text != null) setCenterText(center_text);
        if (right_text != null) setRightText(right_text);
    }

    public void saveState(Bundle outState) {
        outState.putInt("buttonPosition", currentPosition);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        tabs.get(position).getCustomView().setAlpha(positionOffset);
        if (position < 2) {
            tabs.get(position + 1).getCustomView().setAlpha(1 - positionOffset);
        }

        if (!tabSize.isEmpty()) {
            if (position < getCurrentPosition()) {
                final float endX = -tabSize.get(4) / 2 + getX(position, tabSize) + 42;
                final float startX = -tabSize.get(4) / 2 + getX(getCurrentPosition(), tabSize) + 42;

                if (!tabSize.isEmpty()) {
                    float a = endX - (positionOffset * (endX - startX));
                    selectedTabLayout.setX(a);
                }

            } else {
                position++;
                final float endX = -tabSize.get(4) / 2 + getX(position, tabSize) + 42;
                final float startX = -tabSize.get(4) / 2 + getX(getCurrentPosition(), tabSize) + 42;

                float a = startX + (positionOffset * (endX - startX));
                selectedTabLayout.setX(a);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        tabs.get(position).getCustomView().setAlpha(0);
        setCurrentPosition(position);
        moveTab(tabSize, position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void moveTab(List<Integer> tabSize, int position) {
        if (!tabSize.isEmpty()) {
            float backgroundX = -tabSize.get(4) / 2 + getX(position, tabSize) + 42;
            selectedTabLayout.setX(backgroundX);
            switch (position) {
                case 0:
                    actionButton.setImageDrawable(defaultLeftButtonIcon);
                    actionButton.setOnClickListener(leftActionOnClickListener);
                    break;
                case 1:
                    actionButton.setImageDrawable(defaultCenterButtonIcon);
                    actionButton.setOnClickListener(centerActionOnClickListener);
                    break;
                case 2:
                    actionButton.setImageDrawable(defaultRightButtonIcon);
                    actionButton.setOnClickListener(rightActionOnClickListener);
                    break;
            }
        }
    }

    private float getX(int position, List<Integer> sizesList) {
        if (!sizesList.isEmpty()) {
            float margin = (sizesList.get(3) - 3 * sizesList.get(0)) / 6;
            float tabSize = sizesList.get(0);

            float x = 0;
            switch (position) {
                case 0:
                    x = tabSize / 2 + margin - 42;
                    break;
                case 1:
                    x = tabSize * 3 / 2 + 3 * margin - 42;
                    break;
                case 2:
                    x = tabSize * 5 / 2 + 5 * margin - 42;
                    break;
            }
            return x;
        }
        return 0;
    }

    private void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * You can use it, for example, if want to set the same listener to all
     * button and you can use a switch using this method to identify the
     * button that was pressed.
     *
     * @return the current tab position
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Used to set the same OnClickListener to each position of the button.
     *
     * @param l the listener you want to set
     */
    @Override
    public void setOnClickListener(OnClickListener l) {
        setLeftActionOnClickListener(l);
        setCenterActionOnClickListener(l);
        setRightActionOnClickListener(l);
    }


    public View.OnClickListener getRightActionOnClickListener() {
        return rightActionOnClickListener;
    }

    public void setRightActionOnClickListener(View.OnClickListener rightActionOnClickListener) {
        this.rightActionOnClickListener = rightActionOnClickListener;
    }

    public View.OnClickListener getLeftActionOnClickListener() {
        return leftActionOnClickListener;
    }

    public void setLeftActionOnClickListener(View.OnClickListener leftActionOnClickListener) {
        this.leftActionOnClickListener = leftActionOnClickListener;
    }

    public View.OnClickListener getCenterActionOnClickListener() {
        return centerActionOnClickListener;
    }

    public void setCenterActionOnClickListener(View.OnClickListener centerActionOnClickListener) {
        this.centerActionOnClickListener = centerActionOnClickListener;
    }

    public FloatingActionButton getButton() {
        return actionButton;
    }

    public View getLeftView() {
        return leftTab.getCustomView();
    }

    public void setLeftView(View leftView) {
        leftTab.setCustomView(leftView);
    }

    public void setLeftView(@LayoutRes int leftLayout) {
        leftTab.setCustomView(leftLayout);
    }

    public View getCenterView() {
        return centerTab.getCustomView();
    }

    public void setCenterView(View centerView) {
        centerTab.setCustomView(centerView);
    }

    public void setCenterView(@LayoutRes int centerLayout) {
        leftTab.setCustomView(centerLayout);
    }

    public View getRightView() {
        return rightTab.getCustomView();
    }

    public void setRightView(View rightView) {
        rightTab.setCustomView(rightView);
    }

    public void setRightView(@LayoutRes int rightLayout) {
        rightTab.setCustomView(rightLayout);
    }

    public View getTabLayout() {
        return parentLayout;
    }

    public void setTabColor(@ColorInt int backgroundColor) {
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP);
        Drawable image = ContextCompat.getDrawable(context, R.drawable.background);
        Drawable image2 = ContextCompat.getDrawable(context, R.drawable.background2);
        image.setColorFilter(porterDuffColorFilter);
        image2.setColorFilter(porterDuffColorFilter);

        backgroundImage.setImageDrawable(image);
        backgroundImage2.setImageDrawable(image2);
    }


    public void setButtonColor(@ColorInt int backgroundColor) {
        actionButton.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
    }


    public void setLeftIcon(@DrawableRes int leftButtonIcon) {
        this.defaultLeftButtonIcon = context.getResources().getDrawable(leftButtonIcon);
        leftImageView.setImageResource(leftButtonIcon);
    }

    public void setCenterIcon(@DrawableRes int centerButtonIcon) {
        this.defaultCenterButtonIcon = context.getResources().getDrawable(centerButtonIcon);
        centerImageView.setImageResource(centerButtonIcon);
    }

    public void setRightIcon(@DrawableRes int rightButtonIcon) {
        this.defaultRightButtonIcon = context.getResources().getDrawable(rightButtonIcon);
        rightImageView.setImageResource(rightButtonIcon);
    }

    public void setLeftIcon(Drawable leftButtonIcon) {
        leftImageView.setImageDrawable(leftButtonIcon);
    }

    public void setCenterIcon(Drawable centerButtonIcon) {
        centerImageView.setImageDrawable(centerButtonIcon);
    }

    public void setRightIcon(Drawable rightButtonIcon) {
        rightImageView.setImageDrawable(rightButtonIcon);
    }

    public void setLeftText(String leftText) {
        leftTextView.setText(leftText);
    }

    public void setCenterText(String centerText) {
        centerTextView.setText(centerText);
    }

    public void setRightText(String rightText) {
        rightTextView.setText(rightText);
    }

    public void setLeftText(@StringRes int leftText) {
        leftTextView.setText(leftText);
    }

    public void setCenterText(@StringRes int centerText) {
        centerTextView.setText(centerText);
    }

    public void setRightText(@StringRes int rightText) {
        rightTextView.setText(rightText);
    }

    public void setLeftTextColor(@ColorInt int leftText) {
        leftTextView.setTextColor(leftText);
    }

    public void setCenterTextColor(@ColorInt int centerText) {
        centerTextView.setTextColor(centerText);
    }

    public void setRightTextColor(@ColorInt int rightText) {
        rightTextView.setTextColor(rightText);
    }

}
