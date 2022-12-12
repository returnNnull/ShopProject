package com.bam.shopproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bam.shopproject.BootsListAdapter
import com.bam.shopproject.R
import com.bam.shopproject.databinding.FragmentMainBinding
import com.bam.shopproject.room.BootsData
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class BootsListFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private lateinit var viewModel: BootsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[BootsViewModel::class.java]
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BootsListAdapter()
        adapter.itemClick {
            val bundle = Bundle()
            bundle.putInt("ID", it.id)
            Navigation.findNavController(binding.root).navigate(
                R.id.action_SecondFragment_to_bootsAboutFragment,
                bundle
            )
        }
        viewModel.getAll()?.observe(requireActivity()) {

            if (it.isEmpty()) {
                val list = initBootsList()
                list.forEach { boots ->
                    viewModel.insert(boots)
                }
            }

            adapter.items = it
        }

        binding.bootsRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initBootsList(): List<BootsData> {

        val result = arrayListOf<BootsData>()

        for (i in 0..100) {
            result.add(
                BootsData(
                    i,
                    "Name$i", Random.nextDouble(500.0),
                    "description$i",
                    "https://www.pngfind.com/pngs/m/266-2660116_-hd-png.png",
                    false
                )
            )
        }
        return result
    }

}