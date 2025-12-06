package com.group3.smartaccount.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group3.smartaccount.data.model.Transaction
import com.group3.smartaccount.data.model.TransactionType
import com.group3.smartaccount.data.repository.CurrencyRepository
import com.group3.smartaccount.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TransactionUiState(
    val transactions: List<Transaction> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val balance: Double = 0.0
)

class TransactionViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    private val currencyRepo = CurrencyRepository()

    private val _cadToUsd = MutableStateFlow<Double?>(null)
    val cadToUsd = _cadToUsd.asStateFlow()

    private val _cadToEur = MutableStateFlow<Double?>(null)
    val cadToEur = _cadToEur.asStateFlow()

    private val _cadToGbp = MutableStateFlow<Double?>(null)
    val cadToGbp = _cadToGbp.asStateFlow()

    init {
        observeTransactions()
        loadRates()
    }

    private fun observeTransactions() {
        viewModelScope.launch {
            repository.getAllTransactions().collect { list ->
                val income = list.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
                val expense = list.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }

                _uiState.update {
                    it.copy(
                        transactions = list,
                        totalIncome = income,
                        totalExpense = expense,
                        balance = income - expense
                    )
                }
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch { repository.addTransaction(transaction) }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch { repository.updateTransaction(transaction) }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch { repository.deleteTransaction(transaction) }
    }

    fun loadRates() {
        viewModelScope.launch {
            try {
                _cadToUsd.value = currencyRepo.getCadToUsd()
                _cadToEur.value = currencyRepo.getCadToEur()
                _cadToGbp.value = currencyRepo.getCadToGbp()
            } catch (e: Exception) {
                _cadToUsd.value = null
                _cadToEur.value = null
                _cadToGbp.value = null
            }
        }
    }
}
