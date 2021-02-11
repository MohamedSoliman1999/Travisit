package com.travisit.travisitstandard.vvm.destination;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.databinding.FragmentHomeBinding;
import com.travisit.travisitstandard.HomeBusinessesFragment;
import com.travisit.travisitstandard.HomeTourGuidFragment;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.adapter.ViewPagerAdapter;

public class homeFragment extends Fragment {
    private FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppActivity) getActivity()).changeBottomNavVisibility(View.VISIBLE, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initTabAdapter();
        return view;
    }

private void initTabAdapter(){
    HomeBusinessesFragment businessesFragment = new HomeBusinessesFragment();
    HomeTourGuidFragment guid_fragment = new HomeTourGuidFragment();
    binding.homeTabLayout.setupWithViewPager(binding.homeViewPager);
//    ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager(),0);
    ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager(),0);
    viewPagerAdapter.resetAdapter();
    viewPagerAdapter.insertFragment(guid_fragment,getString(R.string.tour_Guides));
    viewPagerAdapter.insertFragment(businessesFragment,getString(R.string.businesses));
    binding.homeViewPager.setAdapter(viewPagerAdapter);
    setTabItemMargin();
}
    private void setTabItemMargin(){
        for(int i=0; i < binding.homeTabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) binding.homeTabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(50, 0, 50, 0);
            tab.requestLayout();
        }
    }
}