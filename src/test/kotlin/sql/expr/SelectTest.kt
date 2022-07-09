package sql.expr

import kotlin.test.Test
import kotlin.test.assertEquals


class SelectTest {
    // given
    private val col = Raw("col")
    private val tab = Raw("tab")

    @Test
    fun `test simple select`() {
        // given
        val expected = "select col\nfrom tab;"

        // when
        val actual = Select(col).from(tab).build()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `test select with where`() {
        // given
        val expected = "select col\nfrom tab\nwhere col=value;"
        val value = Raw("value")

        // when
        val actual = Select(col).from(tab).where(Equals(col, value)).build()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `test select with multiple columns`() {
        // given
        val expected = "select col1, col2\nfrom tab;"
        val col1 = Raw("col1")
        val col2 = Raw("col2")

        // when
        val actual = Select(Multi(col1, col2)).from(tab).build()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `test select with alias and where`() {
        // given
        val expected = "select col as c\nfrom tab\nwhere c=value;"
        val c = Alias(col, "c")
        val value = Raw("value")

        // when
        val actual = Select(c).from(tab).where(Equals(c, value)).build()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `test select with basic inner join`() {
        // given
        val expected = "select col\nfrom tab1\ninner join tab2 on tab1.col1=tab2.col2;"
        val tab1 = Raw("tab1")
        val tab2 = Raw("tab2")
        val col1 = Raw("col1")
        val col2 = Raw("col2")

        // when
        val actual = Select(col).from(tab1).join(tab2).on(Equals(Member(tab1, col1), Member(tab2, col2))).build()

        // then
        assertEquals(expected, actual)
    }
}