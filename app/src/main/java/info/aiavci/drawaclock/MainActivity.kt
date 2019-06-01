package info.aiavci.drawaclock

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var resultFragment = ResultFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFab.setOnClickListener { view ->
            if (resetFab.isShown) {
                // If Options are visible already, save image
                resetFab.hide()

//                showSnackBar(view, "Saving Image")

                setMainFabImage(android.R.drawable.ic_input_add)

                displayDialog()

                extractDrawing()

                return@setOnClickListener
            }

            // Show reset fab and change mainFab icon

            resetFab.show()

            setMainFabImage(android.R.drawable.ic_menu_save)
        }

        resetFab.setOnClickListener { view ->
            // Reset image
            resetFab.hide()

//            showSnackBar(view, "Resetting Board")

            setMainFabImage(android.R.drawable.ic_input_add)

            paintView.clearImage()
        }
    }

    /**
     * Show snackbar
     */
    private fun showSnackBar(view: View, snackbarText: String) {
        Snackbar.make(view, snackbarText, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    /**
     * Set mainFab with given [resourceId]
     */
    private fun setMainFabImage(@DrawableRes resourceId: Int) {
        mainFab.setImageDrawable(ContextCompat.getDrawable(this, resourceId))
    }

    /**
     * Extracts drawing from view
     */
    private fun extractDrawing() {
        resultFragment.dismiss()

        displayDialog(false, isPass = true)
    }

    private fun displayDialog(isLoading: Boolean = true, isPass: Boolean = false) {

        var title = getString(R.string.processing_drawing_title)
        var description = getString(R.string.processing_drawing_description)
        var isDismissable = false

        if (!isLoading && isPass) {
            title = getString(R.string.processing_complete)
            description = getString(R.string.processing_pass)
            isDismissable = true
        } else if (!isLoading) {
            title = getString(R.string.processing_complete)
            description = getString(R.string.processing_fail)
            isDismissable = true
        }

        val bundle = Bundle().apply {
            putString("title", title)

            putString("description", description)

            putBoolean("isDismissable", isDismissable)

            putBoolean("isLoading", isLoading)
        }

        resultFragment = ResultFragment().apply {
            arguments = bundle

            show(supportFragmentManager, "details")
        }
    }
}