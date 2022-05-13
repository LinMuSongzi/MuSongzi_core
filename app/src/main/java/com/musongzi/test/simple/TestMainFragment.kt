package com.musongzi.test.simple

import android.os.Environment
import android.util.Log
import com.musongzi.core.base.fragment.ModelFragment
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.event
import com.musongzi.core.base.manager.ActivityLifeManager.Companion.registerEvent
import com.musongzi.core.databinding.FragmentTestMainBinding
import com.musongzi.test.Enter
import com.musongzi.test.ITestClient
import com.musongzi.test.bean.DiscoverBannerBean
import com.musongzi.test.event.ILoginEvent
import com.musongzi.test.vm.TestViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.*
import java.lang.Math.abs
import java.nio.charset.Charset
import java.text.SimpleDateFormat

class TestMainFragment : ModelFragment<TestViewModel, FragmentTestMainBinding>(), ITestClient,
    ILoginEvent {

    companion object {
        const val MAX_OUNT = 100_000
        const val FOTMAT_DATA = "MM:dd HH:mm:ss:SSS"
    }

    override fun initData() {
        dataBinding.idMainContentTv.setOnClickListener {

            if (!dataBinding.idEdittext.text.isNullOrEmpty()) {
//                var byte = ByteArrayInputStream(dataBinding.idEdittext.text.toStr.toByteArray()ing());

            }

        }

    }

    fun testClick() {
//        Thread {
        val sl = System.currentTimeMillis()
        activity?.runOnUiThread {
            Log.i(
                TAG,
                "initEvent: start ${SimpleDateFormat(FOTMAT_DATA).format(System.currentTimeMillis())}"
            )
        }
//            for (v in 1..MAX_OUNT) {
        ILoginEvent::class.java.event()?.onLogin()
//            IMusicEvent::class.java.event()?.play()
//            EventBus.getDefault().post(DiscoverBannerBean())
//            }
        val el = System.currentTimeMillis()
        activity?.runOnUiThread {
            Log.i(
                TAG,
                "initEvent:   end ${SimpleDateFormat(FOTMAT_DATA).format(System.currentTimeMillis())}"
            )
            Log.i(TAG, "initEvent: time ${abs(sl - el)}")
        }

//        }.start()
    }


    override fun initEvent() {
        dataBinding.idMainContentTv.setOnClickListener {
            testClick()
        }
    }

    var count = 0

    override fun onLogin() {
//        count++
//        if (count == MAX_OUNT) {
        Log.i(TAG, "initEvent onLogin: count = $count")
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(d: DiscoverBannerBean) {
        count++
        if (count == MAX_OUNT) {
            Log.i(TAG, "initEvent onMessage: $count")
        }
    }

    override fun showDialog(msg: String?) {
//        super.showDialog(msg)
//        Log.i(TAG, "EventManger showDialog: msg = $msg ")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        registerEvent(ILoginEvent::class.java) {
            this
        }

        Log.i(TAG, "initView: ${getMainViewModel()}")
        getMainViewModel()?.business?.checkBanner()
    }

    override fun showText(msg: String) {
        val m = "CSDN首页\n" +
                "博客\n" +
                "下载·课程\n" +
                "学习\n" +
                "问答\n" +
                "社区\n" +
                "插件\n" +
                "认证\n" +
                "开源\n" +
                "开发语言\n" +
                " 搜索\n" +
                "\n" +
                "会员中心 \n" +
                "足迹\n" +
                "动态\n" +
                "消息\n" +
                "创作\n" +
                "java用异或进行文档的加密\n" +
                "\n" +
                "小马猿\n" +
                "\n" +
                "于 2022-02-11 21:17:38 发布\n" +
                "\n" +
                "219\n" +
                " 收藏\n" +
                "分类专栏： 小项目 基础 方法 文章标签： java 开发语言\n" +
                "版权\n" +
                "\n" +
                "小项目\n" +
                "同时被 3 个专栏收录\n" +
                "5 篇文章0 订阅\n" +
                "订阅专栏\n" +
                "\n" +
                "基础\n" +
                "21 篇文章0 订阅\n" +
                "订阅专栏\n" +
                "\n" +
                "方法\n" +
                "5 篇文章0 订阅\n" +
                "订阅专栏\n" +
                "1.思路：加密：将一个原始文档和一个密码进行异或操作，得到一个加密二进制文件。\n" +
                "\n" +
                "解密：将加密的文件和用一个密码进行异或操作，得到原始文档。\n" +
                "\n" +
                "2.代码：\n" +
                "\n" +
                "import java.io.*;\n" +
                "import java.nio.charset.StandardCharsets;\n" +
                " \n" +
                "public class FIle {\n" +
                "    private static final int numOfEncAndDec = 0x99; //加密解密秘钥\n" +
                "    private static int dataOfFile = 0; //文件字节内容\n" +
                " \n" +
                "    public static void main(String[] args) {\n" +
                " \n" +
                "        File f1 = new File(\"F:\\\\J\\\\f1.txt\"); //初始文件\n" +
                "        File f = new File(\"F:\\\\J\\\\f.txt\"); //加密文件\n" +
                "        File f2 = new File(\"F:\\\\J\\\\f2.txt\"); //解密文件\n" +
                " \n" +
                "        //创建文件\n" +
                "        try {\n" +
                "            System.out.println(f1.createNewFile());\n" +
                "        } catch (IOException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        try {\n" +
                "            System.out.println(f.createNewFile());\n" +
                "        } catch (IOException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        try {\n" +
                "            System.out.println(f2.createNewFile());\n" +
                "        } catch (IOException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                " \n" +
                "        try {//对文件写入内容\n" +
                "            writeFile(f1,f2);\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        try {//加密操作\n" +
                "            EncFile(f1, f);\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                " \n" +
                "        try {//解密\n" +
                "            DecFile(f,f2);\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                " \n" +
                "    }\n" +
                " \n" +
                "    private static void writeFile(File f1,File f2) throws Exception{\n" +
                "        if(!f1.exists()){\n" +
                "            System.out.println(\"文件不存在\");\n" +
                "            return;\n" +
                "        }\n" +
                "        if(!f2.exists()){\n" +
                "            System.out.println(\"文件不存在\");\n" +
                "            f2.createNewFile();\n" +
                "        }\n" +
                " \n" +
                "        //IO流写入\n" +
                "        FileOutputStream fo1 = new FileOutputStream(f1);\n" +
                "        FileOutputStream fo2 = new FileOutputStream(f2);\n" +
                " \n" +
                "        fo1.write(\"f1.txt的访问\".getBytes(StandardCharsets.UTF_8));\n" +
                "        fo2.write(\"f2.txt对f1.txt加密文档的访问的访问\\n\".getBytes(StandardCharsets.UTF_8));\n" +
                "        fo1.flush();\n" +
                "        fo1.close();\n" +
                "        fo2.flush();\n" +
                "        fo2.close();\n" +
                "    }\n" +
                " \n" +
                "    private static void EncFile(File f1, File f) throws Exception {\n" +
                " \n" +
                "        if(!f1.exists()){//判断是否有此文件\n" +
                "            System.out.println(\"文件不存在\");\n" +
                "            return;\n" +
                "        } \n" +
                "        if(!f.exists()){\n" +
                "            System.out.println(\"文件不存在\");\n" +
                "            f.createNewFile();\n" +
                "        }\n" +
                "        FileInputStream fis  = new FileInputStream(f1);\n" +
                "        FileOutputStream fos = new FileOutputStream(f);\n" +
                "        while ((dataOfFile = fis.read()) > -1) {\n" +
                "            fos.write(dataOfFile^numOfEncAndDec);//对文件进行加密，将文件和秘钥进行异或操作。\n" +
                "        }\n" +
                " \n" +
                "        fis.close();\n" +
                "        fos.flush();\n" +
                "        fos.close();\n" +
                "    }\n" +
                " \n" +
                "    private static void DecFile(File f, File f2) throws Exception {\n" +
                " \n" +
                "        if(!f.exists()){\n" +
                "            System.out.println(\"文件不存在\");\n" +
                "            return;\n" +
                "        }\n" +
                "        if(!f2.exists()){\n" +
                "            System.out.println(\"文件不存在\");\n" +
                "            f2.createNewFile();\n" +
                "        }\n" +
                " \n" +
                "        FileInputStream fis  = new FileInputStream(f);//读取加密文件\n" +
                "        FileOutputStream fos = new FileOutputStream(f2,true);//f2.txt文档可以添加文本\n" +
                " \n" +
                "        while ((dataOfFile = fis.read()) > -1) {\n" +
                "            fos.write(dataOfFile^numOfEncAndDec);//再次进行异操作，提取文件，写入f2.txt.\n" +
                "        }\n" +
                "        //文档关闭\n" +
                "        fis.close();\n" +
                "        fos.flush();\n" +
                "        fos.close();\n" +
                "    }\n" +
                "}\n" +
                "4.结果到文档所在处进行查看,注：没F盘的小伙伴，文档路径可根据自己电脑进行修改。\n" +
                "\n" +
                "小小代码奉上，希望有所帮助。\n" +
                "\n" +
                "\n" +
                "小马猿\n" +
                "关注\n" +
                "\n" +
                "0\n" +
                "\n" +
                "\n" +
                "0\n" +
                "\n" +
                "0\n" +
                "打赏\n" +
                "\n" +
                "专栏目录\n" +
                "【Java学习笔记】\n" +
                "qq_55189924的博客\n" +
                " 192\n" +
                "Java基础语言学习知识点\n" +
                "Java异或对字符进行加密和解密\n" +
                "05-28\n" +
                "class XORTest { public static void main(String args[]){ char a1='欢',a2='迎',a3='下',a4='载'; char secret='8'; a1=(char)(a1^secret); a2=(char)(a2^secret); a3=(char)(a3^secret); a4=(char)(a4^secret); System.out.println(\"密文:\"+a1+a2+a3+a4); a1=(char)(a1^secret); a2=(char)(a2^secret); a3=(char)(a3^secret); a4=(char)(a4^secret); System.out.println(\"原文:\"+a1+a2+a3+a4); } } 下载会有更好的惊喜！！！！！！\n" +
                "参与评论\n" +
                "\n" +
                "\n" +
                "请发表有价值的评论， 博客评论不欢迎灌水，良好的社区氛围需大家一起维护。\n" +
                " \n" +
                "表情包\n" +
                "表情包\n" +
                "...算法的加密和解密_xietansheng的博客_java异或加密解密\n" +
                "4-27\n" +
                "异或加密如果同时知道原文和密文,则对比原文和密文可以推算出密钥,因此异或加密安全性较低,一般只用于简单的加密。 2. 异或加密代码实例 2.1 异或加密工具类封装:XORUtils XORUtils.java完整源码: ...\n" +
                "java异或运算加密_JAVA加密系列(四)- 位运算加密(异或加密)\n" +
                "5-6\n" +
                "使用异或写对称性加密 虽然安全性相对来说没有AES等高,但是优点也是异常突出,加密速度比单向加密都快,消耗的性能非常的小。 public class SecurityUtil { public static void main(String[] args) { ...\n" +
                "java基础之异或运算加密\n" +
                "wxingna的博客\n" +
                " 234\n" +
                "本文将通过一个简单的例子实现java应用异或运算对字符串的加密和解密 import java.util.Scanner; public class Javajiami { public static void main(String arg[]){ Scanner input=new Scanner(System.in); char secret=12...\n" +
                "Java通过异或简单实现加密解密\n" +
                "目前方向Java后端，大三学生，欢迎与我一起学习交流！\n" +
                " 706\n" +
                "这里使用到了getBytes() 将位数组转为String类型 public static String encrypt(String value,char secret){ //字符串转byte数组 byte[] bt=value.getBytes(); //进行遍历加密 for(int i=0;i<bt.length;i++) bt[i]=(byte)(bt[i]...\n" +
                "Java通过异或简单实现加密解密_长路 ㅤ   ...\n" +
                "3-18\n" +
                "publicstaticStringencrypt(String value,charsecret){//字符串转byte数组byte[]bt=value.getBytes();//进行遍历加密for(inti=0;i<bt.length;i++)bt[i]=(byte)(bt[i]^(int)secret);//进行异或运算//将位数组转为String类型Str...\n" +
                "Java IO流 使用异或进行文件的简单加密解密\n" +
                "最新发布\n" +
                "m0_51517626的博客\n" +
                " 77\n" +
                "文件的简单加密解密 文件的简单加密解密一般使用异或操作即相同为0不同为1；两次异或同一个值后，结果还原 1101 异或 1001 — 0100 0100 异或 1001 — 1101 还原 代码实现： public class SecurityTest { public static void main(String[] args) { File srcFile = new File(\"test3.jpg\"); File destFile = new File(\"te\n" +
                "java中用异或方法实现对数据加密解密\n" +
                "delete_bug的博客\n" +
                " 410\n" +
                "如何使输入的数据加密，然后再对该加密数据解密呢，就用异或的方法，代码如下 import java.util.Scanner; public class bb { public static void main(String[] args) { // TODO 自动生成的方法存根 Scanner scan = new Scanner(System.in); System.out.p...\n" +
                "JAVA异或加密\n" +
                "cdc的博客\n" +
                " 2280\n" +
                "JAVA异或加密 int 或者byte类型的数据异或操作后变成另外一个数字，再次和同一个数异或后又变成原来的数字。 例1 /** 加密前数字:3 加密后数字:1 解密后数字:3 加密前字符:abcd加密 加密后字符:c`af爢筄 解密后字符:abcd加密 */ public static void main(String[] args) { int...\n" +
                "java文件加密抑或运算_Java中使用异或运算符实现加密字符串\n" +
                "weixin_30840919的博客\n" +
                " 161\n" +
                "Java中使用异或运算符实现加密字符串通过异或运算符号与一个指定的值进行异或运算，从而改变字符串每个字符的值，这样就可以得到加密后的字符串。import java.util.Scanner;public class Encypt {public static void main(String args[]){Scanner scan = new Scanner(System.in);System....\n" +
                "Java 实现异或加密和解密\n" +
                "阿秦\n" +
                " 4328\n" +
                "/** * 异或加密算法满足如下两个条件时，异或加密算法便是一个安全的加密算法 * 1. 密钥变更频繁 * 2. 密钥的长度大于等于明文的长度 */ public class XORCrypto { private byte[] keyBytes; // 密钥 private int k; public XORCrypto(String key) { ...\n" +
                "java异或加密_java使用异或对文件进行加密解密\n" +
                "weixin_42120275的博客\n" +
                " 296\n" +
                "本文实例为大家分享了java使用异或对文件进行加密解密的具体代码，供大家参考，具体内容如下1.使用异或的方式加密文件的原理一个数异或另一个数两次，结果一定是其本身2.使用异或的原理加密文件/*** 将文件内容加密* 使用异或的方式将a.txt加密复制出一个b.txt，放到同一个文件夹下*/@Testpublic void encryptFile(){FileInputStream in = nul...\n" +
                "java xor_java 简单xor加密\n" +
                "weixin_35476604的博客\n" +
                " 203\n" +
                "java端加密文件package enc;import java.io.FileInputStream;import java.io.FileOutputStream;import java.io.IOException;public class Enc {public void encryptFile(){FileInputStream in = null;FileOutputStream ou...\n" +
                "Java 实现异或(xor)算法的加密和解密\n" +
                "XTS的专栏\n" +
                " 1万+\n" +
                "异或（xor）加密原理 一个整数 a 和任意一个整数 b 异或两次，得到的结果是整数 a 本身，即: a == a ^ b ^ b。这里的 a 就是需要加密的原数据，b 则是密钥。a ^ b 就是加密过程，异或的结果就是加密后的密文；密文 (a ^ b) 再与密钥 b 异或，就是解密过程，得到的结果就是原数据 a 本身。\n" +
                "使用异或进行简单的密码加密（JAVA实现）\n" +
                "小李专栏\n" +
                " 1万+\n" +
                "/**     * 使用异或进行简单的密码加密     * @return String[] 加密后字符串     * @author Administrator     * @since 1.0 2005/11/28     */    public static String setEncrypt(String str){        String sn=\"ziyu\"; //密钥      \n" +
                "Java异或运算（简单的加密，解密）\n" +
                "08-06\n" +
                "Example.java异或运算（简单的加密，解密） PrintErrorAndDebug.java输出错误信息与调试信息 test.java实现两个变量的互换（不借助第3个变量）\n" +
                "异或运算加密字符串（java）\n" +
                "LX__Mr的博客\n" +
                " 813\n" +
                "要求：将输入的一串字符的每个字符ASCII码和0xFF做异或运算,然后输出; 再编程将异或加密的字符串还原为原文。 对于一些字符串，如果不想信息泄露的话，比较常用的一种方法就是对原字符串进行异或运算，再输出转换后的字符串，以达到一个加密效果。 加密思路： ①首先将字符串中的每个字符都转换成对应的ASCII码值。 ②把ASCII码值的十进制转成对应的二进制数。 ③用一个新的字符与原字符串的每一个字符作二进制的异或运算。 ④再将加密之后的二进制转换为对应的十进制。 ⑤把每一个十进制数转化成对应的字符再输出。 代\n" +
                "java对文件简单的加密解密(异或运算)\n" +
                "hgg923的专栏\n" +
                " 7903\n" +
                "简单的加密解密过程：执行第一次加密，执行第二次为解密还原 package xxx; import java.io.File; import java.io.FileInputStream; import java.io.FileOutputStream; import java.io.IOException; import java.io.InputStream; /** * 第一次加密，...\n" +
                "java string加密_Java 字符串的加密与解密\n" +
                "weixin_39542742的博客\n" +
                " 735\n" +
                "该楼层疑似违规已被系统折叠隐藏此楼查看此楼为了保证程序的安全，经常需要用到数据加密的方法。Java 中提供了专门用于加密运算的类和接口。除了使用加密类和接口外，还可以通过多种方式实现字符串的加密。其中常用的就是获取字符串的字节数组，之后对字节数组中的每个字节都进行运算，得到新的内容，这时所获得的字符串与原字符串将不相同，以此达到加密的效果；解密时再将加密字符串进行相反的运算，这样即可得到原字符串...\n" +
                "(Java)文件快速加密（异或加密）\n" +
                "03-08\n" +
                "利用异或加密，但只加密文件的第一个字节，这样子加密速度大大提升，但是不适合加密纯文本的文件，原因可以自己试一试。\n" +
                "Java中的异或^简单用法\n" +
                "热门推荐\n" +
                "xianjianwz的博客\n" +
                " 2万+\n" +
                "    Java中的位运算符中有一个叫做异或的运算符，符号为（^）,其主要是对两个操作数进行位的异或运算，相同取0，相反取1。即两操作数相同时，互相抵消。    举个简单例子：    public class Test    {    public static void main(String[] args){        int a=15;        int b=2;        Sy...\n" +
                "java中使用异或的方式对文件进行加密解密\n" +
                "书生的博客\n" +
                " 2428\n" +
                "1.使用异或的方式加密文件的原理 一个数异或另一个数两次，结果一定是其本身 2.使用异或的原理加密文件 /** * 将文件内容加密 * 使用异或的方式将a.txt加密复制出一个b.txt，放到同一个文件夹下 */ @Test public void encryptFile(){ FileInputStream in\n" +
                "Java 简单的异或加密\n" +
                "hsnzmt的博客\n" +
                " 171\n" +
                "简单的异或加密，用于前后端数据传输加密 1、加密String /** * 加密 String 类型 * * @param content * @param secretKey * @return */ public static String encryptByDES(String content, String secretKey) { byte[] d...\n" +
                "“相关推荐”对你有帮助么？\n" +
                "\n" +
                "非常没帮助\n" +
                "\n" +
                "没帮助\n" +
                "\n" +
                "一般\n" +
                "\n" +
                "有帮助\n" +
                "\n" +
                "非常有帮助\n" +
                "©️2022 CSDN 皮肤主题：游动-白 设计师：我叫白小胖 返回首页\n" +
                "关于我们\n" +
                "招贤纳士\n" +
                "商务合作\n" +
                "寻求报道\n" +
                "\n" +
                "400-660-0108\n" +
                "\n" +
                "kefu@csdn.net\n" +
                "\n" +
                "在线客服\n" +
                "工作时间 8:30-22:00\n" +
                "公安备案号11010502030143\n" +
                "京ICP备19004658号\n" +
                "京网文〔2020〕1039-165号\n" +
                "经营性网站备案信息\n" +
                "北京互联网违法和不良信息举报中心\n" +
                "家长监护\n" +
                "网络110报警服务\n" +
                "中国互联网举报中心\n" +
                "Chrome商店下载\n" +
                "©1999-2022北京创新乐知网络技术有限公司\n" +
                "版权与免责声明\n" +
                "版权申诉\n" +
                "出版物许可证\n" +
                "营业执照\n" +
                "\n" +
                "小马猿\n" +
                "码龄1年\n" +
                " 暂无认证\n" +
                "27\n" +
                "原创\n" +
                "96万+\n" +
                "周排名\n" +
                "9万+\n" +
                "总排名\n" +
                "1万+\n" +
                "访问\n" +
                "\n" +
                "等级\n" +
                "272\n" +
                "积分\n" +
                "1\n" +
                "粉丝\n" +
                "2\n" +
                "获赞\n" +
                "0\n" +
                "评论\n" +
                "6\n" +
                "收藏\n" +
                "新秀勋章\n" +
                "持之以恒\n" +
                "勤写标兵Lv4\n" +
                "私信\n" +
                "关注\n" +
                "搜博主文章\n" +
                "\n" +
                "热门文章\n" +
                "Java网络编程五子棋，具有玩家转态，登陆界面，多人房间对战，悔棋，求和，认输，聊天室，自动判断输赢等功能。  1966\n" +
                "Java实现单人版中国象棋小游戏的实现，具有时间设置，认输，悔棋，求和，自动判断输赢功能。  1956\n" +
                "【C++】C++斐波那契数列算法  607\n" +
                "【c++】c++一维数组查找最小值最大值  462\n" +
                "Java实行ArrayList集合反转  480\n" +
                "您愿意向朋友推荐“博客详情页”吗？\n" +
                "\n" +
                "强烈不推荐\n" +
                "\n" +
                "不推荐\n" +
                "\n" +
                "一般般\n" +
                "\n" +
                "推荐\n" +
                "\n" +
                "强烈推荐\n" +
                "最新文章\n" +
                "Java实现简单的计算器，包含加，减，乘，除，括号处理\n" +
                "Java实行ArrayList集合反转\n" +
                "java用异或找出1到1000内唯一重复的一对数\n" +
                "2022年27篇\n" +
                "\n" +
                " \n" +
                "\n" +
                "分类专栏\n" +
                "\n" +
                "栈\n" +
                "2篇\n" +
                "\n" +
                "方法\n" +
                "5篇\n" +
                "\n" +
                "基础\n" +
                "21篇\n" +
                "\n" +
                "小项目\n" +
                "5篇\n" +
                "\n" +
                "实践\n" +
                "3篇\n" +
                "\n" +
                "系统\n" +
                "2篇\n" +
                "\n" +
                "\n" +
                "举报\n" +
                "\n";
        Thread {
            var file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + File.separator + "加密.txt"
            )
            if (file.exists()) {
                file.delete()
            }else{
                file.createNewFile()
            }
            val outputStream = FileOutputStream(file, true)
            Log.i(TAG, "showText: 开始 ->$m")
            Enter.jiami(ByteArrayInputStream(m.toByteArray()), outputStream,true)

            Log.i(TAG, "Enter_now *********\n**************")
            var file解密 = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + File.separator + "解密.txt"
            )
            if (file解密.exists()) {
                file解密.delete()
            }else{
                file解密.createNewFile()
            }
            val bOut = FileOutputStream(file解密, true)

//            val bOut = ByteArrayOutputStream();
            Enter.jiami(FileInputStream(file), bOut,false)
            bOut.close()
//            Log.i(TAG, "Enter_now:输出 " + bOut.toString(Charsets.UTF_8.name()))

        }.start()
    }


    override fun onLogout() {

    }


}