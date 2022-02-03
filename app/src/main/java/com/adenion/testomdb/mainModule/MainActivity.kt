package com.adenion.testomdb.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mMainVM: MainViewModel
    private lateinit var mActiveFragment: Fragment
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
    private var mFireBaseAuth: FirebaseAuth? = null
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupAuth()
        setupViewModel()
        setupBottomNav()
    }

    private fun setupAuth() {
        mFireBaseAuth = FirebaseAuth.getInstance()
        mAuthStateListener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser
            if (user == null){
                val signIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setAvailableProviders(providers)
                    .build()
                //authResult.launch(signIntent)
                signInLauncher.launch(signIntent)
            }
        }
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
        //val profileFragment = ProfileFragment()
        mActiveFragment = homeFragment
        /*mFragmentManager.beginTransaction().add(R.id.hostFragment, profileFragment, ProfileFragment::class.java.name)
            .hide(profileFragment)
            .commit()*/
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
                /*R.id.action_profile -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(profileFragment).commit()
                    mActiveFragment = profileFragment
                    true
                }*/
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

    private val authResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            Toast.makeText(this, R.string.main_auth_welcome, Toast.LENGTH_LONG).show()
        } else{
            if (IdpResponse.fromResultIntent(it.data) == null){
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mFireBaseAuth?.addAuthStateListener { mAuthStateListener }
    }

    override fun onPause() {
        super.onPause()
        mFireBaseAuth?.removeAuthStateListener { mAuthStateListener }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = mFireBaseAuth?.currentUser
            Toast.makeText(this, "bienvenido", Toast.LENGTH_LONG).show()
        } else {
            if (response == null){
                finish()
            }
        }
    }

    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
        this.onSignInResult(res)
    }
}