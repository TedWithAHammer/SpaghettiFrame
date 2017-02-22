package com.leo.library.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.leo.bulldoglog.BLog;
import com.leo.library.utils.AppUtils;
import com.leo.library.utils.EncryptUtils;
import com.leo.library.utils.ImageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

/**
 * Created by leo on 2017/2/22.
 */
public class DiskLruCacheHelper {
    private static final String DIR_NAME = "diskCache";
    private static final int MAX_COUNT = 5 * 1024 * 1024;
    private static final int DEFAULT_APP_VERSION = 1;

    /**
     * The default valueCount when open DiskLruCache.
     */
    private static final int DEFAULT_VALUE_COUNT = 1;

    private static final String TAG = "DiskLruCacheHelper";

    private DiskLruCache mDiskLruCache;

    public DiskLruCacheHelper(Context context) throws IOException {
        mDiskLruCache = generateCache(context, DIR_NAME, MAX_COUNT);
    }

    public DiskLruCacheHelper(Context context, String dirName) throws IOException {
        mDiskLruCache = generateCache(context, dirName, MAX_COUNT);
    }

    public DiskLruCacheHelper(Context context, String dirName, int maxCount) throws IOException {
        mDiskLruCache = generateCache(context, dirName, maxCount);
    }

    //custom cache dir
    public DiskLruCacheHelper(File dir) throws IOException {
        mDiskLruCache = generateCache(null, dir, MAX_COUNT);
    }

    public DiskLruCacheHelper(Context context, File dir) throws IOException {
        mDiskLruCache = generateCache(context, dir, MAX_COUNT);
    }

    public DiskLruCacheHelper(Context context, File dir, int maxCount) throws IOException {
        mDiskLruCache = generateCache(context, dir, maxCount);
    }

    private DiskLruCache generateCache(Context context, File dir, int maxCount) throws IOException {
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException(
                    dir + " is not a directory or does not exists. ");
        }

        int appVersion = context == null ? DEFAULT_APP_VERSION : AppUtils.getAppVersionCode(context);

        return DiskLruCache.create(FileSystem.SYSTEM,
                dir,
                appVersion,
                DEFAULT_VALUE_COUNT,
                maxCount);
    }

    private DiskLruCache generateCache(Context context, String dirName, int maxCount) throws IOException {
        return DiskLruCache.create(FileSystem.SYSTEM,
                getDiskCacheDir(context, dirName),
                AppUtils.getAppVersionCode(context),
                DEFAULT_VALUE_COUNT,
                maxCount);
    }
    // =======================================
    // ============== String 数据 读写 =============
    // =======================================

    public void put(String key, String value) {
        DiskLruCache.Editor edit = null;
        BufferedSink sink = null;
        try {
            edit = editor(key);
            if (edit == null) return;
            sink = Okio.buffer(edit.newSink(0));
            sink.writeUtf8(value);
            sink.flush();
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                //s
                edit.abort();//write REMOVE
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (sink != null)
                    sink.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAsString(String key) {
        BufferedSource bufferedSource = null;
        Source source = null;
        source = get(key);
        if (source == null) return null;
        String str = null;
        try {
            bufferedSource = Okio.buffer(source);
            str = bufferedSource.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedSource != null)
                    bufferedSource.close();
                source.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }


    public void put(String key, JSONObject jsonObject) {
        put(key, jsonObject.toString());
    }

    public JSONObject getAsJson(String key) {
        String val = getAsString(key);
        try {
            if (val != null)
                return new JSONObject(val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // =======================================
    // ============ JSONArray 数据 读写 =============
    // =======================================

    public void put(String key, JSONArray jsonArray) {
        put(key, jsonArray.toString());
    }

    public JSONArray getAsJSONArray(String key) {
        String JSONString = getAsString(key);
        try {
            JSONArray obj = new JSONArray(JSONString);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // =======================================
    // ============== byte 数据 读写 =============
    // =======================================

    /**
     * 保存 byte数据 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的数据
     */
    public void put(String key, byte[] value) {
        DiskLruCache.Editor editor = null;
        BufferedSink sink = null;
        try {
            editor = editor(key);
            if (editor == null) return;
            sink = Okio.buffer(editor.newSink(0));
            sink.write(value);
            sink.flush();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                editor.abort();//write REMOVE
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } finally {
            try {
                if (sink != null)
                    sink.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public byte[] getAsBytes(String key) {
        byte[] res = new byte[256];
        Source source = null;
        BufferedSource bufferedSource = null;
        source = get(key);
        if (source == null) return null;
        try {
            bufferedSource = Okio.buffer(source);
            bufferedSource.read(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedSource != null)
                    bufferedSource.close();
                source.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }


    // =======================================
    // ============== 序列化 数据 读写 =============
    // =======================================
    public void put(String key, Serializable value) {
        DiskLruCache.Editor editor = editor(key);
        BufferedSink sink = null;
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos = null;
        sink = Okio.buffer(editor.newSink(0));
        if (editor == null) return;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.flush();
            sink.write(bos.toByteArray());
            sink.flush();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (oos != null)
                    oos.close();
                if (bos != null)
                    bos.close();
                sink.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> T getAsSerializable(String key) {
        T t = null;
        Source source = get(key);
        BufferedSource bufferedSource = null;
        ObjectInputStream ois = null;
        if (source == null) return null;
        try {
            bufferedSource = Okio.buffer(source);
            ois = new ObjectInputStream(bufferedSource.inputStream());
            t = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null)
                    ois.close();
                if (bufferedSource != null)
                    bufferedSource.close();
                source.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    // =======================================
    // ============== bitmap 数据 读写 =============
    // =======================================
    public void put(String key, Bitmap bitmap) {
        put(key, ImageUtils.bitmap2Bytes(bitmap));
    }

    public Bitmap getAsBitmap(String key) {
        byte[] bytes = getAsBytes(key);
        if (bytes == null) return null;
        return ImageUtils.bytes2Bitmap(bytes);
    }

    // =======================================
    // ============= drawable 数据 读写 =============
    // =======================================
    public void put(String key, Drawable value) {
        put(key, ImageUtils.drawable2Bitmap(value));
    }

    public Drawable getAsDrawable(String key) {
        byte[] bytes = getAsBytes(key);
        if (bytes == null) {
            return null;
        }
        return ImageUtils.bitmap2Drawable(ImageUtils.bytes2Bitmap(bytes));
    }

    // =======================================
    // ============= other methods =============
    // =======================================
    public boolean remove(String key) {
        try {
            key = EncryptUtils.encryptMD5ToString(key);
            return mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() throws IOException {
        mDiskLruCache.close();
    }

    public void delete() throws IOException {
        mDiskLruCache.delete();
    }

    public void flush() throws IOException {
        mDiskLruCache.flush();
    }

    public boolean isClosed() {
        return mDiskLruCache.isClosed();
    }

    public long size() {
        try {
            return mDiskLruCache.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setMaxSize(long maxSize) {
        mDiskLruCache.setMaxSize(maxSize);
    }

    public File getDirectory() {
        return mDiskLruCache.getDirectory();
    }

    public long getMaxSize() {
        return mDiskLruCache.getMaxSize();
    }


    // =======================================
    // ===遇到文件比较大的，可以直接通过流读写 =====
    // =======================================
    //basic editor
    public DiskLruCache.Editor editor(String key) {
        try {
            key = EncryptUtils.encryptMD2ToString(key);
            //wirte DIRTY
            DiskLruCache.Editor edit = mDiskLruCache.edit(key);
            //edit maybe null :the entry is editing
            if (edit == null) {
                BLog.w(TAG, "the entry spcified key:" + key + " is editing by other . ");
            }
            return edit;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    //basic get
    public Source get(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(EncryptUtils.encryptMD2ToString(key));
            if (snapshot == null) //not find entry , or entry.readable = false
            {
                BLog.e(TAG, "not find entry , or entry.readable = false");
                return null;
            }
            //write READ
            return snapshot.getSource(0);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    // =======================================
    // ============== 序列化 数据 读写 =============
    // =======================================

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

}



