package com.dmribeiro87.cupcakeapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmribeiro87.cupcakeapp.MainActivity
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.FragmentHomeBinding
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import com.dmribeiro87.model.Cupcake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var homeAdapter: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(R.color.white)
        val mActivity = (activity as MainActivity).supportActionBar
        mActivity?.setBackgroundDrawable(
            resources.getDrawable(
                R.color.white,
                resources.newTheme()
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        lifecycleScope.launch {
//            viewModel.populateFirebaseWithCupcakes()
//        }
        viewModel.getCupcakes()
        setupRecyclerView()
        addObserver()

    }

    private fun addObserver(){
        viewModel.cupcakes.observe(viewLifecycleOwner){ list ->
            if (!list.isNullOrEmpty()){
                homeAdapter.setData(list)
                Log.d("***Data", list.toString())
            }else{
                Log.d("***Error", list.size.toString())
            }
        }
    }

    private fun setupRecyclerView() {
        context.let { context ->
            homeAdapter = HomeAdapter()
            binding.rvList.layoutManager = LinearLayoutManager(context)
            binding.rvList.adapter = homeAdapter
        }
        homeAdapter.setAction {
            val action = HomeFragmentDirections.actionNavHomeToDetailsFragment(it)
            NavHostFragment.findNavController(this).navigate(action)
        }
    }



}