package com.panopset.droid.games.ripon

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.stone_list.*

class EditStoneIntentFactory(
    private val parentActivity: MainActivity,
    private val stoneName: String
) {

    fun editStone() {
        if (parentActivity.stone_detail_container == null) {
            launchEditStoneActivity()
        } else {
            updateEditStoneFragment()
        }
    }

    private fun launchEditStoneActivity() {
        val intent = Intent(parentActivity, EditStoneActivity::class.java)
        intent.putExtra(EditStoneFragment.STONE_KEY, stoneName)
        parentActivity.startActivity(intent)
    }

    private fun updateEditStoneFragment() {
        val fragment = EditStoneFragment().apply {
            arguments = Bundle().apply {
                putString(EditStoneFragment.STONE_KEY, stoneName)
            }
        }
        parentActivity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.stone_detail_container, fragment)
            .commit()
    }
}
