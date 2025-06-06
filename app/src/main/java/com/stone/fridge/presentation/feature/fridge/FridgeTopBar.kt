package com.stone.fridge.presentation.feature.fridge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stone.fridge.R
import com.stone.fridge.core.util.Dimens
import com.stone.fridge.navigation.Screen
import com.stone.fridge.presentation.feature.main.MainViewModel
import com.stone.fridge.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FridgeTopBar(
    navController: NavHostController,
    viewModel: MainViewModel,
    isUnread: Boolean,
    updateUnreadNotification: () -> Unit
){
    TopAppBar(
        modifier = Modifier.padding(horizontal = Dimens.topBarPadding),
        title = {
            Row (
                modifier = Modifier.clickable {
                    viewModel.updateTopSelector()
                },
                verticalAlignment = Alignment.CenterVertically
            ){
                if(viewModel.topSelector){
                    Text(
                        text = "냉장고",
                        style = CustomTheme.typography.headline3,
                        color = CustomTheme.colors.textPrimary,
                    )
                } else {
                    Text(
                        text = "냉동고",
                        style = CustomTheme.typography.headline3,
                        color = CustomTheme.colors.textPrimary,
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.chevron_down),
                    contentDescription = "dropdown",
                    tint = CustomTheme.colors.iconSelected
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    updateUnreadNotification()
                    navController.navigate(Screen.NotificationNav)
                }
            ) {
                if(isUnread){
                    Icon(
                        tint = Color.Unspecified,
                        imageVector = ImageVector.vectorResource(R.drawable.bell_new),
                        contentDescription = "alarm"
                    )
                }else{
                    Icon(
                        tint = CustomTheme.colors.iconDefault,
                        imageVector = ImageVector.vectorResource(R.drawable.bell),
                        contentDescription = "alarm"
                    )
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = CustomTheme.colors.surface,
            scrolledContainerColor = CustomTheme.colors.surface,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}