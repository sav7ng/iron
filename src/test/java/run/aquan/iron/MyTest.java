package run.aquan.iron;

import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import run.aquan.iron.security.constants.SecurityConstant;
import run.aquan.iron.security.utils.JwtTokenUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class MyTest
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/13 10:13
 * @Version 1.0
 **/
@Slf4j
public class MyTest {

    @Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", "小白1");
        map.put("2", "小白2");
        map.put("3", "小白3");
        map.put("4", "小白4");
        map.put("5", "小白5");
        System.err.println("map.keySet() : ");
        for (String mp : map.keySet()) {
            log.info("key = " + mp + ", value = " + map.get(mp));
        }
    }

    @Test
    public void testOne() {
        List<String> list = Lists.newArrayList();
        log.info(list.toString());
    }

    @Test
    public void testMD5() {
        String str = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md5.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            log.warn(new BigInteger(1, md5.digest()).toString(16));
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }

    }

    @Test
    public void hmacSha256() throws Exception {
        String data = "";
        String key = "";
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        log.warn(sb.toString().toUpperCase());
    }

    @Test
    public void bcryptPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "anDi_T_X_D_Y";
        String encode = encoder.encode(password);
        System.out.println("第一次加密" + encode);
        String encode1 = encoder.encode(password);
        System.out.println("第一次加密" + encode1);
        System.out.println("第一次加密密文是否验证通过: " + encoder.matches(password, encode));
        System.out.println("第二次加密密文是否验证通过: " + encoder.matches(password, encode1));
    }

    @Test
    public void tokenGetBoby() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstant.JWT_SECRET_KEY);
        SecretKey secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2wiOiJST0xFX1VTRVIiLCJpc3MiOiJBcXVhbiIsImlhdCI6MTU3NzI2OTM4OCwic3ViIjoic3RyaW5nIiwiZXhwIjoxNTc3ODc0MTg4fQ.Gf1sfrU0qrOJWIHvl57kutq8CPlujPnKkQp-yI9mmDM";
        Claims body = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        log.warn(body.getExpiration().toString());
    }


}
