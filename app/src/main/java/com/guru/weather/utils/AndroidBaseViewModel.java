package com.guru.weather.utils;

import com.guru.weather.misc.ClickItemWrapper;
import com.guru.weather.misc.MessageWrapper;
import com.guru.weather.misc.SingleLiveEvent;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

public class AndroidBaseViewModel extends AndroidViewModel implements Observable, LifecycleObserver, INeedClickListener {

    private transient PropertyChangeRegistry mCallbacks;

    protected SingleLiveEvent<ClickItemWrapper> mItemClickNotifier = new SingleLiveEvent<>();

    private SingleLiveEvent<MessageWrapper> mErrorMessageNotifier = new SingleLiveEvent<>();

    public AndroidBaseViewModel(Application application) {
        super(application);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public void notifyChange() {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {}

    public void notifyMessage(int message) {
        notifyMessage(MessageWrapper.withToast(message));
    }

    protected void notifyMessage(MessageWrapper message) {
        mErrorMessageNotifier.setValue(message);
    }

    @Override
    public SingleLiveEvent<ClickItemWrapper> getItemClickListenerNotifier() {
        return mItemClickNotifier;
    }

    public SingleLiveEvent<MessageWrapper> getErrorMessageNotifier() {
        return mErrorMessageNotifier;
    }
}
