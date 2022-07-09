package sql.expr

class Raw(private val s: String) : Expression {
    override fun toDeclaration(): String {
        return s
    }
}