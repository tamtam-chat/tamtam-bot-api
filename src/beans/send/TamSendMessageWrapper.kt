package beans.send

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import beans.common.TamRecipient

/**
 * Created by Nechaev Mikhail
 * Since 12/05/2018.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
data class TamSendMessageWrapper(
        var recipient: TamRecipient? = null,
        var message: TamSendMessage? = null
)