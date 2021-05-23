package fr.ObiWayne.ferrailleurs.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.Model.PieceModel
import fr.ObiWayne.ferrailleurs.PopUp.PiecePopUp
import fr.ObiWayne.ferrailleurs.R


class PieceAdapter(
        val context: MainActivity,
        private val pieceList: List<PieceModel>,
        private val layoutId : Int ) : RecyclerView.Adapter<PieceAdapter.ViewHolder>(){

class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val pieceImage: ImageView = view.findViewById(R.id.Image_item)
    val pieceName: TextView = view.findViewById(R.id.nameItem)
    val pieceDescription:TextView? = view.findViewById(R.id.descriptionItem)

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentPiece = pieceList[position]

        Glide.with(context).load(Uri.parse(currentPiece.imageUrl)).into(holder.pieceImage)
        holder.pieceName.text =currentPiece.name
        holder.pieceDescription?.text =currentPiece.description

        holder.itemView.setOnClickListener{
            PiecePopUp( this, currentPiece).show()
        }}


    override fun getItemCount(): Int = pieceList.size


}