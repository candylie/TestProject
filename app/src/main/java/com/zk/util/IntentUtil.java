package com.zk.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * -
 *
 * @author 张科
 * @date 2019/2/28.
 */
public class IntentUtil {


    public static class Build {
        private boolean isNeedFlag = false;

        private Context context;

        private Intent intent = new Intent();

        private Class<?> cls;


        public static Build newInstance() {
            return new Build();
        }

        public Build setContext(Context context) {
            isNeedFlag = context instanceof Application;
            this.context = context;
            return this;
        }

        public Build setToActivity(Class<?> cls) {
            this.cls = cls;
            return this;
        }

        public void start() {
            intent.setClassName(context, cls.getName());
            if (isNeedFlag) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }


        public Build putExtra(String name, boolean value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, byte value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, char value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, short value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, int value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, long value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, float value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, double value) {
            intent.putExtra(name, value);
            return this;
        }

        public Build putExtra(String name, String value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, CharSequence value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, Parcelable value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, Parcelable[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putStringArrayListExtra(String name, ArrayList<String> value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, Serializable value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, boolean[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, byte[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, short[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, char[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, int[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, long[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, float[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, double[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, String[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, CharSequence[] value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtra(String name, Bundle value) {
            intent.putExtra(name, value);
            return this;
        }


        public Build putExtras(@NonNull Bundle extras) {
            intent.putExtras(extras);
            return this;
        }

        public Build putExtra(Bundle bundle) {
            intent.putExtras(bundle);
            return this;
        }

    }
}
