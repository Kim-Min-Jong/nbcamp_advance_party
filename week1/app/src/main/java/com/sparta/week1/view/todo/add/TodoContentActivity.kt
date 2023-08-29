package com.sparta.week1.view.todo.add

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.sparta.week1.R
import com.sparta.week1.databinding.ActivityTodoWritingBinding
import com.sparta.week1.model.TodoModel
import com.sparta.week1.view.MainActivity
import com.sparta.week1.view.type.TodoContentType
import java.util.UUID

class TodoContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoWritingBinding
    private var model: TodoModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarWriting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
    }

    private fun initViews() = with(binding) {
        val type: TodoContentType
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            type = intent?.getSerializableExtra(EXTRA_TYPE, TodoContentType::class.java) as TodoContentType
            model = intent?.getParcelableExtra(EXTRA_TODO_MODEL, TodoModel::class.java)
        } else {
            type = intent?.getSerializableExtra(EXTRA_TYPE) as TodoContentType
            model = intent?.getParcelableExtra(EXTRA_TODO_MODEL)

        }

        when (type) {
            TodoContentType.ADD -> {
                submitTodo.text = getString(R.string.todo_submit)
            }
            TodoContentType.EDIT -> {
                submitTodo.text = getString(R.string.todo_modify)
                editTextTitle.setText(model?.title.toString())
                editTextDescription.setText(model?.description.toString())
            }
        }
        initButton(type)
    }

    private fun initButton(type: TodoContentType) = with(binding) {
        submitTodo.setOnClickListener {
            if (editTextTitle.text.isEmpty() || editTextDescription.text.isEmpty()) {
                Toast.makeText(this@TodoContentActivity, "모두 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = MainActivity.newIntent(this@TodoContentActivity).apply {
                when(type) {
                    TodoContentType.ADD -> {
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
                    TodoContentType.EDIT -> {
                        val updatedModel = model?.apply {
                            title = editTextTitle.text.toString()
                            description = editTextDescription.text.toString()
                        }
                        putExtra(EXTRA_TODO_MODEL, updatedModel)
                    }
                }
                putExtra(EXTRA_TYPE, type)
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
        const val EXTRA_TYPE = "type"
        fun newIntentForAdd(context: Context) =
            Intent(context, TodoContentActivity::class.java).apply {
                putExtra(EXTRA_TYPE, TodoContentType.ADD)
            }

        fun newIntentForEdit(context: Context, todoModel: TodoModel) =
            Intent(context, TodoContentActivity::class.java).apply {
                putExtra(EXTRA_TODO_MODEL, todoModel)
                putExtra(EXTRA_TYPE, TodoContentType.EDIT)
            }
    }
}