package com.example.smshelper.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.smshelper.R
import com.example.smshelper.adapter.Adapter_Workers
import com.example.smshelper.data_base_room.database.AppDataBase
import com.example.smshelper.data_base_room.entity.Workers_model
import com.example.smshelper.databinding.BottomSheetBinding
import com.example.smshelper.databinding.FragmentWorkersBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkersFragment : Fragment() {
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

    lateinit var binding: FragmentWorkersBinding
    lateinit var appDataBase: AppDataBase
    lateinit var adapterWorkers: Adapter_Workers
    lateinit var tempList: ArrayList<Workers_model>
    lateinit var newList: ArrayList<Workers_model>
    var phone_Number = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkersBinding.inflate(inflater, container, false)

        tempList = ArrayList()
        newList = ArrayList()

        appDataBase = AppDataBase.getInstanse(requireContext())
        adapterWorkers = Adapter_Workers(object : Adapter_Workers.OnClickListener {
            override fun onClickDate(worker: Workers_model) {
                loadBottomSheet(worker)
            }

        }

        )
        val provinceId = arguments?.getInt("province_id")
        if (provinceId != null) {
            val worker_list = appDataBase.getWorekersDao().getByIdList2(provinceId)
            tempList.addAll(worker_list)
            if (worker_list.size == 0) {
                binding.emptyText.visibility = View.VISIBLE
            }
            newList.addAll(tempList)
            adapterWorkers.submitList(tempList)

            setWorkerNotWorker(provinceId)
        }



        binding.apply {
            seachBtn.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    tempList.clear()
                    val searchText = newText!!.toLowerCase(Locale.getDefault())

                    if (searchText.isNotEmpty()) {
                        newList.forEach {
                            if (it.name.toLowerCase(Locale.getDefault()).contains(searchText)) {
                                tempList.add(it)
                            }
                        }
                        rv.adapter!!.notifyDataSetChanged()
                    } else {
                        tempList.clear()
                        tempList.addAll(newList)
                        rv.adapter!!.notifyDataSetChanged()
                    }


                    return false

                }

            })
            cancelButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
//        tempList.addAll(newList)
        binding.rv.adapter = adapterWorkers


        return binding.root
    }

    private fun loadBottomSheet(worker: Workers_model) {
        val bottomSheet = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null, false)
        val bottomSheetBinding = BottomSheetBinding.bind(view)

        phone_Number = worker.number

        bottomSheetBinding.apply {
            nameTv.setText(worker.name)
            phonerNumberTv.setText(worker.number)
            smsTextTv.setText(worker.message)
        }
        bottomSheetBinding.callPhone.setOnClickListener {
            callPhoneNumber(worker.number)
        }
        bottomSheetBinding.smsPhone.setOnClickListener {
            requestPermissionLauncherSMS.launch(Manifest.permission.SEND_SMS)
        }


        bottomSheet.setContentView(view)
        bottomSheet.show()

    }

    private fun callPhoneNumber(phone_Number: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone_Number, null))
            startActivity(intent)
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
            requestPermissionLauncherCall.launch(Manifest.permission.CALL_PHONE)
        } else {
            requestPermissionLauncherCall.launch(Manifest.permission.CALL_PHONE)
        }


    }

    val requestPermissionLauncherCall = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (phone_Number.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone_Number, null))
                startActivity(intent)
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Qong'iroq qilish uchun siz ilovaga ruhsat bermagansiz !",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setWorkerNotWorker(provinceId: Int) {
        val listWorker = appDataBase.getWorekersDao().getByIdList2(provinceId)
        var working = 0
        var notWorking = 0

        for (i in listWorker) {
            if (i.working) {
                working++
            } else {
                notWorking++
            }
        }
        binding.notworking.setText(notWorking.toString())
        binding.working.setText(working.toString())

    }


    val requestPermissionLauncherSMS =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.setData(Uri.parse("sms:" + "$phone_Number"));
                intent.putExtra("exit_on_sent", true);
                startActivityForResult(intent, 1);


            } else {
                Toast.makeText(
                    requireContext(),
                    "Sms jo'natish uchun Ilovaga ruhsat bermagansiz !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}