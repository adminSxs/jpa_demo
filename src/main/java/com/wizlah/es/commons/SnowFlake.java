package com.wizlah.es.commons;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

@Slf4j
public class SnowFlake {

  private static final Object lock = new Object();
  private static volatile SnowFlake instance;
  // todo 反解析时间，10位，sequence
  //   id format  =>
  //   timestamp |datacenter | sequence
  //   41        |10         |  12
  private final long totalBits = 64L;
  private final long signBits = 1L;
  private final long timestampBit = 41L;
  private final long sequenceBits = 12L;
  private final long machineIdBits = 10L;
  private final long MaxMachineId = -1L ^ (-1L << machineIdBits);
  private final long machineIdShift = sequenceBits;
  private final long timestampLeftShift = sequenceBits + machineIdBits;
  private final long twepoch = 1288834974657L;
  private final long machineId;
  private final long sequenceMax = 4096; // 2**12
  private volatile long lastTimestamp = -1L;
  private volatile long sequence = 0L;

  private SnowFlake() {
    machineId = getMachineId();
    if (machineId > MaxMachineId || machineId < 0) {
      try {
        throw new Exception("machineId > MaxMachineId");
      } catch (Exception e) {
        e.printStackTrace();
        log.error("SnowFlake() error");
      }
    }
  }

  public static SnowFlake getInstance() {
    SnowFlake generator = instance;
    if (instance == null) {
      synchronized (lock) {
        generator = instance;
        if (generator == null) {
          try {
            generator = new SnowFlake();
          } catch (Exception e) {
            e.printStackTrace();
            log.error("getInstance error");
          }
          instance = generator;
        }
      }
    }
    return generator;
  }

  public synchronized Long generateLongId() {
    long timestamp = System.currentTimeMillis();
    if (timestamp < lastTimestamp) {
      try {
        throw new Exception(
            "Clock moved backwards.  Refusing to generate id for "
                + (lastTimestamp - timestamp)
                + " milliseconds.");
      } catch (Exception e) {
        e.printStackTrace();
        log.error("generateLongId error");
      }
    }
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) % sequenceMax;
      if (sequence == 0) {
        timestamp = tillNextMillis(lastTimestamp);
      }
    } else {
      sequence = 0;
    }
    lastTimestamp = timestamp;
    Long id =
        ((timestamp - twepoch) << timestampLeftShift) | (machineId << machineIdShift) | sequence;
    return id;
  }

  public synchronized String generateStringId() {
    return String.valueOf(generateLongId());
  }

  protected long tillNextMillis(long lastTimestamp) {
    long timestamp = System.currentTimeMillis();
    while (timestamp <= lastTimestamp) {
      timestamp = System.currentTimeMillis();
    }
    return timestamp;
  }

  public String parseSnowFlake(long snowFlakeId) {
    long sequence = (snowFlakeId << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
    long machineId = (snowFlakeId << (timestampBit + signBits)) >>> (totalBits - machineIdBits);
    long timestamp = snowFlakeId >>> (machineIdBits + sequenceBits);
    String time = DateTimeUtils.timeStampToDate(twepoch + timestamp);

    return String.format(
        "{\"snowFlakeId\":\"%d\",\"timestamp\":\"%s\",\"machineId\":\"%d\",\"sequence\":\"%d\"}",
        snowFlakeId, time, machineId, sequence);
  }

  protected long getMachineId() {

    try {
      InetAddress ip = InetAddress.getLocalHost();
      NetworkInterface network = NetworkInterface.getByInetAddress(ip);
      long id;
      if (network == null) {
        id = 1;
      } else {
        byte[] mac = network.getHardwareAddress();
        id =
            ((0x000000FF & (long) mac[mac.length - 1])
                    | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8)))
                >> 6;
      }
      return id;
    } catch (SocketException e) {
      log.error("SocketException for get mac address");

    } catch (UnknownHostException e) {
      log.error("UnknownHostException for get mac address");
    }
    return 0;
  }
}
