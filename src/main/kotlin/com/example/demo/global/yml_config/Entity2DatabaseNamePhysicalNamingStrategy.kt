package com.example.demo.global.yml_config

import com.example.demo.tool.toLowercaseLine
import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment

class Entity2DatabaseNamePhysicalNamingStrategy : PhysicalNamingStrategy {
    override fun toPhysicalCatalogName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return apply(name)
    }

    override fun toPhysicalSchemaName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return apply(name)
    }

    override fun toPhysicalTableName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return apply(name)
    }

    override fun toPhysicalSequenceName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return apply(name)
    }

    override fun toPhysicalColumnName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return apply(name)
    }

    private fun apply(name: Identifier?): Identifier? {
        if (name == null) return null
        return Identifier(name.text.toLowercaseLine(), name.isQuoted)
    }
}