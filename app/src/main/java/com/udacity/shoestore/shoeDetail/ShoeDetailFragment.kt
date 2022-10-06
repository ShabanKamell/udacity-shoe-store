package com.udacity.shoestore.shoeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe

class ShoeDetailFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding
    private val viewModel: ShoeDetailViewModel by activityViewModels()
    private val shoe = Shoe.empty

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoeDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.shoe = shoe
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observeSave()
    }

    private fun setup() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeSave() {
        viewModel.onSave.observe(viewLifecycleOwner) {
            save()
        }
    }

    private fun save() {
        if (!vaidate()) return
        setFragmentResult(requestKey, bundleOf(bundleKey to shoe))
        findNavController().navigateUp()
    }

    private fun vaidate(): Boolean {
        var isValid = true

        val name = binding.etName.text.toString()
        val size = binding.etSize.text.toString()
        val company = binding.etCompany.text.toString()
        val description = binding.etDescription.text.toString()

        if (name.isEmpty()) {
            binding.etName.error = getString(R.string.required)
            isValid = false
        }

        if (size.isEmpty()) {
            binding.etSize.error = getString(R.string.required)
            isValid = false
        }

        if (company.isEmpty()) {
            binding.etCompany.error = getString(R.string.required)
            isValid = false
        }

        if (description.isEmpty()) {
            binding.etDescription.error = getString(R.string.required)
            isValid = false
        }
        return isValid
    }

    companion object {
        const val requestKey = "ShoeDetailFragment.request"
        const val bundleKey = "ShoeDetailFragment.bundle"
    }
}
