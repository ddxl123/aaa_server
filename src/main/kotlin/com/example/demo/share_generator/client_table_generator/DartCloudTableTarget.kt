package com.example.demo.share_generator.client_table_generator

class DartCloudTableTarget(
        /**
         * 相对 [ClientTableGenerator.kotlinRelativeScanPath] 的路径。
         */
        val kotlinPath: String,
        val tableName: String,
        val dartCloudMemberTargets: ArrayList<DartCloudMemberTarget>,
) {
    override fun toString(): String {
        return "path: $kotlinPath\ntableName: $tableName\ndartCloudMemberTargets: $dartCloudMemberTargets"
    }

    fun toDartSingleTableContent(): String {
        return """
part of drift_db;

@ReferenceTo([])
class $tableName extends CloudTableBase {

  @override
  Set<Column>? get primaryKey => {id};
${
            fun(): String {
                var content = ""
                for (clientMemberTarget in dartCloudMemberTargets) {
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
