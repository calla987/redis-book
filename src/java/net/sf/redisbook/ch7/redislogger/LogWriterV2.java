package net.sf.redisbook.ch7.redislogger;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.redisbook.JedisHelper;
import redis.clients.jedis.Jedis;

public class LogWriterV2 {
	private static final String KEY_WAS_LOG = "was:log:list";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS ");

	JedisHelper helper;

	/**
	 * 레디스에 로그를 기록하기 위한 Logger의 생성자
	 * @param helper 제디스 도우미 객체
	 */
	public LogWriterV2(JedisHelper helper) {
		this.helper = helper;
	}

	/**
	 * 레디스에 로그를 기록하는 메서드. 
	 * @param log 저장할 로그문자열
	 * @return 명령이 수행된 이후의 저장된 리스트의 요소 개수
	 */
	public Long log(String log) {
		Jedis jedis = this.helper.getConnection();
		Long rtn = jedis.lpush(KEY_WAS_LOG, sdf.format(new Date()) + log + "\n");

		this.helper.returnResource(jedis);

		return rtn;
	}
}