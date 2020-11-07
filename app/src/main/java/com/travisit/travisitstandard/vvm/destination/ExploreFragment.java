package com.travisit.travisitstandard.vvm.destination;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.data.Const;
import com.travisit.travisitstandard.databinding.FragmentExploreBinding;
import com.travisit.travisitstandard.model.Offer;
import com.travisit.travisitstandard.model.Tour;
import com.travisit.travisitstandard.utils.SharedPrefManager;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.adapter.OffersAdapter;
import com.travisit.travisitstandard.vvm.adapter.ToursAdapter;
import com.travisit.travisitstandard.vvm.vm.DataVM;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    private FragmentExploreBinding binding;
    private String selectedTab;
    private DataVM vm;
    private OffersAdapter offersAdapter = null;
    private ToursAdapter toursAdapter = null;
    public SharedPrefManager preferences;
    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppActivity)getActivity()).changeBottomNavVisibility(View.VISIBLE, false);
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = new SharedPrefManager(getActivity());
        vm = ViewModelProviders.of(this).get(DataVM.class);
        changeSelectedTab("tours");
        handleUserInteractions(view);
        updateUI();
    }

    private void updateUI() {
        String userPhotoPath = Const.IMAGES_SERVER_ADDRESS + preferences.getUser().getProfilePicture();
        binding.fExploreSdvPp.setImageURI(Uri.parse(userPhotoPath));

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.fExploreLayoutInActiveSearchBar.getRoot().setVisibility(View.VISIBLE);
        binding.fExploreLayoutActiveSearchBar.getRoot().setVisibility(View.INVISIBLE);
    }

    private void handleUserInteractions(View view) {
        binding.fExploreLayoutInActiveSearchBar.layoutInactiveSearchBarTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fExploreLayoutInActiveSearchBar.getRoot().setVisibility(View.INVISIBLE);
                binding.fExploreLayoutActiveSearchBar.getRoot().setVisibility(View.VISIBLE);
                binding.fExploreLayoutActiveSearchBar.layoutSearchBarEtSearch.requestFocus();
                // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(
                        InputMethodManager.SHOW_FORCED, 0);
            }
        });
        binding.fExploreSdvPp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_from_explore_to_profile);
            }
        });

        binding.fExploreTvToursTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedTab("tours");
            }
        });
        binding.fExploreTvOffersTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectedTab("offers");
            }
        });
        binding.fExploreLayoutActiveSearchBar.layoutSearchBarEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        binding.fExploreLayoutActiveSearchBar.layoutSearchBarIvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.fExploreLayoutActiveSearchBar.layoutSearchBarEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(getActivity(), "Got the focus", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void changeSelectedTab(String tab){
        if(tab.equals("offers")){
            binding.fExploreTvOffersTab.setBackground(getResources().getDrawable(R.drawable.rectangle_tab_background_selected));
            binding.fExploreTvToursTab.setBackground(getResources().getDrawable(R.drawable.rectangle_tab_background));
        } else if(tab.equals("tours")){
            binding.fExploreTvToursTab.setBackground(getResources().getDrawable(R.drawable.rectangle_tab_background_selected));
            binding.fExploreTvOffersTab.setBackground(getResources().getDrawable(R.drawable.rectangle_tab_background));
        }
        selectedTab = tab;
        getData();
    }

    private void getData() {
        if(selectedTab.equals("offers")){
            vm.getOffers();
            vm.offersMutableLiveData.observe(getActivity(), new Observer<ArrayList<Offer>>() {
                @Override
                public void onChanged(ArrayList<Offer> offers) {
                    initRecyclerView(null, offers, getView());
                }
            });
        } else if(selectedTab.equals("tours")){
            vm.getTours();
            vm.toursMutableLiveData.observe(getActivity(), new Observer<ArrayList<Tour>>() {
                @Override
                public void onChanged(ArrayList<Tour> tours) {
                    initRecyclerView(tours, null, getView());
                }
            });
        }
    }

    private void initRecyclerView(ArrayList<Tour> tours, ArrayList<Offer> offers, View view) {
        if(tours != null){
            toursAdapter = new ToursAdapter(tours, getActivity(), new ToursAdapter.SelectionPropagator() {
                @Override
                public void tourSelected(Tour tour) {
                    //Navigate to details
                    /*NavDirections action = HomeFragmentDirections.actionFromHomeToOfferDetails().setOffer(offer);
                    Navigation.findNavController(view).navigate(action);*/
                }
            });
            binding.fExploreRvOffers.setAdapter(toursAdapter);
            binding.fExploreRvOffers.setLayoutManager(new LinearLayoutManager(
                    getActivity(),
                    RecyclerView.VERTICAL,
                    false
            ));
            toursAdapter.getFilter().filter("");
        } else {
            offersAdapter = new OffersAdapter(offers, getActivity(), new OffersAdapter.SelectionPropagator() {
                @Override
                public void offerSelected(Offer offer) {
                    //Navigate to details
                    /*NavDirections action = HomeFragmentDirections.actionFromHomeToOfferDetails().setOffer(offer);
                    Navigation.findNavController(view).navigate(action);*/
                }
            });
            binding.fExploreRvOffers.setAdapter(offersAdapter);
            binding.fExploreRvOffers.setLayoutManager(new LinearLayoutManager(
                    getActivity(),
                    RecyclerView.VERTICAL,
                    false
            ));
            offersAdapter.getFilter().filter("");
        }

    }

    private void performSearch() {
        binding.fExploreLayoutActiveSearchBar.layoutSearchBarEtSearch.clearFocus();
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(binding.fExploreLayoutActiveSearchBar.layoutSearchBarEtSearch.getWindowToken(), 0);
        //...perform search
        if(selectedTab.equals("tours") && toursAdapter != null){
            toursAdapter.getFilter().filter(binding.fExploreLayoutActiveSearchBar.layoutSearchBarEtSearch.getText().toString());
        } else if(selectedTab.equals("offers") && offersAdapter != null){
            offersAdapter.getFilter().filter(binding.fExploreLayoutActiveSearchBar.layoutSearchBarEtSearch.getText().toString());
        }
    }
}