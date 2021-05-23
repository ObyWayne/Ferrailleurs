package fr.ObiWayne.ferrailleurs.fragments.search

import android.os.Bundle
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

class SearchTypeResultFragment(
        private val context: MainActivity,
        val pieceSelected : String
) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_result, container, false)

        val verticalView = view.findViewById<RecyclerView>(R.id.vertical_recycler_specific_search)
        val closeB = view.findViewById<ImageView>(R.id.SpecificSearch_closeB)
        val pieceSearchList = ArrayList<PieceModel>()
        for (i in 0 until pieceList.size)
            if(pieceList[i].ref == pieceSelected){
                pieceSearchList.add(pieceList[i])
            }
        verticalView.adapter= PieceAdapter(context , pieceSearchList, R.layout.item_vertical_piece)
        val category3 = view.findViewById<TextView>(R.id.SpecificSearch_item3)
        category3.text = pieceSelected
        category3.visibility = View.VISIBLE


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