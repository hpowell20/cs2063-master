package mobiledev.unb.ca.roompersistencelab.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import mobiledev.unb.ca.roompersistencelab.repository.ItemRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val itemRepository: ItemRepository

    init {
        itemRepository = ItemRepository(application)
    }

    // TODO
    //  Add mapping calls between the UI and Database
}