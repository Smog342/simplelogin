package com.example.simplelogin

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.simplelogin.ui.theme.SimpleLoginTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleLoginTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun Authorization(){

    var login = "";
    var password = "";
    val context = LocalContext.current;

    fun Enter(context: Context){

        if ((login == "") or (password == "")){

            Toast.makeText(
                context,
                "Пожалуйста введите логин и пароль",
                Toast.LENGTH_SHORT
            ).show()

        }else{

            Toast.makeText(
                context,
                "Успешная авторизация",
                Toast.LENGTH_SHORT
            ).show()

        }

    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
    ) {
        OutlinedTextField(value = login, placeholder = {Text(text = "Логин")}, onValueChange = {login = it})
        OutlinedTextField(value = password, placeholder = {Text(text = "Пароль")}, onValueChange = {password = it})
        Button(onClick = {

            Enter(context)

        }) {
            Text("Авторизоваться")
        }

    }
}

@Composable
fun Registration(){

    var login = "";
    var password = "";
    var password2 = "";
    val context = LocalContext.current;

    fun Enter(context: Context){

        if ((login == "") or (password == "") or (password2 == "")){

            Toast.makeText(
                context,
                "Пожалуйста введите логин и пароль",
                Toast.LENGTH_SHORT
            ).show()

        }else if (password != password2){

            Toast.makeText(
                context,
                "Введённые пароли не совпадают",
                Toast.LENGTH_SHORT
            ).show()

        }else{

            Toast.makeText(
                context,
                "Успешная регистрация",
                Toast.LENGTH_SHORT
            ).show()

        }

    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
    ) {
        OutlinedTextField(value = login, placeholder = {Text(text = "Логин")}, onValueChange = {login = it})
        OutlinedTextField(value = password, placeholder = {Text(text = "Пароль")}, onValueChange = {password = it})
        OutlinedTextField(value = password2, placeholder = {Text(text = "Пароль")}, onValueChange = {password2 = it})
        Button(onClick = {

            Enter(context)

        }) {
            Text("Зарегистрироваться")
        }

    }

}

@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun MainScreen(){

    val tabs = listOf(
        TabItem.Authorization,
        TabItem.Registration
    );
    val pagerState = rememberPagerState(pageCount = tabs.size);

    Scaffold(
        topBar = { TopBar() },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }

}

@Composable
fun TopBar(){

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name) , fontSize = 18.sp)},
        backgroundColor = colorResource(id = R.color.black),
        contentColor = Color.White
    )

}

@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun Tabs(tabs: List<TabItem>, pagerState: PagerState){

    val scope = rememberCoroutineScope();

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = colorResource(id = R.color.black),
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }) {
        tabs.forEachIndexed { index, tab ->
            // OR Tab()
            LeadingIconTab(
                icon = { Icon(painter = painterResource(id = tab.icon), contentDescription = "") },
                text = { Text(tab.title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }

}

@ExperimentalPagerApi
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        tabs[page].screen()
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleLoginTheme {
        MainScreen()
    }
}