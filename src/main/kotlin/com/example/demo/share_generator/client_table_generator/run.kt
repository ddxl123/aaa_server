package com.example.demo.share_generator.client_table_generator

fun clientTableGeneratorRun(kotlinPackageName: String, dartCommonLib: String) {
    ClientTableGenerator.run(
            kotlinRelativeScanPath = "/entity",
            kotlinPackageName = kotlinPackageName,
            dartCompletePathNoClass = "$dartCommonLib/table/cloud_table",
    )
}