package fr.ObiWayne.ferrailleurs.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.fragments.search.SearchFragment

class SearchPieceAdapter(
        private val context: MainActivity,
        private val searchTypeList: ArrayList<String>,
        private val brandSelected : String?,
        private val modelSelected : String?,
        private val clicked : Int
) : RecyclerView.Adapter<PieceRefAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val pieceTypeName: TextView = view.findViewById(R.id.TypeItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieceRefAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical_search, parent, false)
        return PieceRefAdapter.ViewHolder(view)}

    override fun onBindViewHolder(holder: PieceRefAdapter.ViewHolder, position: Int) {
        holder.pieceTypeName.text = searchTypeList[position]
        holder.itemView.setOnClickListener{

            if(clicked == 1){
                val searchFragment = SearchFragment(context, searchTypeList[position],modelSelected, clicked )
                val activity :AppCompatActivity   = context
                activity.supportFragmentManager.beginTransaction().replace(R.id.Fragment_container, searchFragment).commit()}

            else if(clicked == 2){
                val searchFragment = SearchFragment(context, brandSelected, searchTypeList[position],clicked)
                val activity :AppCompatActivity   = context
                activity.supportFragmentManager.beginTransaction().replace(R.id.Fragment_container, searchFragment).commit()}}}

    override fun getItemCount(): Int {
        return searchTypeList.size
    }


}
