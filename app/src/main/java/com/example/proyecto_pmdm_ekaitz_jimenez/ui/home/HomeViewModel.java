package com.example.proyecto_pmdm_ekaitz_jimenez.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel: fragmento Home
 * almacenar y gestionar los datos con la UI del fragmento
 *
 * ViewModel mantiene el estado del texto a mostrar en la UI
 * permite que el fragmento observe los cambios en este estado
 */
public class HomeViewModel extends ViewModel {

    // Variable privada MutableLiveData para almacenar el texto
    private final MutableLiveData<String> mText;

    /**
     * Constructor del ViewModel
     * Inicializar el MutableLiveData con un valor vacío
     */
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    /**
     * Método para obtener el LiveData del texto
     * @return Livedata que contiene el texto a mostrar.
     */
    public LiveData<String> getText() {
        return mText;
    }
}
