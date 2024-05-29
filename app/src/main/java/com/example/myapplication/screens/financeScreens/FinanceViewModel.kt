import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.Prevod
import com.example.myapplication.data.PrevodRepository
import com.example.myapplication.data.TypPrevodu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.NumberFormat



class FinanceViewModel(val prevodRepository: PrevodRepository) : ViewModel() {

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
    // Možno sa zíde - slúži na checkbox
    fun onTypeSelected(typPrevodu: TypPrevodu) {
        transactionUiState = transactionUiState.copy(
            transactionDetails = transactionUiState.transactionDetails.copy(typ = typPrevodu)
        )
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

data class TranscactionDetails( //ItemDetails
    val id: Int = 0,
    val nazov: String = "",
    val hodnota: String = "",
    val typ: TypPrevodu? = null
)

data class TransactionUiState( //ItemUiState
    val transactionDetails: TranscactionDetails = TranscactionDetails(),
    val isEntryValid: Boolean = false,
    val prijmy: List<Prevod> = emptyList(),
    val vydaje: List<Prevod> = emptyList(),
    val celkovePrijmy: Double = 0.0,
    val celkoveVydaje: Double = 0.0
)

/**
 * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun TranscactionDetails.toTransaction(): Prevod = Prevod(
    id = id,
    nazov = nazov,
    hodnota = hodnota.toDoubleOrNull() ?: 0.0,
    typ = typ ?: TypPrevodu.PRIJEM
)

/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun Prevod.toTransactionUiState(isEntryValid: Boolean = false): TransactionUiState = TransactionUiState( // Prevod.toItemUiState
    transactionDetails = this.toTransactionDetails(),        //itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun Prevod.toTransactionDetails(): TranscactionDetails = TranscactionDetails(
    id = id,
    nazov = nazov,
    hodnota = hodnota.toString(),
    typ = typ
)

