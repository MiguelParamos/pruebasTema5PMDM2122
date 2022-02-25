package fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.pruebatema5.R

class PreferenciasAlternativasFragment: PreferenceFragmentCompat()  {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val manager: PreferenceManager = preferenceManager
        manager.setSharedPreferencesName(
            "com.example.pruebatema5.PreferenciasDisenioPropio")

        this.setPreferencesFromResource(R.xml.layout_preferencias,rootKey);
    }

}