package app.superhero.src.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import app.superhero.src.fragments.CharacteristicsFragment_;
import app.superhero.src.fragments.CommentsFragment_;
import app.superhero.src.fragments.PowerstatsFragment_;
import app.superhero.src.model.dao.SuperheroMasterData;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public static final int NUM_PAGES = 3;
    public static final int POWERSTATS_POSITION = 0;
    public static final int CHARACTERISTICS_POSITION = 1;
    public static final int COMMENTS_POSITION = 2;

    SuperheroMasterData superheroMasterData;

    List<Fragment> fragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentActivity fa, SuperheroMasterData superheroMasterData) {
        super(fa);
        this.superheroMasterData = superheroMasterData;
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case POWERSTATS_POSITION:
                fragment = PowerstatsFragment_.builder().superheroMasterData(superheroMasterData).build();
                fragments.add(fragment);
                break;

            case CHARACTERISTICS_POSITION:
                fragment = CharacteristicsFragment_.builder().superheroMasterData(superheroMasterData).build();
                fragments.add(fragment);
                break;

            case COMMENTS_POSITION:
                fragment = CommentsFragment_.builder().superheroMasterData(superheroMasterData).build();
                fragments.add(fragment);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return fragment;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}