package com.olym.mjt.utils;

import java.util.HashMap;

public class CharacterParser
{
    private static final String PINYIN_SEPARATOR = "#";
    private static final String WORD_SEPARATOR = "/";
    public static CharacterParser characterParser;
    public static String[] pystr = { "a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang", "chao", "che", "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan", "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die", "ding", "diu", "dong", "dou", "du", "duan", "dui", "dun", "duo", "e", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng", "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv", "luan", "lue", "lun", "luo", "ma", "mai", "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai", "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan", "nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu", "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha", "shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo" };
    private static int[] pyvalue = { 45217, 45219, 45232, 45241, 45244, 45253, 45271, 45279, 45294, 45306, 45485, 45500, 45504, 45510, 45534, 45546, 45550, 45554, 45560, 45731, 45752, 45761, 45762, 45773, 45780, 45785, 45790, 45795, 45797, 45808, 45811, 45821, 45996, 46005, 46011, 46021, 46036, 46052, 46057, 46069, 46247, 46248, 46255, 46261, 46266, 46273, 46275, 46287, 46293, 46294, 46298, 46301, 46309, 46312, 46318, 46324, 46498, 46513, 46518, 46530, 46533, 46540, 46559, 46575, 46584, 46753, 46762, 46763, 46773, 46780, 46795, 46801, 46805, 46814, 46826, 46839, 46840, 47010, 47018, 47035, 47046, 47058, 47073, 47088, 47089, 47090, 47297, 47299, 47305, 47316, 47325, 47335, 47352, 47353, 47355, 47524, 47539, 47548, 47566, 47572, 47575, 47586, 47589, 47605, 47608, 47614, 47777, 47784, 47803, 47806, 47815, 47833, 47835, 47839, 47844, 47853, 47860, 48040, 48049, 48054, 48068, 48082, 48103, 48109, 48119, 48334, 48351, 48553, 48566, 48594, 48621, 48803, 48828, 48830, 48847, 48872, 48879, 48889, 49062, 49066, 49071, 49077, 49084, 49088, 49103, 49107, 49109, 49113, 49117, 49124, 49129, 49133, 49135, 49143, 49316, 49320, 49324, 49331, 49334, 49349, 49356, 49365, 49367, 49378, 49381, 49577, 49578, 49592, 49603, 49616, 49621, 49633, 49647, 49658, 49829, 49835, 49855, 49869, 49875, 49877, 49884, 49896, 49905, 49911, 50082, 50088, 50100, 50101, 50117, 50120, 50128, 50142, 50151, 50159, 50161, 50167, 50173, 50174, 50353, 50356, 50371, 50378, 50383, 50386, 50387, 50392, 50393, 50395, 50396, 50397, 50408, 50415, 50417, 50419, 50426, 50427, 50595, 50599, 50603, 50606, 50607, 50608, 50610, 50614, 50615, 50622, 50628, 50634, 50642, 50647, 50654, 50663, 50665, 50679, 50858, 50862, 50866, 50868, 50873, 50882, 50891, 50906, 50942, 51107, 51129, 51137, 51152, 51157, 51168, 51181, 51183, 51191, 51366, 51377, 51385, 51387, 51391, 51396, 51399, 51401, 51411, 51413, 51414, 51424, 51427, 51437, 51439, 51442, 51444, 51446, 51449, 51453, 51619, 51622, 51626, 51629, 51630, 51631, 51640, 51642, 51658, 51666, 51677, 51689, 51705, 51878, 51925, 51935, 52130, 52132, 52136, 52138, 52141, 52145, 52149, 52153, 52169, 52177, 52180, 52193, 52196, 52207, 52210, 52218, 52389, 52398, 52416, 52429, 52440, 52441, 52445, 52460, 52468, 52473, 52476, 52648, 52661, 52665, 52676, 52678, 52684, 52687, 52698, 52705, 52707, 52724, 52734, 52929, 52939, 52942, 52951, 52980, 53177, 53190, 53216, 53236, 53416, 53437, 53447, 53462, 53469, 53478, 53497, 53669, 53675, 53689, 53705, 53738, 53755, 53932, 53947, 54000, 54178, 54196, 54197, 54212, 54233, 54439, 54459, 54469, 54481, 54484, 54491, 54495, 54498, 54512, 54516, 54517, 54518, 54522, 54698, 54704, 54721, 54736, 54746, 54756, 54772, 54949, 54992, 55003, 55017, 55205, 55207, 55208, 55214, 55221, 55227, 55229, 55240, 55255, 55262, 55266, 55274, 55276, 55280, 55282 };
    private StringBuilder buffer;
    private HashMap<String, String> polyphoneMap = initDictionary();

    private int getChsAscii(String paramString)
    {
        int k = 0;
        int i = 0;
        int j = i;
        label75:
        do
        {
            try
            {
                paramString = paramString.getBytes("gb2312");
                if (paramString != null)
                {
                    j = i;
                    if (paramString.length <= 2)
                    {
                        j = i;
                        if (paramString.length > 0) {
                            break label75;
                        }
                    }
                }
                j = i;
                throw new RuntimeException("illegal resource string");
            }
            catch (Exception paramString)
            {
                Applog.systemOut("ERROR:ChineseSpelling.class-getChsAscii(String chs)" + paramString);
                i = j;
                return i;
            }
            j = i;
            i = k;
            if (paramString.length == 1) {
                i = paramString[0];
            }
            j = i;
        } while (paramString.length != 2);
        i = paramString[0];
        j = paramString[1];
        return (i + 256) * 256 + (j + 256) - 65536;
    }

    public String convert(String paramString)
    {
        Object localObject = null;
        int j = getChsAscii(paramString);
        if ((j > 0) && (j < 160))
        {
            paramString = String.valueOf((char)j);
            return paramString;
        }
        int i = pyvalue.length - 1;
        for (;;)
        {
            paramString = (String)localObject;
            if (i < 0) {
                break;
            }
            if (pyvalue[i] <= j) {
                return pystr[i];
            }
            i -= 1;
        }
    }

    public String convertWithPolyphone(String paramString)
    {
        Object localObject = null;
        int j = getChsAscii(paramString);
        if ((j > 0) && (j < 160))
        {
            paramString = String.valueOf((char)j);
            return paramString;
        }
        int i = pyvalue.length - 1;
        for (;;)
        {
            paramString = (String)localObject;
            if (i < 0) {
                break;
            }
            if (pyvalue[i] <= j) {
                return pystr[i];
            }
            i -= 1;
        }
    }

    public char getFirstCharater(String paramString)
    {
        return getSelling(paramString).charAt(0);
    }

    public String getSelling(String paramString)
    {
        this.buffer = new StringBuilder();
        int i = 0;
        if (i < paramString.length())
        {
            Object localObject = paramString.substring(i, i + 1);
            if (((String)localObject).getBytes().length >= 2)
            {
                String str = convertWithPolyphone((String)localObject);
                localObject = str;
                if (str == null) {
                    localObject = "unknown";
                }
            }
            for (;;)
            {
                this.buffer.append((String)localObject);
                i += 1;
                break;
            }
        }
        return this.buffer.toString();
    }

    public String getSellingWithPolyphone(String paramString)
    {
        if ((this.polyphoneMap != null) && (this.polyphoneMap.isEmpty())) {
            this.polyphoneMap = initDictionary();
        }
        this.buffer = new StringBuilder();
        int i = 0;
        if (i < paramString.length())
        {
            Object localObject1 = paramString.substring(i, i + 1);
            Object localObject2;
            if (((String)localObject1).getBytes().length >= 2)
            {
                localObject2 = convert((String)localObject1);
                localObject1 = localObject2;
                if (localObject2 == null) {
                    localObject1 = "#";
                }
            }
            for (;;)
            {
                if ((i >= 1) && (i + 1 <= paramString.length()))
                {
                    localObject2 = paramString.substring(i - 1, i + 1);
                    if ((!this.polyphoneMap.containsKey(localObject1)) || (!((String)this.polyphoneMap.get(localObject1)).contains((CharSequence)localObject2))) {}
                }
                Object localObject3 = localObject1;
                localObject2 = localObject3;
                String str;
                if (i <= paramString.length() - 2)
                {
                    str = paramString.substring(i, i + 2);
                    localObject2 = localObject3;
                    if (this.polyphoneMap.containsKey(str)) {
                        localObject2 = (String)this.polyphoneMap.get(str);
                    }
                }
                localObject3 = localObject2;
                if (i >= 1)
                {
                    localObject3 = localObject2;
                    if (i + 2 <= paramString.length())
                    {
                        str = paramString.substring(i - 1, i + 2);
                        localObject3 = localObject2;
                        if (this.polyphoneMap.containsKey(localObject1))
                        {
                            localObject3 = localObject2;
                            if (((String)this.polyphoneMap.get(localObject1)).contains(str)) {
                                localObject3 = localObject1;
                            }
                        }
                    }
                }
                localObject2 = localObject3;
                if (i >= 2)
                {
                    localObject2 = localObject3;
                    if (i + 1 <= paramString.length())
                    {
                        str = paramString.substring(i - 2, i + 1);
                        localObject2 = localObject3;
                        if (this.polyphoneMap.containsKey(localObject1))
                        {
                            localObject2 = localObject3;
                            if (((String)this.polyphoneMap.get(localObject1)).contains(str)) {
                                localObject2 = localObject1;
                            }
                        }
                    }
                }
                localObject3 = localObject2;
                if (i <= paramString.length() - 3)
                {
                    str = paramString.substring(i, i + 3);
                    localObject3 = localObject2;
                    if (this.polyphoneMap.containsKey(localObject1))
                    {
                        localObject3 = localObject2;
                        if (((String)this.polyphoneMap.get(localObject1)).contains(str)) {
                            localObject3 = localObject1;
                        }
                    }
                }
                this.buffer.append((String)localObject3);
                i += 1;
                break;
            }
        }
        return this.buffer.toString().toUpperCase();
    }

    public String getSimpleSelling(String paramString)
    {
        this.buffer = new StringBuilder();
        int i = 0;
        if (i < paramString.length())
        {
            Object localObject = paramString.substring(i, i + 1);
            if (((String)localObject).getBytes().length >= 2)
            {
                String str = convert((String)localObject);
                localObject = str;
                if (str == null) {
                    localObject = "unknown";
                }
            }
            for (;;)
            {
                this.buffer.append(((String)localObject).charAt(0));
                i += 1;
                break;
            }
        }
        return this.buffer.toString();
    }

    public String getSimpleSellingPolyphone(String paramString)
    {
        if ((this.polyphoneMap != null) && (this.polyphoneMap.isEmpty())) {
            this.polyphoneMap = initDictionary();
        }
        this.buffer = new StringBuilder();
        int i = 0;
        if (i < paramString.length())
        {
            Object localObject1 = paramString.substring(i, i + 1);
            if (((String)localObject1).getBytes().length >= 2)
            {
                Object localObject2 = convert((String)localObject1);
                localObject1 = localObject2;
                if (localObject2 == null) {
                    localObject1 = "unknown";
                }
                label83:
                if ((i >= 1) && (i + 1 <= paramString.length()))
                {
                    localObject2 = paramString.substring(i - 1, i + 1);
                    if ((!this.polyphoneMap.containsKey(localObject1)) || (!((String)this.polyphoneMap.get(localObject1)).contains((CharSequence)localObject2))) {}
                }
                Object localObject3 = localObject1;
                localObject2 = localObject3;
                String str;
                if (paramString.contains("������"))
                {
                    localObject2 = localObject3;
                    if (i <= paramString.length() - 2)
                    {
                        str = paramString.substring(i, i + 2);
                        localObject2 = localObject3;
                        if (this.polyphoneMap.containsKey(str)) {
                            localObject2 = (String)this.polyphoneMap.get(str);
                        }
                    }
                }
                localObject3 = localObject2;
                if (i >= 1)
                {
                    localObject3 = localObject2;
                    if (i + 2 <= paramString.length())
                    {
                        str = paramString.substring(i - 1, i + 2);
                        localObject3 = localObject2;
                        if (this.polyphoneMap.containsKey(localObject1))
                        {
                            localObject3 = localObject2;
                            if (((String)this.polyphoneMap.get(localObject1)).contains(str)) {
                                localObject3 = localObject1;
                            }
                        }
                    }
                }
                localObject2 = localObject3;
                if (i >= 2)
                {
                    localObject2 = localObject3;
                    if (i + 1 <= paramString.length())
                    {
                        str = paramString.substring(i - 2, i + 1);
                        localObject2 = localObject3;
                        if (this.polyphoneMap.containsKey(localObject1))
                        {
                            localObject2 = localObject3;
                            if (((String)this.polyphoneMap.get(localObject1)).contains(str)) {
                                localObject2 = localObject1;
                            }
                        }
                    }
                }
                localObject3 = localObject2;
                if (i <= paramString.length() - 3)
                {
                    str = paramString.substring(i, i + 3);
                    localObject3 = localObject2;
                    if (this.polyphoneMap.containsKey(localObject1))
                    {
                        localObject3 = localObject2;
                        if (((String)this.polyphoneMap.get(localObject1)).contains(str)) {
                            localObject3 = localObject1;
                        }
                    }
                }
                if (localObject3 == null) {
                    break label457;
                }
                this.buffer.append(((String)localObject3).charAt(0));
            }
            for (;;)
            {
                i += 1;
                break;
                break label83;
                label457:
                this.buffer.append(((String)localObject1).charAt(0));
            }
        }
        return this.buffer.toString();
    }

    /* Error */
    public HashMap<String, String> initDictionary()
    {
        // Byte code:
        //   0: aconst_null
        //   1: astore 5
        //   3: aconst_null
        //   4: astore 8
        //   6: aconst_null
        //   7: astore 4
        //   9: aconst_null
        //   10: astore 7
        //   12: aconst_null
        //   13: astore 6
        //   15: new 909	java/util/HashMap
        //   18: dup
        //   19: invokespecial 940	java/util/HashMap:<init>	()V
        //   22: astore 9
        //   24: new 942	java/io/InputStreamReader
        //   27: dup
        //   28: invokestatic 948	com/olym/mjt/module/MjtApplication:getInstance	()Lcom/olym/mjt/module/MjtApplication;
        //   31: invokevirtual 952	com/olym/mjt/module/MjtApplication:getResources	()Landroid/content/res/Resources;
        //   34: invokevirtual 958	android/content/res/Resources:getAssets	()Landroid/content/res/AssetManager;
        //   37: ldc_w 960
        //   40: invokevirtual 966	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
        //   43: ldc_w 968
        //   46: invokespecial 971	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
        //   49: astore_3
        //   50: new 973	java/io/BufferedReader
        //   53: dup
        //   54: aload_3
        //   55: invokespecial 976	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
        //   58: astore 4
        //   60: aload 4
        //   62: invokevirtual 979	java/io/BufferedReader:readLine	()Ljava/lang/String;
        //   65: astore 5
        //   67: aload 5
        //   69: ifnull +82 -> 151
        //   72: aload 5
        //   74: ldc 8
        //   76: invokevirtual 983	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
        //   79: astore 5
        //   81: aload_0
        //   82: aload 5
        //   84: iconst_1
        //   85: aaload
        //   86: invokevirtual 986	com/olym/mjt/utils/CharacterParser:isNotEmpty	(Ljava/lang/CharSequence;)Z
        //   89: ifeq -29 -> 60
        //   92: aload 5
        //   94: iconst_1
        //   95: aaload
        //   96: ldc 11
        //   98: invokevirtual 983	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
        //   101: astore 6
        //   103: aload 6
        //   105: arraylength
        //   106: istore_2
        //   107: iconst_0
        //   108: istore_1
        //   109: iload_1
        //   110: iload_2
        //   111: if_icmpge -51 -> 60
        //   114: aload 6
        //   116: iload_1
        //   117: aaload
        //   118: astore 7
        //   120: aload_0
        //   121: aload 7
        //   123: invokevirtual 986	com/olym/mjt/utils/CharacterParser:isNotEmpty	(Ljava/lang/CharSequence;)Z
        //   126: ifeq +18 -> 144
        //   129: aload 9
        //   131: aload 7
        //   133: invokevirtual 989	java/lang/String:trim	()Ljava/lang/String;
        //   136: aload 5
        //   138: iconst_0
        //   139: aaload
        //   140: invokevirtual 993	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   143: pop
        //   144: iload_1
        //   145: iconst_1
        //   146: iadd
        //   147: istore_1
        //   148: goto -39 -> 109
        //   151: aload_3
        //   152: ifnull +7 -> 159
        //   155: aload_3
        //   156: invokevirtual 996	java/io/InputStreamReader:close	()V
        //   159: aload 4
        //   161: ifnull +164 -> 325
        //   164: aload 4
        //   166: invokevirtual 997	java/io/BufferedReader:close	()V
        //   169: aload 9
        //   171: areturn
        //   172: astore_3
        //   173: aload_3
        //   174: invokevirtual 1000	java/io/IOException:printStackTrace	()V
        //   177: goto -18 -> 159
        //   180: astore_3
        //   181: aload_3
        //   182: invokevirtual 1000	java/io/IOException:printStackTrace	()V
        //   185: aload 9
        //   187: areturn
        //   188: astore 7
        //   190: aload 8
        //   192: astore_3
        //   193: aload 6
        //   195: astore 4
        //   197: aload_3
        //   198: astore 5
        //   200: aload 7
        //   202: invokevirtual 1001	java/lang/Exception:printStackTrace	()V
        //   205: aload_3
        //   206: ifnull +7 -> 213
        //   209: aload_3
        //   210: invokevirtual 996	java/io/InputStreamReader:close	()V
        //   213: aload 6
        //   215: ifnull -46 -> 169
        //   218: aload 6
        //   220: invokevirtual 997	java/io/BufferedReader:close	()V
        //   223: aload 9
        //   225: areturn
        //   226: astore_3
        //   227: aload_3
        //   228: invokevirtual 1000	java/io/IOException:printStackTrace	()V
        //   231: aload 9
        //   233: areturn
        //   234: astore_3
        //   235: aload_3
        //   236: invokevirtual 1000	java/io/IOException:printStackTrace	()V
        //   239: goto -26 -> 213
        //   242: astore_3
        //   243: aload 5
        //   245: ifnull +8 -> 253
        //   248: aload 5
        //   250: invokevirtual 996	java/io/InputStreamReader:close	()V
        //   253: aload 4
        //   255: ifnull +8 -> 263
        //   258: aload 4
        //   260: invokevirtual 997	java/io/BufferedReader:close	()V
        //   263: aload_3
        //   264: athrow
        //   265: astore 5
        //   267: aload 5
        //   269: invokevirtual 1000	java/io/IOException:printStackTrace	()V
        //   272: goto -19 -> 253
        //   275: astore 4
        //   277: aload 4
        //   279: invokevirtual 1000	java/io/IOException:printStackTrace	()V
        //   282: goto -19 -> 263
        //   285: astore 6
        //   287: aload 7
        //   289: astore 4
        //   291: aload_3
        //   292: astore 5
        //   294: aload 6
        //   296: astore_3
        //   297: goto -54 -> 243
        //   300: astore 6
        //   302: aload_3
        //   303: astore 5
        //   305: aload 6
        //   307: astore_3
        //   308: goto -65 -> 243
        //   311: astore 7
        //   313: goto -120 -> 193
        //   316: astore 7
        //   318: aload 4
        //   320: astore 6
        //   322: goto -129 -> 193
        //   325: aload 9
        //   327: areturn
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	328	0	this	CharacterParser
        //   108	40	1	i	int
        //   106	6	2	j	int
        //   49	107	3	localInputStreamReader	java.io.InputStreamReader
        //   172	2	3	localIOException1	java.io.IOException
        //   180	2	3	localIOException2	java.io.IOException
        //   192	18	3	localObject1	Object
        //   226	2	3	localIOException3	java.io.IOException
        //   234	2	3	localIOException4	java.io.IOException
        //   242	50	3	localObject2	Object
        //   296	12	3	localObject3	Object
        //   7	252	4	localObject4	Object
        //   275	3	4	localIOException5	java.io.IOException
        //   289	30	4	localObject5	Object
        //   1	248	5	localObject6	Object
        //   265	3	5	localIOException6	java.io.IOException
        //   292	12	5	localObject7	Object
        //   13	206	6	arrayOfString	String[]
        //   285	10	6	localObject8	Object
        //   300	6	6	localObject9	Object
        //   320	1	6	localObject10	Object
        //   10	122	7	localCharSequence	CharSequence
        //   188	100	7	localException1	Exception
        //   311	1	7	localException2	Exception
        //   316	1	7	localException3	Exception
        //   4	187	8	localObject11	Object
        //   22	304	9	localHashMap	HashMap
        // Exception table:
        //   from	to	target	type
        //   155	159	172	java/io/IOException
        //   164	169	180	java/io/IOException
        //   24	50	188	java/lang/Exception
        //   218	223	226	java/io/IOException
        //   209	213	234	java/io/IOException
        //   24	50	242	finally
        //   200	205	242	finally
        //   248	253	265	java/io/IOException
        //   258	263	275	java/io/IOException
        //   50	60	285	finally
        //   60	67	300	finally
        //   72	107	300	finally
        //   120	144	300	finally
        //   50	60	311	java/lang/Exception
        //   60	67	316	java/lang/Exception
        //   72	107	316	java/lang/Exception
        //   120	144	316	java/lang/Exception
    }

    public boolean isEmpty(CharSequence paramCharSequence)
    {
        return (paramCharSequence == null) || (paramCharSequence.length() == 0);
    }

    public boolean isNotEmpty(CharSequence paramCharSequence)
    {
        return !isEmpty(paramCharSequence);
    }
}
