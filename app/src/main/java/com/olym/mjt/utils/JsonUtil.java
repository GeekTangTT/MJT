package com.olym.mjt.utils;

import com.lc.methodex.LogFinalUtils;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil
{
    private static char c;
    private static int col;
    private static CharacterIterator it;

    public static void JsonArray2HashMap(JSONArray paramJSONArray, List<Map<?, ?>> paramList)
    {
        int i = 0;
        for (;;)
        {
            if (i < paramJSONArray.length()) {
                try
                {
                    if ((paramJSONArray.get(i) instanceof JSONObject)) {
                        JsonObject2HashMap((JSONObject)paramJSONArray.get(i), paramList);
                    } else if ((paramJSONArray.get(i) instanceof JSONArray)) {
                        JsonArray2HashMap((JSONArray)paramJSONArray.get(i), paramList);
                    }
                }
                catch (JSONException localJSONException)
                {
                    localJSONException.printStackTrace();
                    LogFinalUtils.logForException(localJSONException);
                }
            }
            return;
            i += 1;
        }
    }

    public static void JsonObject2HashMap(JSONObject paramJSONObject, List<Map<?, ?>> paramList)
    {
        Iterator localIterator = paramJSONObject.keys();
        while (localIterator.hasNext())
        {
            try
            {
                String str = (String)localIterator.next();
                Applog.systemOut("key1---" + str + "------" + paramJSONObject.get(str) + (paramJSONObject.get(str) instanceof JSONObject) + paramJSONObject.get(str) + (paramJSONObject.get(str) instanceof JSONArray));
                if (!(paramJSONObject.get(str) instanceof JSONObject)) {
                    break label127;
                }
                JsonObject2HashMap((JSONObject)paramJSONObject.get(str), paramList);
            }
            catch (JSONException localJSONException)
            {
                localJSONException.printStackTrace();
                LogFinalUtils.logForException(localJSONException);
            }
            continue;
            label127:
            if ((paramJSONObject.get(localJSONException) instanceof JSONArray))
            {
                JsonArray2HashMap((JSONArray)paramJSONObject.get(localJSONException), paramList);
            }
            else
            {
                Applog.systemOut("key1:" + localJSONException + "----------jo.get(key1):" + paramJSONObject.get(localJSONException));
                json2HashMap(localJSONException, paramJSONObject.get(localJSONException), paramList);
            }
        }
    }

    private static boolean aggregate(char paramChar1, char paramChar2, boolean paramBoolean)
    {
        if (c != paramChar1) {
            return false;
        }
        nextCharacter();
        skipWhiteSpace();
        if (c == paramChar2)
        {
            nextCharacter();
            return true;
        }
        do
        {
            nextCharacter();
            skipWhiteSpace();
            do
            {
                if (!value()) {
                    break label140;
                }
                skipWhiteSpace();
                if (c != ',') {
                    break;
                }
                nextCharacter();
                skipWhiteSpace();
            } while (!paramBoolean);
            paramChar1 = col;
            if (!string()) {
                return error("string", paramChar1);
            }
            skipWhiteSpace();
        } while (c == ':');
        return error("colon", col);
        if (c == paramChar2)
        {
            nextCharacter();
            return true;
        }
        return error("comma or " + paramChar2, col);
        label140:
        return error("value", col);
    }

    private static boolean array()
    {
        return aggregate('[', ']', false);
    }

    private static boolean error(String paramString, int paramInt)
    {
        return false;
    }

    private static boolean escape()
    {
        int i = col - 1;
        if (" \\\"/bfnrtu".indexOf(c) < 0) {
            return error("escape sequence  \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t  or  \\uxxxx ", i);
        }
        if ((c == 'u') && ((!ishex(nextCharacter())) || (!ishex(nextCharacter())) || (!ishex(nextCharacter())) || (!ishex(nextCharacter())))) {
            return error("unicode escape sequence  \\uxxxx ", i);
        }
        return true;
    }

    private static boolean ishex(char paramChar)
    {
        return "0123456789abcdefABCDEF".indexOf(c) >= 0;
    }

    public static void json2HashMap(String paramString, Object paramObject, List<Map<?, ?>> paramList)
    {
        HashMap localHashMap = new HashMap();
        localHashMap.put(paramString, paramObject);
        paramList.add(localHashMap);
    }

    private static boolean literal(String paramString)
    {
        StringCharacterIterator localStringCharacterIterator = new StringCharacterIterator(paramString);
        int i = localStringCharacterIterator.first();
        if (c != i)
        {
            bool2 = false;
            return bool2;
        }
        int j = col;
        boolean bool2 = true;
        for (i = localStringCharacterIterator.next();; i = localStringCharacterIterator.next())
        {
            boolean bool1 = bool2;
            if (i != 65535)
            {
                if (i != nextCharacter()) {
                    bool1 = false;
                }
            }
            else
            {
                nextCharacter();
                bool2 = bool1;
                if (bool1) {
                    break;
                }
                error("literal " + paramString, j);
                return bool1;
            }
        }
    }

    private static char nextCharacter()
    {
        c = it.next();
        col += 1;
        return c;
    }

    private static boolean number()
    {
        if ((!Character.isDigit(c)) && (c != '-')) {
            return false;
        }
        int i = col;
        if (c == '-') {
            nextCharacter();
        }
        if (c == '0') {
            nextCharacter();
        }
        while (c == '.')
        {
            nextCharacter();
            if (Character.isDigit(c))
            {
                while (Character.isDigit(c)) {
                    nextCharacter();
                }
                if (Character.isDigit(c)) {
                    while (Character.isDigit(c)) {
                        nextCharacter();
                    }
                } else {
                    return error("number", i);
                }
            }
            else
            {
                return error("number", i);
            }
        }
        if ((c == 'e') || (c == 'E'))
        {
            nextCharacter();
            if ((c == '+') || (c == '-')) {
                nextCharacter();
            }
            if (Character.isDigit(c)) {
                while (Character.isDigit(c)) {
                    nextCharacter();
                }
            }
            return error("number", i);
        }
        return true;
    }

    private static boolean object()
    {
        return aggregate('{', '}', true);
    }

    private static void skipWhiteSpace()
    {
        while (Character.isWhitespace(c)) {
            nextCharacter();
        }
    }

    private static boolean string()
    {
        if (c != '"') {
            return false;
        }
        int j = col;
        int i = 0;
        nextCharacter();
        label20:
        if (c != 65535)
        {
            if ((i == 0) && (c == '\\')) {
                i = 1;
            }
            label64:
            do
            {
                for (;;)
                {
                    nextCharacter();
                    break label20;
                    if (i == 0) {
                        break label64;
                    }
                    if (!escape()) {
                        break;
                    }
                    i = 0;
                }
            } while (c != '"');
            nextCharacter();
            return true;
        }
        return error("quoted string", j);
    }

    private static boolean valid(String paramString)
    {
        if ("".equals(paramString)) {}
        do
        {
            return true;
            it = new StringCharacterIterator(paramString);
            c = it.first();
            col = 1;
            if (!value()) {
                return error("value", 1);
            }
            skipWhiteSpace();
        } while (c == 65535);
        return error("end", col);
    }

    public static boolean validate(String paramString)
    {
        return valid(paramString.trim());
    }

    private static boolean value()
    {
        return (literal("true")) || (literal("false")) || (literal("null")) || (string()) || (number()) || (object()) || (array());
    }
}
