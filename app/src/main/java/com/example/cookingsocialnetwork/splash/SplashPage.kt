package com.example.cookingsocialnetwork.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivitySplashPageBinding
import com.example.cookingsocialnetwork.main.MainPage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

class SplashPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var binding: ActivitySplashPageBinding

    //chọn tài khoản google để đăng nhập
    private var signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashPageBinding.inflate(layoutInflater)
        this.setContentView(binding.root)
        supportActionBar?.hide()

        var sharePref = getSharedPreferences("ChangeDarkMode", MODE_PRIVATE)
        val check = sharePref.getInt("darkMode", 2)
        AppCompatDelegate.setDefaultNightMode(check)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        binding.button.setOnClickListener()
        {
            val signInIntent = googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }
    }

    //nhấn back 2 lần mới cho thoát khỏi ứng dụng
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
            exitProcess(0)
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.double_back), Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    //thêm tài khoản google vừa đăng nhập vào firebaseauth
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else updateUI(null)
            }
    }

    //tạo tất cả dữ liệu cần có ủa user
    private fun initUser()
    {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)

        val info = hashMapOf(
            "name" to acct!!.displayName.toString(),
            "avatar" to acct.photoUrl.toString(),
            "gender" to "Nam",
            "username" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "description" to "",
            "birthday" to formatted
        )

        val post = hashMapOf(
            "post" to mutableListOf<String>(),
            "following" to mutableListOf<String>(),
            "followers" to mutableListOf<String>(),
            "favourites" to mutableListOf<String>(),
            "info" to info,
            "notify" to mutableListOf<Map<String,Any>>(),
            "searchHistory" to mutableListOf<String>()
        )

        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .set(post)
    }

    private fun updateUI(user: FirebaseUser?) {
        //Nếu user null thì thoát
        if (user == null) return
        //Nếu user khác null thì kiểm tra đã có trường infor hay chưa
        //Nếu chưa có thì là new user nên thêm trường infor vào
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                    documents ->
                if (documents.data == null) initUser() //khởi tạo các trường document của user
            }

        //Sau khi đăng nhập xog sẽ chuyển đến màn hình home
        val main = Intent(this, MainPage::class.java)
        startActivity(main)
        finish()
    }
}
