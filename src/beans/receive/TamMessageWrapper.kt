package beans.receive

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import beans.common.TamRecipient

/**
 * Created by Nechaev Mikhail
 * Since 12/05/2018.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
data class TamMessageWrapper(
        var sender: TamSender? = null,
        var recipient: TamRecipient? = null,
        var message: TamMessage? = null,
        var timestamp: Long? = null
)