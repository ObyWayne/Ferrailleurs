package fr.ObiWayne.ferrailleurs.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.fragments.search.SearchTypeResultFragment

class PieceRefAdapter(
        private val context: MainActivity,
        private val pieceTypeList: ArrayList<String>
) : RecyclerView.Adapter<PieceRefAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val pieceTypeName: TextView = view.findViewById(R.id.TypeItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical_search, parent, false)
        return ViewHolder(view)}


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pieceTypeName.text = pieceTypeList[position]
        holder.itemView.setOnClickListener{
            val searchResultFragment = SearchTypeResultFragment(context ,pieceTypeList[position])
            val activity :AppCompatActivity   = context
            activity.supportFragmentManager.beginTransaction().replace(R.id.Fragment_container, searchResultFragment).commit()}}

    override fun getItemCount(): Int{
        return pieceTypeList.size
    }

}

