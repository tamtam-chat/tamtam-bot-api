package chat.tamtam.botapi.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jetbrains.annotations.NotNull;

import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.TransportClientException;

/**
 * @author alexandrchuprin
 */
public class FutureResult<S, T> implements Future<T> {
    private final Future<S> delegate;
    private final Mapper<S, T> mapper;

    public FutureResult(Future<S> delegate, Mapper<S, T> mapper) {
        this.delegate = delegate;
        this.mapper = mapper;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return delegate.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return delegate.isCancelled();
    }

    @Override
    public boolean isDone() {
        return delegate.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        try {
            return mapper.map(delegate.get());
        } catch (ClientException | APIException e) {
            throw new ExecutionException(e);
        } catch (ExecutionException e) {
            throw unwrap(e);
        }
    }

    @Override
    public T get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException,
            TimeoutException {
        try {
            return mapper.map(delegate.get(timeout, unit));
        } catch (ClientException | APIException e) {
            throw new ExecutionException(e);
        } catch (ExecutionException e) {
            throw unwrap(e);
        }
    }

    private ExecutionException unwrap(ExecutionException e) {
        Throwable cause = e.getCause();
        if (cause == null) {
            return e;
        }

        if (cause instanceof TransportClientException) {
            return new ExecutionException(new ClientException(cause));
        }

        return e;
    }

    interface Mapper<S, T> {
        T map(S source) throws ClientException, APIException;
    }
}
