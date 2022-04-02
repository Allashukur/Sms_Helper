package com.example.smshelper.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.smshelper.R
import com.example.smshelper.adapter.Adapter_Date
import com.example.smshelper.adapter.Adapter_Province
import com.example.smshelper.data_base_room.database.AppDataBase
import com.example.smshelper.data_base_room.entity.Date_model
import com.example.smshelper.databinding.FragmentProvinceBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProvinceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProvinceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var appDataBase: AppDataBase
    lateinit var binding: FragmentProvinceBinding
    lateinit var adapter: Adapter_Province

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProvinceBinding.inflate(inflater, container, false)

        appDataBase = AppDataBase.getInstanse(requireContext())
        adapter = Adapter_Province(object : Adapter_Province.OnClickListenerDate {
            override fun onClickDate(provinceID: Int) {
                var bundle = Bundle()
                bundle.putInt("province_id", provinceID)
                Navigation.findNavController(requireView()).navigate(R.id.workersFragment, bundle)
            }

        })
        val dateId = arguments?.getInt("date")
        if (dateId != null) {
            appDataBase.getProvinceDao().getByIdListProvince(dateId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    adapter.submitList(it)
                })

            workingWorker(dateId)
        }
        binding.rv.adapter = adapter
        binding.cancelButton.setOnClickListener {
                findNavController().popBackStack()
        }


        return binding.root
    }

    private fun workingWorker(dateID: Int) {
//        val sdf = SimpleDateFormat("dd/MM/yyyy")
//        val currentDate = sdf.format(Date())
//        val todayDate = appDataBase.getDateDao().dateBy(dateID.toString()) as Date_model
        val list_worker = appDataBase.getWorekersDao().getByProvinceIdList(dateID)
        var working = 0
        var notWorking = 0

        for (i in list_worker) {
            if (i.working) {
                working++
            } else {
                notWorking++
            }
        }
        binding.notworking.setText(notWorking.toString())
        binding.working.setText(working.toString())

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProvinceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProvinceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}