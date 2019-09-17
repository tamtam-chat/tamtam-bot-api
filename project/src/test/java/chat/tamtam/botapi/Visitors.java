package chat.tamtam.botapi;

import chat.tamtam.botapi.model.Update;

/**
 * @author alexandrchuprin
 */
public class Visitors {
    public static Update.Visitor noDuplicates(Update.Visitor delegate) {
        return new ProhibitDuplicatesUpdateVisitor(delegate);
    }

    public static VisitedUpdatesTracer tracing(Update.Visitor delegate) {
        return new VisitedUpdatesTracer(delegate);
    }
}
