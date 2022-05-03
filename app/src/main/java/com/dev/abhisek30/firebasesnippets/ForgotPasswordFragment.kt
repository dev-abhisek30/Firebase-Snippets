package com.dev.abhisek30.firebasesnippets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dev.abhisek30.firebasesnippets.databinding.FragmentForgotPasswordBinding
import com.dev.abhisek30.firebasesnippets.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.resetBtn.setOnClickListener {
            val mail = binding.editTextTextEmailAddress2.text.toString()
            if(!mail.isEmpty()){
                Firebase.auth.sendPasswordResetEmail(mail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent.")
                            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                        }
                    }
            }else{
                Toast.makeText(context, "Please fill the mail-id", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    companion object {
        private val TAG = "ForgotPasswordFragment"
    }
}