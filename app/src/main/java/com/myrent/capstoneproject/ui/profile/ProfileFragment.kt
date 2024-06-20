package com.myrent.capstoneproject.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myrent.capstoneproject.R
import com.myrent.capstoneproject.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEdit.setOnClickListener {
            binding.editProfileName.isEnabled = true
            binding.editProfileEmail.isEnabled = true
            binding.btnSave.visibility = View.VISIBLE
            binding.btnEdit.visibility = View.GONE
        }

        binding.btnSave.setOnClickListener {
            // Simpan perubahan profil
            val name = binding.editProfileName.text.toString()
            val email = binding.editProfileEmail.text.toString()

            // Contoh simpan ke shared preferences atau database
            val sharedPreferences = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString("name", name)
                putString("email", email)
                apply()
            }

            binding.editProfileName.isEnabled = false
            binding.editProfileEmail.isEnabled = false
            binding.btnSave.visibility = View.GONE
            binding.btnEdit.visibility = View.VISIBLE
        }
    }
}
