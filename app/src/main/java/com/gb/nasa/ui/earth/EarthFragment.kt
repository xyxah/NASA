package com.gb.nasa.ui.earth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import com.gb.nasa.api.earth.EarthData
import com.gb.nasa.databinding.FragmentEarthBinding

class EarthFragment : Fragment() {


    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    //Ленивая инициализация модели
    private val viewModel: EarthViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(EarthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }
        _binding = FragmentEarthBinding.inflate(inflater, container, false)

        return binding.root

    }

    private fun renderData(data: EarthData) {
        when (data) {
            is EarthData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@EarthFragment)
                        //error(R.drawable.ic_load_error_vector)
                        //placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                    }
                }
            }
            is EarthData.Loading -> {
                //Отобразите загрузку
                // showLoading()
            }
            is EarthData.Error -> {
                //Отобразите ошибку
                // showError(data.error.message)
                toast(data.error.message)
            }
        }

    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = EarthFragment()
    }
}