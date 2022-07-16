package com.gb.nasa

import android.app.ProgressDialog.show
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import com.gb.nasa.api.pictureoftheday.PictureOfTheDayData
import com.gb.nasa.databinding.FragmentPictureOfTheDayBinding

class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!
    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData()
            .observe(viewLifecycleOwner) { renderData(it) }
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val description = serverResponseData.explanation
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        //error(R.drawable.ic_load_error_vector)
                        //placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                    }
                }

                if (description.isNullOrEmpty()){
                    toast("Description is empty")
                }
                else {
                    binding.description.text = description
                }
            }
            is PictureOfTheDayData.Loading -> {
            //Отобразите загрузку
            // showLoading()
            }
            is PictureOfTheDayData.Error -> {
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
        fun newInstance() = PictureOfTheDayFragment()
    }
}
