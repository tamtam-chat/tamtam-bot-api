package beans.common

import com.fasterxml.jackson.databind.annotation.JsonSerialize


/**
 * Created by Nechaev Mikhail
 * Since 12/05/2018.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
data class TamRecipient(
        var chat_id: Long? = null,
        var user_id: Long? = null
)