package fr.ObiWayne.ferrailleurs.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.DataFirestore

class NewAccountFragment(
    private val context: MainActivity
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_account, container, false)


        val validationButton = view.findViewById<ImageView>(R.id.Register_Validation)
        validationButton.setOnClickListener {
            registerError()}

        return view
    }

    private fun registerError() {

        //EditTexts
        val name = view!!.findViewById<EditText>(R.id.Register_Name_Input)
        val lastName = view!!.findViewById<EditText>(R.id.Register_LastName_Input)
        val email = view!!.findViewById<EditText>(R.id.Register_Email_Input)
        val pw1 = view!!.findViewById<EditText>(R.id.Register_Password1_Input)
        val pw2 = view!!.findViewById<EditText>(R.id.Register_Password2_Input)
        val description = view!!.findViewById<EditText>(R.id.Register_description_Input)
        val checkbox = view!!.findViewById<CheckBox>(R.id.Account_checkBox_robot)

        //Error
        val errorField = view?.findViewById<TextView>(R.id.Account_error_not_all_field)
        val errorPwMatch = view?.findViewById<TextView>(R.id.Account_error_pw_dont_match)
        val errorPwWeak = view?.findViewById<TextView>(R.id.Account_error_pw_too_weak)
        val errorCheckBox = view?.findViewById<TextView>(R.id.Account_error_checkBox)


        // Different types d'erreurs

        if( !checkbox.isChecked)  {
            errorPwWeak?.visibility = View.VISIBLE}

        if( name.text.isEmpty()) {
            name.error = "@string/Error_Null"
            errorField?.visibility = View.VISIBLE}

        if (lastName.text.isEmpty()) {
            lastName.error = "@string/Error_Null"
            errorField?.visibility = View.VISIBLE}

        if (email.text.isEmpty()) {
            email.error = "@string/Error_Null"
            errorField?.visibility = View.VISIBLE}

        if (pw1.text.isEmpty() ) {
            pw1.error = "@string/Error_Null"
            errorField?.visibility = View.VISIBLE}

        else if( pw1.text.toString() != pw2.text.toString() ) {
            pw1.error = "@string/Error_Password_no_match"
            pw2.error = "@string/Error_Password_no_match"
            errorPwMatch?.visibility = View.VISIBLE}

        if (pw2.text.isEmpty()) {
            pw2.error = "@string/Error_Null"
            errorField?.visibility = View.VISIBLE}

        else if( pw1.text.toString() != pw2.text.toString() ) {
            pw1.error = "@string/Error_Password_no_match"
            pw2.error = "@string/Error_Password_no_match"
            errorPwMatch?.visibility = View.VISIBLE}


        if( pw1.text.isNotEmpty() && pw2.text.isNotEmpty() &&
                pw1.text.toString() == pw2.text.toString() &&
                pw1.text.length < 5)  {
            pw1.error = "@string/Error_Password_too_weak"
            pw2.error = "@string/Error_Password_too_weak"
            errorPwWeak?.visibility = View.VISIBLE}

        if (pw1.text.isNotEmpty() && pw2.text.isNotEmpty() &&
                pw1.text.toString() == pw2.text.toString() &&
                pw1.text.length >= 5 &&
                checkbox.isChecked){

            errorPwMatch?.visibility = View.INVISIBLE
            errorCheckBox?.visibility = View.INVISIBLE
            errorField?.visibility = View.INVISIBLE
            errorPwWeak?.visibility = View.INVISIBLE

            registerTask(email.text.toString(), pw1.text.toString(),
                name.text.toString(),lastName.text.toString(),
                description.text.toString())

        }


    }

    private fun registerTask(
        mail: String, password: String,
        Name:String, LastName:String,
        Description:String
    ) {
        val task = AuthRef.createUserWithEmailAndPassword(mail, password)
        AuthRef.signInWithEmailAndPassword(mail,password)
        task.addOnCompleteListener(context, OnCompleteListener<AuthResult?> { task ->
            if (task.isSuccessful) {

                val UID = AuthRef.currentUser.uid
                var posted = 0
                Toast.makeText(context, "UID : " + UID, Toast.LENGTH_SHORT).show()

                val user = hashMapOf(
                    "firstName" to Name,
                    "lastName" to LastName,
                    "description" to Description,
                    "liked" to arrayListOf(""),
                    "UID" to UID,
                    "posted" to posted
                )

                DataFirestore.collection("/users").document(UID).set(user)



                val accountFragment = AccountFragment(context)
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.Fragment_container, accountFragment)
                transaction.commit()


            } else {
                Toast.makeText(context, "error retry later", Toast.LENGTH_SHORT).show()
            }
        })
    }
}