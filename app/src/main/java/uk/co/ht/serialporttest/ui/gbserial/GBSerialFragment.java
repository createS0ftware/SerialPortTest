package uk.co.ht.serialporttest.ui.gbserial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uk.co.ht.serialporttest.databinding.FragmentGbserialBinding;


public class GBSerialFragment extends Fragment {

    private FragmentGbserialBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GBSerialViewModel GBSerialViewModel =
                new ViewModelProvider(this).get(GBSerialViewModel.class);

        binding = FragmentGbserialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGbserial;
        GBSerialViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}