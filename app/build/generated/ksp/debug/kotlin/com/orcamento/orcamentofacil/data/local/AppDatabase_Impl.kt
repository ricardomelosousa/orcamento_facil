package com.orcamento.orcamentofacil.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _budgetDao: Lazy<BudgetDao> = lazy {
    BudgetDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "de00c3010b53165ae8f374d7e4f05f75", "a12e6504f4f101865fde5b3aa30912f1") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `budget_templates` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `startDayOfMonth` INTEGER NOT NULL, `endDayOfMonth` INTEGER NOT NULL, `totalLimit` REAL NOT NULL, `autoRenew` INTEGER NOT NULL, `isActive` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `expense_types` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `templateId` INTEGER NOT NULL, `name` TEXT NOT NULL, FOREIGN KEY(`templateId`) REFERENCES `budget_templates`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_expense_types_templateId` ON `expense_types` (`templateId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `budget_periods` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `templateId` INTEGER NOT NULL, `referenceYear` INTEGER NOT NULL, `referenceMonth` INTEGER NOT NULL, `label` TEXT NOT NULL, `startDate` TEXT NOT NULL, `endDate` TEXT NOT NULL, `totalLimit` REAL NOT NULL, FOREIGN KEY(`templateId`) REFERENCES `budget_templates`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_budget_periods_templateId_referenceYear_referenceMonth` ON `budget_periods` (`templateId`, `referenceYear`, `referenceMonth`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `expense_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `periodId` INTEGER NOT NULL, `typeId` INTEGER NOT NULL, `amount` REAL NOT NULL, `expenseDate` TEXT NOT NULL, `description` TEXT NOT NULL, FOREIGN KEY(`periodId`) REFERENCES `budget_periods`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`typeId`) REFERENCES `expense_types`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_expense_entries_periodId` ON `expense_entries` (`periodId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_expense_entries_typeId` ON `expense_entries` (`typeId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'de00c3010b53165ae8f374d7e4f05f75')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `budget_templates`")
        connection.execSQL("DROP TABLE IF EXISTS `expense_types`")
        connection.execSQL("DROP TABLE IF EXISTS `budget_periods`")
        connection.execSQL("DROP TABLE IF EXISTS `expense_entries`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsBudgetTemplates: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsBudgetTemplates.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetTemplates.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetTemplates.put("startDayOfMonth", TableInfo.Column("startDayOfMonth",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetTemplates.put("endDayOfMonth", TableInfo.Column("endDayOfMonth", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetTemplates.put("totalLimit", TableInfo.Column("totalLimit", "REAL", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetTemplates.put("autoRenew", TableInfo.Column("autoRenew", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetTemplates.put("isActive", TableInfo.Column("isActive", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysBudgetTemplates: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesBudgetTemplates: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoBudgetTemplates: TableInfo = TableInfo("budget_templates", _columnsBudgetTemplates,
            _foreignKeysBudgetTemplates, _indicesBudgetTemplates)
        val _existingBudgetTemplates: TableInfo = read(connection, "budget_templates")
        if (!_infoBudgetTemplates.equals(_existingBudgetTemplates)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |budget_templates(com.orcamento.orcamentofacil.data.local.BudgetTemplateEntity).
              | Expected:
              |""".trimMargin() + _infoBudgetTemplates + """
              |
              | Found:
              |""".trimMargin() + _existingBudgetTemplates)
        }
        val _columnsExpenseTypes: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsExpenseTypes.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExpenseTypes.put("templateId", TableInfo.Column("templateId", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExpenseTypes.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysExpenseTypes: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysExpenseTypes.add(TableInfo.ForeignKey("budget_templates", "CASCADE",
            "NO ACTION", listOf("templateId"), listOf("id")))
        val _indicesExpenseTypes: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesExpenseTypes.add(TableInfo.Index("index_expense_types_templateId", false,
            listOf("templateId"), listOf("ASC")))
        val _infoExpenseTypes: TableInfo = TableInfo("expense_types", _columnsExpenseTypes,
            _foreignKeysExpenseTypes, _indicesExpenseTypes)
        val _existingExpenseTypes: TableInfo = read(connection, "expense_types")
        if (!_infoExpenseTypes.equals(_existingExpenseTypes)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |expense_types(com.orcamento.orcamentofacil.data.local.ExpenseTypeEntity).
              | Expected:
              |""".trimMargin() + _infoExpenseTypes + """
              |
              | Found:
              |""".trimMargin() + _existingExpenseTypes)
        }
        val _columnsBudgetPeriods: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsBudgetPeriods.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetPeriods.put("templateId", TableInfo.Column("templateId", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetPeriods.put("referenceYear", TableInfo.Column("referenceYear", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetPeriods.put("referenceMonth", TableInfo.Column("referenceMonth", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetPeriods.put("label", TableInfo.Column("label", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetPeriods.put("startDate", TableInfo.Column("startDate", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetPeriods.put("endDate", TableInfo.Column("endDate", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBudgetPeriods.put("totalLimit", TableInfo.Column("totalLimit", "REAL", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysBudgetPeriods: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysBudgetPeriods.add(TableInfo.ForeignKey("budget_templates", "CASCADE",
            "NO ACTION", listOf("templateId"), listOf("id")))
        val _indicesBudgetPeriods: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesBudgetPeriods.add(TableInfo.Index("index_budget_periods_templateId_referenceYear_referenceMonth",
            true, listOf("templateId", "referenceYear", "referenceMonth"), listOf("ASC", "ASC",
            "ASC")))
        val _infoBudgetPeriods: TableInfo = TableInfo("budget_periods", _columnsBudgetPeriods,
            _foreignKeysBudgetPeriods, _indicesBudgetPeriods)
        val _existingBudgetPeriods: TableInfo = read(connection, "budget_periods")
        if (!_infoBudgetPeriods.equals(_existingBudgetPeriods)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |budget_periods(com.orcamento.orcamentofacil.data.local.BudgetPeriodEntity).
              | Expected:
              |""".trimMargin() + _infoBudgetPeriods + """
              |
              | Found:
              |""".trimMargin() + _existingBudgetPeriods)
        }
        val _columnsExpenseEntries: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsExpenseEntries.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExpenseEntries.put("periodId", TableInfo.Column("periodId", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExpenseEntries.put("typeId", TableInfo.Column("typeId", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExpenseEntries.put("amount", TableInfo.Column("amount", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExpenseEntries.put("expenseDate", TableInfo.Column("expenseDate", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExpenseEntries.put("description", TableInfo.Column("description", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysExpenseEntries: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysExpenseEntries.add(TableInfo.ForeignKey("budget_periods", "CASCADE",
            "NO ACTION", listOf("periodId"), listOf("id")))
        _foreignKeysExpenseEntries.add(TableInfo.ForeignKey("expense_types", "RESTRICT",
            "NO ACTION", listOf("typeId"), listOf("id")))
        val _indicesExpenseEntries: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesExpenseEntries.add(TableInfo.Index("index_expense_entries_periodId", false,
            listOf("periodId"), listOf("ASC")))
        _indicesExpenseEntries.add(TableInfo.Index("index_expense_entries_typeId", false,
            listOf("typeId"), listOf("ASC")))
        val _infoExpenseEntries: TableInfo = TableInfo("expense_entries", _columnsExpenseEntries,
            _foreignKeysExpenseEntries, _indicesExpenseEntries)
        val _existingExpenseEntries: TableInfo = read(connection, "expense_entries")
        if (!_infoExpenseEntries.equals(_existingExpenseEntries)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |expense_entries(com.orcamento.orcamentofacil.data.local.ExpenseEntryEntity).
              | Expected:
              |""".trimMargin() + _infoExpenseEntries + """
              |
              | Found:
              |""".trimMargin() + _existingExpenseEntries)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "budget_templates",
        "expense_types", "budget_periods", "expense_entries")
  }

  public override fun clearAllTables() {
    super.performClear(true, "budget_templates", "expense_types", "budget_periods",
        "expense_entries")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(BudgetDao::class, BudgetDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun budgetDao(): BudgetDao = _budgetDao.value
}
