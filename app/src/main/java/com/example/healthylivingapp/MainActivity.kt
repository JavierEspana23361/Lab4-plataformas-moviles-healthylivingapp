package com.example.healthylivingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.healthylivingapp.ui.theme.HealthyLivingAppTheme

data class Recipe(val name: String, val imageUrl: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthyLivingAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyRecipeApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyRecipeApp(modifier: Modifier = Modifier) {
    var recipeName by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    val recipes = remember { mutableStateListOf<Recipe>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = recipeName,
            onValueChange = { recipeName = it },
            label = { Text("Nombre de la receta") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("URL de la imagen") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (recipeName.isNotBlank() && imageUrl.isNotBlank() && !recipes.any { it.name == recipeName }) {
                recipes.add(Recipe(recipeName, imageUrl))
                recipeName = ""
                imageUrl = ""
            }
        }) {
            Text("Agregar Receta")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(recipes) { recipe ->
                RecipeCard(recipe = recipe, onDelete = {
                    recipes.remove(recipe)
                })
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(recipe.imageUrl),
                contentDescription = "Imagen de la receta",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = recipe.name,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


