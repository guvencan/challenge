package karpat.guvencan.challenge.data.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Favourite(
    @PrimaryKey val number: Int
)