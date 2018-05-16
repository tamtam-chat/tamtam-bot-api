package beans.send

import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * Created by Nechaev Mikhail
 * Since 12/05/2018.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
data class TamWebhookSet(
        var url: String? = null
)