package me.ledis.constant;

public final class ResponseMessage {
    public static final String OK_MESSAGE = "OK";
    public static final String EMPTY_LIST_MESSAGE = "empty list";
    public static final String EMPTY_SET_MESSAGE = "empty set";
    public static final String STRING_SET_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is SET <key> <value>";
    public static final String STRING_GET_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is GET <key>";
    public static final String LLEN_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is LLEN <key>";
    public static final String RPUSH_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is RPUSH <key> <value1> <value2>...";
    public static final String LPOP_SYNTAX_ERROR_MESSAGE = "ERROR: correct syntax is LPOP <key>";
    public static final String RPOP_SYNTAX_ERROR_MESSAGE = "ERROR: correct syntax is RPOP <key>";
    public static final String LRANGE_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is LRANGE <key> <start> <stop>";
    public static final String LRANGE_NOT_INTEGER_ERROR_MESSAGE = "ERROR: start, stop must be an integer";
    public static final String SADD_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is SADD <key> <value1> <value2>...";
    public static final String SCARD_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is SCARD <key>";
    public static final String SMEMBERS_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is SMEMBERS <key>";
    public static final String SINTER_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is SINTER <key>";
    public static final String SREM_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is SREM <key> <value1> <value2>...";
    public static final String DEL_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is DEL <key>";
    public static final String KEYS_SYNTAX_ERROR_MESSAGE = "ERROR: the correct syntax is KEYS";
    public static final String KEY_IS_NOT_LIST_ERROR_MESSAGE = "ERROR: The given key is not a list";
    public static final String KEY_IS_NOT_SET_ERROR_MESSAGE = "ERROR: The given key is not a set";
    public static final String KEY_IS_NOT_EXISTED_MESSAGE = "Key is not existed";
    private ResponseMessage() {
    }
}
