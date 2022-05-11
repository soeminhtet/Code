package com.codigo.code.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.codigo.code.R
import java.text.ParseException
import java.text.SimpleDateFormat
import kotlin.math.abs

fun ImageView.loadURL(url : String?,conorRadius : Float = 0f) {
    this.load(this.context.resources.getString(R.string.imageURL, url)) {
        crossfade(true)
        placeholder(R.drawable.movie_placeholder)
        transformations(RoundedCornersTransformation(radius = conorRadius))
        memoryCacheKey(url)
        memoryCachePolicy(CachePolicy.ENABLED)
    }
}

fun TextView.votePercent(vote : Double?) {
    if (vote == null){
        this.text = "0%"
        return
    }
    val percentVote = (vote * 10).toInt().toString()
    this.text = "$percentVote%"
}

fun TextView.voteCount(count : Int?) {
    if(count == null) {
        this.text = "0 vote"
        return
    }
    this.text = if (count > 1) "$count votes" else "$count vote"
}

fun ImageView.setFav(fav: Boolean) {
    val context = this.context
    val imageResource = if (fav) R.drawable.ic_favorite else R.drawable.ic_favorite_border
    this.setImageDrawable(context.resources.getDrawable(imageResource,context.theme))
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("SimpleDateFormat")
fun TextView.setDetailDate(date : String?) {
    val input = SimpleDateFormat("yyyy-MM-dd")
    val output = SimpleDateFormat("MMM dd,yyyy")
    try {
        val tempDate = input.parse(date)
        this.text = output.format(tempDate)
    } catch (e : ParseException){
        this.text = date
    }
}

fun TextView.voteCountFormat(count : Int?) {
    if (count == null) {
        text = "0"
        return
    }

    val numberString = when {
        abs(count / 1000000) > 1 -> {
            (count / 1000000).toString() + "M"
        }
        abs(count / 1000) > 1 -> {
            (count / 1000).toString() + "K"
        }
        else -> {
            count.toString()
        }
    }
    text = numberString
}


