package com.sparta.week1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.sparta.week1.databinding.ActivityTodoWritingBinding
import com.sparta.week1.model.TodoModel
import java.util.UUID

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
            if (editTextTitle.text.isEmpty() || editTextDescription.text.isEmpty()) {
                Toast.makeText(this@TodoWritingActivity, "모두 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this@TodoWritingActivity, MainActivity::class.java).apply {
                putExtra(
                    EXTRA_TODO_MODEL,
                    TodoModel(
                        UUID.randomUUID().toString(),
                        editTextTitle.text.toString(),
                        editTextDescription.text.toString(),
                        false
                    )
                )
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_TODO_MODEL = "todo_model"
        fun newIntent(context: Context) = Intent(context, TodoWritingActivity::class.java)
    }
}