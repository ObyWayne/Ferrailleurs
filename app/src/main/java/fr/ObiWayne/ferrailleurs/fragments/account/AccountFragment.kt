package fr.ObiWayne.ferrailleurs.fragments.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.DataFirestore
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.UID


class AccountFragment(
    private val context: MainActivity
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)


        val registerButton = view.findViewById<Button>(R.id.Account_RegisterButton)
        registerButton.setOnClickListener {
            goToRegister()}

        val forgetPasswordButton = view?.findViewById<TextView>(R.id.Account_Password_forgot)
        forgetPasswordButton?.setOnClickListener {
            forgetPassword()}

        val sigInButton = view.findViewById<Button>(R.id.Account_SignInButtonButton)
        sigInButton.setOnClickListener {
            loginCheck()}


        return view
    }


    private fun goToRegister() {
        val newAccountFragment = NewAccountFragment(context)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.Fragment_container, newAccountFragment)
        transaction.commit()}

    private fun forgetPassword() {
        val forgetPassword = ForgetPasswordFragment(context)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.Fragment_container, forgetPassword)
        transaction.commit()}


    private fun loginCheck() {
        val email : TextView? = view?.findViewById(R.id.Account_LoginInput)
        val password: EditText? = view?.findViewById(R.id.Account_PasswordInput)

        val error = view?.findViewById<TextView>(R.id.Account_error_Textview)

        if (email!!.text.isEmpty()) {
            email.error = "@string/Error_Null"
            error?.visibility = View.VISIBLE}

        if (password!!.text.isEmpty()) {
            password.error = "@string/Error_Null"
            error?.visibility = View.VISIBLE}

        if (password.text.isNotEmpty()|| email.text.isNotEmpty()) {
            val task = AuthRef.signInWithEmailAndPassword(
                email.text.toString(),
                password.text.toString())
            task.addOnSuccessListener {
                UID = AuthRef.currentUser.uid
                val dataFireStore = DataFirestore.collection("/users").document(UID)
                dataFireStore.get().addOnCompleteListener { document ->
                    val firstName = document.result!!.get("firstName").toString()
                    Toast.makeText(context, "Bienvenue $firstName", Toast.LENGTH_SHORT).show()}

                val repo =PieceRepository()
                repo.dataCheck{
                    error?.visibility = View.INVISIBLE
                    val myAccountFragment = MyAccountFragment(context)
                    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.Fragment_container, myAccountFragment)
                    transaction.commit()}


            }.addOnFailureListener {
                Toast.makeText(context, "Connexion Impossible ", Toast.LENGTH_SHORT).show()
                error?.visibility = View.VISIBLE
            }}}




}


