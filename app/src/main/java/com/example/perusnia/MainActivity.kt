package com.example.perusnia

import FavoriteFragment
import HomeFragment
import NoteFragment
import ProfileFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.perusnia.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment=HomeFragment()
        val favoriteFragment=FavoriteFragment()
        val noteFragment=NoteFragment()
        val profileFragment=ProfileFragment()


        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(homeFragment)
                R.id.favorite->setCurrentFragment(favoriteFragment)
                R.id.note->setCurrentFragment(noteFragment)
                R.id.profile->setCurrentFragment(profileFragment)


            }
            true
        }

    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}
