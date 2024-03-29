package com.nicksidiropoulos.a30daysofrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nicksidiropoulos.a30daysofrecipes.model.Recipe
import com.nicksidiropoulos.a30daysofrecipes.model.RecipeRepository
import com.nicksidiropoulos.a30daysofrecipes.ui.theme.DaysOfRecipesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaysOfRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    RecipeApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ), title = {
                Text(
                    stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            })
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(RecipeRepository.recipes) { recipe ->
                RecipeCard(recipe = recipe)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier) {

    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.primaryContainer,
        label = "color",
    )

    Card(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = color,
        ),
        onClick = { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Text(text = stringResource(id = R.string.day) + " " + recipe.day)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = stringResource(id = recipe.name),
                style = MaterialTheme.typography.displayMedium
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(MaterialTheme.shapes.small),
                painter = painterResource(id = recipe.image),
                contentDescription = stringResource(id = recipe.name),
                contentScale = ContentScale.FillWidth,
            )

            if (expanded) {
                Row(
                    modifier = Modifier.padding(2.dp)
                ) {
                    Icon(
                        Icons.Rounded.Star, contentDescription = null
                    )
                    Text(
                        text = stringResource(id = recipe.description)
                    )
                }
                Row(
                    modifier = Modifier.padding(2.dp)
                ) {
                    Icon(
                        Icons.Rounded.DateRange, contentDescription = null
                    )
                    Text(
                        text = stringResource(id = recipe.duration)
                    )
                }
            }
            ExpandButton(expanded = expanded, onClick = { expanded = !expanded })
        }
    }
}

@Composable
private fun ExpandButton(
    expanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onClick, modifier = modifier
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun RecipeAppPreview() {
    DaysOfRecipesTheme {
        RecipeApp()
    }
}