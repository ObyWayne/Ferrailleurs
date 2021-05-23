package fr.ObiWayne.ferrailleurs.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.ObiWayne.ferrailleurs.Adapter.SearchPieceAdapter
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.R
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.pieceList

class SearchCategory(

        private val context: MainActivity,
        private val clicked:Int,
        private val brandSelected : String?,
        private val modelSelected : String?
) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_category, container, false)
        val verticalView = view.findViewById<RecyclerView>(R.id.vertical_recycler_category_search)
        var modelVar =0
        val modelList = arrayListOf<String>()
        for (i in 0 until pieceList.size){
            modelVar=0

        if (modelList.isEmpty()) {
            modelList.add(pieceList[i].model)}


        for (h in 0 until modelList.size){
            if (modelList[h] == pieceList[i].model){
                modelVar =1}}

        if (modelVar == 0){
            modelList.add(pieceList[i].model)}}


        val brandList = arrayListOf<String>()
        brandList.add("Abarth")
        brandList.add("Alfa Romeo")
        brandList.add("Audi")
        brandList.add("Aston")
        brandList.add("Bentley")
        brandList.add("BMW")
        brandList.add("Mercedes")
        brandList.add("Panhard")
        brandList.add("Peugeot")
        brandList.add("Triumph")
        brandList.add("Volkswagen")
        brandList.add("Volvo")
        if(clicked == 1){
            verticalView.adapter= SearchPieceAdapter(context, brandList, brandSelected,modelSelected,clicked)}

        else if(clicked == 2){
            verticalView.adapter= SearchPieceAdapter(context, modelList, brandSelected,modelSelected,clicked)}

    return view
    }
}