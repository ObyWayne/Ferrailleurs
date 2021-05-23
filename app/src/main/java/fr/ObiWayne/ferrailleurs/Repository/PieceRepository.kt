package fr.ObiWayne.ferrailleurs.Repository


import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.ObiWayne.ferrailleurs.Model.PieceModel
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.DataBaseRef
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.DataFirestore
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.StorageRef
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.UID
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.downloadUri
import fr.ObiWayne.ferrailleurs.Repository.PieceRepository.Singleton.pieceList
import java.util.*


class PieceRepository {

    object Singleton {

        private const val Storage_Url: String = "gs://ferrailleurs-8d9f4.appspot.com"
        val StorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(Storage_Url)

        val DataBaseRef = FirebaseDatabase.getInstance().getReference("pieces")
        val DataFirestore = FirebaseFirestore.getInstance()
        val AuthRef = FirebaseAuth.getInstance()

        val pieceList = arrayListOf<PieceModel>()
        val pieceListUser = arrayListOf<PieceModel>()

        var UID = " "

        var downloadUri: Uri? = null
    }


    fun updateData(callback: () -> Unit) {
        DataBaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pieceList.clear()

                for (ds in snapshot.children) {

                    val Mypiece = ds.getValue(PieceModel::class.java)

                    if (Mypiece != null) {
                        pieceList.add(Mypiece) }
                    }
                callback()}

            override fun onCancelled(snapshot: DatabaseError) {
            }})}


    fun insertPiece(piece: PieceModel) = DataBaseRef.child(piece.id).setValue(piece)


    fun uploadImage(file: Uri, callback: () -> Unit) {
        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = StorageRef.child(fileName)
            val uploadTask = ref.putFile(file)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->

                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl

            }).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    downloadUri = task.result
                    callback()
                }}}}

    fun dataCheck(callback: () -> Unit) {
        val fireStoreRef = DataFirestore.collection("/users").document(UID)

        fireStoreRef.get().addOnCompleteListener { document ->
            if (document != null) {
                var favList = listOf<String>()
                favList = document.result!!.get("liked").toString().removeSuffix("]").split(",")
                DataBaseRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Singleton.pieceListUser.clear()
                        for (ds in snapshot.children) {
                            val Mypiece = ds.getValue(PieceModel::class.java)

                            if (Mypiece != null) {
                                var idPiece = Mypiece.id
                                for (element in favList) {
                                    if (idPiece == element.trim()) {

                                        Singleton.pieceListUser.add(Mypiece)
                                    }
                                }
                            }
                        }
                        callback()
                    }

                    override fun onCancelled(snapshot: DatabaseError) {
                    }})}}}


}