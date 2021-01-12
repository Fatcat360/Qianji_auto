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

package cn.dreamn.qianji_auto.utils.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;

import com.xuexiang.xaop.util.PermissionUtils;

import cn.dreamn.qianji_auto.core.helper.AutoAccessibilityService;


public class Permission {


    static Permission permission;

    public static final int Assist=1;
    public static final int Sms=2;
    public static final int Float=3;
    public static final int Start=4;
    public static final int Battery=5;
    public static final int Lock=6;
    public static final int BatteryIngore=7;
    public static final int Security=8;


    public static Permission getInstance(){
        if(permission==null)permission=new Permission();
        return permission;
    }

    /**
     * 判断是否授权
     * @param permission int
     * @return boolean
     */
    public boolean isGranted(int permission){

        return false;
    }




    @SuppressLint("BatteryLife")
    public void grant(Context context, int permission){
        Intent intent;
        switch (permission){
            case Assist:
                intent = new Intent(android.provider.Settings. ACTION_ACCESSIBILITY_SETTINGS );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case Sms:
                PermissionUtils.permission("android.permission.READ_SMS");
                break;
            case Float:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(context)) {
                        context.startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:cn.dreamn.qianji_auto")));
                    }
                }
                // FloatWindowPermission.getInstance().applyFloatWindowPermission(context);
                break;
            case Start:
                mobileInfoUtil.jumpStartInterface(context);
                break;
            case Battery:
// 将用户引导到系统设置页面
                intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                context.startActivity(intent);
                break;
            case Lock:
                //TODO 直接跳到多任务界面
                break;
            case Security:
                //TODO 留待后期强化
                break;
            case BatteryIngore:
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                    boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
                    //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
                    if(!hasIgnored) {
                        intent = new Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                        intent.setData(Uri.parse("package:"+context.getPackageName()));
                        context.startActivity(intent);
                    }
                }
                break;
            default:break;
        }
    }

    // To check if service is enabled
    public boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service =  "cn.dreamn.qianji_auto/" + AutoAccessibilityService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);

        } catch (Settings.SettingNotFoundException e) {

        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {

            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();


                    if (accessibilityService.equalsIgnoreCase(service)) {

                        return true;
                    }
                }
            }
        } else {
        }

        return false;
    }


}
