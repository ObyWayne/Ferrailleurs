package fr.ObiWayne.ferrailleurs.fragments.search

import android.annotation.SuppressLint
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
import fr.ObiWayne.ferrailleurs.Adapter.PieceRefAdapter
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.R

class SearchFragment(

        private val context: MainActivity,
        private val brandSelected : String?,
        private val modelSelected : String?
) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val verticalView = view.findViewById<RecyclerView>(R.id.vertical_recycler_search)

        val findButton = view.findViewById<Button>(R.id.search_pickUpButton)
        val brandButton = view.findViewById<ImageView>(R.id.search_arrow_brand)
        val modelButton = view.findViewById<ImageView>(R.id.search_arrow_model)

        val modelText = view.findViewById<TextView>(R.id.search_model_text_Input)
        val brandText = view.findViewById<TextView>(R.id.search_brand_text_Input)
        brandText.visibility = View.INVISIBLE
        modelText.visibility = View.INVISIBLE

        if (brandSelected !=null ){
            brandText.text = brandSelected
            brandText.visibility = View.VISIBLE}

        if (modelSelected !=null ){
            modelText.text = modelSelected
            modelText.visibility = View.VISIBLE}


        findButton.setOnClickListener {
            goToSearchResultFragment(brandSelected,modelSelected)}

        brandButton.setOnClickListener {
            goToSearchCategory(1)}

        modelButton.setOnClickListener {
            goToSearchCategory(2)}


        val typeList = arrayListOf<String>()
        typeList.add("Allumage et démarrage")
        typeList.add("Boite de Vitesse")
        typeList.add("Chasis et suspension")
        typeList.add("Carburation et filtration")
        typeList.add("Carroserie et tôleris")
        typeList.add("Circuit de refroidissement")
        typeList.add("Eclairage et Electricité ")
        typeList.add("Equipement interieur")
        typeList.add("Klaxons et avertisseurs")
        typeList.add("Reservoir")
        typeList.add("Roue, jantes et accesoires")
        verticalView.adapter = PieceRefAdapter(context, typeList)


        return view
    }

    private fun goToSearchCategory(clicked:Int) {
        val searchCategoryFragment = SearchCategory(context,clicked,brandSelected,modelSelected)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.Fragment_container, searchCategoryFragment)
        transaction.commit()}

    private fun goToSearchResultFragment(categoryBrand: String?, categoryModel:String?) {
        val searchResultFragment = SearchResultFragment(context,categoryBrand,categoryModel)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.Fragment_container, searchResultFragment)
        transaction.commit()}



}