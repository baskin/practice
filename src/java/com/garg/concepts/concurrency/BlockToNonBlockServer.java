/*
 * $Header$
 */

/*
 * Copyright (c) 2008 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.concepts.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author gargb
 *
 */
public class BlockToNonBlockServer
{
    interface BlockingService<Request, Response>
    {
        Response serve(Request request);
    }

    interface NonBlockingService<Request, Response>
    {
        Future<Response> serve(Request request);
    }

    /**
     * An adapter that converts this interface into one that returns a
     * Future<Response> without blocking.
     */
    public class NonBlockingServiceAdapter<Request, Response>
        implements NonBlockingService<Request, Response>
    {
        private final BlockingService<Request, Response> myBlockingService;

        private ExecutorService myExecutor;

        public NonBlockingServiceAdapter(BlockingService<Request, Response> svc)
        {
            myBlockingService = svc;
            myExecutor = Executors.newFixedThreadPool(3);
        }

        @Override
        public Future<Response> serve(final Request request)
        {
            return myExecutor.submit(new Callable<Response>() {
                        @Override
                        public Response call() throws Exception
                        {
                            return myBlockingService.serve(request);
                        }
                    });
        }
    }

}
