package chat.tamtam.api.requests.queries;

import chat.tamtam.api.objects.User;

/**
 * @author alexandrchuprin
 */
public class MyInfoQuery extends TamTamQuery<User> {
    public MyInfoQuery() {
        super("/me/info", User.class, false);
    }
}
