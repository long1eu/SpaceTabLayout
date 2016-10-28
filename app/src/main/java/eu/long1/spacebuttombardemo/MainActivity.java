package eu.long1.spacebuttombardemo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import eu.long1.spacebuttonbar.PagerAdapter;
import eu.long1.spacebuttonbar.SpaceButtonActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] fragments = new String[3];
        fragments[0] = FragmentA.class.getName();
        fragments[1] = FragmentB.class.getName();
        fragments[2] = FragmentC.class.getName();

        Intent i = new Intent(this, SpaceButtonActivity.class);
        i.putExtra("fragments", fragments);
        startActivity(i);

    }

}
