package fr.ObiWayne.ferrailleurs.Model

class LogoModel(

        val name: String = " Name",
        val imageUrl: String = "https://www.batirama.com/scaled/983/755/1/2017/08/31/125459/images/article/15082-_00erreur.jpg",
        var clicked: Boolean = false
    )

// les parametres "name" et "clicked" ne sont pas utiliser dans cette version.
//Ils ont été initialisé dans l'optique de  future amélioration