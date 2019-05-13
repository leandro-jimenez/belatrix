package com.example.bellatrixsample.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bellatrixsample.R;
import com.example.bellatrixsample.fragments.CompassFragment;
import com.example.bellatrixsample.fragments.ShakerFragment;

public class FragmentPageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    public FragmentPageAdapter(Context ctx, @NonNull FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CompassFragment();
            case 1:
                return new ShakerFragment();
            default:
                return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.page_item_compass);
            case 1:
                return mContext.getString(R.string.page_item_shaker);
            default:
                return "";
        }
    }
}
