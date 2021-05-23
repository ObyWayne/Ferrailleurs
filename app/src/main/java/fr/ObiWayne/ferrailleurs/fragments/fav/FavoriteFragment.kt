package fr.ObiWayne.ferrailleurs.fragments.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import fr.ObiWayne.ferrailleurs.*
import fr.ObiWayne.ferrailleurs.Adapter.LogoAdapter
import fr.ObiWayne.ferrailleurs.Adapter.PieceAdapter
import fr.ObiWayne.ferrailleurs.Model.LogoModel
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.UID
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.pieceList
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.pieceListUser
import fr.ObiWayne.ferrailleurs.fragments.account.AccountFragment

class FavoriteFragment(
        private val context: MainActivity
) : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val logo = arrayListOf<LogoModel>()
        logo.add( LogoModel(
                name = "abarth",
                imageUrl = "https://www.pngitem.com/pimgs/m/26-264769_abarth-logo-png-transparent-fiat-500-abarth-logo.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "alfa romeo",
                imageUrl = "https://down.imgspng.com/download/0720/alfa_romeo_PNG3.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "audi",
                imageUrl = "https://upload.wikimedia.org/wikipedia/fr/thumb/1/15/Audi_logo.svg/1280px-Audi_logo.svg.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "Aston Martin",
                imageUrl = "http://assets.stickpng.com/images/580b585b2edbce24c47b2c11.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "Bentley",
                imageUrl = "http://assets.stickpng.com/images/580b585b2edbce24c47b2c2c.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "BMW",
                imageUrl = "http://assets.stickpng.com/images/580b57fcd9996e24bc43c46e.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "Mercedes",
                imageUrl = "https://i.pinimg.com/originals/03/e1/b0/03e1b0207489ad32d10b9a860ffc6623.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "Panhard",
                imageUrl = "http://marque-voiture.com/wp-content/uploads/2016/11/Le-logo-Panhard.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "Peugeot",
                imageUrl = "http://assets.stickpng.com/images/584831f6cef1014c0b5e4aa6.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "Triumph",
                imageUrl = "https://triumph-frejus.fr/nas/commun/www_sites/7b52009b64fd0a2a49e6d8a939753077792b0554/articles/logo-triumph-952x866.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "Volkswagen",
                imageUrl = "http://assets.stickpng.com/images/580b585b2edbce24c47b2cf2.png",
                clicked = false
        ))
        logo.add( LogoModel(
                name = "Volvo",
                imageUrl = "http://www.logo-voiture.com/wp-content/uploads/2021/01/Volvo-logo-2014-1920x1080-grand.png",
                clicked = false
        ))




        val favoriteHorizontalView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_favorite)
        favoriteHorizontalView.adapter= LogoAdapter(context, logo ,R.layout.item_horizontal_logo)

        val favoriteRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_favorite)

        val connected = AuthRef?.currentUser?.uid
        if ( connected == UID ){

            favoriteRecyclerView.adapter= PieceAdapter(context , pieceListUser, R.layout.item_vertical_piece)

        }
        else{

            favoriteRecyclerView.adapter= PieceAdapter(context , pieceList, R.layout.item_vertical_piece)
            favoriteRecyclerView.alpha = 0.2F

            val notConnectedImage = view.findViewById<ImageView>(R.id.Favorite_NoAccount_Image)
            val notConnectedText = view.findViewById<TextView>(R.id.Favorite_NoAccount_Texte)
            val notConnectedButton = view.findViewById<Button>(R.id.Favorite_NoAccount_Button)


            notConnectedImage?.visibility = View.VISIBLE
            notConnectedText?.visibility = View.VISIBLE
            notConnectedButton?.visibility = View.VISIBLE

            goToAccount(notConnectedButton)}





        return  view
    }

    private fun goToAccount(notConnectedButton : Button) {

        notConnectedButton.setOnClickListener {
            val accountFragment = AccountFragment(context)
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.Fragment_container, accountFragment)
            transaction.addToBackStack(null)
            transaction.commit()}}



}