package com.ggar.vapersgalore.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import java.util.Objects;

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

        binding.value0.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String resistance = binding.value2.getText().length() > 0 ? binding.value2.getText().toString() : "0";
                    resistance = resistance.equals(".") ? "0." : resistance;
                    String voltage = s.toString().trim();
                    voltage = voltage.isEmpty() ? "0" : voltage;
                    voltage = voltage.equals(".") ? "0." : voltage;
                    ElectricCircuitDefinition electricCircuitDefinition = ElectricCircuitDefinition.builder()
                        .voltage(Double.valueOf(voltage))
                        .resistance(Double.valueOf(resistance))
                        .build();
                    binding.value1.setText(String.format("%s", computeService.power(electricCircuitDefinition)));
                    binding.value3.setText(String.format("%s", computeService.current(electricCircuitDefinition)));
                } catch (Exception exception) {
                    binding.textHome.setText(exception.getMessage());
                    Log.d("COMPUTE", Objects.requireNonNull(exception.getMessage()));
                    throw new RuntimeException(exception);
                }
            }
        });

        binding.value2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String voltage = binding.value0.getText().length() > 0 ? binding.value0.getText().toString() : "0";
                    voltage = voltage.equals(".") ? "0." : voltage;
                    String resistance = s.toString().trim();
                    resistance = resistance.isEmpty() ? "0" : resistance;
                    resistance = resistance.equals(".") ? "0." : resistance;
                    ElectricCircuitDefinition electricCircuitDefinition = ElectricCircuitDefinition.builder()
                            .voltage(Double.valueOf(voltage))
                            .resistance(Double.valueOf(resistance))
                            .build();
                    binding.value1.setText(String.format("%s", computeService.power(electricCircuitDefinition)));
                    binding.value3.setText(String.format("%s", computeService.current(electricCircuitDefinition)));
                } catch (Exception exception) {
                    binding.textHome.setText(exception.getMessage());
                    Log.d("COMPUTE", Objects.requireNonNull(exception.getMessage()));
                    throw new RuntimeException(exception);
                }
            }
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