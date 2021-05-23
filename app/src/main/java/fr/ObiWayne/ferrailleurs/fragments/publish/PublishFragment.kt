package fr.ObiWayne.ferrailleurs.fragments.publish

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import fr.ObiWayne.ferrailleurs.*
import fr.ObiWayne.ferrailleurs.Model.PieceModel
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.UID
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.downloadUri
import fr.ObiWayne.ferrailleurs.fragments.home.HomeFragment
import java.util.*

class PublishFragment(
    private val context: MainActivity
) : Fragment() {

    private var file:Uri? = null
    private var  uploadedImage:ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_publish, container, false)

        val pickUpButton: Button  = view.findViewById(R.id.publier_addImage)
        val notCoImage: TextView  = view.findViewById(R.id.Publish_error_NoConnected_Texte)
        val confirmButton: Button = view.findViewById(R.id.search_pickUpButton)

        notCoImage.visibility = View.INVISIBLE
        uploadedImage = view.findViewById(R.id.publier_preview_image)
        pickUpButton.setOnClickListener{pickUpImage()}



        confirmButton.setOnClickListener{
            val connected = AuthRef?.currentUser?.uid
            if ( connected == UID){
                sendForms(view)}

            else{
                error(view)}}


        return  view
    }

    private fun pickUpImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 47 && resultCode == Activity.RESULT_OK )
        {
            if (data == null || data.data == null )return

            file = data.data
            uploadedImage?.setImageURI(file)


        }
    }

    private fun sendForms(view:View) {

        val repo = PieceRepository()
        repo.uploadImage(file!!) {

            val notCoImage = view.findViewById<TextView>(R.id.Publish_error_NoConnected_Texte)
            notCoImage?.visibility = View.INVISIBLE

            val pieceName = view.findViewById<EditText>(R.id.publier_input_title).text.toString()
            val pieceLink = view.findViewById<EditText>(R.id.publier_input_link).text.toString()
            val pieceDescription = view.findViewById<EditText>(R.id.publier_input_description).text.toString()
            val pieceModel = view.findViewById<EditText>(R.id.publier_input_model).text.toString()
            val pieceBrand = view.findViewById<Spinner>(R.id.publier_spinner_brand).selectedItem.toString()
            val pieceRef = view.findViewById<Spinner>(R.id.publier_spinner_type).selectedItem.toString()
            val pieceUrl = downloadUri
            val newPiece = PieceModel(

                    pieceName,
                    pieceDescription,
                    pieceUrl.toString(),
                    false,
                    pieceModel,
                    pieceBrand,
                    pieceLink,
                    UUID.randomUUID().toString(),
                    UID,
                    pieceRef
            )

            repo.insertPiece(newPiece).addOnCompleteListener {

                val homeFragment = HomeFragment(context)
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.Fragment_container, homeFragment)
                transaction.commit()
                Toast.makeText(context, "Pièce ajoutée avec succès !", Toast.LENGTH_SHORT).show()

            }}}


   private fun error(view: View){
       val notConnectedImage = view.findViewById<TextView>(R.id.Publish_error_NoConnected_Texte)
       notConnectedImage?.visibility = View.VISIBLE}}
