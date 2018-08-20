package cn.com.ukey;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class Base64
{
    public static final int DECODE = 0;
    public static final int DONT_GUNZIP = 4;
    public static final int DO_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    public static final int URL_SAFE = 16;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte[] _ORDERED_ALPHABET;
    private static final byte[] _ORDERED_DECODABET;
    private static final byte[] _STANDARD_ALPHABET;
    private static final byte[] _STANDARD_DECODABET;
    private static final byte[] _URL_SAFE_ALPHABET;
    private static final byte[] _URL_SAFE_DECODABET;

    static
    {
        if (!Base64.class.desiredAssertionStatus()) {}
        for (boolean bool = true;; bool = false)
        {
            $assertionsDisabled = bool;
            _STANDARD_ALPHABET = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
            byte[] arrayOfByte = new byte['��'];
            arrayOfByte[0] = -9;
            arrayOfByte[1] = -9;
            arrayOfByte[2] = -9;
            arrayOfByte[3] = -9;
            arrayOfByte[4] = -9;
            arrayOfByte[5] = -9;
            arrayOfByte[6] = -9;
            arrayOfByte[7] = -9;
            arrayOfByte[8] = -9;
            arrayOfByte[9] = -5;
            arrayOfByte[10] = -5;
            arrayOfByte[11] = -9;
            arrayOfByte[12] = -9;
            arrayOfByte[13] = -5;
            arrayOfByte[14] = -9;
            arrayOfByte[15] = -9;
            arrayOfByte[16] = -9;
            arrayOfByte[17] = -9;
            arrayOfByte[18] = -9;
            arrayOfByte[19] = -9;
            arrayOfByte[20] = -9;
            arrayOfByte[21] = -9;
            arrayOfByte[22] = -9;
            arrayOfByte[23] = -9;
            arrayOfByte[24] = -9;
            arrayOfByte[25] = -9;
            arrayOfByte[26] = -9;
            arrayOfByte[27] = -9;
            arrayOfByte[28] = -9;
            arrayOfByte[29] = -9;
            arrayOfByte[30] = -9;
            arrayOfByte[31] = -9;
            arrayOfByte[32] = -5;
            arrayOfByte[33] = -9;
            arrayOfByte[34] = -9;
            arrayOfByte[35] = -9;
            arrayOfByte[36] = -9;
            arrayOfByte[37] = -9;
            arrayOfByte[38] = -9;
            arrayOfByte[39] = -9;
            arrayOfByte[40] = -9;
            arrayOfByte[41] = -9;
            arrayOfByte[42] = -9;
            arrayOfByte[43] = 62;
            arrayOfByte[44] = -9;
            arrayOfByte[45] = -9;
            arrayOfByte[46] = -9;
            arrayOfByte[47] = 63;
            arrayOfByte[48] = 52;
            arrayOfByte[49] = 53;
            arrayOfByte[50] = 54;
            arrayOfByte[51] = 55;
            arrayOfByte[52] = 56;
            arrayOfByte[53] = 57;
            arrayOfByte[54] = 58;
            arrayOfByte[55] = 59;
            arrayOfByte[56] = 60;
            arrayOfByte[57] = 61;
            arrayOfByte[58] = -9;
            arrayOfByte[59] = -9;
            arrayOfByte[60] = -9;
            arrayOfByte[61] = -1;
            arrayOfByte[62] = -9;
            arrayOfByte[63] = -9;
            arrayOfByte[64] = -9;
            arrayOfByte[66] = 1;
            arrayOfByte[67] = 2;
            arrayOfByte[68] = 3;
            arrayOfByte[69] = 4;
            arrayOfByte[70] = 5;
            arrayOfByte[71] = 6;
            arrayOfByte[72] = 7;
            arrayOfByte[73] = 8;
            arrayOfByte[74] = 9;
            arrayOfByte[75] = 10;
            arrayOfByte[76] = 11;
            arrayOfByte[77] = 12;
            arrayOfByte[78] = 13;
            arrayOfByte[79] = 14;
            arrayOfByte[80] = 15;
            arrayOfByte[81] = 16;
            arrayOfByte[82] = 17;
            arrayOfByte[83] = 18;
            arrayOfByte[84] = 19;
            arrayOfByte[85] = 20;
            arrayOfByte[86] = 21;
            arrayOfByte[87] = 22;
            arrayOfByte[88] = 23;
            arrayOfByte[89] = 24;
            arrayOfByte[90] = 25;
            arrayOfByte[91] = -9;
            arrayOfByte[92] = -9;
            arrayOfByte[93] = -9;
            arrayOfByte[94] = -9;
            arrayOfByte[95] = -9;
            arrayOfByte[96] = -9;
            arrayOfByte[97] = 26;
            arrayOfByte[98] = 27;
            arrayOfByte[99] = 28;
            arrayOfByte[100] = 29;
            arrayOfByte[101] = 30;
            arrayOfByte[102] = 31;
            arrayOfByte[103] = 32;
            arrayOfByte[104] = 33;
            arrayOfByte[105] = 34;
            arrayOfByte[106] = 35;
            arrayOfByte[107] = 36;
            arrayOfByte[108] = 37;
            arrayOfByte[109] = 38;
            arrayOfByte[110] = 39;
            arrayOfByte[111] = 40;
            arrayOfByte[112] = 41;
            arrayOfByte[113] = 42;
            arrayOfByte[114] = 43;
            arrayOfByte[115] = 44;
            arrayOfByte[116] = 45;
            arrayOfByte[117] = 46;
            arrayOfByte[118] = 47;
            arrayOfByte[119] = 48;
            arrayOfByte[120] = 49;
            arrayOfByte[121] = 50;
            arrayOfByte[122] = 51;
            arrayOfByte[123] = -9;
            arrayOfByte[124] = -9;
            arrayOfByte[125] = -9;
            arrayOfByte[126] = -9;
            arrayOfByte[127] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            _STANDARD_DECODABET = arrayOfByte;
            _URL_SAFE_ALPHABET = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
            arrayOfByte = new byte['��'];
            arrayOfByte[0] = -9;
            arrayOfByte[1] = -9;
            arrayOfByte[2] = -9;
            arrayOfByte[3] = -9;
            arrayOfByte[4] = -9;
            arrayOfByte[5] = -9;
            arrayOfByte[6] = -9;
            arrayOfByte[7] = -9;
            arrayOfByte[8] = -9;
            arrayOfByte[9] = -5;
            arrayOfByte[10] = -5;
            arrayOfByte[11] = -9;
            arrayOfByte[12] = -9;
            arrayOfByte[13] = -5;
            arrayOfByte[14] = -9;
            arrayOfByte[15] = -9;
            arrayOfByte[16] = -9;
            arrayOfByte[17] = -9;
            arrayOfByte[18] = -9;
            arrayOfByte[19] = -9;
            arrayOfByte[20] = -9;
            arrayOfByte[21] = -9;
            arrayOfByte[22] = -9;
            arrayOfByte[23] = -9;
            arrayOfByte[24] = -9;
            arrayOfByte[25] = -9;
            arrayOfByte[26] = -9;
            arrayOfByte[27] = -9;
            arrayOfByte[28] = -9;
            arrayOfByte[29] = -9;
            arrayOfByte[30] = -9;
            arrayOfByte[31] = -9;
            arrayOfByte[32] = -5;
            arrayOfByte[33] = -9;
            arrayOfByte[34] = -9;
            arrayOfByte[35] = -9;
            arrayOfByte[36] = -9;
            arrayOfByte[37] = -9;
            arrayOfByte[38] = -9;
            arrayOfByte[39] = -9;
            arrayOfByte[40] = -9;
            arrayOfByte[41] = -9;
            arrayOfByte[42] = -9;
            arrayOfByte[43] = -9;
            arrayOfByte[44] = -9;
            arrayOfByte[45] = 62;
            arrayOfByte[46] = -9;
            arrayOfByte[47] = -9;
            arrayOfByte[48] = 52;
            arrayOfByte[49] = 53;
            arrayOfByte[50] = 54;
            arrayOfByte[51] = 55;
            arrayOfByte[52] = 56;
            arrayOfByte[53] = 57;
            arrayOfByte[54] = 58;
            arrayOfByte[55] = 59;
            arrayOfByte[56] = 60;
            arrayOfByte[57] = 61;
            arrayOfByte[58] = -9;
            arrayOfByte[59] = -9;
            arrayOfByte[60] = -9;
            arrayOfByte[61] = -1;
            arrayOfByte[62] = -9;
            arrayOfByte[63] = -9;
            arrayOfByte[64] = -9;
            arrayOfByte[66] = 1;
            arrayOfByte[67] = 2;
            arrayOfByte[68] = 3;
            arrayOfByte[69] = 4;
            arrayOfByte[70] = 5;
            arrayOfByte[71] = 6;
            arrayOfByte[72] = 7;
            arrayOfByte[73] = 8;
            arrayOfByte[74] = 9;
            arrayOfByte[75] = 10;
            arrayOfByte[76] = 11;
            arrayOfByte[77] = 12;
            arrayOfByte[78] = 13;
            arrayOfByte[79] = 14;
            arrayOfByte[80] = 15;
            arrayOfByte[81] = 16;
            arrayOfByte[82] = 17;
            arrayOfByte[83] = 18;
            arrayOfByte[84] = 19;
            arrayOfByte[85] = 20;
            arrayOfByte[86] = 21;
            arrayOfByte[87] = 22;
            arrayOfByte[88] = 23;
            arrayOfByte[89] = 24;
            arrayOfByte[90] = 25;
            arrayOfByte[91] = -9;
            arrayOfByte[92] = -9;
            arrayOfByte[93] = -9;
            arrayOfByte[94] = -9;
            arrayOfByte[95] = 63;
            arrayOfByte[96] = -9;
            arrayOfByte[97] = 26;
            arrayOfByte[98] = 27;
            arrayOfByte[99] = 28;
            arrayOfByte[100] = 29;
            arrayOfByte[101] = 30;
            arrayOfByte[102] = 31;
            arrayOfByte[103] = 32;
            arrayOfByte[104] = 33;
            arrayOfByte[105] = 34;
            arrayOfByte[106] = 35;
            arrayOfByte[107] = 36;
            arrayOfByte[108] = 37;
            arrayOfByte[109] = 38;
            arrayOfByte[110] = 39;
            arrayOfByte[111] = 40;
            arrayOfByte[112] = 41;
            arrayOfByte[113] = 42;
            arrayOfByte[114] = 43;
            arrayOfByte[115] = 44;
            arrayOfByte[116] = 45;
            arrayOfByte[117] = 46;
            arrayOfByte[118] = 47;
            arrayOfByte[119] = 48;
            arrayOfByte[120] = 49;
            arrayOfByte[121] = 50;
            arrayOfByte[122] = 51;
            arrayOfByte[123] = -9;
            arrayOfByte[124] = -9;
            arrayOfByte[125] = -9;
            arrayOfByte[126] = -9;
            arrayOfByte[127] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            _URL_SAFE_DECODABET = arrayOfByte;
            _ORDERED_ALPHABET = new byte[] { 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
            arrayOfByte = new byte['��'];
            arrayOfByte[0] = -9;
            arrayOfByte[1] = -9;
            arrayOfByte[2] = -9;
            arrayOfByte[3] = -9;
            arrayOfByte[4] = -9;
            arrayOfByte[5] = -9;
            arrayOfByte[6] = -9;
            arrayOfByte[7] = -9;
            arrayOfByte[8] = -9;
            arrayOfByte[9] = -5;
            arrayOfByte[10] = -5;
            arrayOfByte[11] = -9;
            arrayOfByte[12] = -9;
            arrayOfByte[13] = -5;
            arrayOfByte[14] = -9;
            arrayOfByte[15] = -9;
            arrayOfByte[16] = -9;
            arrayOfByte[17] = -9;
            arrayOfByte[18] = -9;
            arrayOfByte[19] = -9;
            arrayOfByte[20] = -9;
            arrayOfByte[21] = -9;
            arrayOfByte[22] = -9;
            arrayOfByte[23] = -9;
            arrayOfByte[24] = -9;
            arrayOfByte[25] = -9;
            arrayOfByte[26] = -9;
            arrayOfByte[27] = -9;
            arrayOfByte[28] = -9;
            arrayOfByte[29] = -9;
            arrayOfByte[30] = -9;
            arrayOfByte[31] = -9;
            arrayOfByte[32] = -5;
            arrayOfByte[33] = -9;
            arrayOfByte[34] = -9;
            arrayOfByte[35] = -9;
            arrayOfByte[36] = -9;
            arrayOfByte[37] = -9;
            arrayOfByte[38] = -9;
            arrayOfByte[39] = -9;
            arrayOfByte[40] = -9;
            arrayOfByte[41] = -9;
            arrayOfByte[42] = -9;
            arrayOfByte[43] = -9;
            arrayOfByte[44] = -9;
            arrayOfByte[46] = -9;
            arrayOfByte[47] = -9;
            arrayOfByte[48] = 1;
            arrayOfByte[49] = 2;
            arrayOfByte[50] = 3;
            arrayOfByte[51] = 4;
            arrayOfByte[52] = 5;
            arrayOfByte[53] = 6;
            arrayOfByte[54] = 7;
            arrayOfByte[55] = 8;
            arrayOfByte[56] = 9;
            arrayOfByte[57] = 10;
            arrayOfByte[58] = -9;
            arrayOfByte[59] = -9;
            arrayOfByte[60] = -9;
            arrayOfByte[61] = -1;
            arrayOfByte[62] = -9;
            arrayOfByte[63] = -9;
            arrayOfByte[64] = -9;
            arrayOfByte[65] = 11;
            arrayOfByte[66] = 12;
            arrayOfByte[67] = 13;
            arrayOfByte[68] = 14;
            arrayOfByte[69] = 15;
            arrayOfByte[70] = 16;
            arrayOfByte[71] = 17;
            arrayOfByte[72] = 18;
            arrayOfByte[73] = 19;
            arrayOfByte[74] = 20;
            arrayOfByte[75] = 21;
            arrayOfByte[76] = 22;
            arrayOfByte[77] = 23;
            arrayOfByte[78] = 24;
            arrayOfByte[79] = 25;
            arrayOfByte[80] = 26;
            arrayOfByte[81] = 27;
            arrayOfByte[82] = 28;
            arrayOfByte[83] = 29;
            arrayOfByte[84] = 30;
            arrayOfByte[85] = 31;
            arrayOfByte[86] = 32;
            arrayOfByte[87] = 33;
            arrayOfByte[88] = 34;
            arrayOfByte[89] = 35;
            arrayOfByte[90] = 36;
            arrayOfByte[91] = -9;
            arrayOfByte[92] = -9;
            arrayOfByte[93] = -9;
            arrayOfByte[94] = -9;
            arrayOfByte[95] = 37;
            arrayOfByte[96] = -9;
            arrayOfByte[97] = 38;
            arrayOfByte[98] = 39;
            arrayOfByte[99] = 40;
            arrayOfByte[100] = 41;
            arrayOfByte[101] = 42;
            arrayOfByte[102] = 43;
            arrayOfByte[103] = 44;
            arrayOfByte[104] = 45;
            arrayOfByte[105] = 46;
            arrayOfByte[106] = 47;
            arrayOfByte[107] = 48;
            arrayOfByte[108] = 49;
            arrayOfByte[109] = 50;
            arrayOfByte[110] = 51;
            arrayOfByte[111] = 52;
            arrayOfByte[112] = 53;
            arrayOfByte[113] = 54;
            arrayOfByte[114] = 55;
            arrayOfByte[115] = 56;
            arrayOfByte[116] = 57;
            arrayOfByte[117] = 58;
            arrayOfByte[118] = 59;
            arrayOfByte[119] = 60;
            arrayOfByte[120] = 61;
            arrayOfByte[121] = 62;
            arrayOfByte[122] = 63;
            arrayOfByte[123] = -9;
            arrayOfByte[124] = -9;
            arrayOfByte[125] = -9;
            arrayOfByte[126] = -9;
            arrayOfByte[127] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            arrayOfByte['��'] = -9;
            _ORDERED_DECODABET = arrayOfByte;
            return;
        }
    }

    public static byte[] decode(String paramString)
            throws IOException
    {
        return decode(paramString, 0);
    }

    /* Error */
    public static byte[] decode(String paramString, int paramInt)
            throws IOException
    {
        // Byte code:
        //   0: aload_0
        //   1: ifnonnull +13 -> 14
        //   4: new 176	java/lang/NullPointerException
        //   7: dup
        //   8: ldc -78
        //   10: invokespecial 181	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
        //   13: athrow
        //   14: aload_0
        //   15: ldc 41
        //   17: invokevirtual 186	java/lang/String:getBytes	(Ljava/lang/String;)[B
        //   20: astore_2
        //   21: aload_2
        //   22: astore_0
        //   23: aload_0
        //   24: iconst_0
        //   25: aload_0
        //   26: arraylength
        //   27: iload_1
        //   28: invokestatic 189	cn/com/ukey/Base64:decode	([BIII)[B
        //   31: astore 8
        //   33: iload_1
        //   34: iconst_4
        //   35: iand
        //   36: ifeq +159 -> 195
        //   39: iconst_1
        //   40: istore_1
        //   41: aload 8
        //   43: astore 4
        //   45: aload 8
        //   47: ifnull +136 -> 183
        //   50: aload 8
        //   52: astore 4
        //   54: aload 8
        //   56: arraylength
        //   57: iconst_4
        //   58: if_icmplt +125 -> 183
        //   61: aload 8
        //   63: astore 4
        //   65: iload_1
        //   66: ifne +117 -> 183
        //   69: aload 8
        //   71: astore 4
        //   73: ldc -66
        //   75: aload 8
        //   77: iconst_0
        //   78: baload
        //   79: sipush 255
        //   82: iand
        //   83: aload 8
        //   85: iconst_1
        //   86: baload
        //   87: bipush 8
        //   89: ishl
        //   90: ldc -65
        //   92: iand
        //   93: ior
        //   94: if_icmpne +89 -> 183
        //   97: aconst_null
        //   98: astore 4
        //   100: aconst_null
        //   101: astore 10
        //   103: aconst_null
        //   104: astore_3
        //   105: aconst_null
        //   106: astore 6
        //   108: aconst_null
        //   109: astore 9
        //   111: aconst_null
        //   112: astore 7
        //   114: aconst_null
        //   115: astore 5
        //   117: aconst_null
        //   118: astore 11
        //   120: sipush 2048
        //   123: newarray <illegal type>
        //   125: astore 12
        //   127: new 193	java/io/ByteArrayOutputStream
        //   130: dup
        //   131: invokespecial 194	java/io/ByteArrayOutputStream:<init>	()V
        //   134: astore_0
        //   135: new 196	java/io/ByteArrayInputStream
        //   138: dup
        //   139: aload 8
        //   141: invokespecial 199	java/io/ByteArrayInputStream:<init>	([B)V
        //   144: astore_2
        //   145: new 201	java/util/zip/GZIPInputStream
        //   148: dup
        //   149: aload_2
        //   150: invokespecial 204	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
        //   153: astore_3
        //   154: aload_3
        //   155: aload 12
        //   157: invokevirtual 208	java/util/zip/GZIPInputStream:read	([B)I
        //   160: istore_1
        //   161: iload_1
        //   162: ifge +38 -> 200
        //   165: aload_0
        //   166: invokevirtual 212	java/io/ByteArrayOutputStream:toByteArray	()[B
        //   169: astore 4
        //   171: aload_0
        //   172: invokevirtual 215	java/io/ByteArrayOutputStream:close	()V
        //   175: aload_3
        //   176: invokevirtual 216	java/util/zip/GZIPInputStream:close	()V
        //   179: aload_2
        //   180: invokevirtual 217	java/io/ByteArrayInputStream:close	()V
        //   183: aload 4
        //   185: areturn
        //   186: astore_2
        //   187: aload_0
        //   188: invokevirtual 219	java/lang/String:getBytes	()[B
        //   191: astore_0
        //   192: goto -169 -> 23
        //   195: iconst_0
        //   196: istore_1
        //   197: goto -156 -> 41
        //   200: aload_0
        //   201: aload 12
        //   203: iconst_0
        //   204: iload_1
        //   205: invokevirtual 223	java/io/ByteArrayOutputStream:write	([BII)V
        //   208: goto -54 -> 154
        //   211: astore 4
        //   213: aload_3
        //   214: astore 7
        //   216: aload 4
        //   218: astore_3
        //   219: aload_2
        //   220: astore 4
        //   222: aload_0
        //   223: astore 5
        //   225: aload 7
        //   227: astore 6
        //   229: aload_3
        //   230: invokevirtual 226	java/io/IOException:printStackTrace	()V
        //   233: aload_0
        //   234: invokevirtual 215	java/io/ByteArrayOutputStream:close	()V
        //   237: aload 7
        //   239: invokevirtual 216	java/util/zip/GZIPInputStream:close	()V
        //   242: aload_2
        //   243: invokevirtual 217	java/io/ByteArrayInputStream:close	()V
        //   246: aload 8
        //   248: areturn
        //   249: astore_0
        //   250: aload 8
        //   252: areturn
        //   253: astore_3
        //   254: aload 5
        //   256: astore_2
        //   257: aload 4
        //   259: astore_0
        //   260: aload_2
        //   261: invokevirtual 215	java/io/ByteArrayOutputStream:close	()V
        //   264: aload 6
        //   266: invokevirtual 216	java/util/zip/GZIPInputStream:close	()V
        //   269: aload_0
        //   270: invokevirtual 217	java/io/ByteArrayInputStream:close	()V
        //   273: aload_3
        //   274: athrow
        //   275: astore_0
        //   276: goto -39 -> 237
        //   279: astore_0
        //   280: goto -38 -> 242
        //   283: astore_2
        //   284: goto -20 -> 264
        //   287: astore_2
        //   288: goto -19 -> 269
        //   291: astore_0
        //   292: goto -19 -> 273
        //   295: astore_0
        //   296: goto -121 -> 175
        //   299: astore_0
        //   300: goto -121 -> 179
        //   303: astore_0
        //   304: aload 4
        //   306: areturn
        //   307: astore_3
        //   308: aload_0
        //   309: astore_2
        //   310: aload 10
        //   312: astore_0
        //   313: aload 9
        //   315: astore 6
        //   317: goto -57 -> 260
        //   320: astore 4
        //   322: aload_0
        //   323: astore_3
        //   324: aload_2
        //   325: astore_0
        //   326: aload_3
        //   327: astore_2
        //   328: aload 9
        //   330: astore 6
        //   332: aload 4
        //   334: astore_3
        //   335: goto -75 -> 260
        //   338: astore 5
        //   340: aload_0
        //   341: astore 4
        //   343: aload_2
        //   344: astore_0
        //   345: aload 4
        //   347: astore_2
        //   348: aload_3
        //   349: astore 6
        //   351: aload 5
        //   353: astore_3
        //   354: goto -94 -> 260
        //   357: astore 4
        //   359: aload_3
        //   360: astore_2
        //   361: aload 11
        //   363: astore_0
        //   364: aload 4
        //   366: astore_3
        //   367: goto -148 -> 219
        //   370: astore 4
        //   372: aload_3
        //   373: astore_2
        //   374: aload 4
        //   376: astore_3
        //   377: goto -158 -> 219
        //   380: astore_3
        //   381: goto -162 -> 219
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	384	0	paramString	String
        //   0	384	1	paramInt	int
        //   20	160	2	localObject1	Object
        //   186	57	2	localUnsupportedEncodingException1	UnsupportedEncodingException
        //   256	5	2	localObject2	Object
        //   283	1	2	localException1	Exception
        //   287	1	2	localException2	Exception
        //   309	65	2	localObject3	Object
        //   104	126	3	localObject4	Object
        //   253	21	3	localObject5	Object
        //   307	1	3	localObject6	Object
        //   323	54	3	localObject7	Object
        //   380	1	3	localIOException1	IOException
        //   43	141	4	arrayOfByte1	byte[]
        //   211	6	4	localIOException2	IOException
        //   220	85	4	localUnsupportedEncodingException2	UnsupportedEncodingException
        //   320	13	4	localObject8	Object
        //   341	5	4	str1	String
        //   357	8	4	localIOException3	IOException
        //   370	5	4	localIOException4	IOException
        //   115	140	5	str2	String
        //   338	14	5	localObject9	Object
        //   106	244	6	localObject10	Object
        //   112	126	7	localObject11	Object
        //   31	220	8	arrayOfByte2	byte[]
        //   109	220	9	localObject12	Object
        //   101	210	10	localObject13	Object
        //   118	244	11	localObject14	Object
        //   125	77	12	arrayOfByte3	byte[]
        // Exception table:
        //   from	to	target	type
        //   14	21	186	java/io/UnsupportedEncodingException
        //   154	161	211	java/io/IOException
        //   165	171	211	java/io/IOException
        //   200	208	211	java/io/IOException
        //   242	246	249	java/lang/Exception
        //   127	135	253	finally
        //   229	233	253	finally
        //   233	237	275	java/lang/Exception
        //   237	242	279	java/lang/Exception
        //   260	264	283	java/lang/Exception
        //   264	269	287	java/lang/Exception
        //   269	273	291	java/lang/Exception
        //   171	175	295	java/lang/Exception
        //   175	179	299	java/lang/Exception
        //   179	183	303	java/lang/Exception
        //   135	145	307	finally
        //   145	154	320	finally
        //   154	161	338	finally
        //   165	171	338	finally
        //   200	208	338	finally
        //   127	135	357	java/io/IOException
        //   135	145	370	java/io/IOException
        //   145	154	380	java/io/IOException
    }

    public static byte[] decode(byte[] paramArrayOfByte)
            throws IOException
    {
        return decode(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
    }

    public static byte[] decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
            throws IOException
    {
        if (paramArrayOfByte == null) {
            throw new NullPointerException("Cannot decode null source array.");
        }
        if ((paramInt1 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length)) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", new Object[] { Integer.valueOf(paramArrayOfByte.length), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }));
        }
        if (paramInt2 == 0) {
            return new byte[0];
        }
        if (paramInt2 < 4) {
            throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was " + paramInt2);
        }
        byte[] arrayOfByte2 = getDecodabet(paramInt3);
        byte[] arrayOfByte1 = new byte[paramInt2 * 3 / 4];
        int j = 0;
        byte[] arrayOfByte3 = new byte[4];
        int k = paramInt1;
        int i = 0;
        if (k >= paramInt1 + paramInt2) {}
        int m;
        do
        {
            paramArrayOfByte = new byte[j];
            System.arraycopy(arrayOfByte1, 0, paramArrayOfByte, 0, j);
            return paramArrayOfByte;
            m = arrayOfByte2[(paramArrayOfByte[k] & 0xFF)];
            if (m < -5) {
                break label256;
            }
            if (m < -1) {
                break label296;
            }
            int n = i + 1;
            arrayOfByte3[i] = paramArrayOfByte[k];
            i = n;
            m = j;
            if (n <= 3) {
                break;
            }
            m = j + decode4to3(arrayOfByte3, 0, arrayOfByte1, j, paramInt3);
            i = 0;
            j = m;
        } while (paramArrayOfByte[k] == 61);
        for (;;)
        {
            k += 1;
            j = m;
            break;
            label256:
            throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", new Object[] { Integer.valueOf(paramArrayOfByte[k] & 0xFF), Integer.valueOf(k) }));
            label296:
            m = j;
        }
    }

    private static int decode4to3(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
    {
        if (paramArrayOfByte1 == null) {
            throw new NullPointerException("Source array was null.");
        }
        if (paramArrayOfByte2 == null) {
            throw new NullPointerException("Destination array was null.");
        }
        if ((paramInt1 < 0) || (paramInt1 + 3 >= paramArrayOfByte1.length)) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", new Object[] { Integer.valueOf(paramArrayOfByte1.length), Integer.valueOf(paramInt1) }));
        }
        if ((paramInt2 < 0) || (paramInt2 + 2 >= paramArrayOfByte2.length)) {
            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[] { Integer.valueOf(paramArrayOfByte2.length), Integer.valueOf(paramInt2) }));
        }
        byte[] arrayOfByte = getDecodabet(paramInt3);
        if (paramArrayOfByte1[(paramInt1 + 2)] == 61)
        {
            paramArrayOfByte2[paramInt2] = ((byte)(((arrayOfByte[paramArrayOfByte1[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 1)]] & 0xFF) << 12) >>> 16));
            return 1;
        }
        if (paramArrayOfByte1[(paramInt1 + 3)] == 61)
        {
            paramInt1 = (arrayOfByte[paramArrayOfByte1[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 1)]] & 0xFF) << 12 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 2)]] & 0xFF) << 6;
            paramArrayOfByte2[paramInt2] = ((byte)(paramInt1 >>> 16));
            paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
            return 2;
        }
        paramInt1 = (arrayOfByte[paramArrayOfByte1[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 1)]] & 0xFF) << 12 | (arrayOfByte[paramArrayOfByte1[(paramInt1 + 2)]] & 0xFF) << 6 | arrayOfByte[paramArrayOfByte1[(paramInt1 + 3)]] & 0xFF;
        paramArrayOfByte2[paramInt2] = ((byte)(paramInt1 >> 16));
        paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(paramInt1 >> 8));
        paramArrayOfByte2[(paramInt2 + 2)] = ((byte)paramInt1);
        return 3;
    }

    /* Error */
    public static void decodeFileToFile(String paramString1, String paramString2)
            throws IOException
    {
        // Byte code:
        //   0: aload_0
        //   1: invokestatic 279	cn/com/ukey/Base64:decodeFromFile	(Ljava/lang/String;)[B
        //   4: astore_3
        //   5: aconst_null
        //   6: astore_0
        //   7: aconst_null
        //   8: astore_2
        //   9: new 281	java/io/BufferedOutputStream
        //   12: dup
        //   13: new 283	java/io/FileOutputStream
        //   16: dup
        //   17: aload_1
        //   18: invokespecial 284	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
        //   21: invokespecial 287	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
        //   24: astore_1
        //   25: aload_1
        //   26: aload_3
        //   27: invokevirtual 291	java/io/OutputStream:write	([B)V
        //   30: aload_1
        //   31: invokevirtual 292	java/io/OutputStream:close	()V
        //   34: return
        //   35: astore_1
        //   36: aload_2
        //   37: astore_0
        //   38: aload_1
        //   39: athrow
        //   40: astore_1
        //   41: aload_0
        //   42: invokevirtual 292	java/io/OutputStream:close	()V
        //   45: aload_1
        //   46: athrow
        //   47: astore_0
        //   48: goto -3 -> 45
        //   51: astore_0
        //   52: return
        //   53: astore_2
        //   54: aload_1
        //   55: astore_0
        //   56: aload_2
        //   57: astore_1
        //   58: goto -17 -> 41
        //   61: astore_2
        //   62: aload_1
        //   63: astore_0
        //   64: aload_2
        //   65: astore_1
        //   66: goto -28 -> 38
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	69	0	paramString1	String
        //   0	69	1	paramString2	String
        //   8	29	2	localObject1	Object
        //   53	4	2	localObject2	Object
        //   61	4	2	localIOException	IOException
        //   4	23	3	arrayOfByte	byte[]
        // Exception table:
        //   from	to	target	type
        //   9	25	35	java/io/IOException
        //   9	25	40	finally
        //   38	40	40	finally
        //   41	45	47	java/lang/Exception
        //   30	34	51	java/lang/Exception
        //   25	30	53	finally
        //   25	30	61	java/io/IOException
    }

    /* Error */
    public static byte[] decodeFromFile(String paramString)
            throws IOException
    {
        // Byte code:
        //   0: aconst_null
        //   1: astore 5
        //   3: aconst_null
        //   4: astore 4
        //   6: aload 5
        //   8: astore_3
        //   9: new 294	java/io/File
        //   12: dup
        //   13: aload_0
        //   14: invokespecial 295	java/io/File:<init>	(Ljava/lang/String;)V
        //   17: astore_0
        //   18: iconst_0
        //   19: istore_1
        //   20: aload 5
        //   22: astore_3
        //   23: aload_0
        //   24: invokevirtual 299	java/io/File:length	()J
        //   27: ldc2_w 300
        //   30: lcmp
        //   31: ifle +53 -> 84
        //   34: aload 5
        //   36: astore_3
        //   37: new 166	java/io/IOException
        //   40: dup
        //   41: new 246	java/lang/StringBuilder
        //   44: dup
        //   45: ldc_w 303
        //   48: invokespecial 249	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   51: aload_0
        //   52: invokevirtual 299	java/io/File:length	()J
        //   55: invokevirtual 306	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
        //   58: ldc_w 308
        //   61: invokevirtual 311	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   64: invokevirtual 257	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   67: invokespecial 266	java/io/IOException:<init>	(Ljava/lang/String;)V
        //   70: athrow
        //   71: astore_0
        //   72: aload 4
        //   74: astore_3
        //   75: aload_0
        //   76: athrow
        //   77: astore_0
        //   78: aload_3
        //   79: invokevirtual 312	cn/com/ukey/Base64$InputStream:close	()V
        //   82: aload_0
        //   83: athrow
        //   84: aload 5
        //   86: astore_3
        //   87: aload_0
        //   88: invokevirtual 299	java/io/File:length	()J
        //   91: l2i
        //   92: newarray <illegal type>
        //   94: astore 6
        //   96: aload 5
        //   98: astore_3
        //   99: new 8	cn/com/ukey/Base64$InputStream
        //   102: dup
        //   103: new 314	java/io/BufferedInputStream
        //   106: dup
        //   107: new 316	java/io/FileInputStream
        //   110: dup
        //   111: aload_0
        //   112: invokespecial 319	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   115: invokespecial 320	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
        //   118: iconst_0
        //   119: invokespecial 323	cn/com/ukey/Base64$InputStream:<init>	(Ljava/io/InputStream;I)V
        //   122: astore_0
        //   123: aload_0
        //   124: aload 6
        //   126: iload_1
        //   127: sipush 4096
        //   130: invokevirtual 326	cn/com/ukey/Base64$InputStream:read	([BII)I
        //   133: istore_2
        //   134: iload_2
        //   135: ifge +22 -> 157
        //   138: iload_1
        //   139: newarray <illegal type>
        //   141: astore_3
        //   142: aload 6
        //   144: iconst_0
        //   145: aload_3
        //   146: iconst_0
        //   147: iload_1
        //   148: invokestatic 263	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   151: aload_0
        //   152: invokevirtual 312	cn/com/ukey/Base64$InputStream:close	()V
        //   155: aload_3
        //   156: areturn
        //   157: iload_1
        //   158: iload_2
        //   159: iadd
        //   160: istore_1
        //   161: goto -38 -> 123
        //   164: astore_3
        //   165: goto -83 -> 82
        //   168: astore_0
        //   169: aload_3
        //   170: areturn
        //   171: astore 4
        //   173: aload_0
        //   174: astore_3
        //   175: aload 4
        //   177: astore_0
        //   178: goto -100 -> 78
        //   181: astore 4
        //   183: aload_0
        //   184: astore_3
        //   185: aload 4
        //   187: astore_0
        //   188: goto -113 -> 75
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	191	0	paramString	String
        //   19	142	1	i	int
        //   133	27	2	j	int
        //   8	148	3	localObject1	Object
        //   164	6	3	localException	Exception
        //   174	11	3	str	String
        //   4	69	4	localObject2	Object
        //   171	5	4	localObject3	Object
        //   181	5	4	localIOException	IOException
        //   1	96	5	localObject4	Object
        //   94	49	6	arrayOfByte	byte[]
        // Exception table:
        //   from	to	target	type
        //   9	18	71	java/io/IOException
        //   23	34	71	java/io/IOException
        //   37	71	71	java/io/IOException
        //   87	96	71	java/io/IOException
        //   99	123	71	java/io/IOException
        //   9	18	77	finally
        //   23	34	77	finally
        //   37	71	77	finally
        //   75	77	77	finally
        //   87	96	77	finally
        //   99	123	77	finally
        //   78	82	164	java/lang/Exception
        //   151	155	168	java/lang/Exception
        //   123	134	171	finally
        //   138	151	171	finally
        //   123	134	181	java/io/IOException
        //   138	151	181	java/io/IOException
    }

    /* Error */
    public static void decodeToFile(String paramString1, String paramString2)
            throws IOException
    {
        // Byte code:
        //   0: aconst_null
        //   1: astore_2
        //   2: aconst_null
        //   3: astore_3
        //   4: new 11	cn/com/ukey/Base64$OutputStream
        //   7: dup
        //   8: new 283	java/io/FileOutputStream
        //   11: dup
        //   12: aload_1
        //   13: invokespecial 284	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
        //   16: iconst_0
        //   17: invokespecial 330	cn/com/ukey/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
        //   20: astore_1
        //   21: aload_1
        //   22: aload_0
        //   23: ldc 41
        //   25: invokevirtual 186	java/lang/String:getBytes	(Ljava/lang/String;)[B
        //   28: invokevirtual 331	cn/com/ukey/Base64$OutputStream:write	([B)V
        //   31: aload_1
        //   32: invokevirtual 332	cn/com/ukey/Base64$OutputStream:close	()V
        //   35: return
        //   36: astore_0
        //   37: aload_3
        //   38: astore_2
        //   39: aload_0
        //   40: athrow
        //   41: astore_0
        //   42: aload_2
        //   43: invokevirtual 332	cn/com/ukey/Base64$OutputStream:close	()V
        //   46: aload_0
        //   47: athrow
        //   48: astore_1
        //   49: goto -3 -> 46
        //   52: astore_0
        //   53: return
        //   54: astore_0
        //   55: aload_1
        //   56: astore_2
        //   57: goto -15 -> 42
        //   60: astore_0
        //   61: aload_1
        //   62: astore_2
        //   63: goto -24 -> 39
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	66	0	paramString1	String
        //   0	66	1	paramString2	String
        //   1	62	2	localObject1	Object
        //   3	35	3	localObject2	Object
        // Exception table:
        //   from	to	target	type
        //   4	21	36	java/io/IOException
        //   4	21	41	finally
        //   39	41	41	finally
        //   42	46	48	java/lang/Exception
        //   31	35	52	java/lang/Exception
        //   21	31	54	finally
        //   21	31	60	java/io/IOException
    }

    public static Object decodeToObject(String paramString)
            throws IOException, ClassNotFoundException
    {
        return decodeToObject(paramString, 0, null);
    }

    /* Error */
    public static Object decodeToObject(String paramString, int paramInt, final ClassLoader paramClassLoader)
            throws IOException, ClassNotFoundException
    {
        // Byte code:
        //   0: aload_0
        //   1: iload_1
        //   2: invokestatic 169	cn/com/ukey/Base64:decode	(Ljava/lang/String;I)[B
        //   5: astore 4
        //   7: aconst_null
        //   8: astore_0
        //   9: aconst_null
        //   10: astore 6
        //   12: aconst_null
        //   13: astore 5
        //   15: aconst_null
        //   16: astore_3
        //   17: aconst_null
        //   18: astore 9
        //   20: aconst_null
        //   21: astore 11
        //   23: aconst_null
        //   24: astore 10
        //   26: aconst_null
        //   27: astore 7
        //   29: aconst_null
        //   30: astore 8
        //   32: new 196	java/io/ByteArrayInputStream
        //   35: dup
        //   36: aload 4
        //   38: invokespecial 199	java/io/ByteArrayInputStream:<init>	([B)V
        //   41: astore 4
        //   43: aload_2
        //   44: ifnonnull +48 -> 92
        //   47: aload 11
        //   49: astore_3
        //   50: aload 10
        //   52: astore 5
        //   54: aload 7
        //   56: astore 6
        //   58: new 341	java/io/ObjectInputStream
        //   61: dup
        //   62: aload 4
        //   64: invokespecial 342	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
        //   67: astore_0
        //   68: aload_0
        //   69: astore_3
        //   70: aload_0
        //   71: astore 5
        //   73: aload_0
        //   74: astore 6
        //   76: aload_0
        //   77: invokevirtual 346	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
        //   80: astore_2
        //   81: aload 4
        //   83: invokevirtual 217	java/io/ByteArrayInputStream:close	()V
        //   86: aload_0
        //   87: invokevirtual 347	java/io/ObjectInputStream:close	()V
        //   90: aload_2
        //   91: areturn
        //   92: aload 11
        //   94: astore_3
        //   95: aload 10
        //   97: astore 5
        //   99: aload 7
        //   101: astore 6
        //   103: new 6	cn/com/ukey/Base64$1
        //   106: dup
        //   107: aload 4
        //   109: aload_2
        //   110: invokespecial 350	cn/com/ukey/Base64$1:<init>	(Ljava/io/InputStream;Ljava/lang/ClassLoader;)V
        //   113: astore_0
        //   114: goto -46 -> 68
        //   117: astore_2
        //   118: aload 8
        //   120: astore_3
        //   121: aload 5
        //   123: astore_0
        //   124: aload_2
        //   125: athrow
        //   126: astore_2
        //   127: aload_0
        //   128: invokevirtual 217	java/io/ByteArrayInputStream:close	()V
        //   131: aload_3
        //   132: invokevirtual 347	java/io/ObjectInputStream:close	()V
        //   135: aload_2
        //   136: athrow
        //   137: astore_2
        //   138: aload 9
        //   140: astore_3
        //   141: aload 6
        //   143: astore_0
        //   144: aload_2
        //   145: athrow
        //   146: astore_0
        //   147: goto -16 -> 131
        //   150: astore_0
        //   151: goto -16 -> 135
        //   154: astore_3
        //   155: goto -69 -> 86
        //   158: astore_0
        //   159: aload_2
        //   160: areturn
        //   161: astore_2
        //   162: aload 4
        //   164: astore_0
        //   165: goto -38 -> 127
        //   168: astore_2
        //   169: aload 4
        //   171: astore_0
        //   172: aload 5
        //   174: astore_3
        //   175: goto -31 -> 144
        //   178: astore_2
        //   179: aload 4
        //   181: astore_0
        //   182: aload 6
        //   184: astore_3
        //   185: goto -61 -> 124
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	188	0	paramString	String
        //   0	188	1	paramInt	int
        //   0	188	2	paramClassLoader	ClassLoader
        //   16	125	3	localObject1	Object
        //   154	1	3	localException	Exception
        //   174	11	3	localObject2	Object
        //   5	175	4	localObject3	Object
        //   13	160	5	localObject4	Object
        //   10	173	6	localObject5	Object
        //   27	73	7	localObject6	Object
        //   30	89	8	localObject7	Object
        //   18	121	9	localObject8	Object
        //   24	72	10	localObject9	Object
        //   21	72	11	localObject10	Object
        // Exception table:
        //   from	to	target	type
        //   32	43	117	java/io/IOException
        //   32	43	126	finally
        //   124	126	126	finally
        //   144	146	126	finally
        //   32	43	137	java/lang/ClassNotFoundException
        //   127	131	146	java/lang/Exception
        //   131	135	150	java/lang/Exception
        //   81	86	154	java/lang/Exception
        //   86	90	158	java/lang/Exception
        //   58	68	161	finally
        //   76	81	161	finally
        //   103	114	161	finally
        //   58	68	168	java/lang/ClassNotFoundException
        //   76	81	168	java/lang/ClassNotFoundException
        //   103	114	168	java/lang/ClassNotFoundException
        //   58	68	178	java/io/IOException
        //   76	81	178	java/io/IOException
        //   103	114	178	java/io/IOException
    }

    public static void encode(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
    {
        byte[] arrayOfByte1 = new byte[3];
        byte[] arrayOfByte2 = new byte[4];
        for (;;)
        {
            if (!paramByteBuffer1.hasRemaining()) {
                return;
            }
            int i = Math.min(3, paramByteBuffer1.remaining());
            paramByteBuffer1.get(arrayOfByte1, 0, i);
            encode3to4(arrayOfByte2, arrayOfByte1, i, 0);
            paramByteBuffer2.put(arrayOfByte2);
        }
    }

    public static void encode(ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer)
    {
        byte[] arrayOfByte1 = new byte[3];
        byte[] arrayOfByte2 = new byte[4];
        for (;;)
        {
            if (!paramByteBuffer.hasRemaining()) {
                return;
            }
            int i = Math.min(3, paramByteBuffer.remaining());
            paramByteBuffer.get(arrayOfByte1, 0, i);
            encode3to4(arrayOfByte2, arrayOfByte1, i, 0);
            i = 0;
            while (i < 4)
            {
                paramCharBuffer.put((char)(arrayOfByte2[i] & 0xFF));
                i += 1;
            }
        }
    }

    private static byte[] encode3to4(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4)
    {
        int j = 0;
        byte[] arrayOfByte = getAlphabet(paramInt4);
        if (paramInt2 > 0)
        {
            paramInt4 = paramArrayOfByte1[paramInt1] << 24 >>> 8;
            label25:
            if (paramInt2 <= 1) {
                break label104;
            }
        }
        label104:
        for (int i = paramArrayOfByte1[(paramInt1 + 1)] << 24 >>> 16;; i = 0)
        {
            if (paramInt2 > 2) {
                j = paramArrayOfByte1[(paramInt1 + 2)] << 24 >>> 24;
            }
            paramInt1 = i | paramInt4 | j;
            switch (paramInt2)
            {
                default:
                    return paramArrayOfByte2;
                paramInt4 = 0;
                break label25;
            }
        }
        paramArrayOfByte2[paramInt3] = arrayOfByte[(paramInt1 >>> 18)];
        paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(paramInt1 >>> 12 & 0x3F)];
        paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(paramInt1 >>> 6 & 0x3F)];
        paramArrayOfByte2[(paramInt3 + 3)] = arrayOfByte[(paramInt1 & 0x3F)];
        return paramArrayOfByte2;
        paramArrayOfByte2[paramInt3] = arrayOfByte[(paramInt1 >>> 18)];
        paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(paramInt1 >>> 12 & 0x3F)];
        paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(paramInt1 >>> 6 & 0x3F)];
        paramArrayOfByte2[(paramInt3 + 3)] = 61;
        return paramArrayOfByte2;
        paramArrayOfByte2[paramInt3] = arrayOfByte[(paramInt1 >>> 18)];
        paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(paramInt1 >>> 12 & 0x3F)];
        paramArrayOfByte2[(paramInt3 + 2)] = 61;
        paramArrayOfByte2[(paramInt3 + 3)] = 61;
        return paramArrayOfByte2;
    }

    private static byte[] encode3to4(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
    {
        encode3to4(paramArrayOfByte2, 0, paramInt1, paramArrayOfByte1, 0, paramInt2);
        return paramArrayOfByte1;
    }

    public static String encodeBytes(byte[] paramArrayOfByte)
    {
        Object localObject = null;
        try
        {
            paramArrayOfByte = encodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
            if ((!$assertionsDisabled) && (paramArrayOfByte == null)) {
                throw new AssertionError();
            }
        }
        catch (IOException localIOException)
        {
            do
            {
                paramArrayOfByte = (byte[])localObject;
            } while ($assertionsDisabled);
            throw new AssertionError(localIOException.getMessage());
        }
        return paramArrayOfByte;
    }

    public static String encodeBytes(byte[] paramArrayOfByte, int paramInt)
            throws IOException
    {
        return encodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
    }

    public static String encodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        Object localObject = null;
        try
        {
            paramArrayOfByte = encodeBytes(paramArrayOfByte, paramInt1, paramInt2, 0);
            if ((!$assertionsDisabled) && (paramArrayOfByte == null)) {
                throw new AssertionError();
            }
        }
        catch (IOException localIOException)
        {
            do
            {
                paramArrayOfByte = (byte[])localObject;
            } while ($assertionsDisabled);
            throw new AssertionError(localIOException.getMessage());
        }
        return paramArrayOfByte;
    }

    public static String encodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
            throws IOException
    {
        paramArrayOfByte = encodeBytesToBytes(paramArrayOfByte, paramInt1, paramInt2, paramInt3);
        try
        {
            String str = new String(paramArrayOfByte, "US-ASCII");
            return str;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
        return new String(paramArrayOfByte);
    }

    public static byte[] encodeBytesToBytes(byte[] paramArrayOfByte)
    {
        Object localObject = null;
        try
        {
            paramArrayOfByte = encodeBytesToBytes(paramArrayOfByte, 0, paramArrayOfByte.length, 0);
            return paramArrayOfByte;
        }
        catch (IOException localIOException)
        {
            do
            {
                paramArrayOfByte = (byte[])localObject;
            } while ($assertionsDisabled);
            throw new AssertionError("IOExceptions only come from GZipping, which is turned off: " + localIOException.getMessage());
        }
    }

    /* Error */
    public static byte[] encodeBytesToBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
            throws IOException
    {
        // Byte code:
        //   0: aload_0
        //   1: ifnonnull +14 -> 15
        //   4: new 176	java/lang/NullPointerException
        //   7: dup
        //   8: ldc_w 411
        //   11: invokespecial 181	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
        //   14: athrow
        //   15: iload_1
        //   16: ifge +28 -> 44
        //   19: new 231	java/lang/IllegalArgumentException
        //   22: dup
        //   23: new 246	java/lang/StringBuilder
        //   26: dup
        //   27: ldc_w 413
        //   30: invokespecial 249	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   33: iload_1
        //   34: invokevirtual 253	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   37: invokevirtual 257	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   40: invokespecial 244	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
        //   43: athrow
        //   44: iload_2
        //   45: ifge +28 -> 73
        //   48: new 231	java/lang/IllegalArgumentException
        //   51: dup
        //   52: new 246	java/lang/StringBuilder
        //   55: dup
        //   56: ldc_w 415
        //   59: invokespecial 249	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   62: iload_2
        //   63: invokevirtual 253	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   66: invokevirtual 257	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   69: invokespecial 244	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
        //   72: athrow
        //   73: iload_1
        //   74: iload_2
        //   75: iadd
        //   76: aload_0
        //   77: arraylength
        //   78: if_icmple +43 -> 121
        //   81: new 231	java/lang/IllegalArgumentException
        //   84: dup
        //   85: ldc_w 417
        //   88: iconst_3
        //   89: anewarray 4	java/lang/Object
        //   92: dup
        //   93: iconst_0
        //   94: iload_1
        //   95: invokestatic 239	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   98: aastore
        //   99: dup
        //   100: iconst_1
        //   101: iload_2
        //   102: invokestatic 239	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   105: aastore
        //   106: dup
        //   107: iconst_2
        //   108: aload_0
        //   109: arraylength
        //   110: invokestatic 239	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   113: aastore
        //   114: invokestatic 243	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   117: invokespecial 244	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
        //   120: athrow
        //   121: iload_3
        //   122: iconst_2
        //   123: iand
        //   124: ifeq +140 -> 264
        //   127: aconst_null
        //   128: astore 11
        //   130: aconst_null
        //   131: astore 18
        //   133: aconst_null
        //   134: astore 13
        //   136: aconst_null
        //   137: astore 15
        //   139: aconst_null
        //   140: astore 14
        //   142: aconst_null
        //   143: astore 12
        //   145: aconst_null
        //   146: astore 17
        //   148: aconst_null
        //   149: astore 16
        //   151: new 193	java/io/ByteArrayOutputStream
        //   154: dup
        //   155: invokespecial 194	java/io/ByteArrayOutputStream:<init>	()V
        //   158: astore 10
        //   160: new 11	cn/com/ukey/Base64$OutputStream
        //   163: dup
        //   164: aload 10
        //   166: iload_3
        //   167: iconst_1
        //   168: ior
        //   169: invokespecial 330	cn/com/ukey/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
        //   172: astore 11
        //   174: new 419	java/util/zip/GZIPOutputStream
        //   177: dup
        //   178: aload 11
        //   180: invokespecial 420	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
        //   183: astore 12
        //   185: aload 12
        //   187: aload_0
        //   188: iload_1
        //   189: iload_2
        //   190: invokevirtual 421	java/util/zip/GZIPOutputStream:write	([BII)V
        //   193: aload 12
        //   195: invokevirtual 422	java/util/zip/GZIPOutputStream:close	()V
        //   198: aload 12
        //   200: invokevirtual 422	java/util/zip/GZIPOutputStream:close	()V
        //   203: aload 11
        //   205: invokevirtual 332	cn/com/ukey/Base64$OutputStream:close	()V
        //   208: aload 10
        //   210: invokevirtual 215	java/io/ByteArrayOutputStream:close	()V
        //   213: aload 10
        //   215: invokevirtual 212	java/io/ByteArrayOutputStream:toByteArray	()[B
        //   218: areturn
        //   219: astore 10
        //   221: aload 14
        //   223: astore 13
        //   225: aload 18
        //   227: astore_0
        //   228: aload 16
        //   230: astore 11
        //   232: aload 11
        //   234: astore 12
        //   236: aload_0
        //   237: astore 11
        //   239: aload 10
        //   241: athrow
        //   242: astore_0
        //   243: aload 12
        //   245: astore 10
        //   247: aload 13
        //   249: invokevirtual 422	java/util/zip/GZIPOutputStream:close	()V
        //   252: aload 10
        //   254: invokevirtual 332	cn/com/ukey/Base64$OutputStream:close	()V
        //   257: aload 11
        //   259: invokevirtual 215	java/io/ByteArrayOutputStream:close	()V
        //   262: aload_0
        //   263: athrow
        //   264: iload_3
        //   265: bipush 8
        //   267: iand
        //   268: ifeq +132 -> 400
        //   271: iconst_1
        //   272: istore 6
        //   274: iload_2
        //   275: iconst_3
        //   276: idiv
        //   277: istore 5
        //   279: iload_2
        //   280: iconst_3
        //   281: irem
        //   282: ifle +124 -> 406
        //   285: iconst_4
        //   286: istore 4
        //   288: iload 5
        //   290: iconst_4
        //   291: imul
        //   292: iload 4
        //   294: iadd
        //   295: istore 5
        //   297: iload 5
        //   299: istore 4
        //   301: iload 6
        //   303: ifeq +13 -> 316
        //   306: iload 5
        //   308: iload 5
        //   310: bipush 76
        //   312: idiv
        //   313: iadd
        //   314: istore 4
        //   316: iload 4
        //   318: newarray <illegal type>
        //   320: astore 10
        //   322: iconst_0
        //   323: istore 7
        //   325: iconst_0
        //   326: istore 4
        //   328: iconst_0
        //   329: istore 5
        //   331: iload 7
        //   333: iload_2
        //   334: iconst_2
        //   335: isub
        //   336: if_icmplt +76 -> 412
        //   339: iload 4
        //   341: istore 5
        //   343: iload 7
        //   345: iload_2
        //   346: if_icmpge +27 -> 373
        //   349: aload_0
        //   350: iload 7
        //   352: iload_1
        //   353: iadd
        //   354: iload_2
        //   355: iload 7
        //   357: isub
        //   358: aload 10
        //   360: iload 4
        //   362: iload_3
        //   363: invokestatic 153	cn/com/ukey/Base64:encode3to4	([BII[BII)[B
        //   366: pop
        //   367: iload 4
        //   369: iconst_4
        //   370: iadd
        //   371: istore 5
        //   373: iload 5
        //   375: aload 10
        //   377: arraylength
        //   378: iconst_1
        //   379: isub
        //   380: if_icmpgt +114 -> 494
        //   383: iload 5
        //   385: newarray <illegal type>
        //   387: astore_0
        //   388: aload 10
        //   390: iconst_0
        //   391: aload_0
        //   392: iconst_0
        //   393: iload 5
        //   395: invokestatic 263	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   398: aload_0
        //   399: areturn
        //   400: iconst_0
        //   401: istore 6
        //   403: goto -129 -> 274
        //   406: iconst_0
        //   407: istore 4
        //   409: goto -121 -> 288
        //   412: aload_0
        //   413: iload 7
        //   415: iload_1
        //   416: iadd
        //   417: iconst_3
        //   418: aload 10
        //   420: iload 4
        //   422: iload_3
        //   423: invokestatic 153	cn/com/ukey/Base64:encode3to4	([BII[BII)[B
        //   426: pop
        //   427: iload 5
        //   429: iconst_4
        //   430: iadd
        //   431: istore 9
        //   433: iload 4
        //   435: istore 8
        //   437: iload 9
        //   439: istore 5
        //   441: iload 6
        //   443: ifeq +36 -> 479
        //   446: iload 4
        //   448: istore 8
        //   450: iload 9
        //   452: istore 5
        //   454: iload 9
        //   456: bipush 76
        //   458: if_icmplt +21 -> 479
        //   461: aload 10
        //   463: iload 4
        //   465: iconst_4
        //   466: iadd
        //   467: bipush 10
        //   469: bastore
        //   470: iload 4
        //   472: iconst_1
        //   473: iadd
        //   474: istore 8
        //   476: iconst_0
        //   477: istore 5
        //   479: iload 7
        //   481: iconst_3
        //   482: iadd
        //   483: istore 7
        //   485: iload 8
        //   487: iconst_4
        //   488: iadd
        //   489: istore 4
        //   491: goto -160 -> 331
        //   494: aload 10
        //   496: areturn
        //   497: astore 12
        //   499: goto -247 -> 252
        //   502: astore 10
        //   504: goto -247 -> 257
        //   507: astore 10
        //   509: goto -247 -> 262
        //   512: astore_0
        //   513: goto -310 -> 203
        //   516: astore_0
        //   517: goto -309 -> 208
        //   520: astore_0
        //   521: goto -308 -> 213
        //   524: astore_0
        //   525: aload 10
        //   527: astore 11
        //   529: aload 17
        //   531: astore 10
        //   533: aload 15
        //   535: astore 13
        //   537: goto -290 -> 247
        //   540: astore_0
        //   541: aload 10
        //   543: astore 12
        //   545: aload 11
        //   547: astore 10
        //   549: aload 12
        //   551: astore 11
        //   553: aload 15
        //   555: astore 13
        //   557: goto -310 -> 247
        //   560: astore_0
        //   561: aload 10
        //   563: astore 13
        //   565: aload 11
        //   567: astore 10
        //   569: aload 13
        //   571: astore 11
        //   573: aload 12
        //   575: astore 13
        //   577: goto -330 -> 247
        //   580: astore 11
        //   582: aload 10
        //   584: astore_0
        //   585: aload 11
        //   587: astore 10
        //   589: aload 16
        //   591: astore 11
        //   593: aload 14
        //   595: astore 13
        //   597: goto -365 -> 232
        //   600: astore 12
        //   602: aload 10
        //   604: astore_0
        //   605: aload 12
        //   607: astore 10
        //   609: aload 14
        //   611: astore 13
        //   613: goto -381 -> 232
        //   616: astore 13
        //   618: aload 10
        //   620: astore_0
        //   621: aload 13
        //   623: astore 10
        //   625: aload 12
        //   627: astore 13
        //   629: goto -397 -> 232
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	632	0	paramArrayOfByte	byte[]
        //   0	632	1	paramInt1	int
        //   0	632	2	paramInt2	int
        //   0	632	3	paramInt3	int
        //   286	204	4	i	int
        //   277	201	5	j	int
        //   272	170	6	k	int
        //   323	161	7	m	int
        //   435	54	8	n	int
        //   431	28	9	i1	int
        //   158	56	10	localByteArrayOutputStream	java.io.ByteArrayOutputStream
        //   219	21	10	localIOException1	IOException
        //   245	250	10	localObject1	Object
        //   502	1	10	localException1	Exception
        //   507	19	10	localException2	Exception
        //   531	93	10	localObject2	Object
        //   128	444	11	localObject3	Object
        //   580	6	11	localIOException2	IOException
        //   591	1	11	localObject4	Object
        //   143	101	12	localObject5	Object
        //   497	1	12	localException3	Exception
        //   543	31	12	localObject6	Object
        //   600	26	12	localIOException3	IOException
        //   134	478	13	localObject7	Object
        //   616	6	13	localIOException4	IOException
        //   627	1	13	localObject8	Object
        //   140	470	14	localObject9	Object
        //   137	417	15	localObject10	Object
        //   149	441	16	localObject11	Object
        //   146	384	17	localObject12	Object
        //   131	95	18	localObject13	Object
        // Exception table:
        //   from	to	target	type
        //   151	160	219	java/io/IOException
        //   151	160	242	finally
        //   239	242	242	finally
        //   247	252	497	java/lang/Exception
        //   252	257	502	java/lang/Exception
        //   257	262	507	java/lang/Exception
        //   198	203	512	java/lang/Exception
        //   203	208	516	java/lang/Exception
        //   208	213	520	java/lang/Exception
        //   160	174	524	finally
        //   174	185	540	finally
        //   185	198	560	finally
        //   160	174	580	java/io/IOException
        //   174	185	600	java/io/IOException
        //   185	198	616	java/io/IOException
    }

    /* Error */
    public static void encodeFileToFile(String paramString1, String paramString2)
            throws IOException
    {
        // Byte code:
        //   0: aload_0
        //   1: invokestatic 427	cn/com/ukey/Base64:encodeFromFile	(Ljava/lang/String;)Ljava/lang/String;
        //   4: astore_3
        //   5: aconst_null
        //   6: astore_0
        //   7: aconst_null
        //   8: astore_2
        //   9: new 281	java/io/BufferedOutputStream
        //   12: dup
        //   13: new 283	java/io/FileOutputStream
        //   16: dup
        //   17: aload_1
        //   18: invokespecial 284	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
        //   21: invokespecial 287	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
        //   24: astore_1
        //   25: aload_1
        //   26: aload_3
        //   27: ldc 41
        //   29: invokevirtual 186	java/lang/String:getBytes	(Ljava/lang/String;)[B
        //   32: invokevirtual 291	java/io/OutputStream:write	([B)V
        //   35: aload_1
        //   36: invokevirtual 292	java/io/OutputStream:close	()V
        //   39: return
        //   40: astore_1
        //   41: aload_2
        //   42: astore_0
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_0
        //   47: invokevirtual 292	java/io/OutputStream:close	()V
        //   50: aload_1
        //   51: athrow
        //   52: astore_0
        //   53: goto -3 -> 50
        //   56: astore_0
        //   57: return
        //   58: astore_2
        //   59: aload_1
        //   60: astore_0
        //   61: aload_2
        //   62: astore_1
        //   63: goto -17 -> 46
        //   66: astore_2
        //   67: aload_1
        //   68: astore_0
        //   69: aload_2
        //   70: astore_1
        //   71: goto -28 -> 43
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	74	0	paramString1	String
        //   0	74	1	paramString2	String
        //   8	34	2	localObject1	Object
        //   58	4	2	localObject2	Object
        //   66	4	2	localIOException	IOException
        //   4	23	3	str	String
        // Exception table:
        //   from	to	target	type
        //   9	25	40	java/io/IOException
        //   9	25	45	finally
        //   43	45	45	finally
        //   46	50	52	java/lang/Exception
        //   35	39	56	java/lang/Exception
        //   25	35	58	finally
        //   25	35	66	java/io/IOException
    }

    /* Error */
    public static String encodeFromFile(String paramString)
            throws IOException
    {
        // Byte code:
        //   0: aconst_null
        //   1: astore 5
        //   3: aconst_null
        //   4: astore 4
        //   6: aload 5
        //   8: astore_3
        //   9: new 294	java/io/File
        //   12: dup
        //   13: aload_0
        //   14: invokespecial 295	java/io/File:<init>	(Ljava/lang/String;)V
        //   17: astore_0
        //   18: aload 5
        //   20: astore_3
        //   21: aload_0
        //   22: invokevirtual 299	java/io/File:length	()J
        //   25: l2d
        //   26: ldc2_w 428
        //   29: dmul
        //   30: dconst_1
        //   31: dadd
        //   32: d2i
        //   33: bipush 40
        //   35: invokestatic 432	java/lang/Math:max	(II)I
        //   38: newarray <illegal type>
        //   40: astore 6
        //   42: iconst_0
        //   43: istore_1
        //   44: aload 5
        //   46: astore_3
        //   47: new 8	cn/com/ukey/Base64$InputStream
        //   50: dup
        //   51: new 314	java/io/BufferedInputStream
        //   54: dup
        //   55: new 316	java/io/FileInputStream
        //   58: dup
        //   59: aload_0
        //   60: invokespecial 319	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   63: invokespecial 320	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
        //   66: iconst_1
        //   67: invokespecial 323	cn/com/ukey/Base64$InputStream:<init>	(Ljava/io/InputStream;I)V
        //   70: astore_0
        //   71: aload_0
        //   72: aload 6
        //   74: iload_1
        //   75: sipush 4096
        //   78: invokevirtual 326	cn/com/ukey/Base64$InputStream:read	([BII)I
        //   81: istore_2
        //   82: iload_2
        //   83: ifge +23 -> 106
        //   86: new 183	java/lang/String
        //   89: dup
        //   90: aload 6
        //   92: iconst_0
        //   93: iload_1
        //   94: ldc 41
        //   96: invokespecial 435	java/lang/String:<init>	([BIILjava/lang/String;)V
        //   99: astore_3
        //   100: aload_0
        //   101: invokevirtual 312	cn/com/ukey/Base64$InputStream:close	()V
        //   104: aload_3
        //   105: areturn
        //   106: iload_1
        //   107: iload_2
        //   108: iadd
        //   109: istore_1
        //   110: goto -39 -> 71
        //   113: astore_0
        //   114: aload 4
        //   116: astore_3
        //   117: aload_0
        //   118: athrow
        //   119: astore_0
        //   120: aload_3
        //   121: invokevirtual 312	cn/com/ukey/Base64$InputStream:close	()V
        //   124: aload_0
        //   125: athrow
        //   126: astore_3
        //   127: goto -3 -> 124
        //   130: astore_0
        //   131: aload_3
        //   132: areturn
        //   133: astore 4
        //   135: aload_0
        //   136: astore_3
        //   137: aload 4
        //   139: astore_0
        //   140: goto -20 -> 120
        //   143: astore 4
        //   145: aload_0
        //   146: astore_3
        //   147: aload 4
        //   149: astore_0
        //   150: goto -33 -> 117
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	153	0	paramString	String
        //   43	67	1	i	int
        //   81	28	2	j	int
        //   8	113	3	localObject1	Object
        //   126	6	3	localException	Exception
        //   136	11	3	str	String
        //   4	111	4	localObject2	Object
        //   133	5	4	localObject3	Object
        //   143	5	4	localIOException	IOException
        //   1	44	5	localObject4	Object
        //   40	51	6	arrayOfByte	byte[]
        // Exception table:
        //   from	to	target	type
        //   9	18	113	java/io/IOException
        //   21	42	113	java/io/IOException
        //   47	71	113	java/io/IOException
        //   9	18	119	finally
        //   21	42	119	finally
        //   47	71	119	finally
        //   117	119	119	finally
        //   120	124	126	java/lang/Exception
        //   100	104	130	java/lang/Exception
        //   71	82	133	finally
        //   86	100	133	finally
        //   71	82	143	java/io/IOException
        //   86	100	143	java/io/IOException
    }

    public static String encodeObject(Serializable paramSerializable)
            throws IOException
    {
        return encodeObject(paramSerializable, 0);
    }

    /* Error */
    public static String encodeObject(Serializable paramSerializable, int paramInt)
            throws IOException
    {
        // Byte code:
        //   0: aload_0
        //   1: ifnonnull +14 -> 15
        //   4: new 176	java/lang/NullPointerException
        //   7: dup
        //   8: ldc_w 442
        //   11: invokespecial 181	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
        //   14: athrow
        //   15: aconst_null
        //   16: astore 6
        //   18: aconst_null
        //   19: astore 14
        //   21: aconst_null
        //   22: astore 5
        //   24: aconst_null
        //   25: astore 16
        //   27: aconst_null
        //   28: astore 9
        //   30: aconst_null
        //   31: astore 4
        //   33: aconst_null
        //   34: astore 7
        //   36: aconst_null
        //   37: astore 13
        //   39: aconst_null
        //   40: astore 19
        //   42: aconst_null
        //   43: astore 17
        //   45: aconst_null
        //   46: astore 12
        //   48: aconst_null
        //   49: astore 8
        //   51: aconst_null
        //   52: astore 11
        //   54: aconst_null
        //   55: astore 18
        //   57: aconst_null
        //   58: astore 15
        //   60: aconst_null
        //   61: astore 10
        //   63: new 193	java/io/ByteArrayOutputStream
        //   66: dup
        //   67: invokespecial 194	java/io/ByteArrayOutputStream:<init>	()V
        //   70: astore_2
        //   71: new 11	cn/com/ukey/Base64$OutputStream
        //   74: dup
        //   75: aload_2
        //   76: iload_1
        //   77: iconst_1
        //   78: ior
        //   79: invokespecial 330	cn/com/ukey/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
        //   82: astore_3
        //   83: iload_1
        //   84: iconst_2
        //   85: iand
        //   86: ifeq +96 -> 182
        //   89: aload 19
        //   91: astore 6
        //   93: aload 18
        //   95: astore 7
        //   97: aload 17
        //   99: astore 8
        //   101: aload 15
        //   103: astore 9
        //   105: new 419	java/util/zip/GZIPOutputStream
        //   108: dup
        //   109: aload_3
        //   110: invokespecial 420	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
        //   113: astore 4
        //   115: new 444	java/io/ObjectOutputStream
        //   118: dup
        //   119: aload 4
        //   121: invokespecial 445	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
        //   124: astore 5
        //   126: aload 4
        //   128: astore 6
        //   130: aload 5
        //   132: astore 7
        //   134: aload 4
        //   136: astore 8
        //   138: aload 5
        //   140: astore 9
        //   142: aload 5
        //   144: aload_0
        //   145: invokevirtual 448	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
        //   148: aload 5
        //   150: invokevirtual 449	java/io/ObjectOutputStream:close	()V
        //   153: aload 4
        //   155: invokevirtual 422	java/util/zip/GZIPOutputStream:close	()V
        //   158: aload_3
        //   159: invokevirtual 292	java/io/OutputStream:close	()V
        //   162: aload_2
        //   163: invokevirtual 215	java/io/ByteArrayOutputStream:close	()V
        //   166: new 183	java/lang/String
        //   169: dup
        //   170: aload_2
        //   171: invokevirtual 212	java/io/ByteArrayOutputStream:toByteArray	()[B
        //   174: ldc 41
        //   176: invokespecial 406	java/lang/String:<init>	([BLjava/lang/String;)V
        //   179: astore_0
        //   180: aload_0
        //   181: areturn
        //   182: aload 19
        //   184: astore 6
        //   186: aload 18
        //   188: astore 7
        //   190: aload 17
        //   192: astore 8
        //   194: aload 15
        //   196: astore 9
        //   198: new 444	java/io/ObjectOutputStream
        //   201: dup
        //   202: aload_3
        //   203: invokespecial 445	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
        //   206: astore 5
        //   208: aload 12
        //   210: astore 4
        //   212: goto -86 -> 126
        //   215: astore_3
        //   216: aload 10
        //   218: astore 8
        //   220: aload 14
        //   222: astore_0
        //   223: aload 9
        //   225: astore_2
        //   226: aload_2
        //   227: astore 5
        //   229: aload_0
        //   230: astore 6
        //   232: aload 4
        //   234: astore 7
        //   236: aload_3
        //   237: athrow
        //   238: astore 4
        //   240: aload 7
        //   242: astore_3
        //   243: aload 6
        //   245: astore_2
        //   246: aload 5
        //   248: astore_0
        //   249: aload 8
        //   251: invokevirtual 449	java/io/ObjectOutputStream:close	()V
        //   254: aload_3
        //   255: invokevirtual 422	java/util/zip/GZIPOutputStream:close	()V
        //   258: aload_0
        //   259: invokevirtual 292	java/io/OutputStream:close	()V
        //   262: aload_2
        //   263: invokevirtual 215	java/io/ByteArrayOutputStream:close	()V
        //   266: aload 4
        //   268: athrow
        //   269: astore_0
        //   270: new 183	java/lang/String
        //   273: dup
        //   274: aload_2
        //   275: invokevirtual 212	java/io/ByteArrayOutputStream:toByteArray	()[B
        //   278: invokespecial 407	java/lang/String:<init>	([B)V
        //   281: areturn
        //   282: astore 5
        //   284: goto -30 -> 254
        //   287: astore_3
        //   288: goto -30 -> 258
        //   291: astore_0
        //   292: goto -30 -> 262
        //   295: astore_0
        //   296: goto -30 -> 266
        //   299: astore_0
        //   300: goto -147 -> 153
        //   303: astore_0
        //   304: goto -146 -> 158
        //   307: astore_0
        //   308: goto -146 -> 162
        //   311: astore_0
        //   312: goto -146 -> 166
        //   315: astore 4
        //   317: aload 16
        //   319: astore_0
        //   320: aload 13
        //   322: astore_3
        //   323: aload 11
        //   325: astore 8
        //   327: goto -78 -> 249
        //   330: astore 4
        //   332: aload_3
        //   333: astore_0
        //   334: aload 6
        //   336: astore_3
        //   337: aload 7
        //   339: astore 8
        //   341: goto -92 -> 249
        //   344: astore 5
        //   346: aload_3
        //   347: astore_0
        //   348: aload 4
        //   350: astore_3
        //   351: aload 11
        //   353: astore 8
        //   355: aload 5
        //   357: astore 4
        //   359: goto -110 -> 249
        //   362: astore_3
        //   363: aload_2
        //   364: astore_0
        //   365: aload 9
        //   367: astore_2
        //   368: aload 10
        //   370: astore 8
        //   372: goto -146 -> 226
        //   375: astore 4
        //   377: aload_2
        //   378: astore_0
        //   379: aload_3
        //   380: astore_2
        //   381: aload 4
        //   383: astore_3
        //   384: aload 8
        //   386: astore 4
        //   388: aload 9
        //   390: astore 8
        //   392: goto -166 -> 226
        //   395: astore 5
        //   397: aload_2
        //   398: astore_0
        //   399: aload_3
        //   400: astore_2
        //   401: aload 5
        //   403: astore_3
        //   404: aload 10
        //   406: astore 8
        //   408: goto -182 -> 226
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	411	0	paramSerializable	Serializable
        //   0	411	1	paramInt	int
        //   70	331	2	localObject1	Object
        //   82	121	3	localOutputStream	OutputStream
        //   215	22	3	localIOException1	IOException
        //   242	13	3	localObject2	Object
        //   287	1	3	localException1	Exception
        //   322	29	3	localObject3	Object
        //   362	18	3	localIOException2	IOException
        //   383	21	3	localIOException3	IOException
        //   31	202	4	localObject4	Object
        //   238	29	4	localObject5	Object
        //   315	1	4	localObject6	Object
        //   330	19	4	localObject7	Object
        //   357	1	4	localObject8	Object
        //   375	7	4	localIOException4	IOException
        //   386	1	4	localObject9	Object
        //   22	225	5	localObject10	Object
        //   282	1	5	localException2	Exception
        //   344	12	5	localObject11	Object
        //   395	7	5	localIOException5	IOException
        //   16	319	6	localObject12	Object
        //   34	304	7	localObject13	Object
        //   49	358	8	localObject14	Object
        //   28	361	9	localObject15	Object
        //   61	344	10	localObject16	Object
        //   52	300	11	localObject17	Object
        //   46	163	12	localObject18	Object
        //   37	284	13	localObject19	Object
        //   19	202	14	localObject20	Object
        //   58	137	15	localObject21	Object
        //   25	293	16	localObject22	Object
        //   43	148	17	localObject23	Object
        //   55	132	18	localObject24	Object
        //   40	143	19	localObject25	Object
        // Exception table:
        //   from	to	target	type
        //   63	71	215	java/io/IOException
        //   63	71	238	finally
        //   236	238	238	finally
        //   166	180	269	java/io/UnsupportedEncodingException
        //   249	254	282	java/lang/Exception
        //   254	258	287	java/lang/Exception
        //   258	262	291	java/lang/Exception
        //   262	266	295	java/lang/Exception
        //   148	153	299	java/lang/Exception
        //   153	158	303	java/lang/Exception
        //   158	162	307	java/lang/Exception
        //   162	166	311	java/lang/Exception
        //   71	83	315	finally
        //   105	115	330	finally
        //   142	148	330	finally
        //   198	208	330	finally
        //   115	126	344	finally
        //   71	83	362	java/io/IOException
        //   105	115	375	java/io/IOException
        //   142	148	375	java/io/IOException
        //   198	208	375	java/io/IOException
        //   115	126	395	java/io/IOException
    }

    /* Error */
    public static void encodeToFile(byte[] paramArrayOfByte, String paramString)
            throws IOException
    {
        // Byte code:
        //   0: aload_0
        //   1: ifnonnull +14 -> 15
        //   4: new 176	java/lang/NullPointerException
        //   7: dup
        //   8: ldc_w 452
        //   11: invokespecial 181	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
        //   14: athrow
        //   15: aconst_null
        //   16: astore_2
        //   17: aconst_null
        //   18: astore_3
        //   19: new 11	cn/com/ukey/Base64$OutputStream
        //   22: dup
        //   23: new 283	java/io/FileOutputStream
        //   26: dup
        //   27: aload_1
        //   28: invokespecial 284	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
        //   31: iconst_1
        //   32: invokespecial 330	cn/com/ukey/Base64$OutputStream:<init>	(Ljava/io/OutputStream;I)V
        //   35: astore_1
        //   36: aload_1
        //   37: aload_0
        //   38: invokevirtual 331	cn/com/ukey/Base64$OutputStream:write	([B)V
        //   41: aload_1
        //   42: invokevirtual 332	cn/com/ukey/Base64$OutputStream:close	()V
        //   45: return
        //   46: astore_0
        //   47: aload_3
        //   48: astore_2
        //   49: aload_0
        //   50: athrow
        //   51: astore_0
        //   52: aload_2
        //   53: invokevirtual 332	cn/com/ukey/Base64$OutputStream:close	()V
        //   56: aload_0
        //   57: athrow
        //   58: astore_1
        //   59: goto -3 -> 56
        //   62: astore_0
        //   63: return
        //   64: astore_0
        //   65: aload_1
        //   66: astore_2
        //   67: goto -15 -> 52
        //   70: astore_0
        //   71: aload_1
        //   72: astore_2
        //   73: goto -24 -> 49
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	76	0	paramArrayOfByte	byte[]
        //   0	76	1	paramString	String
        //   16	57	2	localObject1	Object
        //   18	30	3	localObject2	Object
        // Exception table:
        //   from	to	target	type
        //   19	36	46	java/io/IOException
        //   19	36	51	finally
        //   49	51	51	finally
        //   52	56	58	java/lang/Exception
        //   41	45	62	java/lang/Exception
        //   36	41	64	finally
        //   36	41	70	java/io/IOException
    }

    private static final byte[] getAlphabet(int paramInt)
    {
        if ((paramInt & 0x10) == 16) {
            return _URL_SAFE_ALPHABET;
        }
        if ((paramInt & 0x20) == 32) {
            return _ORDERED_ALPHABET;
        }
        return _STANDARD_ALPHABET;
    }

    private static final byte[] getDecodabet(int paramInt)
    {
        if ((paramInt & 0x10) == 16) {
            return _URL_SAFE_DECODABET;
        }
        if ((paramInt & 0x20) == 32) {
            return _ORDERED_DECODABET;
        }
        return _STANDARD_DECODABET;
    }

    public static class InputStream
            extends FilterInputStream
    {
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        public InputStream(InputStream paramInputStream)
        {
            this(paramInputStream, 0);
        }

        public InputStream(InputStream paramInputStream, int paramInt)
        {
            super();
            this.options = paramInt;
            boolean bool1;
            if ((paramInt & 0x8) > 0)
            {
                bool1 = true;
                this.breakLines = bool1;
                if ((paramInt & 0x1) <= 0) {
                    break label94;
                }
                bool1 = bool2;
                label39:
                this.encode = bool1;
                if (!this.encode) {
                    break label100;
                }
            }
            label94:
            label100:
            for (int i = 4;; i = 3)
            {
                this.bufferLength = i;
                this.buffer = new byte[this.bufferLength];
                this.position = -1;
                this.lineLength = 0;
                this.decodabet = Base64.getDecodabet(paramInt);
                return;
                bool1 = false;
                break;
                bool1 = false;
                break label39;
            }
        }

        public int read()
                throws IOException
        {
            int j;
            if (this.position < 0)
            {
                if (this.encode)
                {
                    arrayOfByte = new byte[3];
                    j = 0;
                    i = 0;
                }
            }
            else
            {
                for (;;)
                {
                    if (i >= 3) {}
                    int k;
                    do
                    {
                        if (j <= 0) {
                            break;
                        }
                        Base64.encode3to4(arrayOfByte, 0, j, this.buffer, 0, this.options);
                        this.position = 0;
                        this.numSigBytes = 4;
                        if (this.position < 0) {
                            break label299;
                        }
                        if (this.position < this.numSigBytes) {
                            break label213;
                        }
                        return -1;
                        k = this.in.read();
                    } while (k < 0);
                    arrayOfByte[i] = ((byte)k);
                    j += 1;
                    i += 1;
                }
                return -1;
            }
            byte[] arrayOfByte = new byte[4];
            int i = 0;
            for (;;)
            {
                if (i >= 4) {}
                do
                {
                    if (i != 4) {
                        break label197;
                    }
                    this.numSigBytes = Base64.decode4to3(arrayOfByte, 0, this.buffer, 0, this.options);
                    this.position = 0;
                    break;
                    do
                    {
                        j = this.in.read();
                    } while ((j >= 0) && (this.decodabet[(j & 0x7F)] <= -5));
                } while (j < 0);
                arrayOfByte[i] = ((byte)j);
                i += 1;
            }
            label197:
            if (i == 0) {
                return -1;
            }
            throw new IOException("Improperly padded Base64 input.");
            label213:
            if ((this.encode) && (this.breakLines) && (this.lineLength >= 76))
            {
                this.lineLength = 0;
                return 10;
            }
            this.lineLength += 1;
            arrayOfByte = this.buffer;
            i = this.position;
            this.position = (i + 1);
            i = arrayOfByte[i];
            if (this.position >= this.bufferLength) {
                this.position = -1;
            }
            return i & 0xFF;
            label299:
            throw new IOException("Error in Base64 code reading stream.");
        }

        public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
                throws IOException
        {
            int i = 0;
            if (i >= paramInt2) {}
            do
            {
                return i;
                int j = read();
                if (j >= 0)
                {
                    paramArrayOfByte[(paramInt1 + i)] = ((byte)j);
                    i += 1;
                    break;
                }
            } while (i != 0);
            return -1;
        }
    }

    public static class OutputStream
            extends FilterOutputStream
    {
        private byte[] b4;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(OutputStream paramOutputStream)
        {
            this(paramOutputStream, 1);
        }

        public OutputStream(OutputStream paramOutputStream, int paramInt)
        {
            super();
            boolean bool1;
            if ((paramInt & 0x8) != 0)
            {
                bool1 = true;
                this.breakLines = bool1;
                if ((paramInt & 0x1) == 0) {
                    break label106;
                }
                bool1 = bool2;
                label34:
                this.encode = bool1;
                if (!this.encode) {
                    break label112;
                }
            }
            label106:
            label112:
            for (int i = 3;; i = 4)
            {
                this.bufferLength = i;
                this.buffer = new byte[this.bufferLength];
                this.position = 0;
                this.lineLength = 0;
                this.suspendEncoding = false;
                this.b4 = new byte[4];
                this.options = paramInt;
                this.decodabet = Base64.getDecodabet(paramInt);
                return;
                bool1 = false;
                break;
                bool1 = false;
                break label34;
            }
        }

        public void close()
                throws IOException
        {
            flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void flushBase64()
                throws IOException
        {
            if (this.position > 0)
            {
                if (this.encode)
                {
                    this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
                    this.position = 0;
                }
            }
            else {
                return;
            }
            throw new IOException("Base64 input not properly padded.");
        }

        public void resumeEncoding()
        {
            this.suspendEncoding = false;
        }

        public void suspendEncoding()
                throws IOException
        {
            flushBase64();
            this.suspendEncoding = true;
        }

        public void write(int paramInt)
                throws IOException
        {
            if (this.suspendEncoding) {
                this.out.write(paramInt);
            }
            do
            {
                do
                {
                    do
                    {
                        return;
                        if (!this.encode) {
                            break;
                        }
                        arrayOfByte = this.buffer;
                        i = this.position;
                        this.position = (i + 1);
                        arrayOfByte[i] = ((byte)paramInt);
                    } while (this.position < this.bufferLength);
                    this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
                    this.lineLength += 4;
                    if ((this.breakLines) && (this.lineLength >= 76))
                    {
                        this.out.write(10);
                        this.lineLength = 0;
                    }
                    this.position = 0;
                    return;
                    if (this.decodabet[(paramInt & 0x7F)] <= -5) {
                        break;
                    }
                    byte[] arrayOfByte = this.buffer;
                    int i = this.position;
                    this.position = (i + 1);
                    arrayOfByte[i] = ((byte)paramInt);
                } while (this.position < this.bufferLength);
                paramInt = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
                this.out.write(this.b4, 0, paramInt);
                this.position = 0;
                return;
            } while (this.decodabet[(paramInt & 0x7F)] == -5);
            throw new IOException("Invalid character in Base64 data.");
        }

        public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
                throws IOException
        {
            if (this.suspendEncoding) {
                this.out.write(paramArrayOfByte, paramInt1, paramInt2);
            }
            for (;;)
            {
                return;
                int i = 0;
                while (i < paramInt2)
                {
                    write(paramArrayOfByte[(paramInt1 + i)]);
                    i += 1;
                }
            }
        }
    }
}
