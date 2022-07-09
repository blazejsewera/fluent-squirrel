package sql.expr

class Select(private val selectExpr: Expression) : Expression {
    private lateinit var fromExpr: Expression
    private var whereExpr: Expression? = null
    private val joined: MutableList<Join> = mutableListOf()

    override fun toDeclaration(): String {
        return "select ${selectExpr.toDeclaration()}\nfrom ${fromExpr.toDeclaration()}" + whereToSql() + joinsToSql()

    }

    private fun whereToSql(): String {
        return if (whereExpr != null) "\nwhere ${whereExpr?.toReference()}" else ""
    }

    private fun joinsToSql(): String {
        return if (joined.size > 0) joined.joinToString(separator = "\n", prefix = "\n") { it.toDeclaration() } else ""
    }

    fun from(table: Expression): Select {
        this.fromExpr = table
        return this
    }

    fun where(e: Expression): Select {
        whereExpr = e
        return this
    }

    fun join(table: Expression): Join {
        return innerJoin(table)
    }

    fun innerJoin(table: Expression): Join {
        val j = Join(this, table, JoinType.INNER_JOIN)
        joined.add(j)
        return j
    }

    fun leftJoin(table: Expression): Join {
        val j = Join(this, table, JoinType.LEFT_JOIN)
        joined.add(j)
        return j
    }

    fun rightJoin(table: Expression): Join {
        val j = Join(this, table, JoinType.RIGHT_JOIN)
        joined.add(j)
        return j
    }

    fun fullOuterJoin(table: Expression): Join {
        val j = Join(this, table, JoinType.FULL_OUTER_JOIN)
        joined.add(j)
        return j
    }
}

class Join(private val s: Select, private val table: Expression, private val joinType: JoinType) :
    Expression {
    private lateinit var onExpr: Expression

    override fun toDeclaration(): String {
        return "${joinType.sql} ${table.toDeclaration()} on ${onExpr.toDeclaration()}"
    }

    fun on(onExpr: Expression): Select {
        this.onExpr = onExpr
        return s
    }
}

enum class JoinType(val sql: String) {
    INNER_JOIN("inner join"),
    LEFT_JOIN("left join"),
    RIGHT_JOIN("right join"),
    FULL_OUTER_JOIN("full outer join")
}
