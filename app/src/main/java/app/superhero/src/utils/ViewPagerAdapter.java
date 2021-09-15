package app.superhero.src.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import app.superhero.src.fragments.CharacteristicsFragment;
import app.superhero.src.fragments.CommentsFragment;
import app.superhero.src.fragments.PowerstatsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public static final int NUM_PAGES = 3;
    private static final int POWERSTATS_POSITION = 0;
    private static final int CHARACTERISTICS_POSITION = 1;
    private static final int COMMENTS_POSITION = 2;

    public ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case POWERSTATS_POSITION:
                fragment = new PowerstatsFragment();
                break;

            case CHARACTERISTICS_POSITION:
                fragment = new CharacteristicsFragment();
                break;

            case COMMENTS_POSITION:
                fragment = new CommentsFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}