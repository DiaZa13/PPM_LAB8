package com.example.lab_8.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.lab_8.R
import com.example.lab_8.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.home_fragment.*

class homeFragment : Fragment() {

    companion object {
        fun newInstance() = homeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        binding.btnSearch.setOnClickListener{
            val keyword = txtKeyword.text.toString()
            val points = txtPoints.text.toString()
            val author = txtAuthor.text.toString()
            viewModel.searchNews(keyword,"story",points,author)
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_newsFragment)
        }
    return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
        binding.viewModel = viewModel



    }

}
