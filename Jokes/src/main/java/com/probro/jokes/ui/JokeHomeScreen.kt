package com.probro.jokes.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.probro.datalayer.model.local.Joke
import com.probro.jokes.utils.JokeListEvents

private val TAG = "JokeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeHomeScreen(
    jokeState: JokeState,
    events: JokeListEvents,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { events.onJokeRequest() }) {
                Text("Another Joke?")
            }
        },
        topBar = {
            TopJokeBar(
                jokeSource = jokeState.jokeSource,
                pastSources = jokeState.pastSources,
                onJokeSourceSelect = { source ->
                    // Select jokes from a different known source.
                    Log.e(TAG, "JokeHomeScreen: Requested source was $source")
                },
            ) {
                events.navigateup()
            }
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            when {
                jokeState.isLoading -> {
                    // Make a more customized version of a progress bar.
                    CircularProgressIndicator()
                }

                jokeState.errMsg.isNotEmpty() -> {
                    Log.e(TAG, "JokeScreen: there was an error. ${jokeState.errMsg}")
                }

                else -> {
                    JokeList(
                        jokeList = jokeState.jokeList.toTypedArray(),
                        events = events,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
fun TopJokeBar(
    jokeSource: String,
    pastSources: Set<String>,
    onJokeSourceSelect: (source: String) -> Unit,
    onBackPress: () -> Unit,
) {
    Box(modifier = Modifier.clip(MaterialTheme.shapes.medium)) {
        OutlinedIconButton(
            onClick = onBackPress,
            modifier = Modifier.align(Alignment.TopStart),
        ) {
            Image(
                imageVector = Icons.TwoTone.ArrowBack,
                contentDescription = "Go to previous section.",
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Jokes Page",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
            )
            JokeSourceDropDown(
                jokeSource,
                pastSources,
                onJokeSourceSelect = onJokeSourceSelect,
            )
        }
    }
}

@Composable
fun JokeSourceDropDown(
    jokeSource: String,
    pastSources: Set<String>,
    onJokeSourceSelect: (source: String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    if (!jokeSource.isEmpty()) {
        Column {
            OutlinedButton(onClick = { isExpanded = !isExpanded }) {
                Text(
                    text = jokeSource,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                )
                Image(
                    imageVector = Icons.Outlined.ArrowDropDown,
                    contentDescription = "Choose from available joke sources.",
                )
            }
            if (isExpanded) {
                LazyColumn {
                    items(pastSources.toTypedArray()) { source ->
                        Text(
                            text = source,
                            modifier = Modifier.clickable {
                                onJokeSourceSelect(source)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JokeList(
    jokeList: Array<Joke>,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    events: JokeListEvents,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = scrollState,
        modifier = modifier,
    ) {
        items(
            items = jokeList,
            key = { it.id },
        ) { currentJoke ->
            JokeDisplay(
                joke = currentJoke,
                modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                onFavoriteSelect = events.onFavoriteSelect,
                onShareSelect = events.onShareSelect,
                onJokeDislike = events.onJokeDislike,
            )
        }
    }
}

const val DEFAULT_DIVIDER_WIDTH = .75f

@Composable
fun JokeDisplay(
    joke: Joke,
    modifier: Modifier = Modifier,
    onFavoriteSelect: (Joke) -> Unit,
    onShareSelect: (Joke) -> Unit,
    onJokeDislike: (Joke) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .heightIn(min = 60.dp, 400.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
    ) {
        JokePresentation(
            joke = joke,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(vertical = 8.dp)
                .weight(2.5f),
        )
        JokeInteractionRow(
            onFavoriteSelect = { onFavoriteSelect(joke) },
            onShareSelect = { onShareSelect(joke) },
            onJokeDislike = { onJokeDislike(joke) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(.5f),
        )
    }
}

@Composable
fun JokePresentation(joke: Joke, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
    ) {
        Column() {
            JokeText(
                joke = joke.joke,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f),
            )
            Divider(
                Modifier
                    .fillMaxWidth(DEFAULT_DIVIDER_WIDTH)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp),
            )
            Text(
                text = joke.source.url,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 8.dp)
                    .weight(.4f),
            )
        }
    }
}

@Composable
fun JokeInteractionRow(
    onFavoriteSelect: () -> Unit,
    onShareSelect: () -> Unit,
    onJokeDislike: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        IconButton(
            icon = Icons.TwoTone.Favorite,
            contentDescription = "Marks joke as a favorite.",
            onIconSelect = onFavoriteSelect,
        )
        IconButton(
            icon = Icons.TwoTone.Share,
            contentDescription = "Share this joke with a friend.",
            onIconSelect = onShareSelect,
        )
        IconButton(
            icon = Icons.TwoTone.Close,
            contentDescription = "Marks joke as unacceptable",
            onIconSelect = onJokeDislike,
        )
    }
}

@Composable
fun IconButton(
    icon: ImageVector,
    contentDescription: String,
    onIconSelect: () -> Unit,
) {
    Button(
        onClick = onIconSelect,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.outlinedButtonColors(),
    ) {
        Image(
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun JokeText(joke: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = joke,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
        )
    }
}

val testJoke = Joke(
    joke = "Today, my son asked \"Can I have a book mark?\" and " +
        "I burst into tears. 11 years old and he still doesn't know my name is Brian.",
)

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun JokeTextPreview() {
    JokeList(
        jokeList = arrayOf(testJoke, testJoke, testJoke, testJoke),
        modifier = Modifier.fillMaxWidth(),
        events = object : JokeListEvents {
            override val onFavoriteSelect: (Joke) -> Unit
                get() = {}
            override val onShareSelect: (Joke) -> Unit
                get() = {}
            override val onJokeDislike: (Joke) -> Unit
                get() = {}
            override val onJokeRequest: () -> Unit
                get() = {}
            override val navigateup: () -> Unit
                get() = {}
        },
    )
}
