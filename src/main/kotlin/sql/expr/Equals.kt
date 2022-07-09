package sql.expr

class Equals(private val a: Expression, private val b: Expression) : Expression {
    override fun toDeclaration(): String {
        return "${a.toReference()}=${b.toReference()}"
    }
}