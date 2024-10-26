package com.mukiva.p2pmessanger.di

import com.mukiva.feature.groups.di.groupsFeatureModule
import com.mukiva.feature.main.di.mainFeatureModule
import org.koin.core.module.Module

expect val platformModules: Module

val featureModules = mainFeatureModule + groupsFeatureModule