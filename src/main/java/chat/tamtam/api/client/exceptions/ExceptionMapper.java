package chat.tamtam.api.client.exceptions;

/**
 * @author alexandrchuprin
 */
public class ExceptionMapper {
    public static APIException map(chat.tamtam.api.objects.Error error) {
        switch (error.getCode()) {
            case "too.many.requests":
                return new TooManyRequestsException(error.getMessage());
        }

        return new APIException(error.getMessage());
    }
}
