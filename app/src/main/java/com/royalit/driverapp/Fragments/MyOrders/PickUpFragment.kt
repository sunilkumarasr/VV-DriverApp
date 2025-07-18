package com.royalit.driverapp.Fragments.MyOrders

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.royalit.driverapp.Activitys.OrderDetailsActivity
import com.royalit.driverapp.Adapters.Pending.PendingOrdersListAdapter
import com.royalit.driverapp.Adapters.PickUp.PickUpOrdersListAdapter
import com.royalit.driverapp.Api.RetrofitClient
import com.royalit.driverapp.Config.Preferences
import com.royalit.driverapp.Config.ViewController
import com.royalit.driverapp.Modal.OrderHistoryResponse
import com.royalit.driverapp.Modal.ProductModel
import com.royalit.driverapp.R
import com.royalit.driverapp.databinding.FragmentAllOrdersBinding
import com.royalit.driverapp.databinding.FragmentPickUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickUpFragment : Fragment() {

    private lateinit var binding: FragmentPickUpBinding

    private var hasLoadedOnce = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPickUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasLoadedOnce) {
            init()
            hasLoadedOnce = true
        }

    }

    private fun init() {
        if (!ViewController.noInterNetConnectivity(requireActivity())) {
            ViewController.showToast(requireActivity(), "Please check your connection ")
            return
        } else {
            getOrdersListApi()
        }
    }


    private fun getOrdersListApi() {
        //PickUp 3
        val stateDate = Preferences.loadStringValue(requireActivity(), Preferences.stateDate, "").toString()
        val endDate = Preferences.loadStringValue(requireActivity(), Preferences.endDate, "").toString()
        ViewController.showLoading(requireActivity())
        val apiServices = RetrofitClient.apiInterface
        val call = apiServices.getOrdersListApi(getString(R.string.api_key),"3",stateDate,endDate)
        call.enqueue(object : Callback<ProductModel> {
            override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                ViewController.hideLoading()
                try {
                    if (response.isSuccessful) {
                        val responseList = response.body()?.response

                        if (responseList != null) {
                            if (responseList.isNotEmpty()){
                                dataSet(responseList)
                            }else{
                                binding.linearNoData.visibility = View.VISIBLE
                            }
                        }else{
                            binding.linearNoData.visibility = View.VISIBLE
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("onResponseException", e.message.toString())
                    binding.linearNoData.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                Log.e("onFailuregetProductsApi", "API Call Failed: ${t.message}")
                ViewController.hideLoading()
                binding.linearNoData.visibility = View.VISIBLE
            }
        })
    }
    private fun dataSet(selectedOrdersList: List<OrderHistoryResponse>) {
        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(requireActivity(), 1)
            binding.recyclerview.adapter  = PickUpOrdersListAdapter(requireActivity(), selectedOrdersList) { item ->
                val intent = Intent(requireActivity(), OrderDetailsActivity::class.java).apply {
                    putExtra("orderType", "Delivered")
                    putExtra("orderId", item.orderId)
                    putExtra("customerId", item.customerId)
                }
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.from_right, R.anim.to_left)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoadedOnce && isVisible) {
            init()
            hasLoadedOnce = true
        }
    }

    override fun onPause() {
        super.onPause()
        hasLoadedOnce = false
    }

}