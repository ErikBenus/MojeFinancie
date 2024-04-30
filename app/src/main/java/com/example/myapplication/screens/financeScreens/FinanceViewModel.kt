import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Prevod
import com.example.myapplication.data.PrevodRepository
import com.example.myapplication.data.TypPrevodu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FinanceViewModel(val prevodRepository: PrevodRepository) : ViewModel() {

    private val _prijmy: MutableStateFlow<List<Prevod>> = MutableStateFlow(emptyList())
    val prijmy: StateFlow<List<Prevod>> = _prijmy

    private val _vydaje: MutableStateFlow<List<Prevod>> = MutableStateFlow(emptyList())
    val vydaje: StateFlow<List<Prevod>> = _vydaje

    private val _celkovePrijmy: MutableStateFlow<Double> = MutableStateFlow(0.0)
    val celkovePrijmy: StateFlow<Double> = _celkovePrijmy

    private val _celkoveVydaje: MutableStateFlow<Double> = MutableStateFlow(0.0)
    val celkoveVydaje: StateFlow<Double> = _celkoveVydaje

    init {
        loadPrijmy()
        loadVydaje()
    }

    private fun loadPrijmy() {
        viewModelScope.launch {
            prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.PRIJEM).collect { prijmy ->
                _prijmy.value = prijmy
                _celkovePrijmy.value = prijmy.sumByDouble { it.hodnota }
            }
        }
    }


    private fun loadVydaje() {
        viewModelScope.launch {
            prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.VYDAJ).collect { vydaje ->
                _vydaje.value = vydaje
                _celkoveVydaje.value = vydaje.sumByDouble { it.hodnota }
            }
        }
    }

}




