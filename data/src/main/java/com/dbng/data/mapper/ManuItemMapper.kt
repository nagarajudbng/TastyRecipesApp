package com.dbng.data.mapper

import com.dbng.data.model.menuresponse.MenuItem


// Created by Nagaraju on 13/11/24.

 fun MenuItem.toDomain(): com.dbng.domain.model.MenuItem {
    return com.dbng.domain.model.MenuItem(
        id?:0,
        name?:"a",
        thumbnailUrl?:"url",
        description?:"desc",
        price?.total,
        1,
        servingsNounSingular?:"a",
        topics.map { it.name }.toString(),
        canonicalId?:"",
        servingsNounSingular?:"a",
        sections.map { it.components.map { it.rawText }.toString() }.toString()
    )
}