package fr.ObiWayne.ferrailleurs.PopUp

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import fr.ObiWayne.ferrailleurs.Adapter.PieceAdapter
import fr.ObiWayne.ferrailleurs.Model.PieceModel
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.DataFirestore
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.UID
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.pieceListUser


class PiecePopUp(
        private val adapter: PieceAdapter,
        private val currentPiece: PieceModel
) : Dialog(adapter.context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_piece_details)

        setUpComponent()
        setUpCloseButton()
        setUpCopyButton()
        if(AuthRef?.currentUser?.uid == UID){
            setUpFavButton()
        }

    }


    private fun updateFav1 (button: ImageView){
        if (currentPiece.liked){
            button.setImageResource(R.drawable.ic_full_favorite)}

        else{
            button.setImageResource(R.drawable.ic_empty_favorite)}}



    private fun updateFav (button: ImageView){
        button.setImageResource(R.drawable.ic_empty_favorite)
        for (i in 0 until pieceListUser.size){
            if (currentPiece.id == pieceListUser[i].id){
                button.setImageResource(R.drawable.ic_full_favorite)
            }}}

    private fun setUpFavButton() {

        val favButton: ImageView = findViewById(R.id.popUp_FavButton)
        updateFav(favButton)
        var yes = 0
        favButton.setOnClickListener{
            for (i in 0 until pieceListUser.size){
                if (currentPiece.id == pieceListUser[i].id){
                    Toast.makeText(context, "Retiré des favoris", Toast.LENGTH_SHORT).show()
                    DataFirestore.collection("/users").document(UID).update("liked",FieldValue.arrayRemove(currentPiece.id))
                    yes =1
                }
            }
            if (yes != 1){
                Toast.makeText(context, "Ajouté aux favoris", Toast.LENGTH_SHORT).show()
                DataFirestore.collection("/users").document(UID).update("liked",FieldValue.arrayUnion(currentPiece.id))
            }
            val repo =PieceRepository()
            repo.dataCheck {updateFav(favButton)
            }



        }
    }



    private fun setUpCopyButton() {
        val text = currentPiece.link
        findViewById<ImageView>(R.id.popUp_CopyButton).setOnClickListener{
            val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

            var clipData: ClipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, "Copié dans le presse papier", Toast.LENGTH_SHORT).show()}}


    private fun setUpCloseButton() {
        findViewById<ImageView>(R.id.popUp_CloseButton).setOnClickListener{
            dismiss()}}


    private fun setUpComponent() {
        val pieceImage = findViewById<ImageView>(R.id.popUp_Image)
        Glide.with(adapter.context).load(Uri.parse(currentPiece.imageUrl.toString())).into(pieceImage)
        findViewById<TextView>(R.id.popUp_Name).text = currentPiece.name
        findViewById<TextView>(R.id.popUp_Description).text = currentPiece.description
        findViewById<TextView>(R.id.popUp_Brand).text = currentPiece.brand
        findViewById<TextView>(R.id.popUp_Model).text = currentPiece.model
        val user = currentPiece.posted
        DataFirestore.collection("/users").document(user).get().addOnCompleteListener { document ->
            val firstName = document?.result!!.get("firstName").toString()
            if (firstName == null){
                findViewById<TextView>(R.id.popUp_posted_By).text = "Delete User"}

            else{
                findViewById<TextView>(R.id.popUp_posted_By).text = firstName}
            
        }}}