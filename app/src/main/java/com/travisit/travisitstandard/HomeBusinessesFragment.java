package com.travisit.travisitstandard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.travisit.travisitstandard.databinding.FragmentHomeBusinessesFragmentBinding;
import com.travisit.travisitstandard.vvm.adapter.BusinessGuidAdapter;

import java.util.ArrayList;


public class HomeBusinessesFragment extends Fragment {
    FragmentHomeBusinessesFragmentBinding binding;
    View view;
    public HomeBusinessesFragment() {
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
        //return inflater.inflate(R.layout.fragment_home_businesses_fragment, container, false);
        binding=FragmentHomeBusinessesFragmentBinding.inflate(inflater,container,false);
        view=binding.getRoot();
        handleUserInteractions(view);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        binding.fBusinessesLayoutInActiveSearchBar.getRoot().setVisibility(View.VISIBLE);
        binding.fBusinessesLayoutActiveSearchBar.getRoot().setVisibility(View.INVISIBLE);
    }
    private void handleUserInteractions(View view) {
        binding.fBusinessesLayoutInActiveSearchBar.layoutInactiveSearchBarTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fBusinessesLayoutInActiveSearchBar.getRoot().setVisibility(View.INVISIBLE);
                binding.fBusinessesLayoutActiveSearchBar.getRoot().setVisibility(View.VISIBLE);
                binding.fBusinessesLayoutActiveSearchBar.layoutSearchBarEtSearch.requestFocus();
                // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(
                        InputMethodManager.SHOW_FORCED, 0);
            }
        });
        binding.fBusinessesLayoutActiveSearchBar.layoutSearchBarEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // performSearch();
                    return true;
                }
                return false;
            }
        });
        binding.fBusinessesLayoutActiveSearchBar.layoutSearchBarIvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.fBusinessesLayoutActiveSearchBar.layoutSearchBarEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
    private void setupRecycleView(){
        ArrayList<BusinessGuid>list=new ArrayList<>();
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
    }
}