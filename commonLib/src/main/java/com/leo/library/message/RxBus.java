package com.leo.library.message;


import java.security.PublicKey;
import java.util.Vector;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by leo on 2017/2/17.
 */

public class RxBus {
    private static volatile RxBus instance;

    private Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                instance = new RxBus();
            }
        }
        return instance;
    }

    /**
     * 发送消息
     *
     * @param tag     消息标记
     * @param content 消息体
     */
    public void rxBusPost(String tag, Object content) {
        bus.onNext(new EventWrapper(tag, content));
    }

    /**
     * 接受消息回掉
     *
     * @param tag 筛选标记
     * @return
     */
    public Observable<Object> rxBusReceiveMsg(final String tag) {
        return bus.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                if (o instanceof EventWrapper) {
                    if (((EventWrapper) o).tag.equals(tag))
                        return true;
                }

                return false;
            }
        }).flatMap(new Func1<Object, Observable<?>>() {
            @Override
            public Observable<?> call(Object o) {
                if (o instanceof EventWrapper) {
                    return Observable.just(((EventWrapper) o).content);
                }
                return null;
            }
        });
    }

    /**
     * 加tag便于筛选消息
     */
    private static class EventWrapper {
        private String tag;
        private Object content;

        EventWrapper(String _tag, Object _content) {
            this.tag = _tag;
            this.content = _content;
        }

        public String getTag() {
            return tag;
        }

        public Object getContent() {
            return content;
        }
    }

}
