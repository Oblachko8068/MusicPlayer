package com.example.music

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.music.databinding.ActivityMainBinding
import com.example.music.homeFragment.HomeFragment
import com.example.music.fragment.MusicPlayerFragment
import com.example.music.playlistFragment.PlaylistsFragment
import com.example.music.fragment.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, HomeFragment())
                .commit()
        }
        checkLastPlayedMusic()
        setBottomNavigationView()
        binding.buttonSettings.setOnClickListener {
            launchFragment(fragment = SettingsFragment())
        }
    }

    private fun checkLastPlayedMusic() {
        launchPlayerFragment()
    }

    @SuppressLint("CommitTransaction")
    private fun launchPlayerFragment() {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.music_player_container, MusicPlayerFragment())
            .commitAllowingStateLoss()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_playlists -> selectedFragment = PlaylistsFragment()
            }
            if (selectedFragment != null) {
                launchFragment(selectedFragment)
            }
            true
        }
    }

    @SuppressLint("CommitTransaction")
    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss()
    }
}