
package com.garg.concepts.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

public class CompletionServiceExample
{
    interface Result { }

    // Usage Examples. Suppose you have a set of solvers for a certain problem,
    // each returning a value of some type Result, and would like to run them
    // concurrently, processing the results of each of them that return a
    // non-null value, in some method use(Result r). You could write this as:

    void solve(Executor e, Collection<Callable<Result>> solvers)
        throws InterruptedException, ExecutionException
    {
        CompletionService<Result> ecs =
            new ExecutorCompletionService<Result>(e);

        for (Callable<Result> s : solvers) {
            ecs.submit(s);
        }

        int n = solvers.size();

        for (int i = 0; i < n; ++i) {
            Result r = ecs.take().get();

            if (r != null) {
                use(r);
            }
        }
    }

    private void use(Result r) { }

    // Suppose instead that you would like to use the first non-null result of
    // the set of tasks, ignoring any that encounter exceptions, and canceling
    // all other tasks when the first one is ready:
    void solveFirst(Executor e, Collection<Callable<Result>> solvers)
             throws InterruptedException
    {
        CompletionService<Result> ecs =
            new ExecutorCompletionService<Result>(e);
        int n = solvers.size();
        List<Future<Result>> futures = new ArrayList<Future<Result>>(n);
        Result result = null;

        try {
            for (Callable<Result> s : solvers) {
                futures.add(ecs.submit(s));
            }

            for (int i = 0; i < n; ++i) {
                try {
                    Result r = ecs.take().get();

                    if (r != null) {
                        result = r;

                        break;
                    }
                }
                catch (ExecutionException ignore) { }
            }
        }
        finally {
            for (Future<Result> f : futures) {
                f.cancel(true);
            }
        }

        if (result != null) {
            use(result);
        }
    }

}
