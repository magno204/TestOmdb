package com.adenion.testomdb.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.adenion.testomdb.R
import com.adenion.testomdb.common.utils.ITopAux
import com.adenion.testomdb.databinding.ActivityMainBinding
import com.adenion.testomdb.homeModule.HomeFragment
import com.adenion.testomdb.mainModule.viewModel.MainViewModel
import com.adenion.testomdb.movieModule.MoviesFragment
import com.adenion.testomdb.profileModule.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mMainVM: MainViewModel
    private lateinit var mActiveFragment: Fragment
    private lateinit var mFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupViewModel()
        setupBottomNav()
    }

    private fun setupViewModel() {
        mMainVM = ViewModelProvider(this)[MainViewModel::class.java]
        mMainVM.getMovies().observe(this, { movies ->

        })
    }

    private fun setupBottomNav() {
        mFragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val moviesFragment = MoviesFragment()
        val profileFragment = ProfileFragment()
        mActiveFragment = homeFragment
        mFragmentManager.beginTransaction().add(R.id.hostFragment, profileFragment, ProfileFragment::class.java.name)
            .hide(profileFragment)
            .commit()
        mFragmentManager.beginTransaction().add(R.id.hostFragment, moviesFragment, MoviesFragment::class.java.name)
            .hide(moviesFragment)
            .commit()
        mFragmentManager.beginTransaction().add(R.id.hostFragment, homeFragment, HomeFragment::class.java.name)
            .commit()

        mBinding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(homeFragment).commit()
                    mActiveFragment = homeFragment
                    true
                }
                R.id.action_movie -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(moviesFragment).commit()
                    mActiveFragment = moviesFragment
                    true
                }
                R.id.action_profile -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(profileFragment).commit()
                    mActiveFragment = profileFragment
                    true
                }
                else -> false
            }
        }

        mBinding.bottomNav.setOnItemReselectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    (homeFragment as ITopAux).goToTop()
                }
            }
        }
    }
}