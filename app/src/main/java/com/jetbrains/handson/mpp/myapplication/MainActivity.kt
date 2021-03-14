package com.jetbrains.handson.mpp.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity(), OnFragmentChangeListener {
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        fragmentManager = supportFragmentManager
        val fragmentA = FragmentA()
        fragmentManager.beginTransaction().replace(R.id.backstack_container, fragmentA, FragmentA::class.java.simpleName).addToBackStack(null).commitAllowingStateLoss();

    }

    override fun onNext(tag: String?) {
        val currentFragment = getFragmentByTag(tag)
        currentFragment.let {
            when (it) {
                is FragmentA -> {
                    //hide the container with backstack
                    findViewById<View>(R.id.backstack_container).visibility = View.GONE
                    val fragmentB = FragmentB()
                    fragmentManager.beginTransaction().add(R.id.non_backstack_container, fragmentB, FragmentB::class.java.simpleName).addToBackStack("b")
                            .commitAllowingStateLoss()
                    //show the container with out backstack
                    findViewById<View>(R.id.non_backstack_container).visibility = View.VISIBLE

                }
                is FragmentB -> {
                    val fragmentC = FragmentC()
                    fragmentManager.beginTransaction().replace(R.id.backstack_container, fragmentC, FragmentC::class.java.simpleName).addToBackStack("c")
                            .commitAllowingStateLoss()
                    //remove itself, this will trigger the ondestroyview callback
                    fragmentManager.beginTransaction().remove(getFragmentByTag(FragmentB::class.java.simpleName)!!).commitNowAllowingStateLoss()
                    findViewById<View>(R.id.non_backstack_container).visibility = View.GONE
                    findViewById<View>(R.id.backstack_container).visibility = View.VISIBLE
                }
                else -> {
                    //dont do anything
                }
            }
        }
    }

    override fun onPrevious(tag: String?) {
        val currentFragment = getFragmentByTag(tag)
        currentFragment.let {
            when (it) {
                is FragmentA -> {
                    //do nothing
                }
                is FragmentB -> {
                    findViewById<View>(R.id.non_backstack_container).visibility = View.GONE
                    findViewById<View>(R.id.backstack_container).visibility = View.VISIBLE
                    fragmentManager.beginTransaction().remove(getFragmentByTag(FragmentB::class.java.simpleName)!!).commitNowAllowingStateLoss()
                }
                else -> {
                    fragmentManager.popBackStackImmediate()
                }
            }
        }
    }

    private fun getFragmentByTag(tag: String?): Fragment? {
        return fragmentManager.findFragmentByTag(tag)
    }
}