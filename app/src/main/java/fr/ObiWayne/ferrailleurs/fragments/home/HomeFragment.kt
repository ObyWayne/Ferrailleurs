package fr.ObiWayne.ferrailleurs.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.ObiWayne.ferrailleurs.Adapter.PieceAdapter
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.Model.PieceModel
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.pieceList
import fr.ObiWayne.ferrailleurs.R

class HomeFragment(

        private val context: MainActivity
) :Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val verticalView = view.findViewById<RecyclerView>(R.id.vertical_recycler_home)
        verticalView.adapter= PieceAdapter(context , pieceList , R.layout.item_vertical_piece)


        return view
    }
}