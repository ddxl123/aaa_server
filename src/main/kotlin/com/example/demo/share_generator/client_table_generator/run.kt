package com.example.demo.share_generator.client_table_generator

fun clientTableGeneratorRun(kotlinPackageName: String, dartCommonLib: String, dartShareEnumImport: String) {
    ClientTableGenerator.run(
            kotlinRelativeScanPath = "/entity",
            kotlinPackageName = kotlinPackageName,
            dartRelativeGenerateRootPath = "/table/cloud",
            dartLibPath = "$dartCommonLib/drift",
            dartShareEnumImport = dartShareEnumImport,
    )
}