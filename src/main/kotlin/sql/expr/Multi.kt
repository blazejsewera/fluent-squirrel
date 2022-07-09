package sql.expr

class Multi(private vararg val e: Expression) : Expression {
    override fun toDeclaration(): String {
        return e.joinToString { it.toDeclaration() }
    }
}