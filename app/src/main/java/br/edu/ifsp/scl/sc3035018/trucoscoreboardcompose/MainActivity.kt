package br.edu.ifsp.scl.sc3035018.trucoscoreboardcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3035018.trucoscoreboardcompose.ui.theme.TrucoScoreBoardComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrucoScoreBoardComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var teamAPoints by remember { mutableIntStateOf(0) }
    var teamBPoints by remember { mutableIntStateOf(0) }

    val hasWinner = teamAPoints >= 12 || teamBPoints >= 12

    val messageMaoDeOnze = when {
        teamAPoints == 11 && teamBPoints == 11 -> "Ambas as equipes estão em Mão de 11!"
        teamAPoints == 11 -> "Equipe A em Mão de 11!"
        teamBPoints == 11 -> "Equipe B em Mão de 11!"
        else -> ""
    }

    val winner = when {
        teamAPoints >= 12 -> "Equipe A venceu a partida!"
        teamBPoints >= 12 -> "Equipe B venceu a partida!"
        else -> ""
    }

    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Placar Truco",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
        Text(
            text = messageMaoDeOnze,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            ) {
            CardTeamPoints(
                teamName = "A",
                onAddPoint = { teamAPoints++ },
                onAddThreePoints = { teamAPoints +=3 },
                teamPoints = teamAPoints,
                winner = hasWinner
            )
            CardTeamPoints(
                teamName = "B",
                onAddPoint = { teamBPoints++ },
                onAddThreePoints = { teamBPoints +=3 },
                teamPoints = teamBPoints,
                winner = hasWinner
            )
        }
        Text(
            text = winner,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Button(
            onClick = { teamAPoints = 0
                        teamBPoints = 0 },
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            ) {
            Text(
                text = "Resetar Pontuações",
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun CardTeamPoints(
    teamName: String,
    onAddPoint: () -> Unit,
    onAddThreePoints: () -> Unit,
    teamPoints: Int,
    winner: Boolean
) {
    Column(
        modifier = Modifier.padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Time $teamName",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "$teamPoints",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            modifier = Modifier.padding(20.dp)
        )
        Button(
            onClick = { onAddPoint() },
            enabled = !winner
        ) {
            Text(text = "+1 Ponto", fontSize = 16.sp)
        }
        Button(
            onClick = { onAddThreePoints() },
            enabled = !winner
        ) {
            Text(text = "+3 Pontos", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardTeamPointsPreview() {
    CardTeamPoints(teamName = "A", onAddPoint = {}, onAddThreePoints = {}, teamPoints = 11, winner = true)
}