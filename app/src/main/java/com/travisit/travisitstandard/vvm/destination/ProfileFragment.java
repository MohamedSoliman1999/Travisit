package com.travisit.travisitstandard.vvm.destination;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.data.Const;
import com.travisit.travisitstandard.databinding.FragmentProfileBinding;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.utils.FileType;
import com.travisit.travisitstandard.utils.PathUtil;
import com.travisit.travisitstandard.utils.SharedPrefManager;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.vm.ProfileVM;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final int REQUEST_IMAGE_PP = 125;
    private FragmentProfileBinding binding;
    public SharedPrefManager preferences;
    private ProfileVM vm;
    private String ppPath = "";
    private User user = null;
    private boolean isEditMode = false;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppActivity) getActivity()).changeBottomNavVisibility(View.GONE, false);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = new SharedPrefManager(getActivity());
        vm = ViewModelProviders.of(this).get(ProfileVM.class);
        user = preferences.getUser();
        if (user == null) {
            vm.getProfile();
            vm.profileMutableLiveData.observe(getActivity(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null) {
                        user = user;
                        preferences.saveUser(user);
                        updateUI(true);
                    }
                }
            });
        } else {
            updateUI(true);
        }
        switchMode();
        handleUserInteractions(view);

    }

    private void handleUserInteractions(View view) {
        binding.fProfileTvChangePp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(REQUEST_IMAGE_PP);
            }
        });
        binding.fProfileIvOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }

    private void updateUI(Boolean changePhoto) {
        if(!AppActivity.isTourGuide){
            binding.fProfileTietHourlyRate.setVisibility(View.INVISIBLE);
        }
        if (changePhoto) {
            String userPhotoPath = Const.IMAGES_SERVER_ADDRESS + user.getProfilePicture();
            binding.fProfileSdvPp.setImageURI(Uri.parse(userPhotoPath));
        }
        binding.fProfileTvUserName.setText(user.getFullName());
        binding.fProfileEtUserName.setText(user.getFullName());
        binding.fProfileTietUserEmail.setText(user.getEmail());
        binding.fProfileTietPhoneNo.setText(user.getPhone());
        if(AppActivity.isTourGuide){
            binding.fProfileTietHourlyRate.setText(user.getHourlyRate());
        }
    }

    private void switchMode() {
        if (isEditMode) {
            binding.fProfileTietUserEmail.setCursorVisible(true);
            binding.fProfileTietUserEmail.setFocusable(true);
            binding.fProfileTietUserEmail.setFocusableInTouchMode(true);
            binding.fProfileTietPhoneNo.setCursorVisible(true);
            binding.fProfileTietPhoneNo.setFocusable(true);
            binding.fProfileTietPhoneNo.setFocusableInTouchMode(true);
            binding.fProfileTietHourlyRate.setCursorVisible(true);
            binding.fProfileTietHourlyRate.setFocusable(true);
            binding.fProfileTietHourlyRate.setFocusableInTouchMode(true);
            binding.fProfileEtUserName.setVisibility(View.VISIBLE);
            binding.fProfileTvUserName.setVisibility(View.INVISIBLE);
            binding.fProfileTvChangePp.setVisibility(View.VISIBLE);
        } else {
            binding.fProfileTietUserEmail.setCursorVisible(false);
            binding.fProfileTietUserEmail.setFocusable(false);
            binding.fProfileTietUserEmail.setFocusableInTouchMode(false);
            binding.fProfileTietPhoneNo.setCursorVisible(false);
            binding.fProfileTietPhoneNo.setFocusable(false);
            binding.fProfileTietPhoneNo.setFocusableInTouchMode(false);
            binding.fProfileTietHourlyRate.setCursorVisible(false);
            binding.fProfileTietHourlyRate.setFocusable(false);
            binding.fProfileTietHourlyRate.setFocusableInTouchMode(false);
            binding.fProfileEtUserName.setVisibility(View.INVISIBLE);
            binding.fProfileTvUserName.setVisibility(View.VISIBLE);
            binding.fProfileTvChangePp.setVisibility(View.GONE);
        }
    }

    private void pickImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri thumbnail = data.getData();
            PathUtil pathUtil = new PathUtil(getActivity());
            String path = pathUtil.getPath(thumbnail);
            Log.d("Profile", "Selected Image path: " + path);

            if (requestCode == REQUEST_IMAGE_PP) {
                ppPath = path;
                binding.fProfileSdvPp.setImageURI(thumbnail);
            }
        }
    }

    private void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);

        if (isEditMode) {
            popup.inflate(R.menu.menu_edit_profile_options);

        } else {
            popup.inflate(R.menu.menu_profile_options);
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save_option:
                        if(AppActivity.isTourGuide){
                            //TODO OTHER DATA
                            vm.editGuideProfile(
                                    getFieldText("name"),
                                    getFieldText("email"),
                                    getFieldText("phone"),
                                    user.getEducation(),
                                    user.getWorkExperience(),
                                    Integer.parseInt(getFieldText("rate")),
                                    user.getMembershipNumber(),
                                    user.getLicenseNumber());
                        } else {
                            vm.editTravelerProfile(
                                    getFieldText("name"),
                                    getFieldText("email"),
                                    getFieldText("phone"),
                                    user.getDateOfBirth(),
                                    user.getNationality()
                            );
                        }
                        vm.profileMutableLiveData.observe(getActivity(), new Observer<User>() {
                            @Override
                            public void onChanged(User returnedUser) {
                                user.setFullName(getFieldText("name"));
                                user.setEmail(getFieldText("email"));
                                user.setPhone(getFieldText("phone"));
                                user.setHourlyRate(Integer.parseInt(getFieldText("rate")));
                                preferences.saveUser(user);
                                isEditMode = false;
                                switchMode();
                                updateUI(false);
                            }
                        });
                        if (ppPath != "") {
                            Log.d("hereCCC", ppPath);
                            vm.uploadFile(ppPath);
                            vm.filePMutableLiveData.observe(getActivity(), new Observer<User>() {
                                @Override
                                public void onChanged(User user) {
                                    Log.d("hereCCC", user.getProfilePicture());
                                    ppPath = "";
                                    user.setProfilePicture(user.getProfilePicture());
                                    preferences.saveUser(user);
                                }
                            });
                        }
                        return true;
                    case R.id.cancel_option:
                        isEditMode = false;
                        switchMode();
                        ppPath = "";
                        return true;
                    case R.id.edit_option:
                        isEditMode = true;
                        switchMode();
                        ppPath = "";
                        return true;
                    default:
                        return false;
                }
            }
        });

        Log.d("smth", "tshow");
        popup.show();
    }

    private String getFieldText(String fieldName) {
        switch (fieldName) {
            case "name":
                return binding.fProfileEtUserName.getText().toString();
            case "email":
                return binding.fProfileTietUserEmail.getText().toString();
            case "phone":
                return binding.fProfileTietPhoneNo.getText().toString();
            case "rate":
                return binding.fProfileTietHourlyRate.getText().toString();
            default:
                return "invalid";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}