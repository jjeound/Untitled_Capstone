package com.example.untitled_capstone.presentation.feature.home.screen

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.untitled_capstone.R
import com.example.untitled_capstone.core.util.Dimens
import com.example.untitled_capstone.domain.model.RecipeRaw
import com.example.untitled_capstone.presentation.feature.home.state.AiState
import com.example.untitled_capstone.presentation.feature.home.composable.MyRecipe
import com.example.untitled_capstone.presentation.feature.home.composable.SetTaste
import com.example.untitled_capstone.presentation.feature.home.HomeEvent
import com.example.untitled_capstone.presentation.feature.home.state.RecipeState
import com.example.untitled_capstone.presentation.feature.home.state.TastePrefState
import com.example.untitled_capstone.presentation.feature.home.composable.ChatBot
import com.example.untitled_capstone.presentation.feature.main.MainViewModel
import com.example.untitled_capstone.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(snackBarHostState: SnackbarHostState, mainViewModel: MainViewModel, state: RecipeState, recipeItems: LazyPagingItems<RecipeRaw>, tastePrefState: TastePrefState, aiState: AiState,
               onEvent: (HomeEvent) -> Unit, navigate: (Long) -> Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    Column(
        modifier = Modifier.padding(
            horizontal = Dimens.surfaceHorizontalPadding)
            .padding(top = Dimens.surfaceVerticalPadding)
            .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })}
    ) {
        SetTaste(tastePrefState, onEvent)
        Spacer(modifier = Modifier.height(Dimens.largePadding))
        Card (
            colors = CardDefaults.cardColors(
                containerColor = CustomTheme.colors.onSurface,
            ),
            shape = RoundedCornerShape(Dimens.cornerRadius),
            modifier = Modifier.fillMaxWidth()
        ){
            Column(
                modifier = Modifier.padding(horizontal = Dimens.largePadding)
                    .padding(top = Dimens.largePadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.largePadding)
            ) {
                Text(
                    text = "My 레시피",
                    style = CustomTheme.typography.title1,
                    color = CustomTheme.colors.textPrimary,
                    modifier = Modifier.fillMaxWidth()
                )
                LaunchedEffect(key1 = recipeItems.loadState) {
                    if(recipeItems.loadState.refresh is LoadState.Error) {
                        Toast.makeText(
                            context,
                            "Error: " + (recipeItems.loadState.refresh as LoadState.Error).error.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    if(recipeItems.loadState.refresh is LoadState.Loading || state.loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        if(recipeItems.itemCount == 0){
                            AsyncImage(
                                modifier = Modifier.fillMaxWidth(),
                                model = R.drawable.home_banner,
                                contentDescription = "home_banner",
                            )
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            state = rememberLazyGridState(),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                        ) {
                            items( recipeItems.itemCount,){ index ->
                                val item = recipeItems[index]
                                if(item != null){
                                    onEvent(HomeEvent.GetRecipeById(item.id))
                                    MyRecipe(item, onEvent = onEvent,
                                        onClick = {
                                            navigate(item.id)
                                        }
                                    )
                                }
                            }
                            item {
                                if (recipeItems.loadState.append is LoadState.Loading && recipeItems.itemCount > 10) {
                                    CircularProgressIndicator(modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally))
                                }
                            }
                        }
                    }
                }
            }
        }
        if(mainViewModel.showBottomSheet.value){
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { mainViewModel.showBottomSheet.value = false},
                containerColor = CustomTheme.colors.onSurface
            ) {
                ChatBot(aiState, onEvent)
            }
        }
    }
}
