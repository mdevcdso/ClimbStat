package com.example.climbstat.domain.model

enum class DifficultyTier {
    EASY, MEDIUM, HARD;

    companion object {
        fun fromColor(color: String?): DifficultyTier? {
            return when (color?.trim()?.lowercase()) {
                "jaune", "vert" -> EASY
                "bleu", "rouge" -> MEDIUM
                "noir", "violette" -> HARD
                else -> null
            }
        }
    }
}