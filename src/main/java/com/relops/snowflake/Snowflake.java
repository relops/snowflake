package com.relops.snowflake;

public class Snowflake {

  public static final int NODE_SHIFT = 10;
  public static final int SEQ_SHIFT = 12;

  public static final short MAX_NODE = 1024;
  public static final short MAX_SEQUENCE = 4096;

  private short sequence;
  private long referenceTime;

  private int node;

  public Snowflake(int node) {
    if (node < 0 || node > MAX_NODE) {
      throw new IllegalArgumentException(String.format("node must be between %s and %s", 0, MAX_NODE));
    }
    this.node = node;
  }

  public long next() {

    long currentTime = System.currentTimeMillis();
    long counter;

    synchronized(this) {

      if (currentTime < referenceTime) {
        throw new RuntimeException(String.format("Last referenceTime %s is after reference time %s", referenceTime, currentTime));
      } else if (currentTime > referenceTime) {
        this.sequence = 0;
      } else {
        if (this.sequence < Snowflake.MAX_SEQUENCE) {
          this.sequence++;
        } else {
          throw new RuntimeException("Sequence exhausted at " + this.sequence);
        }
      }
      counter = this.sequence;
      referenceTime = currentTime;
    }

    return currentTime << NODE_SHIFT << SEQ_SHIFT | node << SEQ_SHIFT | counter;
  }

}
