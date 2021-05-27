package life.avishekworld.domain.util

import androidx.core.text.isDigitsOnly
import life.avishekworld.domain.model.Card

interface CardValidator {
    fun isValidCard(card: Card) : Boolean
    fun isValidCard(cardNumber: String) : Boolean
}

@ExperimentalStdlibApi
class CardValidatorImpl(private val verifier : LuhnAlgoVerifier) : CardValidator {
    override fun isValidCard(card: Card): Boolean {
        return isValidCard(card.cardNumber)
    }

    override fun isValidCard(cardNumber: String): Boolean {
        return when {
            cardNumber.length < CARD_NUMBER_MIN_LENGTH || cardNumber.length > CARD_NUMBER_MAX_LENGTH -> false
            !cardNumber.isDigits() -> false
            else -> verifier.isValidCard(cardNumber)
        }
    }

    companion object {
        const val CARD_NUMBER_MIN_LENGTH = 13
        const val CARD_NUMBER_MAX_LENGTH = 19
    }
}

@ExperimentalStdlibApi
class LuhnAlgoVerifier {

    fun isValidCard(cardNumber: String): Boolean {
        val lastDigit = cardNumber[cardNumber.length-1].digitToInt()
        val cardNumberWOLastDigit = cardNumber.substring(0, cardNumber.length - 1)
        val cardNumberWOLastDigitReversed = cardNumberWOLastDigit.reversed()
        val cardNumberArray = cardNumberWOLastDigitReversed.toCharArray().map {
            it.digitToInt()
        }
        val cardNumberOddMultiplierArray = cardNumberArray.mapIndexed { index, num ->
            if ((index + 1) % 2 == 1) num * 2 else num
        }
        val cardNumberSub9Array = cardNumberOddMultiplierArray.map {
            if (it > 9) it - 9 else it
        }
        val cardNumberSum = cardNumberSub9Array.sum()
        val cardNumber10Mod = cardNumberSum % 10
        return (cardNumber10Mod + lastDigit) % 10 == 0
    }

    fun isValidCardShort(cardNumber: String): Boolean {
        val lastDigit = cardNumber[cardNumber.length-1].digitToInt()
        return (((cardNumber
            .substring(0, cardNumber.length - 1)
            .reversed()
            .toCharArray().map {
                it.digitToInt()
            }
            .mapIndexed { index, num ->
                if ((index + 1) % 2 == 1) num * 2 else num
            }.map {
                if (it > 9) it - 9 else it
            }
            .sum()) % 10) + lastDigit) % 10 == 0
    }
}