package chat.tamtam.botapi.queries;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.SimpleQueryResult;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * @author alexandrchuprin
 */
public class UnsubscribeQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldReturnUnsuccessfulResultOnNonExistingSubscription() throws Exception {
        String url = "https://" + randomText(32) + ".com";
        SimpleQueryResult result = new UnsubscribeQuery(client, url).execute();
        assertThat(result.isSuccess(), is(false));
        assertThat(result.getMessage(), not(isEmptyOrNullString()));
    }
}