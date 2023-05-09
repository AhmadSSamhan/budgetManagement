package com.android.task.budgetmanagement.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferencesImpl.Companion.LANG_AR
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferencesImpl.Companion.LANG_EN
import com.android.task.budgetmanagement.databinding.FragmentSettingBinding
import com.android.task.budgetmanagement.ui.setting.ThemeId.DARK
import com.android.task.budgetmanagement.ui.setting.ThemeId.LIGHT
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!


    private val viewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        activeSelectedLanguage()
        activeCurrentTheme()
    }

    private fun initListener() {
        binding.rbArabic.setOnClickListener {
            applyLanguageChanges(LANG_AR)
        }
        binding.rbEnglish.setOnClickListener {
            applyLanguageChanges(LANG_EN)
        }
        binding.rbDark.setOnClickListener {
            viewModel.setSelectedTheme(DARK)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            requireActivity().recreate()
        }
        binding.rbLight.setOnClickListener {
            viewModel.setSelectedTheme(LIGHT)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            requireActivity().recreate()
        }
    }

    private fun activeSelectedLanguage() {
        if (viewModel.isSelectedLanguageArabic() == true) {
            binding.rbArabic.isChecked = true
        } else {
            binding.rbEnglish.isChecked = true
        }
    }

    private fun activeCurrentTheme() {
        when (viewModel.getSelectedTheme) {
            LIGHT -> {
                binding.rbLight.isChecked = true
            }
            DARK -> {
                binding.rbDark.isChecked = true
            }
        }
    }

    private fun applyLanguageChanges(strLanguage: String) {
        viewModel.saveUserLanguageAndRefreshApp(strLanguage, WeakReference(requireActivity()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}