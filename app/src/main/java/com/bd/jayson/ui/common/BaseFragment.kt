package com.bd.jayson.ui.common

import androidx.fragment.app.Fragment

import dagger.hilt.android.AndroidEntryPoint
import com.bd.jayson.data.storage.SessionPreference
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment: Fragment() {

    @Inject lateinit var mPref: SessionPreference
}