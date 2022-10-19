package com.testtask.androidassignmentone.view.adapters

import android.content.ClipData
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.testtask.androidassignmentone.R
import com.testtask.androidassignmentone.data.network.model.Sentence
import com.testtask.androidassignmentone.databinding.ItemQuestionsBinding

class QuestionsAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val randomList = ArrayList<Sentence>()
    private val originalList = ArrayList<Sentence>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ItemQuestionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionsVH(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is QuestionsVH) {
            holder.bind(randomList[position])
        }
    }

    override fun getItemCount(): Int {
        return randomList.size
    }

    inner class QuestionsVH(private val itemBinding: ItemQuestionsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(sentence: Sentence) {
            val context = itemView.context
            itemBinding.sentenceTv.text = sentence.sentence
            itemBinding.answerTv.text = sentence.answer

            if (sentence.correctAnswer == true) {
                itemBinding.answerStatusIv.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_check_circle_green_24
                    )
                )
            } else if (sentence.correctAnswer == false) {
                itemBinding.answerStatusIv.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_error_yellow_24
                    )
                )
                itemBinding.answerStatusTwoIv.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_cancel_red_24
                    )
                )
            }

            itemBinding.answerTv.setOnTouchListener { view, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    val data = ClipData.newHtmlText("answer", itemBinding.answerTv.text, "")
                    val shadowBuilder = View.DragShadowBuilder(view)
                    view.startDragAndDrop(data, shadowBuilder, view, 0)
                }
                true
            }

            itemBinding.answerEditText.setOnDragListener { _, event ->
                when (event.action) {
                    DragEvent.ACTION_DRAG_STARTED -> {}
                    DragEvent.ACTION_DROP -> {
                        val item = event.clipData.getItemAt(0)
                        val paste = item.text
                        itemBinding.answerEditText.setText("")
                        itemBinding.answerEditText.clearFocus()
                        itemBinding.answerEditText.append(paste.toString())
                        sentence.userAnswer = paste.toString()
                    }
                    DragEvent.ACTION_DRAG_ENDED -> {}
                    DragEvent.ACTION_DRAG_EXITED -> {}
                    else -> {}
                }
                false
            }
        }
    }

    fun setData(data: List<Sentence>) {
        randomList.clear()
        originalList.clear()
        originalList.addAll(data)
        val tempList = ArrayList<Sentence>()
        val answersList = ArrayList<String>()
        for (item in data) {
            answersList.add(item.answer)
        }
        answersList.reverse()
        answersList.shuffle()
        for (i in data.indices) {
            tempList.add(Sentence(data[i].sentence, answersList[i]))
        }
        randomList.addAll(tempList)
        notifyDataSetChanged()
    }

    fun checkAnswers() {
        for (i in originalList.indices) {
            randomList[i].correctAnswer =
                originalList[i].answer.equals(randomList[i].userAnswer, true)
        }
        notifyDataSetChanged()
    }
}