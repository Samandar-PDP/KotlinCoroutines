package com.sdk.kotlincoroutines

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sdk.kotlincoroutines.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DataStore")

    companion object {
        val NAME = stringPreferencesKey("name")
        val AGE = intPreferencesKey("age")
    }
    suspend fun saveUser(user: User) {
        context.dataStore.edit {
            it[NAME] = user.name
            it[AGE] = user.age
        }
    }
    fun getUser(): Flow<User?> {
        return context.dataStore.data.map {
            User(
                name = it[NAME] ?: "",
                age = it[AGE] ?: -1
            )
        }
    }
}