package eu.long1.spacetablayoutdemo;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SpaceTabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> lis = new ArrayList<>();
        lis.add(new FragmentA());
        lis.add(new FragmentB());
        lis.add(new FragmentC());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);

        tabLayout.initialize(viewPager, getSupportFragmentManager(), lis, savedInstanceState);

        tabLayout.setOnClickListener(this);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        int i = tabLayout.getCurrentPosition();
        String a = "";
        switch (i) {
            case 0:
                a = "0";
                break;
            case 1:
                a = "1";
                break;
            case 2:
                a = "2";
                break;
        }

        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
    }
}
