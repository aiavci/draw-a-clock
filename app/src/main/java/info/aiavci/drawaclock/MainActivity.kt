package info.aiavci.drawaclock

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFab.setOnClickListener { view ->
            if (resetFab.isShown) {
                // If Options are visible already, save image
                resetFab.hide()

//                showSnackBar(view, "Saving Image")

                setMainFabImage(android.R.drawable.ic_input_add)

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
}
