package io.legado.app.data.entities


import io.legado.app.constant.BookType
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonObject


import kotlin.math.max

data class Book(
        var bookUrl: String = "",                   // 详情页Url(本地书源存储完整文件路径)
        var tocUrl: String = "",                    // 目录页Url (toc=table of Contents)
        var origin: String = BookType.local,        // 书源URL(默认BookType.local)
        var originName: String = "",                //书源名称
        var name: String = "",                   // 书籍名称(书源获取)
        var author: String = "",                 // 作者名称(书源获取)
        override var kind: String? = null,                    // 分类信息(书源获取)
        var customTag: String? = null,              // 分类信息(用户修改)
        var coverUrl: String? = null,               // 封面Url(书源获取)
        var customCoverUrl: String? = null,         // 封面Url(用户修改)
        var intro: String? = null,            // 简介内容(书源获取)
//        var customIntro: String? = null,      // 简介内容(用户修改)
//        var charset: String? = null,                // 自定义字符集名称(仅适用于本地书籍)
        var type: Int = 0,                          // @BookType
//        var group: Int = 0,                         // 自定义分组索引号
        var latestChapterTitle: String? = null,     // 最新章节标题
        var latestChapterTime: Long = System.currentTimeMillis(),            // 最新章节标题更新时间
        var lastCheckTime: Long = System.currentTimeMillis(),                // 最近一次更新书籍信息的时间
        var lastCheckCount: Int = 0,                // 最近一次发现新章节的数量
        var totalChapterNum: Int = 0,               // 书籍目录总数
//        var durChapterTitle: String? = null,        // 当前章节名称
//        var durChapterIndex: Int = 0,               // 当前章节索引
//        var durChapterPos: Int = 0,                 // 当前阅读的进度(首行字符的索引位置)
//        var durChapterTime: Long = System.currentTimeMillis(),               // 最近一次阅读书籍的时间(打开正文的时间)
        override var wordCount: String? = null,
//        var canUpdate: Boolean = true,              // 刷新书架时更新书籍信息
//        var order: Int = 0,                         // 手动排序
//        var originOrder: Int = 0,                   //书源排序
        var useReplaceRule: Boolean = true,         // 正文使用净化替换规则
        var variable: String? = null                // 自定义书籍变量信息(用于书源规则检索书籍信息)
) : BaseBook {

    override var variableMap: HashMap<String, String>? = null
        get() {
            if (field == null) {
                field = GSON.fromJsonObject<HashMap<String, String>>(variable) ?: HashMap()
            }
            return field
        }


    override var infoHtml: String? = null

    override var tocHtml: String? = null

    override fun putVariable(key: String, value: String) {
        variableMap?.put(key, value)
        variable = GSON.toJson(variableMap)
    }

    fun toSearchBook(): SearchBook {
        return SearchBook(
                name = name,
                author = author,
                kind = kind,
                bookUrl = bookUrl,
                origin = origin,
                originName = originName,
                type = type,
                wordCount = wordCount,
                latestChapterTitle = latestChapterTitle,
                coverUrl = coverUrl,
                intro = intro,
                tocUrl = tocUrl,
//                originOrder = originOrder,
                variable = variable
        ).apply {
            this.infoHtml = this@Book.infoHtml
            this.tocHtml = this@Book.tocHtml
        }
    }
}