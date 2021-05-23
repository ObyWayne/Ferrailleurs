package fr.ObiWayne.ferrailleurs.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef

class ChangePasswordFragment(
        private val context: MainActivity
) : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)

        val confirm = view.findViewById<Button>(R.id.Reset_Validation_Button)
        val abort = view.findViewById<Button>(R.id.Reset_Abort_Button)

        val editNewPW = view.findViewById<EditText>(R.id.Reset_Password_New_Password)
        val editNewPWConfirm = view.findViewById<EditText>(R.id.Reset_Password_New_PasswordConfirm)


        confirm.setOnClickListener {
            if (editNewPW.text.toString() == editNewPWConfirm.text.toString()){
                AuthRef.sendPasswordResetEmail("pierre.morbois@esme.fr")
            }
            else {

            }
        }

        abort.setOnClickListener {
            val myAccountFragment = MyAccountFragment(context)
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.Fragment_container, myAccountFragment)
            transaction.commit()
        }


        return view
    }
}