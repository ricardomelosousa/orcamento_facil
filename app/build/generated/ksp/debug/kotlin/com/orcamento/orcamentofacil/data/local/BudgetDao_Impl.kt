package com.orcamento.orcamentofacil.`data`.local

import androidx.collection.LongSparseArray
import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndex
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.room.util.recursiveFetchLongSparseArray
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlin.text.StringBuilder
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class BudgetDao_Impl(
  __db: RoomDatabase,
) : BudgetDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfBudgetTemplateEntity: EntityInsertAdapter<BudgetTemplateEntity>

  private val __insertAdapterOfExpenseTypeEntity: EntityInsertAdapter<ExpenseTypeEntity>

  private val __insertAdapterOfBudgetPeriodEntity: EntityInsertAdapter<BudgetPeriodEntity>

  private val __insertAdapterOfBudgetPeriodEntity_1: EntityInsertAdapter<BudgetPeriodEntity>

  private val __insertAdapterOfExpenseEntryEntity: EntityInsertAdapter<ExpenseEntryEntity>

  private val __updateAdapterOfBudgetTemplateEntity:
      EntityDeleteOrUpdateAdapter<BudgetTemplateEntity>

  private val __updateAdapterOfBudgetPeriodEntity: EntityDeleteOrUpdateAdapter<BudgetPeriodEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfBudgetTemplateEntity = object :
        EntityInsertAdapter<BudgetTemplateEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `budget_templates` (`id`,`name`,`startDayOfMonth`,`endDayOfMonth`,`totalLimit`,`autoRenew`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: BudgetTemplateEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindLong(3, entity.startDayOfMonth.toLong())
        statement.bindLong(4, entity.endDayOfMonth.toLong())
        statement.bindDouble(5, entity.totalLimit)
        val _tmp: Int = if (entity.autoRenew) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        val _tmp_1: Int = if (entity.isActive) 1 else 0
        statement.bindLong(7, _tmp_1.toLong())
      }
    }
    this.__insertAdapterOfExpenseTypeEntity = object : EntityInsertAdapter<ExpenseTypeEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `expense_types` (`id`,`templateId`,`name`) VALUES (nullif(?, 0),?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ExpenseTypeEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.templateId)
        statement.bindText(3, entity.name)
      }
    }
    this.__insertAdapterOfBudgetPeriodEntity = object : EntityInsertAdapter<BudgetPeriodEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `budget_periods` (`id`,`templateId`,`referenceYear`,`referenceMonth`,`label`,`startDate`,`endDate`,`totalLimit`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: BudgetPeriodEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.templateId)
        statement.bindLong(3, entity.referenceYear.toLong())
        statement.bindLong(4, entity.referenceMonth.toLong())
        statement.bindText(5, entity.label)
        statement.bindText(6, entity.startDate)
        statement.bindText(7, entity.endDate)
        statement.bindDouble(8, entity.totalLimit)
      }
    }
    this.__insertAdapterOfBudgetPeriodEntity_1 = object : EntityInsertAdapter<BudgetPeriodEntity>()
        {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `budget_periods` (`id`,`templateId`,`referenceYear`,`referenceMonth`,`label`,`startDate`,`endDate`,`totalLimit`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: BudgetPeriodEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.templateId)
        statement.bindLong(3, entity.referenceYear.toLong())
        statement.bindLong(4, entity.referenceMonth.toLong())
        statement.bindText(5, entity.label)
        statement.bindText(6, entity.startDate)
        statement.bindText(7, entity.endDate)
        statement.bindDouble(8, entity.totalLimit)
      }
    }
    this.__insertAdapterOfExpenseEntryEntity = object : EntityInsertAdapter<ExpenseEntryEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `expense_entries` (`id`,`periodId`,`typeId`,`amount`,`expenseDate`,`description`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ExpenseEntryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.periodId)
        statement.bindLong(3, entity.typeId)
        statement.bindDouble(4, entity.amount)
        statement.bindText(5, entity.expenseDate)
        statement.bindText(6, entity.description)
      }
    }
    this.__updateAdapterOfBudgetTemplateEntity = object :
        EntityDeleteOrUpdateAdapter<BudgetTemplateEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `budget_templates` SET `id` = ?,`name` = ?,`startDayOfMonth` = ?,`endDayOfMonth` = ?,`totalLimit` = ?,`autoRenew` = ?,`isActive` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: BudgetTemplateEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindLong(3, entity.startDayOfMonth.toLong())
        statement.bindLong(4, entity.endDayOfMonth.toLong())
        statement.bindDouble(5, entity.totalLimit)
        val _tmp: Int = if (entity.autoRenew) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        val _tmp_1: Int = if (entity.isActive) 1 else 0
        statement.bindLong(7, _tmp_1.toLong())
        statement.bindLong(8, entity.id)
      }
    }
    this.__updateAdapterOfBudgetPeriodEntity = object :
        EntityDeleteOrUpdateAdapter<BudgetPeriodEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `budget_periods` SET `id` = ?,`templateId` = ?,`referenceYear` = ?,`referenceMonth` = ?,`label` = ?,`startDate` = ?,`endDate` = ?,`totalLimit` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: BudgetPeriodEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.templateId)
        statement.bindLong(3, entity.referenceYear.toLong())
        statement.bindLong(4, entity.referenceMonth.toLong())
        statement.bindText(5, entity.label)
        statement.bindText(6, entity.startDate)
        statement.bindText(7, entity.endDate)
        statement.bindDouble(8, entity.totalLimit)
        statement.bindLong(9, entity.id)
      }
    }
  }

  public override suspend fun insertTemplate(template: BudgetTemplateEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfBudgetTemplateEntity.insertAndReturnId(_connection,
        template)
    _result
  }

  public override suspend fun insertTypes(types: List<ExpenseTypeEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfExpenseTypeEntity.insert(_connection, types)
  }

  public override suspend fun insertOrReplacePeriod(period: BudgetPeriodEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfBudgetPeriodEntity.insertAndReturnId(_connection, period)
    _result
  }

  public override suspend fun insertPeriod(entity: BudgetPeriodEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfBudgetPeriodEntity_1.insertAndReturnId(_connection, entity)
    _result
  }

  public override suspend fun insertExpense(entry: ExpenseEntryEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfExpenseEntryEntity.insertAndReturnId(_connection, entry)
    _result
  }

  public override suspend fun updateTemplate(template: BudgetTemplateEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfBudgetTemplateEntity.handle(_connection, template)
  }

  public override suspend fun updatePeriod(entity: BudgetPeriodEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfBudgetPeriodEntity.handle(_connection, entity)
  }

  public override fun observeTemplates(): Flow<List<TemplateWithTypes>> {
    val _sql: String = "SELECT * FROM budget_templates WHERE isActive = 1 ORDER BY name"
    return createFlow(__db, true, arrayOf("expense_types", "budget_templates")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfStartDayOfMonth: Int = getColumnIndexOrThrow(_stmt, "startDayOfMonth")
        val _columnIndexOfEndDayOfMonth: Int = getColumnIndexOrThrow(_stmt, "endDayOfMonth")
        val _columnIndexOfTotalLimit: Int = getColumnIndexOrThrow(_stmt, "totalLimit")
        val _columnIndexOfAutoRenew: Int = getColumnIndexOrThrow(_stmt, "autoRenew")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _collectionTypes: LongSparseArray<MutableList<ExpenseTypeEntity>> =
            LongSparseArray<MutableList<ExpenseTypeEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionTypes.containsKey(_tmpKey)) {
            _collectionTypes.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshipexpenseTypesAscomOrcamentoOrcamentofacilDataLocalExpenseTypeEntity(_connection,
            _collectionTypes)
        val _result: MutableList<TemplateWithTypes> = mutableListOf()
        while (_stmt.step()) {
          val _item: TemplateWithTypes
          val _tmpTemplate: BudgetTemplateEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpStartDayOfMonth: Int
          _tmpStartDayOfMonth = _stmt.getLong(_columnIndexOfStartDayOfMonth).toInt()
          val _tmpEndDayOfMonth: Int
          _tmpEndDayOfMonth = _stmt.getLong(_columnIndexOfEndDayOfMonth).toInt()
          val _tmpTotalLimit: Double
          _tmpTotalLimit = _stmt.getDouble(_columnIndexOfTotalLimit)
          val _tmpAutoRenew: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfAutoRenew).toInt()
          _tmpAutoRenew = _tmp != 0
          val _tmpIsActive: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_1 != 0
          _tmpTemplate =
              BudgetTemplateEntity(_tmpId,_tmpName,_tmpStartDayOfMonth,_tmpEndDayOfMonth,_tmpTotalLimit,_tmpAutoRenew,_tmpIsActive)
          val _tmpTypesCollection: MutableList<ExpenseTypeEntity>
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          _tmpTypesCollection = checkNotNull(_collectionTypes.get(_tmpKey_1))
          _item = TemplateWithTypes(_tmpTemplate,_tmpTypesCollection)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTemplate(templateId: Long): TemplateWithTypes? {
    val _sql: String = "SELECT * FROM budget_templates WHERE id = ?"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, templateId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfStartDayOfMonth: Int = getColumnIndexOrThrow(_stmt, "startDayOfMonth")
        val _columnIndexOfEndDayOfMonth: Int = getColumnIndexOrThrow(_stmt, "endDayOfMonth")
        val _columnIndexOfTotalLimit: Int = getColumnIndexOrThrow(_stmt, "totalLimit")
        val _columnIndexOfAutoRenew: Int = getColumnIndexOrThrow(_stmt, "autoRenew")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _collectionTypes: LongSparseArray<MutableList<ExpenseTypeEntity>> =
            LongSparseArray<MutableList<ExpenseTypeEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionTypes.containsKey(_tmpKey)) {
            _collectionTypes.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshipexpenseTypesAscomOrcamentoOrcamentofacilDataLocalExpenseTypeEntity(_connection,
            _collectionTypes)
        val _result: TemplateWithTypes?
        if (_stmt.step()) {
          val _tmpTemplate: BudgetTemplateEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpStartDayOfMonth: Int
          _tmpStartDayOfMonth = _stmt.getLong(_columnIndexOfStartDayOfMonth).toInt()
          val _tmpEndDayOfMonth: Int
          _tmpEndDayOfMonth = _stmt.getLong(_columnIndexOfEndDayOfMonth).toInt()
          val _tmpTotalLimit: Double
          _tmpTotalLimit = _stmt.getDouble(_columnIndexOfTotalLimit)
          val _tmpAutoRenew: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfAutoRenew).toInt()
          _tmpAutoRenew = _tmp != 0
          val _tmpIsActive: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_1 != 0
          _tmpTemplate =
              BudgetTemplateEntity(_tmpId,_tmpName,_tmpStartDayOfMonth,_tmpEndDayOfMonth,_tmpTotalLimit,_tmpAutoRenew,_tmpIsActive)
          val _tmpTypesCollection: MutableList<ExpenseTypeEntity>
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          _tmpTypesCollection = checkNotNull(_collectionTypes.get(_tmpKey_1))
          _result = TemplateWithTypes(_tmpTemplate,_tmpTypesCollection)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observePeriods(): Flow<List<BudgetPeriodEntity>> {
    val _sql: String = "SELECT * FROM budget_periods ORDER BY startDate DESC"
    return createFlow(__db, true, arrayOf("budget_periods")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateId: Int = getColumnIndexOrThrow(_stmt, "templateId")
        val _columnIndexOfReferenceYear: Int = getColumnIndexOrThrow(_stmt, "referenceYear")
        val _columnIndexOfReferenceMonth: Int = getColumnIndexOrThrow(_stmt, "referenceMonth")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfTotalLimit: Int = getColumnIndexOrThrow(_stmt, "totalLimit")
        val _result: MutableList<BudgetPeriodEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: BudgetPeriodEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateId: Long
          _tmpTemplateId = _stmt.getLong(_columnIndexOfTemplateId)
          val _tmpReferenceYear: Int
          _tmpReferenceYear = _stmt.getLong(_columnIndexOfReferenceYear).toInt()
          val _tmpReferenceMonth: Int
          _tmpReferenceMonth = _stmt.getLong(_columnIndexOfReferenceMonth).toInt()
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpTotalLimit: Double
          _tmpTotalLimit = _stmt.getDouble(_columnIndexOfTotalLimit)
          _item =
              BudgetPeriodEntity(_tmpId,_tmpTemplateId,_tmpReferenceYear,_tmpReferenceMonth,_tmpLabel,_tmpStartDate,_tmpEndDate,_tmpTotalLimit)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observePeriodDetails(periodId: Long): Flow<PeriodWithExpenses?> {
    val _sql: String = "SELECT * FROM budget_periods WHERE id = ?"
    return createFlow(__db, true, arrayOf("expense_types", "expense_entries", "budget_periods")) {
        _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, periodId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateId: Int = getColumnIndexOrThrow(_stmt, "templateId")
        val _columnIndexOfReferenceYear: Int = getColumnIndexOrThrow(_stmt, "referenceYear")
        val _columnIndexOfReferenceMonth: Int = getColumnIndexOrThrow(_stmt, "referenceMonth")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfTotalLimit: Int = getColumnIndexOrThrow(_stmt, "totalLimit")
        val _collectionExpenses: LongSparseArray<MutableList<ExpenseEntryWithType>> =
            LongSparseArray<MutableList<ExpenseEntryWithType>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionExpenses.containsKey(_tmpKey)) {
            _collectionExpenses.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshipexpenseEntriesAscomOrcamentoOrcamentofacilDataLocalExpenseEntryWithType(_connection,
            _collectionExpenses)
        val _result: PeriodWithExpenses?
        if (_stmt.step()) {
          val _tmpPeriod: BudgetPeriodEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateId: Long
          _tmpTemplateId = _stmt.getLong(_columnIndexOfTemplateId)
          val _tmpReferenceYear: Int
          _tmpReferenceYear = _stmt.getLong(_columnIndexOfReferenceYear).toInt()
          val _tmpReferenceMonth: Int
          _tmpReferenceMonth = _stmt.getLong(_columnIndexOfReferenceMonth).toInt()
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpTotalLimit: Double
          _tmpTotalLimit = _stmt.getDouble(_columnIndexOfTotalLimit)
          _tmpPeriod =
              BudgetPeriodEntity(_tmpId,_tmpTemplateId,_tmpReferenceYear,_tmpReferenceMonth,_tmpLabel,_tmpStartDate,_tmpEndDate,_tmpTotalLimit)
          val _tmpExpensesCollection: MutableList<ExpenseEntryWithType>
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          _tmpExpensesCollection = checkNotNull(_collectionExpenses.get(_tmpKey_1))
          _result = PeriodWithExpenses(_tmpPeriod,_tmpExpensesCollection)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun findPeriodByReference(
    templateId: Long,
    year: Int,
    month: Int,
  ): BudgetPeriodEntity? {
    val _sql: String =
        "SELECT * FROM budget_periods WHERE templateId = ? AND referenceYear = ? AND referenceMonth = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, templateId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, year.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, month.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateId: Int = getColumnIndexOrThrow(_stmt, "templateId")
        val _columnIndexOfReferenceYear: Int = getColumnIndexOrThrow(_stmt, "referenceYear")
        val _columnIndexOfReferenceMonth: Int = getColumnIndexOrThrow(_stmt, "referenceMonth")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfTotalLimit: Int = getColumnIndexOrThrow(_stmt, "totalLimit")
        val _result: BudgetPeriodEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateId: Long
          _tmpTemplateId = _stmt.getLong(_columnIndexOfTemplateId)
          val _tmpReferenceYear: Int
          _tmpReferenceYear = _stmt.getLong(_columnIndexOfReferenceYear).toInt()
          val _tmpReferenceMonth: Int
          _tmpReferenceMonth = _stmt.getLong(_columnIndexOfReferenceMonth).toInt()
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpTotalLimit: Double
          _tmpTotalLimit = _stmt.getDouble(_columnIndexOfTotalLimit)
          _result =
              BudgetPeriodEntity(_tmpId,_tmpTemplateId,_tmpReferenceYear,_tmpReferenceMonth,_tmpLabel,_tmpStartDate,_tmpEndDate,_tmpTotalLimit)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getExpenseTypeById(typeId: Long): ExpenseTypeEntity? {
    val _sql: String = "SELECT * FROM expense_types WHERE id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, typeId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateId: Int = getColumnIndexOrThrow(_stmt, "templateId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _result: ExpenseTypeEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateId: Long
          _tmpTemplateId = _stmt.getLong(_columnIndexOfTemplateId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          _result = ExpenseTypeEntity(_tmpId,_tmpTemplateId,_tmpName)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observePeriodSpent(periodId: Long): Flow<Double> {
    val _sql: String = "SELECT COALESCE(SUM(amount), 0) FROM expense_entries WHERE periodId = ?"
    return createFlow(__db, false, arrayOf("expense_entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, periodId)
        val _result: Double
        if (_stmt.step()) {
          val _tmp: Double
          _tmp = _stmt.getDouble(0)
          _result = _tmp
        } else {
          _result = 0.0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPeriodsContainingDate(date: String): List<BudgetPeriodEntity> {
    val _sql: String = "SELECT * FROM budget_periods WHERE startDate <= ? AND endDate >= ?"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, date)
        _argIndex = 2
        _stmt.bindText(_argIndex, date)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateId: Int = getColumnIndexOrThrow(_stmt, "templateId")
        val _columnIndexOfReferenceYear: Int = getColumnIndexOrThrow(_stmt, "referenceYear")
        val _columnIndexOfReferenceMonth: Int = getColumnIndexOrThrow(_stmt, "referenceMonth")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfTotalLimit: Int = getColumnIndexOrThrow(_stmt, "totalLimit")
        val _result: MutableList<BudgetPeriodEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: BudgetPeriodEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateId: Long
          _tmpTemplateId = _stmt.getLong(_columnIndexOfTemplateId)
          val _tmpReferenceYear: Int
          _tmpReferenceYear = _stmt.getLong(_columnIndexOfReferenceYear).toInt()
          val _tmpReferenceMonth: Int
          _tmpReferenceMonth = _stmt.getLong(_columnIndexOfReferenceMonth).toInt()
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpTotalLimit: Double
          _tmpTotalLimit = _stmt.getDouble(_columnIndexOfTotalLimit)
          _item =
              BudgetPeriodEntity(_tmpId,_tmpTemplateId,_tmpReferenceYear,_tmpReferenceMonth,_tmpLabel,_tmpStartDate,_tmpEndDate,_tmpTotalLimit)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observePeriodsByTemplate(templateId: Long): Flow<List<BudgetPeriodEntity>> {
    val _sql: String = "SELECT * FROM budget_periods WHERE templateId = ? ORDER BY startDate DESC"
    return createFlow(__db, true, arrayOf("budget_periods")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, templateId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTemplateId: Int = getColumnIndexOrThrow(_stmt, "templateId")
        val _columnIndexOfReferenceYear: Int = getColumnIndexOrThrow(_stmt, "referenceYear")
        val _columnIndexOfReferenceMonth: Int = getColumnIndexOrThrow(_stmt, "referenceMonth")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfTotalLimit: Int = getColumnIndexOrThrow(_stmt, "totalLimit")
        val _result: MutableList<BudgetPeriodEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: BudgetPeriodEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateId: Long
          _tmpTemplateId = _stmt.getLong(_columnIndexOfTemplateId)
          val _tmpReferenceYear: Int
          _tmpReferenceYear = _stmt.getLong(_columnIndexOfReferenceYear).toInt()
          val _tmpReferenceMonth: Int
          _tmpReferenceMonth = _stmt.getLong(_columnIndexOfReferenceMonth).toInt()
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpTotalLimit: Double
          _tmpTotalLimit = _stmt.getDouble(_columnIndexOfTotalLimit)
          _item =
              BudgetPeriodEntity(_tmpId,_tmpTemplateId,_tmpReferenceYear,_tmpReferenceMonth,_tmpLabel,_tmpStartDate,_tmpEndDate,_tmpTotalLimit)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteTypesByTemplate(templateId: Long) {
    val _sql: String = "DELETE FROM expense_types WHERE templateId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, templateId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  private
      fun __fetchRelationshipexpenseTypesAscomOrcamentoOrcamentofacilDataLocalExpenseTypeEntity(_connection: SQLiteConnection,
      _map: LongSparseArray<MutableList<ExpenseTypeEntity>>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, true) { _tmpMap ->
        __fetchRelationshipexpenseTypesAscomOrcamentoOrcamentofacilDataLocalExpenseTypeEntity(_connection,
            _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`templateId`,`name` FROM `expense_types` WHERE `templateId` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "templateId")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfTemplateId: Int = 1
      val _columnIndexOfName: Int = 2
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        val _tmpRelation: MutableList<ExpenseTypeEntity>? = _map.get(_tmpKey)
        if (_tmpRelation != null) {
          val _item_1: ExpenseTypeEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateId: Long
          _tmpTemplateId = _stmt.getLong(_columnIndexOfTemplateId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          _item_1 = ExpenseTypeEntity(_tmpId,_tmpTemplateId,_tmpName)
          _tmpRelation.add(_item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  private
      fun __fetchRelationshipexpenseTypesAscomOrcamentoOrcamentofacilDataLocalExpenseTypeEntity_1(_connection: SQLiteConnection,
      _map: LongSparseArray<ExpenseTypeEntity?>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, false) { _tmpMap ->
        __fetchRelationshipexpenseTypesAscomOrcamentoOrcamentofacilDataLocalExpenseTypeEntity_1(_connection,
            _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`templateId`,`name` FROM `expense_types` WHERE `id` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "id")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfTemplateId: Int = 1
      val _columnIndexOfName: Int = 2
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        if (_map.containsKey(_tmpKey)) {
          val _item_1: ExpenseTypeEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTemplateId: Long
          _tmpTemplateId = _stmt.getLong(_columnIndexOfTemplateId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          _item_1 = ExpenseTypeEntity(_tmpId,_tmpTemplateId,_tmpName)
          _map.put(_tmpKey, _item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  private
      fun __fetchRelationshipexpenseEntriesAscomOrcamentoOrcamentofacilDataLocalExpenseEntryWithType(_connection: SQLiteConnection,
      _map: LongSparseArray<MutableList<ExpenseEntryWithType>>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, true) { _tmpMap ->
        __fetchRelationshipexpenseEntriesAscomOrcamentoOrcamentofacilDataLocalExpenseEntryWithType(_connection,
            _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`periodId`,`typeId`,`amount`,`expenseDate`,`description` FROM `expense_entries` WHERE `periodId` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "periodId")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfPeriodId: Int = 1
      val _columnIndexOfTypeId: Int = 2
      val _columnIndexOfAmount: Int = 3
      val _columnIndexOfExpenseDate: Int = 4
      val _columnIndexOfDescription: Int = 5
      val _collectionType: LongSparseArray<ExpenseTypeEntity?> =
          LongSparseArray<ExpenseTypeEntity?>()
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_columnIndexOfTypeId)
        _collectionType.put(_tmpKey, null)
      }
      _stmt.reset()
      __fetchRelationshipexpenseTypesAscomOrcamentoOrcamentofacilDataLocalExpenseTypeEntity_1(_connection,
          _collectionType)
      while (_stmt.step()) {
        val _tmpKey_1: Long
        _tmpKey_1 = _stmt.getLong(_itemKeyIndex)
        val _tmpRelation: MutableList<ExpenseEntryWithType>? = _map.get(_tmpKey_1)
        if (_tmpRelation != null) {
          val _item_1: ExpenseEntryWithType
          val _tmpEntry: ExpenseEntryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPeriodId: Long
          _tmpPeriodId = _stmt.getLong(_columnIndexOfPeriodId)
          val _tmpTypeId: Long
          _tmpTypeId = _stmt.getLong(_columnIndexOfTypeId)
          val _tmpAmount: Double
          _tmpAmount = _stmt.getDouble(_columnIndexOfAmount)
          val _tmpExpenseDate: String
          _tmpExpenseDate = _stmt.getText(_columnIndexOfExpenseDate)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          _tmpEntry =
              ExpenseEntryEntity(_tmpId,_tmpPeriodId,_tmpTypeId,_tmpAmount,_tmpExpenseDate,_tmpDescription)
          val _tmpType: ExpenseTypeEntity?
          val _tmpKey_2: Long
          _tmpKey_2 = _stmt.getLong(_columnIndexOfTypeId)
          _tmpType = _collectionType.get(_tmpKey_2)
          if (_tmpType == null) {
            error("Relationship item 'type' was expected to be NON-NULL but is NULL in @Relation involving a parent column named 'typeId' and entityColumn named 'id'.")
          }
          _item_1 = ExpenseEntryWithType(_tmpEntry,_tmpType)
          _tmpRelation.add(_item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
