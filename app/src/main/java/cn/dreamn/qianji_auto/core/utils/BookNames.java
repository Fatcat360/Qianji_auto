/*
 * Copyright (C) 2021 dreamn(dream@dreamn.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.dreamn.qianji_auto.core.utils;

import com.tencent.mmkv.MMKV;

import java.lang.reflect.Array;

import cn.dreamn.qianji_auto.core.db.BookName;
import cn.dreamn.qianji_auto.core.db.DbManger;

public class BookNames {
    public static String getDefault(){
        MMKV mmkv=MMKV.defaultMMKV();
        return mmkv.getString("defaultBookName","默认账本");
    }
    public static  void change(String bookName){
        MMKV mmkv=MMKV.defaultMMKV();
        mmkv.encode("defaultBookName",bookName);
    }
    public static String[] getAll(){
        BookName[] bookNames= DbManger.db.BookNameDao().getAll();
        String[] result = new String[bookNames.length+1];
        for(int i=0;i<bookNames.length;i++){
            result[i]=bookNames[i].name;
        }
        result[bookNames.length]="默认账本";
        return result;
    }

    public  static void del(int id){
        DbManger.db.BookNameDao().del(id);
    }
    public static void add(String bookName){
        DbManger.db.BookNameDao().add(bookName);
    }
}
