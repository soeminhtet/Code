package com.codigo.code.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.codigo.code.R
import java.text.ParseException
import java.text.SimpleDateFormat
import kotlin.math.abs

object BindingAdapter {

    @BindingAdapter(value = ["android:loadImage"])
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        view.load(view.context.resources.getString(R.string.imageURL, url)) {
            crossfade(true)
            placeholder(R.drawable.movie_placeholder)
            transformations(RoundedCornersTransformation(radius = 10f))
            memoryCacheKey(url)
            memoryCachePolicy(CachePolicy.ENABLED)
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter(value = ["android:votePercent"])
    @JvmStatic
    fun votePercent(view: TextView, vote : Double?) {
        if (vote == null){
            view.text = "0%"
            return
        }
        val percentVote = (vote * 10).toInt().toString()
        view.text = "$percentVote%"
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter(value = ["android:voteCount"])
    @JvmStatic
    fun voteCount(view: TextView, voteCount : Int?) {
        if (voteCount == null) {
            view.text = "0"
            return
        }

        val numberString = when {
            abs(voteCount / 1000000) > 1 -> {
                (voteCount / 1000000).toString() + "M"
            }
            abs(voteCount / 1000) > 1 -> {
                (voteCount / 1000).toString() + "K"
            }
            else -> {
                voteCount.toString()
            }
        }
        view.text = numberString
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @BindingAdapter(value = ["android:setFav"])
    @JvmStatic
    fun setFav(view : ImageView, fav : Boolean){
        val context = view.context
        val imageResource = if (fav) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        view.setImageDrawable(context.resources.getDrawable(imageResource,context.theme))
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter(value = ["android:setDetailDate"])
    @JvmStatic
    fun setDetailDate(view : TextView, date: String) {
        val input = SimpleDateFormat("yyyy-MM-dd")
        val output = SimpleDateFormat("MMM dd,yyyy")
        try {
            val tempDate = input.parse(date)
            view.text = output.format(tempDate)
        } catch (e : ParseException){
            view.text = date
        }
    }

    @BindingAdapter(value = ["android:setVoteCount"])
    @JvmStatic
    fun setVoteCount(view : TextView, count : Int?) {
        if(count == null) {
            view.text = "0 vote"
            return
        }
        view.text = if (count > 1) "$count votes" else "$count vote"
    }
}