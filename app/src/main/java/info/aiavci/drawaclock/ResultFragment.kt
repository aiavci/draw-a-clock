package info.aiavci.drawaclock

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import kotlinx.android.synthetic.main.fragment_result.view.*

/**
 * Fragment displaying loading progress or result
 */
class ResultFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        isCancelable = false

        val title = arguments?.getString("title")
        val description = arguments?.getString("description")
        val isDismissable = arguments?.getBoolean("isDismissable") ?: false
        val isLoading = arguments?.getBoolean("isLoading") ?: false

        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.fragment_result, container, false).apply {
            messageTitle.text = title

            messageDescription.text = description

            buttonSection.visibility = if (isDismissable) View.VISIBLE else View.GONE

            progressLoader.visibility = if (isLoading) View.VISIBLE else View.GONE

            dismissButton.setOnClickListener {
                this@ResultFragment.dismiss()
            }
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}
