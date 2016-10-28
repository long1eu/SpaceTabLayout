package eu.long1.spacebuttonbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpaceButtonActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TabLayout.Tab leftTab;
    private TabLayout.Tab centerTab;
    private TabLayout.Tab rightTab;

    private RelativeLayout tabView;
    private RelativeLayout selectedTabLayout;
    private FloatingActionButton actionButton;
    private ImageView backgroundImage;
    private ImageView backgroundImage2;

    List<TabLayout.Tab> tabs = new ArrayList<>();
    private List<Integer> tabSize = new ArrayList<>();
    private List<Fragment> fragments;

    private int currentPosition = 2;

    private View.OnClickListener leftActionOnClickListener;
    private View.OnClickListener centerActionOnClickListener;
    private View.OnClickListener rightActionOnClickListener;
    private int leftButtonIcon = R.drawable.ic_left_action_selected;
    private int centerButtonIcon = R.drawable.ic_center_action_selected;
    private int rightButtonIcon = R.drawable.ic_right_action_selected;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_button);

        if (fragments != null) {
            if (fragments.size() != 3) {
                throw new RuntimeException("You need to add three fragments but you entered " + fragments.size() + ".");
            }
        } else {
            throw new RuntimeException("You need to add three fragments but you entered 0.");
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        tabLayout = (TabLayout) findViewById(R.id.spaceTab);
        tabLayout.setupWithViewPager(viewPager);


        leftTab = tabLayout.getTabAt(0);
        leftTab.setCustomView(R.layout.left_tab_layout);

        centerTab = tabLayout.getTabAt(1);
        centerTab.setCustomView(R.layout.center_tab_layout);

        rightTab = tabLayout.getTabAt(2);
        rightTab.setCustomView(R.layout.right_tab_layout);

        tabs.add(leftTab);
        tabs.add(centerTab);
        tabs.add(rightTab);


        tabLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        tabSize.add(leftTab.getCustomView().getWidth());
                        tabSize.add(centerTab.getCustomView().getWidth());
                        tabSize.add(rightTab.getCustomView().getWidth());
                        tabSize.add(tabLayout.getWidth());
                        tabSize.add(backgroundImage.getWidth());

                        if (savedInstanceState != null) moveTab(tabSize, currentPosition);

                        if (Build.VERSION.SDK_INT < 16)
                            tabLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        else tabLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });


        viewPager.setCurrentItem(currentPosition);
        viewPager.addOnPageChangeListener(this);


        tabView = (RelativeLayout) findViewById(R.id.tabLayout);
        selectedTabLayout = (RelativeLayout) findViewById(R.id.selectedTabLayout);
        backgroundImage = (ImageView) findViewById(R.id.backgroundImage);
        backgroundImage2 = (ImageView) findViewById(R.id.backgroundImage2);
        actionButton = (FloatingActionButton) findViewById(R.id.fab);

        selectedTabLayout.bringToFront();
        tabLayout.setSelectedTabIndicatorHeight(0);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "hei", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void init(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("buttonPosition", currentPosition);
        super.onSaveInstanceState(outState);
    }

    public void moveTab(List<Integer> tabSize, int position) {
        if (!tabSize.isEmpty()) {
            float backgroundX = -tabSize.get(4) / 2 + getX(position, tabSize) + 42;
            selectedTabLayout.setX(backgroundX);
            switch (position) {
                case 0:
                    actionButton.setImageResource(leftButtonIcon);
                    actionButton.setOnClickListener(leftActionOnClickListener);
                    break;
                case 1:
                    actionButton.setImageResource(centerButtonIcon);
                    actionButton.setOnClickListener(centerActionOnClickListener);
                    break;
                case 2:
                    actionButton.setImageResource(rightButtonIcon);
                    actionButton.setOnClickListener(rightActionOnClickListener);
                    break;
            }
        }
    }

    public float getX(int position, List<Integer> sizesList) {
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

    private void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
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

    public FloatingActionButton getActionButton() {
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
        return tabView;
    }

    public void setTabBackgroundColor(@ColorRes int backgroundColor, @Nullable Context context) {
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(ContextCompat.getColor(this, backgroundColor), PorterDuff.Mode.SRC_ATOP);
        Drawable image = AppCompatResources.getDrawable(this, R.drawable.background);
        Drawable image2 = AppCompatResources.getDrawable(this, R.drawable.background2);
        image.setColorFilter(porterDuffColorFilter);
        image2.setColorFilter(porterDuffColorFilter);

        backgroundImage.setImageDrawable(image);
        backgroundImage2.setImageDrawable(image2);
    }

    public void setTabBackgroundColor(@ColorInt int backgroundColor) {
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP);
        Drawable image = AppCompatResources.getDrawable(this, R.drawable.background);
        Drawable image2 = AppCompatResources.getDrawable(this, R.drawable.background2);
        image.setColorFilter(porterDuffColorFilter);
        image2.setColorFilter(porterDuffColorFilter);

        backgroundImage.setImageDrawable(image);
        backgroundImage2.setImageDrawable(image2);
    }

    public void setActionButtonIcon(@DrawableRes int buttonIcon) {
        actionButton.setImageResource(buttonIcon);
    }

    public void setActionButtonBackgroundColor(@ColorRes int backgroundColor) {
        actionButton.setBackgroundColor(ContextCompat.getColor(this, backgroundColor));
    }

    public void setLeftIcon(@DrawableRes int leftButtonIcon) {
        this.leftButtonIcon = leftButtonIcon;
        ((ImageView) ((LinearLayout) getLeftView()).getChildAt(0)).setImageResource(leftButtonIcon);
    }

    public void setCenterIcon(@DrawableRes int centerButtonIcon) {
        this.centerButtonIcon = centerButtonIcon;
        ((ImageView) ((LinearLayout) getCenterView()).getChildAt(0)).setImageResource(centerButtonIcon);
    }

    public void setRightIcon(@DrawableRes int rightButtonIcon) {
        this.rightButtonIcon = rightButtonIcon;
        ((ImageView) ((LinearLayout) getRightView()).getChildAt(0)).setImageResource(rightButtonIcon);
    }

    public void setLeftText(String leftText) {
        ((TextView) ((LinearLayout) getLeftView()).getChildAt(1)).setText(leftText);
    }

    public void setCenterText(String centerText) {
        ((TextView) ((LinearLayout) getCenterView()).getChildAt(1)).setText(centerText);
    }

    public void setRightText(String rightText) {
        ((TextView) ((LinearLayout) getRightView()).getChildAt(1)).setText(rightText);
    }

    public void setLeftText(@StringRes int leftText) {
        ((TextView) ((LinearLayout) getLeftView()).getChildAt(1)).setText(leftText);
    }

    public void setCenterText(@StringRes int centerText) {
        ((TextView) ((LinearLayout) getCenterView()).getChildAt(1)).setText(centerText);
    }

    public void setRightText(@StringRes int rightText) {
        ((TextView) ((LinearLayout) getRightView()).getChildAt(1)).setText(rightText);
    }


}
