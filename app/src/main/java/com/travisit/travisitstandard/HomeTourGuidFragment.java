package com.travisit.travisitstandard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.travisit.travisitstandard.data.BusinessGuid;
import com.travisit.travisitstandard.databinding.FragmentHomeTourGuidFragmentBinding;
import com.travisit.travisitstandard.model.Tour;
import com.travisit.travisitstandard.vvm.adapter.BusinessGuidAdapter;
import com.travisit.travisitstandard.vvm.vm.TourGuidVM;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeTourGuidFragment extends Fragment {
    FragmentHomeTourGuidFragmentBinding binding;
    View view;
    TourGuidVM vm;
    public HomeTourGuidFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_tour_guid_fragment, container, false);
        binding=FragmentHomeTourGuidFragmentBinding.inflate(inflater,container,false);
        view=binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm= ViewModelProviders.of(this).get(TourGuidVM.class);
        handleUserInteractions(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.fTourGuidLayoutInActiveSearchBar.getRoot().setVisibility(View.VISIBLE);
        binding.fTourGuidLayoutActiveSearchBar.getRoot().setVisibility(View.INVISIBLE);
    }
    private void handleUserInteractions(View view) {
        binding.fTourGuidLayoutInActiveSearchBar.layoutInactiveSearchBarTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fTourGuidLayoutInActiveSearchBar.getRoot().setVisibility(View.INVISIBLE);
                binding.fTourGuidLayoutActiveSearchBar.getRoot().setVisibility(View.VISIBLE);
                binding.fTourGuidLayoutActiveSearchBar.layoutSearchBarEtSearch.requestFocus();
                // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(
                        InputMethodManager.SHOW_FORCED, 0);
            }
        });
        binding.fTourGuidLayoutActiveSearchBar.layoutSearchBarEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   // performSearch();
                    return true;
                }
                return false;
            }
        });
        binding.fTourGuidLayoutActiveSearchBar.layoutSearchBarIvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.fTourGuidLayoutActiveSearchBar.layoutSearchBarEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(getActivity(), "Got the focus", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        setupRecycleView();

    }
    int page=0,limit=10;
    private void setupRecycleView(){
        ArrayList<BusinessGuid> list=new ArrayList<>();
        list.add(new BusinessGuid());
        list.add(new BusinessGuid());
        list.add(new BusinessGuid());
        list.add(new BusinessGuid());
        list.add(new BusinessGuid());
        list.add(new BusinessGuid());
        list.add(new BusinessGuid());
        BusinessGuidAdapter adapter=new BusinessGuidAdapter(list,this.getContext());
        binding.fragmentHomeRvOffers.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.fragmentHomeRvOffers.setAdapter(adapter);
        binding.fragmentHomeRvOffers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    vm.getTourGuid(page,limit);
                    page++;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        vm.arrayListMutableLiveData.observe(getActivity(), new Observer<ArrayList<BusinessGuid>>() {
            @Override
            public void onChanged(ArrayList<BusinessGuid> businessGuids) {
                if (businessGuids!=null){
                    adapter.arrayList.addAll(businessGuids);
                    adapter.notifyDataSetChanged();
                }
//                adapter.setList(businessGuids);
            }
        });
    }
}