package sql.expr

class Alias(private val e: Expression, private val alias: String) : Expression {
    override fun toDeclaration(): String {
        return "${e.toReference()} as $alias"
    }

    override fun toReference(): String {
        return alias
    }
}