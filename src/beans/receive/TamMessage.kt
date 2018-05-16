package beans.receive

import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * Created by Nechaev Mikhail
 * Since 12/05/2018.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
data class TamMessage(
        var mid: String? = null,
        var text: String? = null,
        var seq: Long? = null
)