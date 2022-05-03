package com.dev.abhisek30.firebasesnippets

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dev.abhisek30.firebasesnippets.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        auth = Firebase.auth
        val userSignedIn = checkUser(auth.currentUser)
        if(userSignedIn){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.signUpTxtView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.logInWithGoogleBtn.setOnClickListener {
            logInWithGoogle()
        }

        binding.logInWithFacebookBtn.setOnClickListener {
            logInWithFacebook()
        }

        binding.loginBtn.setOnClickListener {
            val mail = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val check = checkIsEmpty(mail,password)
            if(check){
                Toast.makeText(context, "Please fill all the fields..", Toast.LENGTH_SHORT).show()
            }else{
                logInManually(mail,password)
            }
        }

        binding.forgotPasswordTxtview.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.signUpTxtView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                //updateUI(null)
                Toast.makeText(context, "Sorry!! Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        //showProgressBar()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    //updateUI(user)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    //val view = binding.mainLayout
                    //Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    //updateUI(null)
                    Toast.makeText(context, "Sorry!! Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

                //hideProgressBar()
            }
    }

    private fun checkIsEmpty(mail: String, password: String): Boolean {
        return mail.isEmpty() || password.isEmpty()
    }

    private fun logInManually(mail: String, password: String) {
        auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val cuser = FirebaseAuth.getInstance().currentUser
                Toast.makeText(context, "Log In Successful", Toast.LENGTH_SHORT).show()
                if (cuser != null) {
                    Toast.makeText(context, "Welcome ${cuser.phoneNumber}", Toast.LENGTH_LONG).show()
                }
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(context, "Sorry!! Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logInWithFacebook() {
        TODO("Not yet implemented")
    }

    private fun logInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun checkUser(currentUser: FirebaseUser?):Boolean {
        return currentUser != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }
}

