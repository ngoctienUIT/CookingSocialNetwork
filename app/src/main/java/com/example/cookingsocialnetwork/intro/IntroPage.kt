package com.example.cookingsocialnetwork.intro

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.databinding.ActivityIntroPageBinding
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
import com.google.firebase.messaging.ktx.messaging
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

class IntroPage: AppCompatActivity() {
    lateinit var binding: ActivityIntroPageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

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

    private val introSliderAdapter
        get() = IntroSliderAdapter(
            listOf(
                IntroSlide(
                    "Bài viết",
                    "Bạn có thể đăng tải và xem các bài viết về món ăn",
                    R.drawable.intro1
                ),
                IntroSlide(
                    "Tìm kiếm",
                    "Tìm kiếm những món ăn mà bạn yêu thích",
                    R.drawable.intro2
                ),
                IntroSlide(
                    "Trang cá nhân",
                    "Xây dựng trang cá nhân của riêng bạn",
                    R.drawable.intro3
                )
            )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.textSkipIntro
        binding.introSliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)

        binding.introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        binding.introSliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if (position == introSliderAdapter.itemCount -1) binding.buttonNext.text = getString(R.string.login)
                else binding.buttonNext.text = getString(R.string.next)
                super.onPageSelected(position)
            }
        })

        binding.buttonNext.setOnClickListener {
            if (binding.introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                binding.introSliderViewPager.currentItem += 1
            } else {
                val signInIntent = googleSignInClient.signInIntent
                signInLauncher.launch(signInIntent)
            }
        }

        binding.textSkipIntro.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }

        val sharePref = getSharedPreferences("ChangeDarkMode", MODE_PRIVATE)
        val check = sharePref.getInt("darkMode", 2)
        AppCompatDelegate.setDefaultNightMode(check)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.indicatorsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsContainer.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
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
    private fun initUser() {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)

        val info = hashMapOf(
            "name" to acct!!.displayName.toString(),
            "avatar" to acct.photoUrl.toString(),
            "gender" to "0",
            "username" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "description" to "",
            "birthday" to formatted
        )

        val post = hashMapOf(
            "post" to mutableListOf<String>(),
            "following" to mutableListOf<String>(),
            "followers" to mutableListOf<String>(),
            "favourites" to mutableListOf<String>(),
            "searchHistory" to mutableListOf<String>(),
            "info" to info,
            "notify" to mutableListOf<Map<String, Any>>()
        )

        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .set(post)
    }

    private fun updateUI(user: FirebaseUser?) {
        //Nếu user null thì thoát
        if (user == null) return
        //Nếu user khác null thì kiểm tra đã có trường info hay chưa
        //Nếu chưa có thì là new user nên thêm trường info vào
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener { documents ->
                if (documents.data == null) initUser() // khởi tạo các trường document của user
            }
        subscribeTopics()

        //Sau khi đăng nhập xog sẽ chuyển đến màn hình home
        val main = Intent(this, MainPage::class.java)
        startActivity(main)
        finish()
    }

    //Firebase Messenger
    private fun subscribeTopics() {
        // [START subscribe_topics]
        Firebase.messaging.subscribeToTopic("notification")
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Log.d(TAG, msg)
//                SendNotify.sendMessage("abc","ngoctien.c42019@gmail.com","JIrREV7L50OG8nlz5Vei","comment", "notification")
            }
        // [END subscribe_topics]
    }
}