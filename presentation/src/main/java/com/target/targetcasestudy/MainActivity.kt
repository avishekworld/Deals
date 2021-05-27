package com.target.targetcasestudy

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.target.targetcasestudy.databinding.ActivityMainBinding
import com.target.targetcasestudy.navigation.NavEvent
import com.target.targetcasestudy.navigation.SingeEvent
import com.target.targetcasestudy.ui.deals.viewmodel.DealVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  private val viewModel : DealVM by viewModel()
  private lateinit var binding : ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    viewModel.navigationEvent.observe(this) {
      handleNavigation(it)
    }
    viewModel.handleEvent(DealVM.DealsEvent.MainViewInit)
  }

  private fun handleNavigation(singleNavEvent: SingeEvent<NavEvent>) {
    singleNavEvent.consume()?.let { navEvent ->
      when (navEvent) {
        is NavEvent.ActivityNavEvent -> {
          startActivity(navEvent.build(this))
        }
        is NavEvent.FragmentNavEvent -> {
          supportFragmentManager.beginTransaction().apply {
            replace(binding.container.id, navEvent.build())
            setReorderingAllowed(true)
            addToBackStack("")
            commit()
          }
        }
        is NavEvent.FragmentDialogNavEvent -> {
          navEvent.build().show(supportFragmentManager, "")
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.credit_card -> {
        viewModel.handleEvent(DealVM.DealsEvent.PaymentViewClicked)
        true
      }
      else -> false
    }
  }
}
