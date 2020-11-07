package com.travisit.travisitstandard.vvm.destination;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travisit.travisitstandard.databinding.FragmentHomeBinding;
import com.travisit.travisitstandard.home_businesses_fragment;
import com.travisit.travisitstandard.home_tour_guid_fragment;
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
        initAdapter();
        return view;
    }

private void initAdapter(){
    home_businesses_fragment businessesFragment = new home_businesses_fragment();
    home_tour_guid_fragment guid_fragment = new home_tour_guid_fragment();
    binding.homeTabLayout.setupWithViewPager(binding.homeViewPager);
    ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager(),0);
    viewPagerAdapter.resetAdapter();
    viewPagerAdapter.insertFragment(guid_fragment,"Tour Guides");
    viewPagerAdapter.insertFragment(businessesFragment,"Businesses");
    binding.homeViewPager.setAdapter(viewPagerAdapter);
    setTabItemMargin();
}
private void setTabItemMargin(){
    for(int i=0; i < binding.homeTabLayout.getTabCount(); i++) {
        View tab = ((ViewGroup) binding.homeTabLayout.getChildAt(0)).getChildAt(i);
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
        p.setMargins(15, 0, 15, 0);
        tab.requestLayout();
    }
}
}