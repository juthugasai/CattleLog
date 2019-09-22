package com.example.cattlelog

import android.content.pm.PackageManager
import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.widget.TextView
import com.example.cattlelog.database.CattlelogDatabase
import java.io.*

private const val PERMISSION_CODE = 1000
const val DESIRED_FILE_NAME_KEY = "DESIRED FILE NAME"
const val DESIRED_FILE_NAME_VALUE = "${CattlelogDatabase.DATABASE_NAME}.db"

class MainActivity : AppCompatActivity() {

    private lateinit var downloadFileIntent: Intent
    private lateinit var databaseStatusTextView: TextView
    private lateinit var dbFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseStatusTextView = findViewById<TextView>(R.id.databaseStatusTextView)
        dbFile = File(filesDir, DESIRED_FILE_NAME_VALUE)

        if (dbFile.exists()) {
            databaseStatusTextView.setText(getString(R.string.already_have_database))
        } else {
            databaseStatusTextView.setText(getString(R.string.dont_have_database_yet))
        }

        downloadButton.setOnClickListener {
            downloadFileIntent = Intent(this@MainActivity, DownloadDatabase::class.java)
            downloadFileIntent.putExtra(DESIRED_FILE_NAME_KEY, DESIRED_FILE_NAME_VALUE)
            startIntentWithPermission(it, downloadFileIntent)
        }
    }

    private fun startIntentWithPermission(view: View, intent: Intent) {
        // Checks if version of Android is >= Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                // request permission if denied
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_CODE)
                // requestPermissions(arrayOf(Manifest.permission_group.STORAGE), PERMISSION_CODE)
            } else {
                // If user allows apps to write to external storage (i.e., we don't need explicit permission)
                startActivity(intent)
            }
        } else {
            // OS is out of date, no permissions are needed
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startActivity(downloadFileIntent)
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
