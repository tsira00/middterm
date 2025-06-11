import com.google.gson.annotations.SerializedName

data class Artist<T>(
    val id: Int,
    val name: String,
    val period: String,
    val description: String,
    val imageUrl: String,

)
data class ReqresObj<T>(
    var page: Int?,
    @SerializedName("per_page")
    var perPage: Int?,
    var total: Long?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    var data: T?
)