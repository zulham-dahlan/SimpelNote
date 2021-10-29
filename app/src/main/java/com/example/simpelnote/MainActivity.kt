package com.example.simpelnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.simpelnote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNew.setOnClickListener(this)
        binding.buttonOpen.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    private fun newFile(){
        binding.edtFile.setText("")
        binding.edtTitle.setText("")
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show()
    }

    private fun showList(){
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items){dialog, item -> loadData(items[item].toString())}
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title : String){
        val fileModel = FileHelper.readFromFile(this, title)
        binding.edtTitle.setText(fileModel.filename)
        binding.edtFile.setText(fileModel.data)
        Toast.makeText(this, "Loading "  + fileModel.filename + " data" , Toast.LENGTH_SHORT).show()
    }

    private fun saveFile(){
        when{
            binding.edtTitle.text.toString().isEmpty() -> Toast.makeText(this, "Judul harus diisi !", Toast.LENGTH_SHORT).show()
            binding.edtFile.text.toString().isEmpty() -> Toast.makeText(this, "Konten harus diisi !", Toast.LENGTH_SHORT).show()
            else -> {
                val title = binding.edtTitle.text.toString()
                val text = binding.edtFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Menyimpan " + fileModel.filename + " file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}