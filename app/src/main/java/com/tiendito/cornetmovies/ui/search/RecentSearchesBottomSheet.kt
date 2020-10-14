package com.tiendito.cornetmovies.ui.search

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.cornetmovies.R
import com.tiendito.cornetmovies.ui.MoviesViewModel
import com.tiendito.cornetmovies.ui.adapter.RecentSearchAdapter
import kotlinx.android.synthetic.main.fragment_recent_searches.*


/**
 * Created by Mohamed Salama on 10/14/2020.
 */
class RecentSearchesBottomSheet : BottomSheetDialogFragment() {

    var recentSearchClickCallback: RecentSearchClickCallback? = null

    private val moviesViewModel: MoviesViewModel by activityViewModels()

    private lateinit var recentSearchAdapter: RecentSearchAdapter

    private var root: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recentSearchAdapter = RecentSearchAdapter(
            context,
            OnRecyclerItemClickListener {
                dismiss()
                recentSearchClickCallback?.onRecentSearchSelected(
                    recentSearchAdapter.getItem(it).keyword
                )
            })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener(OnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        })
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (root != null) {
            val parent = root?.parent as ViewGroup
            parent.removeView(root)
        }
        try {
            root = inflater.inflate(R.layout.fragment_recent_searches, container, false)
        } catch (e: InflateException) {
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        handleActions()
    }

    private fun handleActions() {
        edtSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                dismiss()
                recentSearchClickCallback?.onRecentSearchSelected(edtSearch.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun initRecyclerView() {
        recentSearchesRV.adapter = recentSearchAdapter
        moviesViewModel.recentSearchedLiveDate.observe(viewLifecycleOwner, Observer {
            recentSearchAdapter.items = it
        })

    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay
            .getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    interface RecentSearchClickCallback {
        fun onRecentSearchSelected(keyWord: String)
    }
}