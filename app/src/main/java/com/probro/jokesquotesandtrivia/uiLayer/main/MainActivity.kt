package com.probro.jokesquotesandtrivia.uiLayer.main

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.probro.jokesquotesandtrivia.R
import com.probro.jokesquotesandtrivia.uiLayer.theme.JokesQuotesAndTriviaTheme
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class MainActivity : FragmentActivity(R.layout.activity_main) {
    private val TAG = "MainActivity"

    private val splitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        requestJokeModule()
    }

    private fun requestJokeModule() {
        val request = SplitInstallRequest.newBuilder()
            .addModule(":Jokes")
            .addModule(":Quotes")
            .addModule(":Trivia")
            .build()

        splitInstallManager.startInstall(request).addOnSuccessListener {
            Log.e(TAG, "onCreate: Got the module $it")
        }.addOnFailureListener {
            Log.e(TAG, "onCreate: Sorry we had some issue, Couldnt get the module. \n$it")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JokesQuotesAndTriviaTheme {
        Greeting("Android")
    }
}
