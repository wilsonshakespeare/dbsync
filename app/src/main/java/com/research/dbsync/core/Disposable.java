package com.research.dbsync.core;

/**
 * @author iFreedom87
 * #since 2016-06-23
 */
public class Disposable implements IDisposable {
    protected boolean _isDisposed = false;

    public Disposable(){
        _isDisposed = false;
    }

    public void dispose(){
        _isDisposed = true;
    }

    public boolean isDisposed(){
        return _isDisposed;
    }
}
