import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class DashboardFragment : Fragment() {
    private lateinit var statusText: TextView
    private lateinit var startStopButton: Button
    private var botRunning = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        statusText = view.findViewById(R.id.statusText)
        startStopButton = view.findViewById(R.id.startStopButton)

        checkBotStatus()
        startStopButton.setOnClickListener { toggleBotState() }

        return view
    }

    private fun checkBotStatus() {
        val url = "http://127.0.0.1:8000/api/bot-status/"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                botRunning = response.getBoolean("running")
                updateUI()
            },
            { error -> Log.e("DashboardFragment", "Error: ${error.message}") })

        Volley.newRequestQueue(requireContext()).add(request)
    }

    private fun toggleBotState() {
        val url = "http://127.0.0.1:8000/api/bot-toggle/"
        val requestBody = JSONObject().apply { put("action", if (botRunning) "stop" else "start") }

        val request = JsonObjectRequest(Request.Method.POST, url, requestBody,
            { response ->
                botRunning = response.getBoolean("running")
                updateUI()
            },
            { error -> Log.e("DashboardFragment", "Error: ${error.message}") })

        Volley.newRequestQueue(requireContext()).add(request)
    }

    private fun updateUI() {
        statusText.text = "Bot Status: ${if (botRunning) "Running" else "Stopped"}"
        startStopButton.text = if (botRunning) "Stop Bot" else "Start Bot"
    }
}