package com.travisit.travisitstandard;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;

import com.travisit.travisitstandard.databinding.FragmentTourManagamentBinding;
import com.travisit.travisitstandard.model.Tour;
import com.travisit.travisitstandard.utils.ManagementOption;
import com.travisit.travisitstandard.utils.PathUtil;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.vm.TourVM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class TourManagamentFragment extends Fragment {
    private static final int REQUEST_FIRST_IMAGE = 127;
    private static final int REQUEST_SECOND_IMAGE = 128;
    private static final int REQUEST_THIRD_IMAGE = 129;
    private String firstImagePath = "";
    private String secondImagePath = "";
    private String thirdImagePath = "";
    private boolean isNew = true;
    FragmentTourManagamentBinding binding;
    Tour tour;
    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (getFieldText("name").length() == 0 ||
                    getFieldText("description").length() == 0 ||
                    getFieldText("start date").length() == 0 ||
                    getFieldText("end date").length() == 0 ) {
                binding.fTourManagementMtbtnSave.setEnabled(false);
                binding.fTourManagementMtbtnSave.setAlpha(0.5f);
            } else {
                binding.fTourManagementMtbtnSave.setEnabled(true);
                binding.fTourManagementMtbtnSave.setAlpha(1f);
            }
        }
    };
    private TourVM vm;
    private ArrayList<Integer> branchesIDs = new ArrayList<>();
    private ArrayList<String> branchesNames = new ArrayList<>();
    private Calendar startDateCalendar = Calendar.getInstance();
    private Calendar endDateCalendar = Calendar.getInstance();
    String myFormat = "yyyy-MMM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
    final DatePickerDialog.OnDateSetListener startDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDateCalendar.set(Calendar.YEAR, year);
            startDateCalendar.set(Calendar.MONTH, monthOfYear);
            startDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            binding.fTourManagementTietStartDate.setText(sdf.format(startDateCalendar.getTime()));
        }
    };
    final DatePickerDialog.OnDateSetListener endDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            endDateCalendar.set(Calendar.YEAR, year);
            endDateCalendar.set(Calendar.MONTH, monthOfYear);
            endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            binding.fTourManagementTietEndDate.setText(sdf.format(endDateCalendar.getTime()));
        }
    };
    public TourManagamentFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this).get(TourVM.class);
        //tour = TourManagementFragmentArgs.fromBundle(getArguments()).gettour();
        if(tour == null){
            updateUI(ManagementOption.ADD);
            isNew = true;
        } else {
            updateUI(ManagementOption.EDIT);
            isNew = false;
        }
        handleUserInteractions(view);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppActivity) getActivity()).changeBottomNavVisibility(View.GONE, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding = FragmentTourManagamentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
    private void handleUserInteractions(View view) {
        binding.fTourManagementSdv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(REQUEST_FIRST_IMAGE);
            }
        });
        binding.fTourManagementSdv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(REQUEST_SECOND_IMAGE);
            }
        });
        binding.fTourManagementSdv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(REQUEST_THIRD_IMAGE);
            }
        });
        binding.fTourManagementMtbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tour == null){
                    createtourObject();
                    vm.addtour(tour);
                } else {
                    uploadPhotos(view);
                    tour.setTitle(getFieldText("name"));
                    tour.setProgram(getFieldText("description"));
                    tour.setStartTime(getFieldText("start date"));
                    tour.setEndTime(getFieldText("end date"));
                    //tour.setBranchID(Integer.parseInt(getFieldText("branch id")));
                    //vm.edittour(tour);
                }
                vm.tourMutableLiveData.observe(getActivity(), new Observer<Tour>() {
                    @Override
                    public void onChanged(Tour tour) {
                        if(isNew){
                            uploadPhotos(view);
                        }
                    }
                });
            }
        });

        binding.fTourManagementTietStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), startDatePickerListener, startDateCalendar
                        .get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH),
                        startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        binding.fTourManagementTietEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), endDatePickerListener, endDateCalendar
                        .get(Calendar.YEAR), endDateCalendar.get(Calendar.MONTH),
                        endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        binding.fTourManagementTietTitle.addTextChangedListener(watcher);
        binding.fTourManagementTietProgram.addTextChangedListener(watcher);
        binding.fTourManagementTietStartDate.addTextChangedListener(watcher);
        binding.fTourManagementTietEndDate.addTextChangedListener(watcher);
        updateBtnStatusUI();
    }
    private void createtourObject() {
        tour = new Tour(
                getFieldText("title"),
                getFieldText("program"),
                Double.parseDouble(getFieldText("hourRate")),
                getFieldText("start date"),
                getFieldText("end date"),
                vm.isPublic
        );
    }
    private void updateUI(ManagementOption option) {
        switch(option){
            case ADD:{
                binding.fTourManagementTvTitle.setText(getString(R.string.tour_add_header));
                binding.fTourManagementMtbtnSave.setText(getString(R.string.add_now));
                break;
            }
            case EDIT: {
                binding.fTourManagementTvTitle.setText(getString(R.string.tour_edit_header));
                binding.fTourManagementMtbtnSave.setText(getString(R.string.update_now));
                binding.fTourManagementTietTitle.setText(tour.getTitle());
                binding.fTourManagementTietProgram.setText(tour.getProgram());
                binding.fTourManagementTietStartDate.setText(tour.getStartTime());
                binding.fTourManagementTietEndDate.setText(tour.getEndTime());
                break;
            }
        }

    }
    private void pickImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }
    private void uploadPhotos(View view){
        vm.uploadFiles(firstImagePath,secondImagePath,thirdImagePath);
        vm.photosMutableLiveData.observe(getActivity(), new Observer<Tour>() {
            @Override
            public void onChanged(Tour tour) {
                Navigation.findNavController(view).navigateUp();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri thumbnail = data.getData();
            PathUtil pathUtil = new PathUtil(getActivity());
            String path = pathUtil.getPath(thumbnail);

            if (requestCode == REQUEST_FIRST_IMAGE) {
                firstImagePath = path;
                binding.fTourManagementSdv1.setImageURI(thumbnail);
            } else if (requestCode == REQUEST_SECOND_IMAGE) {
                secondImagePath = path;
                binding.fTourManagementSdv2.setImageURI(thumbnail);
            } else if (requestCode == REQUEST_THIRD_IMAGE) {
                thirdImagePath = path;
                binding.fTourManagementSdv3.setImageURI(thumbnail);
            }
        }
    }
    private String getFieldText(String fieldName) {
        switch (fieldName) {
            case "name":
                return binding.fTourManagementTietTitle.getText().toString();
            case "program":
                return binding.fTourManagementTietProgram.getText().toString();
            case "start date":
                return binding.fTourManagementTietStartDate.getText().toString();
            case "end date":
                return binding.fTourManagementTietEndDate.getText().toString();
            case "hourRate":
                return binding.priceTxt.getText().toString();
            default:
                return "invalid";
        }
    }
    private void updateBtnStatusUI(){
        //defualt is public
        vm.isPublic=true;
        binding.publicBtn.setBackgroundResource(R.drawable.public_btn_click);
        binding.publicBtn.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
        binding.privateBtn.setBackgroundResource(R.drawable.public_btn_unclick);
        binding.privateBtn.setTextColor(getActivity().getResources().getColor(R.color.colorHeaderBlack));

        binding.privateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.privateBtn.setBackgroundResource(R.drawable.public_btn_click);
                binding.privateBtn.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                binding.publicBtn.setBackgroundResource(R.drawable.public_btn_unclick);
                binding.publicBtn.setTextColor(getActivity().getResources().getColor(R.color.colorHeaderBlack));
                vm.isPublic=false;
                /*set Status is private*/
            }
        });
        binding.publicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.publicBtn.setBackgroundResource(R.drawable.public_btn_click);
                binding.publicBtn.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                binding.privateBtn.setBackgroundResource(R.drawable.public_btn_unclick);
                binding.privateBtn.setTextColor(getActivity().getResources().getColor(R.color.colorHeaderBlack));
                vm.isPublic=true;
                /*set Status is public*/
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}