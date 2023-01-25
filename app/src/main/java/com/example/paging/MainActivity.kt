
package com.example.paging

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.paging.Data.Network.Characters
import com.example.paging.Data.Network.RicknMortApi
import com.example.paging.Data.Repository.Repository
import com.example.paging.ui.theme.Circularprograss
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
   var isLoading by remember { mutableStateOf(false)}
    val characters = viewModel.character.collectAsLazyPagingItems()
    val context = LocalContext.current
    when(characters.loadState.append){
        is LoadState.NotLoading -> isLoading
        is LoadState.Error  -> Toast.makeText(context,"Error loading",Toast.LENGTH_LONG).show()
        LoadState.Loading  -> isLoading = true
    }
    
    LazyColumn(){
        items(characters){ item ->
            Column {
                Row(modifier = Modifier.fillParentMaxWidth(),
                ){
                    AsyncImage(model = ImageRequest.Builder(context).data(item!!.image).build(),
                        error = painterResource(id = R.drawable.baseline_image_24),
                        contentDescription = "Upload Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(80.dp)
                            .height(80.dp))
                    Spacer(modifier = Modifier.width(40.dp))
                   Column(horizontalAlignment = Alignment.CenterHorizontally) {
                       Text(text = item!!.name, modifier = Modifier.padding(5.dp),
                           fontWeight = FontWeight.Bold
                       )
                       Text(text = item!!.gender, modifier = Modifier.padding(5.dp),
                           fontWeight = FontWeight.Bold
                       )
                       Text(text = item!!.status, modifier = Modifier.padding(5.dp),
                           fontWeight = FontWeight.Bold
                       )
                       Text(text = item!!.type, modifier = Modifier.padding(5.dp),
                           fontWeight = FontWeight.Bold
                       )
                   }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }

        item {
            Circularprograss(isLoading = isLoading)
        }
    }
    

}