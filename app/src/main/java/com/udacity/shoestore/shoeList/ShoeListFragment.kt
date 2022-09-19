package com.udacity.shoestore.shoeList

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ListItemShoeBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.shoeDetail.ShoeDetailFragment

class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    private val viewModel: ShoeListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAddShoeResult()
    }

    private fun setupAddShoeResult() {
        setFragmentResultListener(ShoeDetailFragment.requestKey) { requestKey, bundle ->
            if (requestKey != ShoeDetailFragment.requestKey) return@setFragmentResultListener
            val item: Shoe? = if (Build.VERSION.SDK_INT >= 33) {
                bundle.getParcelable(ShoeDetailFragment.bundleKey, Shoe::class.java)
            } else {
                bundle.getParcelable(ShoeDetailFragment.bundleKey)
            }
            addItem(item)
        }
    }

    private fun addItem(item: Shoe?) {
        if (item == null) return
        viewModel.addItem(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setupMenu()
        setupListeners()
        observeShoes()
    }

    private fun setupListeners() {
        binding.fab.setOnClickListener {
            it.findNavController()
                .navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment())
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_logout, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.logout -> {
                            val options = NavOptions.Builder()
                                .setPopUpTo(R.id.loginFragment, true)
                                .setLaunchSingleTop(true)
                                .build()
                            findNavController().navigate(R.id.loginFragment, null, options)
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    private fun observeShoes() {
        viewModel.shoes.observe(viewLifecycleOwner) { shoes ->
            shoes?.let {
                showItems(it)
            }
        }
    }

    private fun showItems(items: List<Shoe>) {
        items.forEach { showItem(it) }
    }

    private fun showItem(item: Shoe) {
        val binding: ListItemShoeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item_shoe, null, false)
        binding.item = item
        this.binding.linearLayout.addView(binding.root)
    }
}
