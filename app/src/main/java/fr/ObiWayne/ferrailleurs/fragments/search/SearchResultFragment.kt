package fr.ObiWayne.ferrailleurs.fragments.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import fr.ObiWayne.ferrailleurs.Adapter.PieceAdapter
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.Model.PieceModel
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.pieceList

class SearchResultFragment(
        private val context: MainActivity,
        val brandSelected: String?,
        val modelSelected: String?
) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_result, container, false)


        val verticalView = view.findViewById<RecyclerView>(R.id.vertical_recycler_specific_search)
        val closeB = view.findViewById<ImageView>(R.id.SpecificSearch_closeB)
        val pieceSearchList = ArrayList<PieceModel>()

        if (brandSelected != null && modelSelected!=null){
            for (i in 0 until pieceList.size){
                if(pieceList[i].brand == brandSelected && pieceList[i].model == modelSelected){
                    pieceSearchList.add(pieceList[i])
                }
            }
            verticalView.adapter= PieceAdapter(context , pieceSearchList, R.layout.item_vertical_piece)
            val category1 = view.findViewById<TextView>(R.id.SpecificSearch_item1)
            val category2 = view.findViewById<TextView>(R.id.SpecificSearch_item2)
            category1.text = brandSelected
            category1.visibility = View.VISIBLE
            category2.text = modelSelected
            category2.visibility = View.VISIBLE }



        else if (brandSelected == null ) {
            for (i in 0 until pieceList.size) {
                if (pieceList[i].model == modelSelected) {
                    pieceSearchList.add(pieceList[i])
                }
            }
            verticalView.adapter= PieceAdapter(context , pieceSearchList, R.layout.item_vertical_piece)
            val category3 = view.findViewById<TextView>(R.id.SpecificSearch_item3)
            category3.text = modelSelected
            category3.visibility = View.VISIBLE}



        else if (modelSelected == null ) {
            for (i in 0 until pieceList.size) {
                if (pieceList[i].brand == brandSelected) {
                    pieceSearchList.add(pieceList[i])
                }
            }
            verticalView.adapter= PieceAdapter(context , pieceSearchList, R.layout.item_vertical_piece)
            val category3 = view.findViewById<TextView>(R.id.SpecificSearch_item3)
            category3.text = brandSelected
            category3.visibility = View.VISIBLE}

        
        closeB.setOnClickListener {
            val searchFragment = SearchFragment(context,null,null,0)
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.Fragment_container, searchFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return view
    }
}