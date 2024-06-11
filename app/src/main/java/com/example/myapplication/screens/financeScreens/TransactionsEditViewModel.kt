

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Prevod
import com.example.myapplication.data.PrevodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch




class TransactionsEditViewModel(
    private val prevodRepository: PrevodRepository,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    var transactionUiState by mutableStateOf(TransactionUiState())
        private set

    private fun validateInput(uiState: TranscactionDetails = transactionUiState.transactionDetails): Boolean {
        return with(uiState) {
            nazov.isNotBlank() && hodnota.isNotBlank() && typ != null
        }
    }


    fun getPrevodById(id: Int): Flow<Prevod?> {
        return prevodRepository.getPrevodStream(id)
    }

    suspend fun updateTransaction() {
        if (validateInput()) {
            prevodRepository.updatePrevod(transactionUiState.transactionDetails.toTransaction())
        }
    }

    fun updateUiState(transactionDetails: TranscactionDetails) {
        transactionUiState =
            TransactionUiState(transactionDetails = transactionDetails, isEntryValid = validateInput(transactionDetails))
    }


    init {
        val prevodId = savedStateHandle.get<Int>("prevodId") ?: throw IllegalArgumentException("prevodId is required")
        viewModelScope.launch {
            transactionUiState = prevodRepository.getPrevodStream(prevodId)
                .filterNotNull()
                .first()
                .toTransactionUiState(true)
        }
        }
    }




