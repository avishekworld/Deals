package com.target.targetcasestudy

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.target.targetcasestudy.ui.deals.DealListFragment
import com.target.targetcasestudy.ui.payment.PaymentDialogFragment
import kotlinx.coroutines.launch
import life.avishekworld.data.api.deals.TargetDealsApi
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction()
      .replace(R.id.container, DealListFragment())
      .commit()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.credit_card -> {
        PaymentDialogFragment().show(supportFragmentManager, "CreditCardValidation")
        true
      }
      else -> false
    }
  }
}
