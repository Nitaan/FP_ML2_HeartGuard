package com.nitamlII.heart_uasii.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.nitamlII.heart_uasii.R // Adjust this import to your actual R file location
import com.nitamlII.heart_uasii.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var sliderAdapter: ImageSliderAdapter

    private val images = intArrayOf(
        R.drawable.slide1,
        R.drawable.slide2
    )

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        sliderAdapter = ImageSliderAdapter(requireContext(), images)
        viewPager.adapter = sliderAdapter
        tabLayout.setupWithViewPager(viewPager, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
