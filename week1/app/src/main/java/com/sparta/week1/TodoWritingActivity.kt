package com.sparta.week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.sparta.week1.databinding.ActivityTodoWritingBinding

class TodoWritingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoWritingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarWriting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initButton()
    }
    private fun initButton() = with(binding) {
        submitTodo.setOnClickListener {
            if(editTextTitle.text.isEmpty() || editTextDescription.text.isEmpty()) {
                Toast.makeText(this@TodoWritingActivity, "모두 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this@TodoWritingActivity, MainActivity::class.java).apply {
                putExtra("title", editTextTitle.text.toString())
                putExtra("desc", editTextDescription.text.toString())
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
//            setResult(RESULT_OK, intent)
            startActivity(intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}