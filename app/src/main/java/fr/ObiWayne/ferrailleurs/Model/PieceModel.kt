package fr.ObiWayne.ferrailleurs.Model

import android.net.Uri
import androidx.core.net.toUri

class PieceModel(

        val name: String = " Name",
        val description: String = "Description",
        val imageUrl: String =  "https://www.batirama.com/scaled/983/755/1/2017/08/31/125459/images/article/15082-_00erreur.jpg",
        var liked: Boolean = false,
        val model: String = "Model",
        val brand: String = "Brand",
        val link: String = "Link",
        val id: String = "piece0",
        var posted: String = "null",
        val ref : String = ""
        )

// "name", "description", "imageUrl", "model", "brand", "link", "posted" sont les caractéristiques qui apparaissent sur la fiche technique(PopUp)
// "id" et "ref" sont les parametres qui permettent la recherche de piece
// "liked" etait le parametre qui servait a liké une piece avant l'implémentation des comptes : inutiliser dans la version actuelle