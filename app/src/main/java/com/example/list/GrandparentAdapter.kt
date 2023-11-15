import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.list.GrandparentDetailActivity
import com.example.list.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GrandparentAdapter(private val context: Context) :
    RecyclerView.Adapter<GrandparentAdapter.ViewHolder>() {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("GrandPeople")

    private val grandparentList = mutableListOf<Grandparent>()

    init {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                grandparentList.clear()
                for (snapshot in dataSnapshot.children) {
                    val grandparent = snapshot.getValue(Grandparent::class.java)
                    grandparent?.let {
                        it.key = snapshot.key
                        grandparentList.add(it)
                    }
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grandparent = grandparentList[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context, GrandparentDetailActivity::class.java)

            intent.putExtra("grandparent", grandparent)


            context.startActivity(intent)
        }

        holder.textViewName.text = grandparent.name
        holder.textViewAddress.text = grandparent.address
        holder.checkBoxTaskCompleted.isChecked = grandparent.completed

        Glide.with(context)
            .load(grandparent.img)
            .into(holder.imageViewGrandparent)

        holder.checkBoxTaskCompleted.setOnCheckedChangeListener { buttonView, isChecked ->
            val databaseReference = FirebaseDatabase.getInstance().getReference("GrandPeople")
            grandparent.completed = isChecked

            if (grandparent.key != null) {
                databaseReference.child(grandparent.key!!).setValue(grandparent)
            }
        }
    }

    override fun getItemCount(): Int {
        return grandparentList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxTaskCompleted: CheckBox = itemView.findViewById(R.id.checkBoxTaskCompleted)
        val imageViewGrandparent: ImageView = itemView.findViewById(R.id.imageViewGrandparent)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
    }
}