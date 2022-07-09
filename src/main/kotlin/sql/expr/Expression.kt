package sql.expr

interface Expression {
    fun toDeclaration(): String

    fun toReference(): String {
        return toDeclaration()
    }

    fun build(): String {
        return "${toDeclaration()};"
    }
}