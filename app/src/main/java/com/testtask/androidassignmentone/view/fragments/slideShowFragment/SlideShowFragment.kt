package com.testtask.androidassignmentone.view.fragments.slideShowFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.testtask.androidassignmentone.R
import com.testtask.androidassignmentone.data.network.model.SlideshowModel
import com.testtask.androidassignmentone.databinding.FragmentSlideShowBinding
import com.testtask.androidassignmentone.view.adapters.SlideShowViewPager
import com.testtask.androidassignmentone.view.interfeces.ConditionViewPager
import org.koin.androidx.viewmodel.ext.android.viewModel

class SlideShowFragment : Fragment(), ConditionViewPager {

    private var _binding: FragmentSlideShowBinding? = null
    private val binding get() = _binding!!
    private val vm: SlideShowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideShowBinding.inflate(inflater, container, false)
        vm.getSLideShowData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContent()
    }

    private fun setContent() {
        observeData()
    }

    private fun observeData() {
        vm.slides.observe(viewLifecycleOwner) { list ->
            list?.let {
                if (list.isEmpty()) {
                    Toast.makeText(requireContext(), R.string.empty_data_alert, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    initViewPager(it)
                }
            }
        }
    }

    private fun initViewPager(slideshowModels: List<SlideshowModel>) {
        val adapter = SlideShowViewPager(slideshowModels, this)
        binding.slideShow.adapter = adapter
        binding.slideShow.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.slideShow)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun condition(position: Int, fullSize: Int) {

    }
}