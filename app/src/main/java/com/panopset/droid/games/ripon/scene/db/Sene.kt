package com.panopset.droid.games.ripon.scene.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sene_table")
class Sene(
    @PrimaryKey
    @field:NonNull
    @ColumnInfo(name = "name") var name: String
    )