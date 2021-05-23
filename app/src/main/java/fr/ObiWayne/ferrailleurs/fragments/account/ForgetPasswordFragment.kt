package fr.ObiWayne.ferrailleurs.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef
import fr.ObiWayne.ferrailleurs.fragments.account.AccountFragment

class ForgetPasswordFragment(
        private val context: MainActivity
) : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forget_password, container, false)

        val confirm = view.findViewById<Button>(R.id.Forget_Validation_Button)
        val email = view.findViewById<EditText>(R.id.My_Account_UserName_Input).text

        confirm.setOnClickListener {
                AuthRef.sendPasswordResetEmail(email.toString())
            Toast.makeText(context, "Email envoyée sur :$email", Toast.LENGTH_SHORT).show()

            val accountFragment = AccountFragment(context)
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.Fragment_container, accountFragment)
            transaction.commit()}


        return view
    }}