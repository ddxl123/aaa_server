package com.example.demo.share_generator.client_table_generator

fun clientTableGeneratorRun(kotlinPackageName: String, dartCommonLib: String, dartShareEnumImport: String) {
    ClientTableGenerator.run(
            kotlinRelativeScanPath = "/entity",
            kotlinPackageName = kotlinPackageName,
            dartCloudTableRelativeGenerateRootPath = "/table/cloud",
            dartLibPath = "$dartCommonLib/drift",
            dartShareEnumImport = dartShareEnumImport,
    )
}