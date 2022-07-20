package enclave.encare.encare.config;

public class RegexConfig {
    public static String name = "[^0-9\\.\\,\\?\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\-\\:\\;\'\"]{2,40}";
    public static String phone = "[0-9]{8,10}";
    public static String otp = "[0-9]{6}";
    public static String day = "^(0[1-9]|1[0-9]|2[0-9]|3[0-1])/(0[0-9]|1[0-2])/(19|20)([0-9]{2})";
}
