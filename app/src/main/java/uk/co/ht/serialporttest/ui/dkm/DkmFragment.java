package uk.co.ht.serialporttest.ui.dkm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uk.co.ht.serialporttest.databinding.FragmentDkmBinding;

public class DkmFragment extends Fragment {

    private FragmentDkmBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       DkmViewModel dkmViewModel =
                new ViewModelProvider(this).get(DkmViewModel.class);

        binding = FragmentDkmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dkmViewModel.connect(binding.textDkm.getText().toString());
            }
        });
        final TextView textView = binding.textDkm;
        dkmViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}