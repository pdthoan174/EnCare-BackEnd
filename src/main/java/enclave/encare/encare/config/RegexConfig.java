package enclave.encare.encare.config;

public class RegexConfig {
    public static String name = "[^0-9\\.\\,\\?\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\-\\:\\;\'\"]{2,40}";
    public static String phone = "[0-9]{8,10}";
    public static String otp = "[0-9]{6}";
}
