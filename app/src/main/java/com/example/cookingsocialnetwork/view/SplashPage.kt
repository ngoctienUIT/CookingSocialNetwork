package com.example.cookingsocialnetwork.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cookingsocialnetwork.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_splash_page.*

class SplashPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_page)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        button.setOnClickListener()
        {
            signIn()
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
            System.exit(0)
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun initInforUser()
    {
        val acct = GoogleSignIn.getLastSignedInAccount(this)

        val infor = hashMapOf<String, Any>(
            "name" to acct!!.displayName.toString(),
            "avatar" to acct.photoUrl.toString(),
            "gender" to "Nam",
            "id" to "user00001",
            "description" to "abc",
            "birthday" to FieldValue.serverTimestamp()
        )

        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("infor")
            .set(infor)
    }

    private fun initFavouritesUser()
    {
        val favourites = hashMapOf(
            "favourites" to mutableListOf<String>()
        )

        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("favourites")
            .set(favourites)
    }

    private fun initFollowersUser()
    {
        val followers = hashMapOf(
            "followers" to mutableListOf<String>()
        )

        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("followers")
            .set(followers)
    }

    private fun initFollowingUser()
    {
        val following = hashMapOf(
            "following" to mutableListOf<String>()
        )

        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("following")
            .set(following)
    }

    private fun initPostUser()
    {
        val post = hashMapOf(
            "post" to mutableListOf<String>()
        )

        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .document("post")
            .set(post)
    }

    private fun initUser()
    {
        initInforUser()
        initFavouritesUser()
        initFollowersUser()
        initFollowingUser()
        initPostUser()
    }

    private fun updateUI(user: FirebaseUser?) {
        //Nếu user null thì thoát
        if (user == null) return
        //Nếu user khác null thì kiểm tra đã có trường infor hay chưa
        //Nếu chưa có thì là new user nên thêm trường infor vào
        FirebaseFirestore.getInstance()
            .collection(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                    documents ->
                if (documents.size() == 0)
                {
                    //khởi tạo các trường document user
                    initUser()
                }
            }

        //Sau khi đăng nhập xog sẽ chuyển đến màn hình home
        val homePage = Intent(this, HomePage::class.java)
        startActivity(homePage)
    }
}
