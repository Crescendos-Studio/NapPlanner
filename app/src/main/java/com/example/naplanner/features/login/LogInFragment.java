package com.example.naplanner.features.login;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.naplanner.MainActivity;
import com.example.naplanner.R;
import com.example.naplanner.databinding.FragmentLogInBinding;
import com.example.naplanner.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInFragment extends Fragment {

    private FragmentLogInBinding binding;
    private FirebaseAuth fAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        fAuth = FirebaseAuth.getInstance();
        ((MainActivity) requireActivity()).hideInteractionBars();
    }

    private void setupUI() {
        binding.logInFragmentSendButton.setOnClickListener(new View.OnClickListener() {
            UserModel data = new UserModel();

            @Override
            public void onClick(View view) {

                String input = binding.logInFragmentMailEditText.getText().toString();
                if (!input.isEmpty() && validateEmail(input)) {
                    data.setMail(input);
                } else {
                    sendErrorMsg("Introduzca un correo valido");
                    return;
                }

                String pass = binding.logInFragmentPasswordEditText.getText().toString();
                if (!pass.isEmpty())
                    fAuth.signInWithEmailAndPassword(data.getMail(), pass).addOnCompleteListener(authComplete());
                else {
                    sendErrorMsg("Introduzca una contraseña");
                }

            }
        });

        binding.logInFragmentLinkToSignUpText.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_FirstFragment_to_signUpChoiceFragment));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean validateEmail(CharSequence mail) {
        return !mail.toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    private void sendErrorMsg(String error) {
        Toast.makeText(requireActivity().getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    private OnCompleteListener<AuthResult> authComplete() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_FirstFragment_to_teacherTasksFragment);
                } else {
                    sendErrorMsg("Correo o Contraseña incorrectos");
                }
            }
        };
    }

}