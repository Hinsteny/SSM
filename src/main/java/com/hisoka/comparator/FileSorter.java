package com.hisoka.comparator;

import com.hisoka.utils.FileUtil;

import java.io.File;
import java.text.Collator;
import java.util.Comparator;

/**
 * @author Hinsteny
 * @Describtion 文件排序工具类
 * @date 2016/11/11
 * @copyright: 2016 All rights reserved.
 */
public class FileSorter implements Comparator<File>  {

    private static Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);

    public static final int TYPE_DEFAULT = -1;
    //按照修改日期降序排列
    public static final int TYPE_MODIFIED_DATE_DOWN = 1;
    //按照修改日期升序排列
    public static final int TYPE_MODIFIED_DATE_UP = 2;
    //按照文件大小降序排列
    public static final int TYPE_SIZE_DOWN = 3;
    //按照文件大小升序排列
    public static final int TYPE_SIZE_UP = 4;
    //按照名字升序排序
    public static final int TYPE_NAME_UP = 5;
    //按照名字降序排列
    public static final int TYPE_NAME_DOWN = 6;
    //按照扩展名升序排列
    public static final int TYPE_SUFFIX_UP = 7;
    //按照扩展名降序排列
    public static final int TYPE_SUFFIX_DOWN = 8;
    //
    public static final int TYPE_DIR = 9;

    private int mType = 5;

    public FileSorter(int type) {
        if (type < 0 || type > 9) {
            type = TYPE_DIR;
        }
        mType = type;
    }

    @Override
    public int compare(File object1, File object2) {
        int result = 0;
        switch (mType) {

            case TYPE_MODIFIED_DATE_DOWN:// last modified date down
                result = compareByModifiedDateDown(object1, object2);
                break;

            case TYPE_MODIFIED_DATE_UP:// last modified date up
                result = compareByModifiedDateUp(object1, object2);
                break;

            case TYPE_SIZE_DOWN: // file size down
                result = compareBySizeDown(object1, object2);
                break;

            case TYPE_SIZE_UP: // file size up
                result = compareBySizeUp(object1, object2);
                break;

            case TYPE_NAME_UP: // name
                result = compareByNameUp(object1, object2);
                break;
            case TYPE_NAME_DOWN: // name
                result = compareByNameDown(object1, object2);
                break;

            case TYPE_DIR: // dir or file
                result = compareByDir(object1, object2);
                break;

            case TYPE_SUFFIX_DOWN:
                result = compareBySuffixDown(object1, object2);
                break;
            case TYPE_SUFFIX_UP:
                result = compareBySuffixUp(object1, object2);
                break;

            default:
                result = compareByDir(object1, object2);
                break;
        }
        return result;
    }

    private int compareByModifiedDateDown(File object1, File object2) {
        return compareDate(object1, object2, -1);
    }

    private int compareByModifiedDateUp(File object1, File object2) {
        return compareDate(object1, object2, 1);
    }

    private int compareDate(File object1, File object2, int flag) {
        if (object1.isDirectory() && object2.isDirectory()) {
            return compareModifiedDate(object1, object2, flag);
        }
        if (object1.isDirectory() && object2.isFile()) {
            return -1;
        }
        if (object1.isFile() && object2.isDirectory()) {
            return 1;
        }
        return compareModifiedDate(object1, object2, flag);
    }

    private int compareModifiedDate(File object1, File object2, int downOrup) {
        long d1 = object1.lastModified();
        long d2 = object2.lastModified();
        if (d1 == d2) {
            return 0;
        } else {
            return downOrup < 0 ? (d1 < d2 ? 1 : -1) : (d1 > d2 ? 1 : -1);
        }
    }

    private int compareBySizeDown(File object1, File object2) {
        if (object1.isDirectory() && object2.isDirectory()) {
            return calculateFolderSize(object1, object2, -1);
        }
        if (object1.isDirectory() && object2.isFile()) {
            return -1;
        }
        if (object1.isFile() && object2.isDirectory()) {
            return 1;
        }
        long s1 = object1.length();
        long s2 = object2.length();

        if (s1 == s2) {
            return 0;
        } else {
            return s1 < s2 ? 1 : -1;
        }
    }

    private int compareBySizeUp(File object1, File object2) {
        if (object1.isDirectory() && object2.isDirectory()) {
            return calculateFolderSize(object1, object2, 1);
        }
        if (object1.isDirectory() && object2.isFile()) {
            return -1;
        }
        if (object1.isFile() && object2.isDirectory()) {
            return 1;
        }
        long s1 = object1.length();
        long s2 = object2.length();
        if (s1 == s2) {
            return 0;
        } else {
            return s1 > s2 ? 1 : -1;
        }
    }

    private int calculateFolderSize(File object1, File object2, int upOrDown) {
        long s1 = FileUtil.getFileSize(object1);
        long s2 = FileUtil.getFileSize(object2);
        if (s1 == s2) {
            return upOrDown > 0 ? compareByName(object1, object2)
                    : -compareByName(object1, object2);
        } else {
            return upOrDown > 0 ? (s1 > s2 ? 1 : -1) : (s1 < s2 ? 1 : -1);
        }
    }

    private int compareByNameDown(File object1, File object2) {
        if (object1.isDirectory() && object2.isFile()) {
            return -1;
        } else if (object1.isDirectory() && object2.isDirectory()) {
            return -compareByName(object1, object2);
        } else if (object1.isFile() && object2.isDirectory()) {
            return 1;
        } else { // object1.isFile() && object2.isFile())
            return -compareByName(object1, object2);
        }
    }

    private int compareByNameUp(File object1, File object2) {
        if (object1.isDirectory() && object2.isFile()) {
            return -1;
        } else if (object1.isDirectory() && object2.isDirectory()) {
            return compareByName(object1, object2);
        } else if (object1.isFile() && object2.isDirectory()) {
            return 1;
        } else { // object1.isFile() && object2.isFile())
            return compareByName(object1, object2);
        }

    }

    private int compareByName(File object1, File object2) {
        Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
        return cmp.compare(object1.getName(), object2.getName());
    }

    private int compareByDir(File object1, File object2) {
        if (object1.isDirectory() && object2.isFile()) {
            return -1;
        } else if (object1.isDirectory() && object2.isDirectory()) {
            return compareByName(object1, object2);
        } else if (object1.isFile() && object2.isDirectory()) {
            return 1;
        } else { // object1.isFile() && object2.isFile())
            return compareByName(object1, object2);
        }
    }

    private int compareBySuffixUp(File f1, File f2) {

        return compareSuffix(f1, f2, 1);
    }

    private int compareBySuffixDown(File f1, File f2) {
        return compareSuffix(f1, f2, -1);
    }

    private int compareSuffix(File f1, File f2, int upOrdown) {
        if (f1.isDirectory() && f2.isDirectory()) {
            return cmp.compare(f1.getName(), f2.getName());
        }
        if (f1.isDirectory() && f2.isFile()) {
            return -1;
        }
        if (f1.isFile() && f2.isDirectory()) {
            return 1;
        }
        String filename1 = f1.getName();
        String filename2 = f2.getName();
        int s1 = filename1.lastIndexOf(".");
        int s2 = filename2.lastIndexOf(".");
        if (s1 < 0 || s2 < 0) {
            return upOrdown > 0 ? cmp.compare(filename1, filename2) : -cmp.compare(filename1, filename2);
        } else {
            String suffix1 = filename1.substring(s1).trim();
            String suffix2 = filename2.substring(s2).trim();
            return upOrdown > 0 ? cmp.compare(suffix1, suffix2) : -cmp.compare(suffix1, suffix2);
        }
    }

}
