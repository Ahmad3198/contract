package com.example.contract.di.component

import com.example.contract.di.module.FragmentModule
import dagger.Component

/**
 * Created by ogulcan on 07/02/2018.
 */
@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

}