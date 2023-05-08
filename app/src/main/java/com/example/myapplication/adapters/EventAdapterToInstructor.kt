package com.example.myapplication.adapters
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.models.Event
import com.example.myapplication.R
import com.google.firebase.firestore.FirebaseFirestore

class EventAdapterToInstructor(
    private val eventList: ArrayList<Event>,
    private val context: Context,
    private val db: FirebaseFirestore,
    private val userID: String
) : RecyclerView.Adapter<EventAdapterToInstructor.EventViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_event_to_instructor, parent, false)

        return EventViewHolder(itemView)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        val event: Event = eventList[position]

        holder.eventTitle.text = event.title
        holder.eventPlace.text = event.place
        holder.eventDate.text = event.date
        holder.eventTime.text = event.time
        holder.eventID.text = event.eid
        holder.eventByID.text = event.eventID

                holder.editEventButton.setOnClickListener(){
                    updateEvent(event)
                }

                holder.deleteEventButton.setOnClickListener(){
                    eventList[position].eid?.let { it1 -> eventList[position].eventID?.let { it2 ->
                        deleteEvent(it1,position,
                            it2
                        )
                    } }
                }
    }

    override fun getItemCount(): Int {

        return eventList.size
    }

    public class EventViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val eventTitle: TextView = itemView.findViewById(R.id.eventTitle)
        val eventPlace: TextView = itemView.findViewById(R.id.eventPlace)
        val eventDate: TextView = itemView.findViewById(R.id.eventDate)
        val eventTime: TextView = itemView.findViewById(R.id.eventTime)
        val eventID: TextView = itemView.findViewById(R.id.eventID)
        val eventByID:TextView = itemView.findViewById(R.id.eventRandomID)

        val editEventButton: ImageView = itemView.findViewById(R.id.editEventButton)
        val deleteEventButton: ImageView = itemView.findViewById(R.id.deleteEventButton)

    }

    private fun updateEvent(event: Event) {
        val intent = Intent(context, EditEventActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("title", event.title)
        intent.putExtra("place", event.place)
        intent.putExtra("date", event.date)
        intent.putExtra("time", event.time)
        intent.putExtra("id", event.eid)
        intent.putExtra("eventID", event.eventID)
        context.startActivity(intent)
    }

    private fun deleteEvent(randomId: String, position: Int, userID: String) {

        db.collection("eventsByInstructorID").document(userID).collection("singleEvents")
            .document(randomId)
            .delete()
            .addOnCompleteListener {
                eventList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, eventList.size)
                Toast.makeText(context, "Event has been deleted!", Toast.LENGTH_SHORT).show()
            }

        db.collection("events")
            .document(randomId)
            .delete()

    }

}