package com.royalit.driverapp.Fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.royalit.driverapp.Activitys.MyAccountActivity
import com.royalit.driverapp.Activitys.OrderDetailsActivity
import com.royalit.driverapp.Adapters.ViewPagerAdapter
import com.royalit.driverapp.Config.Preferences
import com.royalit.driverapp.R
import com.royalit.driverapp.databinding.FragmentDeliveriesBinding
import com.royalit.driverapp.databinding.FragmentHomeBinding
import java.util.Calendar

class DeliveriesFragment : Fragment() {

    private lateinit var binding: FragmentDeliveriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDeliveriesBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

    }

    private fun init() {

        //default current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        // Format: yyyy-MM-dd
        val currentDate = String.format("%04d-%02d-%02d", year, month + 1, day)
        binding.txtDate.text = currentDate
        Preferences.saveStringValue(requireActivity(), Preferences.stateDate, currentDate)
        Preferences.saveStringValue(requireActivity(), Preferences.endDate, currentDate)

        binding.cardDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                    binding.txtDate.text = selectedDate
                    Preferences.saveStringValue(requireActivity(), Preferences.stateDate, selectedDate)
                    Preferences.saveStringValue(requireActivity(), Preferences.endDate, selectedDate)
                    adapterSet()
                },
                year, month, day
            )

            // ✅ Only allow today
            val todayMillis = calendar.timeInMillis
            datePickerDialog.datePicker.minDate = todayMillis
            datePickerDialog.datePicker.maxDate = todayMillis

            datePickerDialog.show()

        }

        adapterSet()

    }

    private fun adapterSet() {
        // Set up the adapter
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_all)
                1 -> getString(R.string.tab_PickUp)
                2 -> getString(R.string.tab_complete)
                else -> getString(R.string.tab_all)
            }
        }.attach()
    }


}