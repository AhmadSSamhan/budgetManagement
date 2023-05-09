package com.android.task.budgetmanagement.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.data.model.AccountModel
import com.android.task.budgetmanagement.domain.usecase.AccountDummyUseCase
import com.android.task.budgetmanagement.domain.usecase.GetAllAccountUseCase
import com.android.task.budgetmanagement.utils.NetworkHelper
import com.android.task.budgetmanagement.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val accountDummyUseCase: AccountDummyUseCase,
    private val repository: GetAllAccountUseCase
) : ViewModel() {

    val allAccounts: LiveData<List<AccountEntity>> = repository.getAllAccount()

    init {
        //TODO you can enable the below fun to fetch data from API
        // fetchAccountByApiService()
    }

    /**
     * This fun connected with real data fetching from API service,
     * so you active it from the init scope.
     */
    private val _users = MutableLiveData<Resource<List<AccountModel>>>()
    val users: LiveData<Resource<List<AccountModel>>>
        get() = _users

    private fun fetchAccountByApiService() {
        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                accountDummyUseCase.execute().let {
                    if (it.isSuccessful) {
                        _users.postValue(Resource.success(it.body()))
                    } else _users.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _users.postValue(Resource.error("No internet connection", null))
        }
    }

}