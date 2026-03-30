package com.orcamento.orcamentofacil

import android.app.Application
import com.orcamento.orcamentofacil.data.local.AppContainer

class OrcamentoFacilApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this, )
    }
}
