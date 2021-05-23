package fr.ObiWayne.ferrailleurs.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.ObiWayne.ferrailleurs.Model.LogoModel
import fr.ObiWayne.ferrailleurs.MainActivity
import fr.ObiWayne.ferrailleurs.R

class LogoAdapter(
        private val context: MainActivity,
        private val LogoList: List<LogoModel>,
        private val layoutId : Int ) : RecyclerView.Adapter<LogoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logoImage: ImageView = view.findViewById(R.id.Image_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentLogo= LogoList[position]
        Glide.with(context).load(Uri.parse(currentLogo.imageUrl)).into(holder.logoImage)
    }


        override fun getItemCount(): Int = LogoList.size



    }

