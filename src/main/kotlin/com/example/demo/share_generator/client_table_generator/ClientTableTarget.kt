package com.example.demo.share_generator.client_table_generator

class ClientTableTarget(
        /**
         * 相对 [ClientTableGenerator.kotlinRelativeScanPath] 的路径。
         */
        val path: String,
        val tableName: String,
        val clientMemberTargets: ArrayList<ClientMemberTarget>,
) {
    override fun toString(): String {
        return "path: $path\ntableName: $tableName\nclientMemberTargets: $clientMemberTargets"
    }

    fun toDartSingleTableContent(): String {
        return """
part of drift_db_table_part;

@ReferenceTo([])
class $tableName extends Table {

  @override
  Set<Column>? get primaryKey => {id};
${
            fun(): String {
                var content = ""
                for (clientMemberTarget in clientMemberTargets) {
                    if (clientMemberTarget.referenceTos.isNotEmpty()) {
                        content += """
  @ReferenceTo([${clientMemberTarget.referenceTos.joinToString(", ").removeSuffix(",")}])"""
                    }
                    content += """
  ${clientMemberTarget.typeTarget.dartDriftColumnType.typeName} get ${clientMemberTarget.name} => ${clientMemberTarget.typeTarget.dartDriftInternalType.typeName}()${
                        if (clientMemberTarget.isNullable) ".nullable()" else ""
                    }();
"""
                }
                return content
            }()
        }
}
        """
    }
}
