package eu.long1.spacebuttonbar;

import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class SpaceButtonActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    TabLayout.Tab leftTab;
    TabLayout.Tab centerTab;
    TabLayout.Tab rightTab;

    FloatingActionButton selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_button);

        Bundle bundle = getIntent().getExtras();
        String[] fragments = bundle.getStringArray("fragments");
        if (fragments != null) {
            if (fragments.length != 3) {
                throw new RuntimeException("You need to add three fragments but you entered " + fragments.length + ".");
            }
        } else {
            throw new RuntimeException("You need to add three fragments but you entered 0.");
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        try {
            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), SpaceButtonActivity.this, fragments));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("You entered wrong fragment class name");
        }

        selectedTab = (FloatingActionButton) findViewById(R.id.fab);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


        leftTab = tabLayout.getTabAt(0);
        leftTab.setCustomView(R.layout.left_tab_layout);

        centerTab = tabLayout.getTabAt(1);
        centerTab.setCustomView(R.layout.center_tab_layout);

        rightTab = tabLayout.getTabAt(2);
        rightTab.setCustomView(R.layout.right_tab_layout);

        final List<TabLayout.Tab> tabs = new ArrayList<>();
        tabs.add(leftTab);
        tabs.add(centerTab);
        tabs.add(rightTab);

        final List<Integer> tabSize = new ArrayList<>();


        tabLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        tabSize.add(leftTab.getCustomView().getWidth());
                        tabSize.add(centerTab.getCustomView().getWidth());
                        tabSize.add(rightTab.getCustomView().getWidth());
                        tabSize.add(tabLayout.getWidth());
                    }


                });


        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabs.get(position).getCustomView().setAlpha(positionOffset);
                if (position < 2) {
                    tabs.get(position + 1).getCustomView().setAlpha(1 - positionOffset);
                }


            }

            @Override
            public void onPageSelected(int position) {
                tabs.get(position).getCustomView().setAlpha(0);

                if (!tabSize.isEmpty()) {

                    int margins = (tabSize.get(3) - tabSize.get(0) - tabSize.get(0) - tabSize.get(0)) / 6;
                    Log.d("test", margins + " " + tabSize.get(3) + " " + tabSize.get(0));
                    int x = 0;
                    int x0 = tabSize.get(0) / 2 + margins;
                    int x1 = x0 + tabSize.get(0) + 2 * margins;
                    int x2 = x1 + tabSize.get(0) + 2 * margins;

                    switch (position) {
                        case 0:
                            x = x0 - 28;
                            break;
                        case 1:
                            x = x1 - 42;
                            break;
                        case 2:
                            x = x2 - 56;
                            break;
                    }
                    Log.d("test", x + " ");
                    selectedTab.setX(x);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
