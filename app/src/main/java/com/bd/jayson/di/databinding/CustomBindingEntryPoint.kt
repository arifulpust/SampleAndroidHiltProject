package com.bd.jayson.di.databinding

import androidx.databinding.DataBindingComponent
import com.bd.jayson.util.BindingUtil

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@EntryPoint
@BindingScoped
@InstallIn(CustomBindingComponent::class)
interface CustomBindingEntryPoint: DataBindingComponent {

    @BindingScoped
    override fun getBindingUtil(): BindingUtil
}