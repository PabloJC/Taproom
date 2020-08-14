package com.pabji.taproom.ui.common.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentViewBinding<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected fun onCreateViewBinding(viewBinding: T): View? {
        _binding = viewBinding
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun setToolbar(toolbar: Toolbar) {
        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(toolbar)
        }
    }

    protected fun setToolbarWithBack(toolbar: Toolbar) {
        setToolbar(toolbar)
        (activity as? AppCompatActivity)?.let {
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            it.supportActionBar?.setHomeButtonEnabled(true)
            setTitle("")
        }
    }

    protected fun setTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }
}