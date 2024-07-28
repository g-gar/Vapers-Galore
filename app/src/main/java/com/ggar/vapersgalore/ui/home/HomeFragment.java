package com.ggar.vapersgalore.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ggar.vapersgalore.databinding.FragmentHomeBinding;
import com.ggar.vapersgalore.domain.ElectricCircuitDefinition;
import com.ggar.vapersgalore.service.ComputeService;
import com.ggar.vapersgalore.service.ComputeServiceImpl;

import java.util.List;

public class HomeFragment extends Fragment {

    private ComputeService computeService;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.computeService = new ComputeServiceImpl();
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

//        final List<CheckBox> checkBoxList = List.of(binding.checkBox0, binding.checkBox1, binding.checkBox2, binding.checkBox3);
        final List<EditText> editTextList = List.of(binding.value0, binding.value1, binding.value2, binding.value3);

        binding.button.setOnClickListener(listener -> {
            ValidateSelectionResult validateSelectionResult = validateSelection();
            Double power = this.computeService.power(ElectricCircuitDefinition.builder()
                    .voltage(Double.valueOf(editTextList.get(0).getText().toString()))
                    .resistance(Double.valueOf(editTextList.get(2).getText().toString()))
                    .build()
            );
            Double current = this.computeService.current(ElectricCircuitDefinition.builder()
                .voltage(Double.valueOf(editTextList.get(0).getText().toString()))
                .resistance(Double.valueOf(editTextList.get(2).getText().toString()))
                .build()
            );
            editTextList.get(1).setText(String.format("%s", power));
            editTextList.get(3).setText(String.format("%s", current));
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ValidateSelectionResult validateSelection() {
        return new ValidateSelectionResult();
    }

    class ValidateSelectionResult {
        Boolean result = true;
        String text;
    }
}