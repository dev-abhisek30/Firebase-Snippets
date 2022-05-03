package com.dev.abhisek30.firebasesnippets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dev.abhisek30.firebasesnippets.databinding.FragmentHomeBinding
import com.dev.abhisek30.firebasesnippets.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl
            val emailVerified = user.isEmailVerified
            val uid = user.uid

            binding.displayNameTxtView.text = name
            binding.mailTxtView.text = email
            Picasso.get().load("$photoUrl").into(binding.profileImageView)
            binding.verifiedTxtView.text = emailVerified.toString()
            binding.uidTxtView.text = uid
        }
        if (user != null) {
            Log.d("HOME","${user.phoneNumber},${user.displayName}")
        }
        binding.logOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        return view
    }

    companion object {

    }
}