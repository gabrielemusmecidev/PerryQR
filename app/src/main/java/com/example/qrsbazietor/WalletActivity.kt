package com.example.qrsbazietor

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text
import androidx.compose.ui.tooling.data.position
import androidx.preference.contains
import androidx.preference.size
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import com.google.android.material.tabs.TabLayout

class WalletActivity : AppCompatActivity() {
    private lateinit var qrCodeImageView: ImageView
    private lateinit var seedEditText: EditText
    private lateinit var savedSeedsSpinner: Spinner
    private lateinit var generateButton: Button
    private lateinit var savedSeedsList: MutableList<String>
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        // Inizializzazione delle Views
        qrCodeImageView = findViewById(R.id.qrCodeImageView)
        seedEditText = findViewById(R.id.seedEditText)
        savedSeedsSpinner = findViewById(R.id.savedSeedsSpinner)
        generateButton = findViewById(R.id.generateButton)

        // Setup SharedPreferences per salvare le seeds
        sharedPreferences = getSharedPreferences(WalletActivity.PREF_NAME, MODE_PRIVATE)
        savedSeedsList = mutableListOf() // Inizializza la lista
        loadSavedSeeds() // Carica le seeds salvate all'avvio

        // Setup Spinner Adapter
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, savedSeedsList)
        savedSeedsSpinner.adapter = spinnerAdapter

        // Listener per il bottone "Genera QR Code"
        generateButton.setOnClickListener { generateQRCode() }

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout) // This is now correct!

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //Gestione selezione scheda
                val selectedTabPosition = tab?.position
                when (selectedTabPosition) {
                    0 -> {
                        //Sceda "Da validare"

                    }

                    1 -> {
                        //Sceda "In uso"
                    }

                    2 -> {
                        //Sceda "Scaduti"
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
        tabLayout.selectTab(tabLayout.getTabAt(0))
    }

    private fun generateQRCode() {
        var subscrSeed = seedEditText.text.toString()
        if (subscrSeed.isEmpty()) {
            subscrSeed = savedSeedsSpinner.selectedItem as? String ?: "" // Ottieni dalla spinner se vuoto
            if (subscrSeed.isEmpty() || savedSeedsSpinner.selectedItemPosition == Spinner.INVALID_POSITION) {
                Toast.makeText(this, "Inserisci o seleziona una Subscr Seed", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Logica Python tradotta in Java
        val formattedTstamp = formattedTimestamp
        val ticketString = subscrSeed + formattedTstamp

        // Genera QR Code
        val qrCodeBitmap = generateQrCodeBitmap(ticketString)
        if (qrCodeBitmap != null) {
            qrCodeImageView.setImageBitmap(qrCodeBitmap)
            saveSeedIfNew(subscrSeed) // Salva la seed se non è già presente
        } else {
            Toast.makeText(this, "Errore nella generazione del QR Code", Toast.LENGTH_SHORT).show()
        }
    }

    private val formattedTimestamp: String
        get() {
            val nowTstamp = ZonedDateTime.now(ZoneOffset.UTC)
            val ticketTstamp = nowTstamp.plusMinutes(1).plusSeconds(10) // Equivalente di timedelta
            val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss") // Equivalente di strftime
            return ticketTstamp.format(formatter)
        }

    private fun generateQrCodeBitmap(data: String): Bitmap? {
        val writer: MultiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512) // Dimensione del QR Code
            val encoder: BarcodeEncoder = BarcodeEncoder()
            return encoder.createBitmap(bitMatrix)
        } catch (e: WriterException) {
            Log.e("QRCodeGenerator", "Errore nella generazione del QR Code", e)
            return null
        }
    }

    private fun loadSavedSeeds() {
        val savedSeedsString = sharedPreferences.getString(WalletActivity.SEEDS_KEY, "")
        if (!savedSeedsString.isNullOrEmpty()) {
            val seedsArray = savedSeedsString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (seed in seedsArray) {
                seed.trim().takeIf { it.isNotEmpty() }?.let { savedSeedsList.add(it) }
            }
        }
    }

    private fun saveSeedIfNew(seed: String) {
        if (!savedSeedsList.contains(seed)) {
            savedSeedsList.add(seed)
            spinnerAdapter.notifyDataSetChanged() // Aggiorna lo Spinner
            saveSeedsList() // Salva la lista aggiornata nelle SharedPreferences
        }
    }

    private fun saveSeedsList() {
        val editor = sharedPreferences.edit()
        val seedsString = StringBuilder()
        for (i in savedSeedsList.indices) {
            seedsString.append(savedSeedsList[i])
            if (i < savedSeedsList.size - 1) {
                seedsString.append(",") // Usa la virgola come separatore
            }
        }
        editor.putString(WalletActivity.SEEDS_KEY, seedsString.toString())
        editor.apply() // Usa apply() per operazioni asincrone
    }

    companion object {
        const val PREF_NAME = "SavedSeeds"
        const val SEEDS_KEY = "seeds"
    }

    //Listener per TabLayout


}