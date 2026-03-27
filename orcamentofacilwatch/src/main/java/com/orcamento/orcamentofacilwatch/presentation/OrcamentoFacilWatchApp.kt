package com.orcamento.orcamentofacilwatch.presentation



import android.app.Application
import com.orcamento.orcamentofacilwatch.presentation.di.WearContainer

class OrcamentoFacilWatchApp : Application() {
    lateinit var container: WearContainer

    override fun onCreate() {
        super.onCreate()
        container = WearContainer(applicationContext)
    }
}