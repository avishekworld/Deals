package life.avishekworld.domain.util

import junit.framework.TestCase
import org.junit.runner.RunWith

@ExperimentalStdlibApi
class CardValidatorImplTest : TestCase() {

    private val verifier = LuhnAlgoVerifier()
    private val cardValidator = CardValidatorImpl(verifier)

    fun testIsValidCardTrue() {
        val testCardNumbers = arrayOf(
            //visa
            "4716949832692425",
            "4716458224057346",
            "4539937582990751131",

            //master card
            "5147365013480751",
            "2221007282253958",
            "2720997668794458",

            //amex
            "340201109881782",
            "349628874896119",
            "370578076623062",

            //discover
            "6011925274408956",
            "6011279755569385",
            "6011285640826537367"
        )

        testCardNumbers.forEach {
            assertTrue(cardValidator.isValidCard(it))
        }
    }

    fun testIsValidCardFalse() {
        val testCardNumbers = arrayOf(
            "",
            "471694983269",
            "4716949832692426",
            "5147365013480752",
            "350201109881782",
            "6011925274448956",
            "45399375829907511311"
        )

        testCardNumbers.forEach {
            assertFalse(cardValidator.isValidCard(it))
        }
    }
}