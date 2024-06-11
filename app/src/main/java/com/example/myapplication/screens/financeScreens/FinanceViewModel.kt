import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Prevod
import com.example.myapplication.data.PrevodRepository
import com.example.myapplication.data.TypPrevodu
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


class FinanceViewModel(private val prevodRepository: PrevodRepository) : ViewModel() {

    var transactionUiState by mutableStateOf(TransactionUiState())
        private set
    private fun validateInput(uiState: TranscactionDetails = transactionUiState.transactionDetails): Boolean {
        return with(uiState) {
            nazov.isNotBlank() && hodnota.isNotBlank() && typ != null
        }
    }

    suspend fun saveTransaction() {
        if (validateInput()) {
            prevodRepository.insertPrevod(transactionUiState.transactionDetails.toTransaction())
        }
    }

    fun updateUiState(transactionDetails: TranscactionDetails) {
        transactionUiState =
            TransactionUiState(transactionDetails = transactionDetails, isEntryValid = validateInput(transactionDetails))
    }


    init {
        viewModelScope.launch {
            prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.VYDAJ)
                .combine(prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.PRIJEM)) { vydaje, prijmy ->
                    val celkoveVydaje = vydaje.sumOf { -it.hodnota }
                    val celkovePrijmy = prijmy.sumOf { it.hodnota }
                    transactionUiState = transactionUiState.copy(
                        vydaje = vydaje,
                        celkoveVydaje = celkoveVydaje,
                        prijmy = prijmy,
                        celkovePrijmy = celkovePrijmy
                    )
                }
                .collect()

        }
    }
}

data class TranscactionDetails(
    val id: Int = 0,
    val nazov: String = "",
    val hodnota: String = "",
    val typ: TypPrevodu? = null
)

data class TransactionUiState(
    val transactionDetails: TranscactionDetails = TranscactionDetails(),
    val isEntryValid: Boolean = false,
    val prijmy: List<Prevod> = emptyList(),
    val vydaje: List<Prevod> = emptyList(),
    val celkovePrijmy: Double = 0.0,
    val celkoveVydaje: Double = 0.0
)


fun TranscactionDetails.toTransaction(): Prevod = Prevod(
    id = id,
    nazov = nazov,
    hodnota = hodnota.toDoubleOrNull() ?: 0.0,
    typ = typ ?: TypPrevodu.PRIJEM
)


fun Prevod.toTransactionUiState(isEntryValid: Boolean = false): TransactionUiState = TransactionUiState(
    transactionDetails = this.toTransactionDetails(),
    isEntryValid = isEntryValid
)


fun Prevod.toTransactionDetails(): TranscactionDetails = TranscactionDetails(
    id = id,
    nazov = nazov,
    hodnota = hodnota.toString(),
    typ = typ
)

