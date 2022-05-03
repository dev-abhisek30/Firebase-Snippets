package com.dev.abhisek30.firebasesnippets

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    /*private fun signUpManually(mail: String, password: String) {
        auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(LoginFragment.TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                Handler().postDelayed({
                    val cuser = FirebaseAuth.getInstance().currentUser
                    Toast.makeText(context, "Log In Successful", Toast.LENGTH_SHORT).show()
                    if (cuser != null) {
                        Toast.makeText(context, "Welcome ${cuser.phoneNumber}", Toast.LENGTH_LONG).show()
                    }
                },1000)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(LoginFragment.TAG, "createUserWithEmail:failure ${task.exception}", task.exception)
                Toast.makeText(context, "Sorry!! Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    companion object {
    }
}