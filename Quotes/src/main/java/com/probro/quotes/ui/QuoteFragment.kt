package com.probro.quotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.probro.jokesquotesandtrivia.uiLayer.theme.JokesQuotesAndTriviaTheme
import com.probro.quotes.viewmodel.QuoteViewModel

class QuoteFragment : Fragment() {

    val quoteVM by viewModels<QuoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                JokesQuotesAndTriviaTheme {
                    QuoteScreen()
                }
            }
        }
    }
}

@Composable
fun QuoteScreen() {
    Text("Quote Screen")
}
