import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midd_khvedianeli.R
import com.squareup.picasso.Picasso

class ArtistAdapter(
    private val artists: MutableList<Artist>,
    private val onDelete: (position: Int) -> Unit
) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    inner class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageArtist: ImageView = itemView.findViewById(R.id.imageArtist)
        val imageExpandToggle: ImageView = itemView.findViewById(R.id.imageExpandToggle)
        val layoutCollapsed: LinearLayout = itemView.findViewById(R.id.layoutCollapsed)
        val layoutExpanded: LinearLayout = itemView.findViewById(R.id.layoutExpanded)
        val textArtistName: TextView = itemView.findViewById(R.id.textArtistName)
        val textArtistPeriod: TextView = itemView.findViewById(R.id.textArtistPeriod)
        val textArtistDescription: TextView = itemView.findViewById(R.id.textArtistDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artists[position]
        holder.textArtistName.text = artist.name
        holder.textArtistPeriod.text = artist.period
        holder.textArtistDescription.text = artist.description

        Picasso.get()
            .load(artist.imageUrl)
            .placeholder(R.drawable.ic_artist_placeholder)
            .error(R.drawable.ic_artist_placeholder)
            .into(holder.imageArtist)

        val expanded = artist.isExpanded
        holder.layoutExpanded.visibility = if (expanded) View.VISIBLE else View.GONE
        holder.imageExpandToggle.setImageResource(
            if (expanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more
        )

        holder.imageExpandToggle.setOnClickListener {
            artist.isExpanded = !artist.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = artists.size

    fun deleteItem(position: Int) {
        artists.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItems(newArtists: List<Artist>) {
        val start = artists.size
        artists.addAll(newArtists)
        notifyItemRangeInserted(start, newArtists.size)
    }
}
