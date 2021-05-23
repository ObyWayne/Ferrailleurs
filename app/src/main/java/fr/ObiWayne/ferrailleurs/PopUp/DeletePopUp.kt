package fr.ObiWayne.ferrailleurs.PopUp

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.PopUp.Singleton.SUPPRDEF
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.DataFirestore
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.UID

object Singleton {
    var SUPPRDEF = 0
}

class DeletePopUp(
        context : MainActivity
) : Dialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_delete)

        no()
        yes()
    }

    private fun yes() {
        val yesButton =findViewById<Button>(R.id.PopUpDelete_YesButton)
        yesButton.setOnClickListener {
            DataFirestore.collection("/users").document(UID).delete()
            AuthRef.currentUser.delete()
            AuthRef.signOut()
            SUPPRDEF = 1

            dismiss()
        }}

    private fun no() {
        val noButton =findViewById<Button>(R.id.PopUpDelete_NoButton)
        noButton.setOnClickListener{
            dismiss()}}



}