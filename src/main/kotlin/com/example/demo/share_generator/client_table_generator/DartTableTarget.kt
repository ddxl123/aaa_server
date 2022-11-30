package com.example.demo.share_generator.client_table_generator

enum class TableType {
    cloud,
    local,
}

class DartTableTarget(
        /**
         * 相对 [ClientTableGenerator.kotlinRelativeScanPath] 的路径。
         */
        val kotlinPath: String,
        val tableName: String,
        val tableType: TableType,
        val dartMemberTargets: ArrayList<DartMemberTarget>,
) {
    override fun toString(): String {
        return "path: $kotlinPath\ntableName: $tableName\ndartCloudMemberTargets: $dartMemberTargets"
    }

    fun toDartSingleTableContent(): String {
        return """
part of drift_db;

@ReferenceTo([])
class $tableName extends ${if (tableType == TableType.cloud) "CloudTableBase" else "LocalTableBase"}  {

  @override
  Set<Column>? get primaryKey => {id};
${
            fun(): String {
                var content = ""
                for (clientMemberTarget in dartMemberTargets) {
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
