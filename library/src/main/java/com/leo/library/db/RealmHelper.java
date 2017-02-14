package com.leo.library.db;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * 支持简单数据库操作，复杂操作较难封装
 * Created by leo on 2017/2/14.
 */

public class RealmHelper {
    public static <T extends RealmObject> void insertRealmObjectAsync(Realm realm, final T t) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(t);
            }
        });
    }

    public static <T extends RealmObject> RealmResults<T> queryRealmObject(Realm realm, Class cls, Map<String, String> queryCondition) {
        RealmQuery<T> realmQuery = (RealmQuery<T>) realm.where(cls);
        for (String key : queryCondition.keySet()) {
            realmQuery.equalTo(key, queryCondition.get(key));
        }
        return realmQuery.findAll();
    }

    public static <T extends RealmObject> void deleteRealmObject(Realm realm, Class cls, Map<String, String> queryCondition) {
        final RealmResults<T> realmResults = queryRealmObject(realm, cls, queryCondition);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults.deleteAllFromRealm();
            }
        });
    }

}
