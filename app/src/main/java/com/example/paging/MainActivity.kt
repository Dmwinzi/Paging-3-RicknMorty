
package com.example.paging

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.example.paging.Data.Network.Characters
import com.example.paging.Data.Network.RicknMortApi
import com.example.paging.Data.Repository.Repository
import com.example.paging.ui.theme.PagingTheme
import kotlinx.coroutines.flow.collect

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ricknMortApi  = RicknMortApi.invoke()
        val repository = Repository(ricknMortApi)
        var factory  = Mainviewmodelfactory(repository)
        val viewModel by viewModels<Mainviewmodel> {factory}
        setContent {
            PagingTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    RicknMortycharacters(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun RicknMortycharacters(viewModel: Mainviewmodel) {
    val characters = viewModel.character.collectAsLazyPagingItems()

    Log.d("chars",characters.itemSnapshotList.items.toString())

    LazyColumn(){
        items(characters){ item ->
            Column {
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillParentMaxWidth()
                ){
                   Image(painter = rememberAsyncImagePainter(model = item!!.image ),
                       contentDescription = null,
                       modifier = Modifier.clip(CircleShape)
                   )
                    Text(text = item!!.name, modifier = Modifier.padding(5.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider()
            }

        }
    }

}