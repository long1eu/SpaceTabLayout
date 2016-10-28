package eu.long1.spacebuttombardemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacebuttonbar.PagerAdapter;
import eu.long1.spacebuttonbar.SpaceButtonActivity;

public class MainActivity extends SpaceButtonActivity {

    List<Fragment> fragmentList = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);


    }

    public void init() {
        fragmentList.add(new FragmentA());
        fragmentList.add(new FragmentB());
        fragmentList.add(new FragmentC());
        this.init(fragmentList);
    }

}
