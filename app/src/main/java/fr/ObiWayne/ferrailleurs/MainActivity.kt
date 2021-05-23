package fr.ObiWayne.ferrailleurs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.AuthRef
import fr.ObiWayne.ferrailleurs.fragments.*
import fr.ObiWayne.ferrailleurs.fragments.account.AccountFragment
import fr.ObiWayne.ferrailleurs.fragments.account.MyAccountFragment
import fr.ObiWayne.ferrailleurs.fragments.fav.FavoriteFragment
import fr.ObiWayne.ferrailleurs.fragments.home.HomeFragment
import fr.ObiWayne.ferrailleurs.fragments.publish.PublishFragment
import fr.ObiWayne.ferrailleurs.fragments.search.SearchFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //page au debut
        AuthRef.signOut()
        loadFragment(HomeFragment(this),R.string.home_page_title)

        //importer la bottomnav
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.home_page -> {
                    loadFragment(HomeFragment(this),R.string.home_page_title)
                            return@setOnNavigationItemSelectedListener true
                }
                R.id.search_page -> {
                    loadFragment(SearchFragment(this,null,null),R.string.bottom_view_rechercher)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fav_page -> {
                    loadFragment(FavoriteFragment(this), R.string.Favorite_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.publish_page -> {
                    loadFragment(PublishFragment(this), R.string.publier_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.more_page -> {


                    val connected = AuthRef?.currentUser?.uid
                    if ( connected == PieceRepository.Singleton.UID){
                        loadFragment(MyAccountFragment(this), R.string.Account_SignIn)
                    }
                    else{
                        loadFragment(AccountFragment(this), R.string.Account_SignIn)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    }

    private fun loadFragment(fragment: Fragment, string : Int) {

        val repo = PieceRepository()


        findViewById<TextView>(R.id.Home_Text).text = resources.getString(string)

        repo.updateData{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.Fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()}}

}