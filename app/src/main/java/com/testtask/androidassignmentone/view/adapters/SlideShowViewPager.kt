package com.testtask.androidassignmentone.view.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.testtask.androidassignmentone.data.models.SlideType
import com.testtask.androidassignmentone.data.network.model.SlideshowModel
import com.testtask.androidassignmentone.databinding.ItemImageSlidesBinding
import com.testtask.androidassignmentone.databinding.ItemQuestionsSlidesBinding
import com.testtask.androidassignmentone.view.interfeces.ConditionViewPager

class SlideShowViewPager(
    private val list: List<SlideshowModel>,
    private val condition: ConditionViewPager
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val rvAdapter = QuestionsAdapter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ItemType.IMAGE_TYPE.ordinal) {
            val itemBinding = ItemImageSlidesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SlideImageShowVH(itemBinding)
        } else {
            val itemBinding = ItemQuestionsSlidesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SlideQuestionsShowVH(itemBinding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].template.equals(SlideType.IMAGE.type, true)) {
            ItemType.IMAGE_TYPE.ordinal
        } else {
            ItemType.QUESTIONS_TYPE.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SlideImageShowVH) {
            holder.bind(list[position])
            condition.condition(position, list.size)
        } else if (holder is SlideQuestionsShowVH) {
            holder.bind(list[position])
            condition.condition(position, list.size)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class SlideImageShowVH(private val itemBinding: ItemImageSlidesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(slideShowUIModel: SlideshowModel) {
            val context = itemView.context

            Glide.with(context)
                .asDrawable()
                .load(slideShowUIModel.image.url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(
                    ContextCompat.getDrawable(
                        context,
                        android.R.drawable.ic_menu_report_image
                    )
                )
                .into(object : CustomTarget<Drawable?>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        itemBinding.slideImageView.setImageDrawable(resource)
                    }
                })
        }
    }

    inner class SlideQuestionsShowVH(private val itemBinding: ItemQuestionsSlidesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(slideShowUIModel: SlideshowModel) {
            itemBinding.questionsRv.adapter = rvAdapter
            rvAdapter.setData(slideShowUIModel.sentences)

            itemBinding.checkAnswersBtn.setOnClickListener {
                rvAdapter.checkAnswers()
            }
        }
    }

    enum class ItemType {
        IMAGE_TYPE, QUESTIONS_TYPE
    }
}