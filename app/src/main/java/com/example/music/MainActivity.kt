package com.example.music

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.music.databinding.ActivityMainBinding
import com.example.music.fragment.MusicPlayerFragment
import com.example.music.musicListFragment.MusicListFragment
import com.example.music.playlistFragment.PlaylistsFragment
import com.example.music.service.MusicService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLayout()
        startService(Intent(this, MusicService::class.java))
        if (savedInstanceState == null) {
            showInitialFragment()
        }
    }

    private fun setupLayout() {
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWindowInsets()
        setupNavigation()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupNavigation() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> showFragment(MusicListFragment())
                R.id.nav_playlists -> showFragment(PlaylistsFragment())
            }
            true
        }
    }

    private fun showInitialFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MusicListFragment())
            .commit()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}