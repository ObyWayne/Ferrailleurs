package fr.ObiWayne.ferrailleurs.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.PopUp.DeletePopUp
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.DataFirestore
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.UID
import fr.ObiWayne.ferrailleurs.fragments.account.AccountFragment
import fr.ObiWayne.ferrailleurs.fragments.account.ChangePasswordFragment


class MyAccountFragment(
        private val context: MainActivity
) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_account, container, false)


        //Edit Image
        val editName = view.findViewById<ImageView>(R.id.My_Account_EditName)
        val editLastName = view.findViewById<ImageView>(R.id.My_Account_EditlastName)
        val editEmail = view.findViewById<ImageView>(R.id.My_Account_EditEmail)
        val editDescription = view.findViewById<ImageView>(R.id.My_Account_EditDescription)

        //Confirm Image
        val editNameConfirm = view.findViewById<ImageView>(R.id.My_Account_UserName_InputValidation)
        val editLastNameConfirm = view.findViewById<ImageView>(R.id.My_Account_EditlastNameValidation)
        val editEmailConfirm = view.findViewById<ImageView>(R.id.My_Account_EditEmailValidation)
        val editDescriptionConfirm = view.findViewById<ImageView>(R.id.My_Account_EditDescriptionValidation)

        //Current Text
        val currentName = view.findViewById<TextView>(R.id.My_Account_UserName)
        val currentLastName = view.findViewById<TextView>(R.id.My_Account_User_lastName)
        val currentEmail = view.findViewById<TextView>(R.id.My_Account_User_contact)
        val currentDescription = view.findViewById<TextView>(R.id.My_Account_User_Description)

        //New EditText
        val editNameInput = view.findViewById<EditText>(R.id.My_Account_UserName_Input)
        val editLastNameInput = view.findViewById<EditText>(R.id.My_Account_UserlastName_Input)
        val editEmailInput = view.findViewById<EditText>(R.id.My_Account_UserEmail_Input)
        val editDescriptionInput = view.findViewById<EditText>(R.id.My_Account_User_Description_Input)

        //Other Button
        val editPwB = view.findViewById<Button>(R.id.My_Account_EditPassword_Button)
        val deleteB = view.findViewById<Button>(R.id.My_Account_DeleteAccount_Button)
        val logOutB = view.findViewById<Button>(R.id.My_Account_Logout_Button)


        setUpComponent(view)

        editName.setOnClickListener{
            changeInfo(editName, editNameInput, "firstName", editNameConfirm, currentName)}

        editLastName.setOnClickListener{
            changeInfo(editLastName, editLastNameInput, "lastName", editLastNameConfirm, currentLastName)}

        editDescription.setOnClickListener{
            changeInfo(editDescription, editDescriptionInput, "description", editDescriptionConfirm, currentDescription)}

        editEmail.setOnClickListener{
            changeEmail(editEmail, editEmailInput, editEmailConfirm, currentEmail)}



        editPwB.setOnClickListener {
            changePassword()}

        logOutB.setOnClickListener {
            AuthRef.signOut()
            goLoginBack()}

        deleteB.setOnClickListener {
            DeletePopUp(context).show()}



        return view}

    private fun changePassword(){
        val changePasswordFragment = ChangePasswordFragment(context)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.Fragment_container, changePasswordFragment)
        transaction.commit()}

    private fun goLoginBack(){
        val accountFragment = AccountFragment(context)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.Fragment_container, accountFragment)
        transaction.commit()}


    private fun changeEmail(change: View, editText: EditText, validation: View, text: View) {
        change.visibility = View.INVISIBLE
        text.visibility = View.INVISIBLE
        editText.visibility = View.VISIBLE
        validation.visibility = View.VISIBLE

        validation.setOnClickListener {
            AuthRef.currentUser.updateEmail(editText.text.toString())
            change.visibility = View.VISIBLE
            text.visibility = View.VISIBLE
            editText.visibility = View.INVISIBLE
            validation.visibility = View.INVISIBLE
            refresh()}}




    private fun changeInfo(change: View, editText: EditText, field: String, validation: View, text: View) {
        change.visibility = View.INVISIBLE
        text.visibility = View.INVISIBLE
        editText.visibility = View.VISIBLE
        validation.visibility= View.VISIBLE

        validation.setOnClickListener {
            DataFirestore.collection("/users").document(UID).update(field, editText.text.toString())
            change.visibility = View.VISIBLE
            text.visibility = View.VISIBLE
            editText.visibility = View.INVISIBLE
            validation.visibility= View.INVISIBLE
            refresh()}}



    private fun refresh() {
        val myAccountFragment = MyAccountFragment(context)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.Fragment_container, myAccountFragment)
        transaction.commit()}}



private fun setUpComponent(view: View) {

    val dataFireStore = DataFirestore.collection("/users").document(UID)
    dataFireStore.get().addOnCompleteListener { document ->
        val firstName = document.result!!.get("firstName").toString()
        val lastName = document.result!!.get("lastName").toString()
        val description = document.result!!.get("description").toString()

        view.findViewById<TextView>(R.id.My_Account_UserName).text = firstName
        view.findViewById<EditText>(R.id.My_Account_UserName_Input).hint = firstName

        view.findViewById<TextView>(R.id.My_Account_User_lastName).text = lastName
        view.findViewById<EditText>(R.id.My_Account_UserlastName_Input).hint = lastName

        view.findViewById<TextView>(R.id.My_Account_User_Description).text = description
        view.findViewById<EditText>(R.id.My_Account_User_Description_Input).hint = description

        view.findViewById<TextView>(R.id.My_Account_User_contact).text = AuthRef.currentUser.email
        view.findViewById<EditText>(R.id.My_Account_UserEmail_Input).hint = AuthRef.currentUser.email}}
