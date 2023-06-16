package com.probro.trivia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.probro.trivia.util.DEFAULT_GAME_SIZE
import com.probro.trivia.util.MAX_GAME_SIZE
import com.probro.trivia.util.MIN_GAME_SIZE
import com.probro.trivia.util.TriviaNavEvents
import kotlin.math.roundToInt

const val GAME_CONFIG_ALPHA = .5f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onStartGame: (event: TriviaNavEvents) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
            ) {
                Text(
                    text = "Home Screen",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                )
            }
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onClick = { onStartGame(TriviaNavEvents.HomeScreenToGameScreen) },
                shape = RectangleShape,
            ) {
                Text(
                    text = "To Game",
                    Modifier.padding(8.dp),
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        val gameConfigShape = MaterialTheme.shapes.medium
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .clip(gameConfigShape)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center,
        ) {
            GameConfigurationOptions(
                GameConfigState(),
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(.9f)
                    .padding(20.dp)
                    .clip(gameConfigShape)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onBackground,
                        gameConfigShape,
                    )
                    .background(MaterialTheme.colorScheme.primary),
            ) {
            }
        }
    }
}

data class GameConfigState(
    val triviaListSize: Int = DEFAULT_GAME_SIZE,
    val category: Categories = Categories.MIX,
    val difficulty: GameDifficulty = GameDifficulty.MIX,
)

@Composable
fun GameConfigurationOptions(
    configState: GameConfigState,
    modifier: Modifier = Modifier,
    onGameConfigurationsChange: (config: GameConfigValues) -> Unit,
) {
    var questionListSize by remember { mutableStateOf(configState.triviaListSize) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier,
    ) {
        val sliderShape = MaterialTheme.shapes.medium
        SliderWithText(
            text = "Game Size",
            value = questionListSize.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(sliderShape)
                .border(1.dp, MaterialTheme.colorScheme.onSurface, sliderShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
        ) {
            questionListSize = it.roundToInt()
            onGameConfigurationsChange(GameConfigValues.GameSize(questionListSize))
        }
        GameDifficultyOptionsDisplay(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = MaterialTheme.shapes.medium,
                )
                .background(MaterialTheme.colorScheme.secondaryContainer),
        ) {
            onGameConfigurationsChange(GameConfigValues.Difficulty(it))
        }
        CategorySelection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.small)
                .background(Color.Transparent),
        )
    }
}

@Composable
fun CategorySelection(
    categoryOptions: List<String> = listOf("basic", "ass", "test", "values", "here"),
    modifier: Modifier = Modifier,
) {
    var chosenOne by remember { mutableStateOf(categoryOptions.first()) }
    Column(
        modifier = modifier,
    ) {
        DropdownOptions(chosenOne, categoryOptions) { newChosenOne ->
            chosenOne = newChosenOne
        }
    }
}

@Composable
fun DropdownOptions(
    category: String,
    categoryOptions: List<String> = listOf("basic", "ass", "test", "values", "here"),
    onCategoryChosen: (chosen: String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        onClick = { isExpanded = true },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
    ) {
        Text(
            text = "Chosen category:",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = category.uppercase(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
        )
        Image(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "Image of a dropdown arrow.",
        )
    }

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { isExpanded = false },
        modifier = Modifier
            .fillMaxWidth(.5f)
            .background(MaterialTheme.colorScheme.background),
    ) {
        categoryOptions.forEach { option ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                    )
                },
                onClick = {
                    onCategoryChosen(option)
                    isExpanded = false
                },
            )
        }
    }
}

@Composable
fun GameDifficultyOptionsDisplay(
    modifier: Modifier = Modifier,
    onDifficultySelect: (difficulty: GameDifficulty) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RadioButtonOption(
            buttonText = GameDifficulty.MIX.name,
            modifier = Modifier.fillMaxWidth(),
        ) {
            onDifficultySelect(GameDifficulty.MIX)
        }
        RadioButtonOption(
            buttonText = GameDifficulty.EASY.name,
            modifier = Modifier.fillMaxWidth(),
        ) {
            onDifficultySelect(GameDifficulty.EASY)
        }
        RadioButtonOption(
            buttonText = GameDifficulty.MEDIUM.name,
            modifier = Modifier.fillMaxWidth(),
        ) {
            onDifficultySelect(GameDifficulty.MEDIUM)
        }
        RadioButtonOption(
            buttonText = GameDifficulty.HARD.name,
            modifier = Modifier.fillMaxWidth(),
        ) {
            onDifficultySelect(GameDifficulty.HARD)
        }
    }
}

@Composable
fun RadioButtonOption(
    buttonText: String,
    modifier: Modifier = Modifier,
    onButtonSelected: () -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .clickable { onButtonSelected() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { isSelected = !isSelected },
            modifier = Modifier,
        )
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
    }
    Divider()
}

@Composable
fun SliderWithText(
    text: String,
    modifier: Modifier = Modifier,
    value: Float = 0f,
    onValueChange: (value: Float) -> Unit,
) {
    val sliderValue by remember { mutableStateOf(value) }
    Box(
        modifier = Modifier.background(Color.Transparent),
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
            )
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = MIN_GAME_SIZE.toString(),
                    modifier = Modifier.wrapContentSize(),
                )
                Slider(
                    value = sliderValue,
                    onValueChange = {
                        onValueChange(sliderValue)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                )
                Text(
                    text = MAX_GAME_SIZE.toString(),
                    modifier = Modifier.wrapContentSize(),
                )
            }
        }
    }
}

sealed class GameConfigValues {
    data class Difficulty(val difficulty: GameDifficulty) : GameConfigValues()
    data class Category(val category: Categories) : GameConfigValues()
    data class GameSize(val size: Int) : GameConfigValues()
}

enum class GameDifficulty(name: String) {
    EASY("easy"), MEDIUM("medium"), HARD(("hard")), MIX("mix")
}

enum class Categories {
    MIX,
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen() {
    }
}
