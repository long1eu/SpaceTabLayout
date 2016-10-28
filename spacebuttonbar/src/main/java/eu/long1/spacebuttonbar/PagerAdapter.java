package eu.long1.spacebuttonbar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private Context context;
    List<Class> fragmentsClasses = new ArrayList<>();


    public PagerAdapter(FragmentManager fm, Context context, String[] fragments) throws ClassNotFoundException {
        super(fm);
        this.context = context;

        for (String fragment : fragments) {
            fragmentsClasses.add(Class.forName(fragment));
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return (Fragment) fragmentsClasses.get(position).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
