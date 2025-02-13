class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> loadFragment(DashboardFragment())
                R.id.nav_code_editor -> loadFragment(CodeEditorFragment())
                R.id.nav_settings -> loadFragment(SettingsFragment())
            }
            true
        }
        bottomNav.selectedItemId = R.id.nav_dashboard
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}