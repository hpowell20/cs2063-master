package ca.unb.mobiledev.readwritedemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.*

class MainActivity : AppCompatActivity() {
    private var fileNameEditText: EditText? = null
    private var fileDataEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileNameEditText = findViewById(R.id.editFile)
        fileDataEditText = findViewById(R.id.editData)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnView = findViewById<Button>(R.id.btnView)

        btnSave.setOnClickListener {
            val fileName = fileNameEditText!!.text.toString()
            if (TextUtils.isEmpty(fileName)) {
                Toast.makeText(applicationContext,
                    getString(R.string.err_file_name),
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = fileDataEditText!!.text.toString()
            if (TextUtils.isEmpty(data)) {
                Toast.makeText(applicationContext,
                    getString(R.string.err_file_data),
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Write the results to the file
            writeDataToFile(fileName, data)

            // Notification and clean up
            Toast.makeText(applicationContext, getString(R.string.lbl_data_saved), Toast.LENGTH_LONG).show()
            fileNameEditText!!.text.clear()
            fileDataEditText!!.text.clear()
        }

        btnView.setOnClickListener {
            val fileName = fileNameEditText!!.text.toString()
            if (TextUtils.isEmpty(fileName)) {
                Toast.makeText(applicationContext,
                    getString(R.string.err_file_name),
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Read the file details
            val data = readDataFromFile(fileName)

            //Displaying data on EditText
            fileDataEditText!!.setText(data)
        }
    }

    private fun writeDataToFile(fileName: String, fileData: String) {
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write(fileData.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun readDataFromFile(fileName: String): String {
        val fileInputStream = openFileInput(fileName)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()

        var text: String?
            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                stringBuilder.append(text)
            }

        return stringBuilder.toString()
    }
}