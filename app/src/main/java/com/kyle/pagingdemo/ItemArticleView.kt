package com.kyle.pagingdemo

import android.widget.TextView
import com.kyle.pagingdemo.paging.PagingVHolder

/**
 * Author   : kyle
 * Date     : 2020/6/15
 * Function : 文章itemview
 */


class ItemArticleView(private val entity: ArticleEntity) :
    PagingItemView<ItemArticleView>(R.layout.item_article) {


    override fun areItemsTheSame(data: ItemArticleView): Boolean {
        return entity.id == data.entity.id
    }

    override fun areContentsTheSame(data: ItemArticleView): Boolean {
        return entity.id == data.entity.id
    }

    override fun onBindView(holder: PagingVHolder, position: Int) {
        super.onBindView(holder, position)
        holder.itemView.findViewById<TextView>(R.id.textview).text = entity.title
    }
}
