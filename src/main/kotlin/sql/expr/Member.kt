package sql.expr

class Member(private val table: Expression, private val column: Expression) : Expression {
    override fun toDeclaration(): String {
        return "${table.toDeclaration()}.${column.toDeclaration()}"
    }
}