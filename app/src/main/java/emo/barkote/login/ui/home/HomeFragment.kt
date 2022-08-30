package emo.barkote.login.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import emo.barkote.login.databinding.HomefragmentBinding

class HomeFragment : Fragment() {
    private lateinit var binding: HomefragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomefragmentBinding.inflate(layoutInflater,container,false)

        return binding.root
    }


}