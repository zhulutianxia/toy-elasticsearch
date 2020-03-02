package com.toy.search.constant;

/**
 * 公共常量
 */
public interface Constants {

    String CDN_IMG_URL_ONLINE = "https://ts.zlimg.com";

    Long BJ_DEPOT_ID = 100101L;

    /**
     * 索引名称
     */
    class INDEX_NAME {
        public static final String TOY_INDEX = "toyindex";
        public static final String KEYWORDS_INDEX = "keywordsindex";
    }

    class TOY_INDEX_TYPE_NAME {
        public static final String TOY = "toy";
        public static final String KEYWORDS = "keywords";
    }

    /**
     * 散租场景来源
     */
    int NORMAL_SCENE = 101;
    /**
     * 会员场景来源
     */
    int MEMBER_SCENE = 201;

    int MAX_AGE_LIMIT = 999;
    String HAN_ZI_SUI = "岁";
    String HAN_ZI_GE_YUE = "个月";
    String HAN_ZI_BAN = "半";
    String HAN_ZI_YI_SHANG = "以上";
    String RANGE_SEPARATOR = "~";

    long INVALID_USER_ID = -1;
    long MIN_USER_ID = 0;
    String ENCODING = "UTF-8";
    String TOKEN = "token";
    String COOKIE_NAME = "ZL_UEC";
    String COOKIE_SPLIT = ";";
    byte[] COOKIE_KEY = "zhulutianxia@yangcong365.com".getBytes();

    String RSA_PUBLIC_KEY =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0bxeHab9OWUyN3FcgxK/\n" +
                    "/60DIp5Kj56nDZ8MW/QOJAXh+ISkCUFR7QvU9eIDCoEcneCA8nXgfdz1QNGUHncY\n" +
                    "VnFxK6LDLwNCQSUJF3VOVuh+VGv6wnfX/i6+Flt92NIH0SHhBHUeJs+rCp6my2jX\n" +
                    "il/kk8+G/7C7aQtaTNUgC8orosiVbi+yWY2xKsDVkviXBOvDcGLlhGoKU1gzEukZ\n" +
                    "Lv2MERag3bC1elQgypAZdljF5gV1YKfVBR2sgT4zc/4wvZ9zxnO8HT2ijUer9oJf\n" +
                    "Z1x9Fm043iuzAT+EPLBgcQbOGx1LuB5haOYmnnagSqnIczrVNHO4ot7rxvqTKPG5\n" +
                    "eQIDAQAB";

    String RSA_PRIVATE_KEY =
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDRvF4dpv05ZTI3\n" +
                    "cVyDEr//rQMinkqPnqcNnwxb9A4kBeH4hKQJQVHtC9T14gMKgRyd4IDydeB93PVA\n" +
                    "0ZQedxhWcXErosMvA0JBJQkXdU5W6H5Ua/rCd9f+Lr4WW33Y0gfRIeEEdR4mz6sK\n" +
                    "nqbLaNeKX+STz4b/sLtpC1pM1SALyiuiyJVuL7JZjbEqwNWS+JcE68NwYuWEagpT\n" +
                    "WDMS6Rku/YwRFqDdsLV6VCDKkBl2WMXmBXVgp9UFHayBPjNz/jC9n3PGc7wdPaKN\n" +
                    "R6v2gl9nXH0WbTjeK7MBP4Q8sGBxBs4bHUu4HmFo5iaedqBKqchzOtU0c7ii3uvG\n" +
                    "+pMo8bl5AgMBAAECggEBAKAaVw3zp2Hk6gyAsMotyfr2q2oFw40W4xb2zoxzJUhC\n" +
                    "ZVk8h6u4/T0ixxJb0U53eMzb0BNGAHST83PjiFopN57lQiq5OJ3usujfyKzrkz2s\n" +
                    "hKMVOnqw2WTjcZ/Hf62/xs/SON8aOEYQSNT2fwTPp5mt0eMzjC4rTyTE0c3M84WL\n" +
                    "ETeH5yBx+0/aKaQ1rGEoOJAde58GcCxWqDo+KtBRo9Wg7wCJ6L7UKM4UPT2Xvnmd\n" +
                    "E7mg7z8hH5Af2rbicJYQWumsdoH+uPnSArwQcFNu3lUn2Xs7WUiwjJGgeuQfa6md\n" +
                    "kOGvxK5QHWCZF4+CSEeD60UIAKa2ehh/4egQJxFHXcECgYEA58Vc/hd7nuXcuRUD\n" +
                    "jcuS7AGxPh5MUem7/4nVVDnzB0VhZAXRxXhv9l26SJLU2999s5im4iWA1wUFB06f\n" +
                    "uUELMmrNJpg1xxnDALAiwaIgpUu59++s7CKBlKlhoc5PeheopEUrl0i3PxMqL7N3\n" +
                    "jB28bvXQmNwKrWlWNFQ4a585no0CgYEA56lNOQST9kubOU5WoK4DWUzs4o1gAFbf\n" +
                    "om8W5MAMdjZNOfzFyCF/V1hiW53IUdNiLW3+Ly423KvUjWt0S11LNTWyrToWRhtu\n" +
                    "sj+fYGnLw/QEXeB/uAdowYxXuyYiGnom8m5j9/NB0lHaYUT3dzFt4LNAPwNJQIex\n" +
                    "ekSh3ArssZ0CgYAaQ/iU5w5gZIZOBz6e3BorWsm+U2qswcNHY1Rktih/g3JVBn/O\n" +
                    "VCbEEb0hqlorDLnPF5aZ/EwMkgoO2O8q7F8beJUFMayugpHhyxQ1Q21Vri5GFchP\n" +
                    "/l0W/tcMojfJi1LWQux8uYXixQY736fFnj0TihH4sL8ZBd36Ei1OrP23pQKBgQDX\n" +
                    "iuWn5IOKyOP/GM1vKRlCkFljnWiySCJBrxzc73do0bNe/bpEMC6cZUb2uUK2J3iU\n" +
                    "jl/PJC+1eEcG4fDNYltkgbbiiEZLKqD/RkUS3rzRcIxTkFslLfehBeRm498dRYRR\n" +
                    "Ura+793RQ4ltsU6S3zG9vOpM9tDEMrzF52+2HZAG3QKBgEH9V6t8wXZXWF+u/dwF\n" +
                    "23lXLhqx/nS0k5hBKzdIbmVFRjw7SXjmGurJuekuhr8vmLtG8YcfJv9VNJRleZKP\n" +
                    "9FGlQ8ziX2p5o4Jku4rDwnYpAYXpvW4Csy3X4fZ0DgUDbo1rJxgCl12o0S0+xiQr\n" +
                    "5E28kv1ua/OX/WtGc2l+nuIx";
}
