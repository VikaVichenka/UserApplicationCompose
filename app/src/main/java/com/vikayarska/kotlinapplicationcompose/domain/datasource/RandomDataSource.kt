package com.vikayarska.kotlinapplicationcompose.domain.datasource

import com.vikayarska.kotlinapplicationcompose.BuildConfig
import com.vikayarska.kotlinapplicationcompose.domain.model.LoremType
import com.vikayarska.kotlinapplicationcompose.domain.model.NameType
import com.vikayarska.kotlinapplicationcompose.domain.model.TextType
import com.vikayarska.kotlinapplicationcompose.network.api.RandomApi
import retrofit2.Response
import javax.inject.Inject

class RandomDataSource @Inject constructor(private val randomApi: RandomApi) {

    suspend fun getIntro(loremType: LoremType, textType: TextType, amount: Int) =
        randomApi.getIntro(BuildConfig.API_KEY_RANDOM_TEXT, loremType.value, textType.value, amount)

    suspend fun getFullNames(nameType: NameType, quantity: Int): Response<List<String>> {
        return randomApi.getFullName(
            BuildConfig.API_KEY_RANDOM_TEXT,
            nameType.value,
            quantity
        )
    }

}