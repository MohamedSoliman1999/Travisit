package com.travisit.travisitstandard.vvm.destination;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.databinding.FragmentExploreItemDetailsBinding;
import com.travisit.travisitstandard.model.Offer;
import com.travisit.travisitstandard.model.Tour;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.vm.DataVM;

public class ExploreItemDetailsFragment extends Fragment {
    private FragmentExploreItemDetailsBinding binding;
    private DataVM vm;
    private Tour tour;
    private Offer offer;

    public ExploreItemDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppActivity) getActivity()).changeBottomNavVisibility(View.GONE, false);
        binding = FragmentExploreItemDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this).get(DataVM.class);
        offer = ExploreItemDetailsFragmentArgs.fromBundle(getArguments()).getOffer();
        tour = ExploreItemDetailsFragmentArgs.fromBundle(getArguments()).getTour();
        if(offer == null & tour == null){
            Toast.makeText(getActivity(), getString(R.string.missing_item), Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigateUp();
        }
        handleUserInteractions();
        updateUI();
    }

    private void handleUserInteractions() {
        binding.fExploreDetailsIvOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }

    private void updateUI() {
        //TODO CHECK IF NOT MY TOUR HIDE ALSO
        Boolean itemIsOffer = false;
        if(offer != null) {
            itemIsOffer = true;
        }

        if(itemIsOffer || !AppActivity.isTourGuide){
            binding.fExploreDetailsIvOptions.setVisibility(View.INVISIBLE);
            binding.fExploreDetailsIvFav.setVisibility(View.INVISIBLE);
        }
    }
    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit_option:
                        //TODO NAVIGATE TO EDIT
                        /*NavDirections action = OfferDetailsFragmentDirections.actionFromOfferDetailsToEditOffer().setOffer(offer);
                        Navigation.findNavController(v).navigate(action);*/
                        return true;
                    case R.id.delete_option:
                        showAlertDialog(offer.getId(), v);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.menu_options);
        popup.show();
    }
    private void showAlertDialog(int tourID, View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.delete_alert);
        builder.setMessage(getActivity().getString(R.string.confirm_delete_message));
        builder.setPositiveButton(getActivity().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vm.deleteTour(tourID);
                vm.deletingMutableLiveData.observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String string) {
                        if(string.equals("done")) {
                            Navigation.findNavController(view).navigateUp();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton(getActivity().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}